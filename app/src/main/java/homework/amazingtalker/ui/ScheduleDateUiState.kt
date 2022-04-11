package homework.amazingtalker.ui

import java.util.*

data class ScheduleDateUiState(
    var mornings: List<ScheduleTime> = emptyList(),
    var evening: List<ScheduleTime> = emptyList(),
    var night: List<ScheduleTime> = emptyList(),
) {
    data class ScheduleTimeType(var typeName: String)
    data class ScheduleTime(var start: Date, var isBooked: Boolean)
}