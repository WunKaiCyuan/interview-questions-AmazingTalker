package homework.amazingtalker.data.repository

import homework.amazingtalker.data.http.ScheduleRetrofitService
import homework.amazingtalker.data.model.ScheduleApiModel
import javax.inject.Inject

class ScheduleRemoteDataSource @Inject constructor(
    private val calendarService: ScheduleRetrofitService,
) {
    fun fetchSchedule(teacherName: String, startAt: String): ScheduleApiModel {
        try {
            val response = calendarService.fetchSchedule(teacherName, startAt).execute()
            return response.body()!!
        } catch (e: Exception) {
            throw e
        }
    }
}

