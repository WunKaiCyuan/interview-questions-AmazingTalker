package homework.amazingtalker.data.model

data class ScheduleApiModel(
    val available: List<TimeScope>,
    val booked: List<TimeScope>,
) {
    data class TimeScope(var start: String, var end: String)
}