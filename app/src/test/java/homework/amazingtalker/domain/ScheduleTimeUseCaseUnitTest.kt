package homework.amazingtalker.domain

import com.google.gson.Gson
import homework.amazingtalker.data.model.ScheduleApiModel
import org.junit.Assert
import org.junit.Test
import java.util.*

class ScheduleTimeUseCaseUnitTest {

    private val useCase = ScheduleTimeUseCase()

    @Test
    fun getThisWeekFirstDate() {
        val c = Calendar.getInstance()
        c.set(2022, 4 - 1, 8, 0, 0, 0)

        val c2 = Calendar.getInstance()
        c2.set(2022, 4 - 1, 9, 0, 0, 0)

        val gson = Gson()
        val schedule = gson.fromJson(
            "{\n" +
                    "  \"available\": [\n" +
                    "    {\n" +
                    "      \"start\": \"2022-04-05T04:30:00Z\",\n" +
                    "      \"end\": \"2022-04-05T05:00:00Z\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"start\": \"2022-04-05T07:30:00Z\",\n" +
                    "      \"end\": \"2022-04-05T08:00:00Z\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"start\": \"2022-04-06T05:30:00Z\",\n" +
                    "      \"end\": \"2022-04-06T06:00:00Z\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"start\": \"2022-04-06T14:00:00Z\",\n" +
                    "      \"end\": \"2022-04-06T14:30:00Z\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"start\": \"2022-04-07T06:00:00Z\",\n" +
                    "      \"end\": \"2022-04-07T06:30:00Z\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"start\": \"2022-04-07T07:30:00Z\",\n" +
                    "      \"end\": \"2022-04-07T10:00:00Z\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"start\": \"2022-04-07T12:30:00Z\",\n" +
                    "      \"end\": \"2022-04-07T13:30:00Z\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"start\": \"2022-04-07T14:00:00Z\",\n" +
                    "      \"end\": \"2022-04-07T14:30:00Z\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"start\": \"2022-04-08T09:00:00Z\",\n" +
                    "      \"end\": \"2022-04-08T09:30:00Z\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"start\": \"2022-04-08T12:00:00Z\",\n" +
                    "      \"end\": \"2022-04-08T12:30:00Z\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"start\": \"2022-04-08T13:00:00Z\",\n" +
                    "      \"end\": \"2022-04-08T14:30:00Z\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"start\": \"2022-04-09T05:30:00Z\",\n" +
                    "      \"end\": \"2022-04-09T06:00:00Z\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"booked\": [\n" +
                    "    {\n" +
                    "      \"start\": \"2022-04-04T12:00:00Z\",\n" +
                    "      \"end\": \"2022-04-04T13:30:00Z\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"start\": \"2022-04-05T03:00:00Z\",\n" +
                    "      \"end\": \"2022-04-05T04:30:00Z\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"start\": \"2022-04-05T05:00:00Z\",\n" +
                    "      \"end\": \"2022-04-05T07:30:00Z\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"start\": \"2022-04-05T08:00:00Z\",\n" +
                    "      \"end\": \"2022-04-05T12:30:00Z\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"start\": \"2022-04-06T03:00:00Z\",\n" +
                    "      \"end\": \"2022-04-06T05:30:00Z\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"start\": \"2022-04-06T06:00:00Z\",\n" +
                    "      \"end\": \"2022-04-06T14:00:00Z\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"start\": \"2022-04-07T03:00:00Z\",\n" +
                    "      \"end\": \"2022-04-07T06:00:00Z\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"start\": \"2022-04-07T06:30:00Z\",\n" +
                    "      \"end\": \"2022-04-07T07:30:00Z\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"start\": \"2022-04-07T10:00:00Z\",\n" +
                    "      \"end\": \"2022-04-07T12:30:00Z\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"start\": \"2022-04-07T13:30:00Z\",\n" +
                    "      \"end\": \"2022-04-07T14:00:00Z\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"start\": \"2022-04-08T03:00:00Z\",\n" +
                    "      \"end\": \"2022-04-08T09:00:00Z\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"start\": \"2022-04-08T09:30:00Z\",\n" +
                    "      \"end\": \"2022-04-08T12:00:00Z\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"start\": \"2022-04-08T12:30:00Z\",\n" +
                    "      \"end\": \"2022-04-08T13:00:00Z\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"start\": \"2022-04-09T04:00:00Z\",\n" +
                    "      \"end\": \"2022-04-09T05:30:00Z\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"start\": \"2022-04-09T06:00:00Z\",\n" +
                    "      \"end\": \"2022-04-09T08:00:00Z\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"start\": \"2022-04-10T06:00:00Z\",\n" +
                    "      \"end\": \"2022-04-10T06:30:00Z\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}", ScheduleApiModel::class.java
        )

        val scheduleTimes =
            useCase.getTimeAreaSchedule(c.time, c2.time, schedule.available, schedule.booked)

        val actual = scheduleTimes.size
        val expected = 6

        Assert.assertEquals(expected, actual)
    }
}