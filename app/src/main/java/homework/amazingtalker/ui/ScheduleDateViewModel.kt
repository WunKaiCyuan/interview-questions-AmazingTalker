package homework.amazingtalker.ui

import homework.amazingtalker.data.repository.ScheduleRepository
import homework.amazingtalker.domain.ScheduleTimeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*
import javax.inject.Inject

class ScheduleDateViewModel @Inject constructor(
    private val scheduleTimeUseCase: ScheduleTimeUseCase,
    private val scheduleRepository: ScheduleRepository,
) {
    private val _uiState = MutableStateFlow(ScheduleDateUiState())
    val uiState = _uiState.asStateFlow()

    private var diffDate = 0

    fun initialize(diffDate: Int) {
        this.diffDate = diffDate
    }

    fun refreshScheduleTime() {

        val schedule = scheduleRepository.getSchedule()
        val startAt = scheduleRepository.getStartAt()

        val fromCalendar = Calendar.getInstance()
        val toCalendar = Calendar.getInstance()
        if (startAt != null) {
            fromCalendar.time = startAt
            toCalendar.time = startAt
        }
        fromCalendar.add(Calendar.DATE, diffDate)

        val moringsFromCalendar = fromCalendar.clone() as Calendar
        val moringsToCalendar = fromCalendar.clone() as Calendar
        val eveningFromCalendar = fromCalendar.clone() as Calendar
        val eveningToCalendar = fromCalendar.clone() as Calendar
        val nightFromCalendar = fromCalendar.clone() as Calendar
        val nightToCalendar = fromCalendar.clone() as Calendar
        setTime(moringsFromCalendar, 6)
        setTime(moringsToCalendar, 12)
        setTime(eveningFromCalendar, 12)
        setTime(eveningToCalendar, 18)
        setTime(nightFromCalendar, 18)
        setTime(nightToCalendar, 24)

        val mornings =
            scheduleTimeUseCase.getTimeAreaSchedule(
                moringsFromCalendar.time,
                moringsToCalendar.time,
                schedule.available,
                schedule.booked
            )

        val evening =
            scheduleTimeUseCase.getTimeAreaSchedule(
                eveningFromCalendar.time,
                eveningToCalendar.time,
                schedule.available,
                schedule.booked
            )

        val night =
            scheduleTimeUseCase.getTimeAreaSchedule(
                nightFromCalendar.time,
                nightToCalendar.time,
                schedule.available,
                schedule.booked
            )

        _uiState.value = _uiState.value.copy(mornings = mornings, evening = evening, night = night)
    }

    private fun setTime(calendar: Calendar, hours: Int) {
        calendar.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            add(Calendar.HOUR_OF_DAY, hours)
        }
    }
}