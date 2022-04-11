package homework.amazingtalker.data.http

import homework.amazingtalker.data.model.ScheduleApiModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ScheduleRetrofitService {

    @GET("v1/guest/teachers/{teacherName}/schedule")
    fun fetchSchedule(
        @Path("teacherName") teacherName: String,
        @Query("start_at") startAt: String
    ): Call<ScheduleApiModel>
}