package homework.amazingtalker.ui

data class ScheduleUiState(
    var canToPreviousWeek: Boolean = false,
    var canToNextWeek: Boolean = false,
    var weekAreaText: String = "",
    var weekDates: List<String> = emptyList(),
)

