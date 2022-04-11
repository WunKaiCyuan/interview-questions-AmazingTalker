package homework.amazingtalker.ui

import androidx.lifecycle.ViewModel
import homework.amazingtalker.data.repository.ScheduleRepository
import homework.amazingtalker.domain.WeekDateUseCase
import homework.amazingtalker.event.ScheduleChangeEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.greenrobot.eventbus.EventBus
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ScheduleViewModel @Inject constructor(
    private val weekDateUseCase: WeekDateUseCase,
    private val scheduleRepository: ScheduleRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScheduleUiState())
    val uiState = _uiState.asStateFlow()

    private lateinit var teacherName: String
    private lateinit var thisWeekFirstDate: Date
    private lateinit var thisWeekLastDate: Date

    fun initialize(teacherName: String) {
        this.teacherName = teacherName
        val currentDate = weekDateUseCase.getThisWeekFirstDate(Calendar.getInstance().time)
        changeWeekDate(currentDate)
    }

    fun fetchThisWeekSchedule() {
        val startAt = thisWeekFirstDate
        changeWeekDate(startAt)
        fetchSchedule(startAt)
    }

    fun fetchNextWeekSchedule() {
        val startAt = weekDateUseCase.getNextWeekFirstDate(thisWeekFirstDate)
        changeWeekDate(startAt)
        fetchSchedule(startAt)
    }

    fun fetchPreviousWeekSchedule() {
        val startAt = weekDateUseCase.getPreviousWeekFirstDate(thisWeekFirstDate)
        changeWeekDate(startAt)
        fetchSchedule(startAt)
    }

    private fun fetchSchedule(startAt: Date) {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val schedule = scheduleRepository.fetchSchedule(teacherName, sdf.format(startAt))
        scheduleRepository.setStartAt(startAt)
        scheduleRepository.setSchedule(schedule)
        EventBus.getDefault().post(ScheduleChangeEvent(startAt, schedule))
    }

    private fun changeWeekDate(currentWeekFirstDate: Date) {
        thisWeekFirstDate = weekDateUseCase.getThisWeekFirstDate(currentWeekFirstDate)
        thisWeekLastDate = weekDateUseCase.getThisWeekLastDate(currentWeekFirstDate)
        val sdfForStartDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val sdfForEndDate = SimpleDateFormat("MM-dd", Locale.getDefault())
        val weekAreaText =
            "${sdfForStartDate.format(thisWeekFirstDate)} - ${sdfForEndDate.format(thisWeekLastDate)}"

        val weekDates = ArrayList<String>()
        val sdfForWeekDate = SimpleDateFormat("E, MMM d", Locale.getDefault())
        for (i in 0 until 7) {
            val calendar = Calendar.getInstance()
            calendar.time = thisWeekFirstDate
            calendar.add(Calendar.DATE, i)
            val dateText = sdfForWeekDate.format(calendar.time)
            weekDates.add(dateText)
        }

        val currentDate = Calendar.getInstance().time
        _uiState.value = _uiState.value.copy(
            canToPreviousWeek = thisWeekFirstDate > currentDate,
            canToNextWeek = true,
            weekAreaText = weekAreaText,
            weekDates = weekDates
        )
    }
}

