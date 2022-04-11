package homework.amazingtalker.data.repository

import homework.amazingtalker.data.model.ScheduleApiModel
import java.util.*
import javax.inject.Inject

class ScheduleRepository @Inject constructor(
    private val remoteDataSource: ScheduleRemoteDataSource,
    private val localDataSource: ScheduleLocalDataSource
) {
    fun fetchSchedule(teacherName: String, startAt: String): ScheduleApiModel =
        remoteDataSource.fetchSchedule(teacherName, startAt)

    fun setSchedule(schedule: ScheduleApiModel) {
        localDataSource.setSchedule(schedule)
    }

    fun getSchedule(): ScheduleApiModel {
        return localDataSource.getSchedule()
    }

    fun setStartAt(startAt: Date) {
        localDataSource.setStartAt(startAt)
    }

    fun getStartAt(): Date? {
        return localDataSource.getStartAt()
    }
}