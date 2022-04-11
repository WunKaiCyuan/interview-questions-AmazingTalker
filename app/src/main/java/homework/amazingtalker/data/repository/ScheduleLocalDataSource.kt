package homework.amazingtalker.data.repository

import homework.amazingtalker.data.model.ScheduleApiModel
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

class ScheduleLocalDataSource @Inject constructor() {

    fun setSchedule(schedule: ScheduleApiModel) {
        memoryScheduleModel = schedule
    }

    fun getSchedule(): ScheduleApiModel {
        return memoryScheduleModel
    }

    fun setStartAt(startAt: Date) {
        memoryStartAt = startAt
    }

    fun getStartAt(): Date? {
        return memoryStartAt
    }

    companion object {
        private var memoryScheduleModel: ScheduleApiModel =
            ScheduleApiModel(emptyList(), emptyList())

        private var memoryStartAt: Date? = null
    }
}