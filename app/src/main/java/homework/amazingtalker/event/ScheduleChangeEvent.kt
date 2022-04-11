package homework.amazingtalker.event

import homework.amazingtalker.data.model.ScheduleApiModel
import java.util.*

data class ScheduleChangeEvent(val startAt: Date, val schedule: ScheduleApiModel)