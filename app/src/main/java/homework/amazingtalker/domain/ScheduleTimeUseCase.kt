package homework.amazingtalker.domain

import homework.amazingtalker.data.model.ScheduleApiModel
import homework.amazingtalker.ui.ScheduleDateUiState
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ScheduleTimeUseCase @Inject constructor() {
    fun getTimeAreaSchedule(
        fromDate: Date,
        toDate: Date,
        available: List<ScheduleApiModel.TimeScope>,
        booked: List<ScheduleApiModel.TimeScope>,
    ): List<ScheduleDateUiState.ScheduleTime> {
        val result = ArrayList<ScheduleDateUiState.ScheduleTime>()
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        val availableSchedultTime = available.filter {
            val startDate = sdf.parse(it.start)!!
            fromDate <= startDate && startDate < toDate
        }.map {
            val startDate = sdf.parse(it.start)!!
            ScheduleDateUiState.ScheduleTime(startDate, false)
        }

        val bookedSchedultTime = booked.filter {
            val startDate = sdf.parse(it.start)!!
            fromDate <= startDate && startDate < toDate
        }.map {
            val startDate = sdf.parse(it.start)!!
            ScheduleDateUiState.ScheduleTime(startDate, true)
        }

        result.addAll(availableSchedultTime)
        result.addAll(bookedSchedultTime)
        result.sortBy { it.start }
        return result
    }
}