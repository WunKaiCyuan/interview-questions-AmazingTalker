package homework.amazingtalker.domain

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class WeekDateUseCase @Inject constructor() {
    fun getThisWeekFirstDate(
        date: Date = Calendar.getInstance().time,
        firstDayOfWeek: Int = Calendar.SATURDAY
    ): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val d: Int
        if (firstDayOfWeek - 7 - dayOfWeek == -7) {
            d = 0
        } else {
            d = firstDayOfWeek - 7 - dayOfWeek
        }
        calendar.add(Calendar.DATE, d)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        return calendar.time
    }

    fun getThisWeekLastDate(
        date: Date = Calendar.getInstance().time,
        firstDayOfWeek: Int = Calendar.SATURDAY
    ): Date {
        val firstDate = this.getThisWeekFirstDate(date, firstDayOfWeek)
        val calendar = Calendar.getInstance()
        calendar.time = firstDate
        calendar.add(Calendar.DATE, 6)
        return calendar.time
    }

    fun getPreviousWeekFirstDate(
        date: Date = Calendar.getInstance().time,
        firstDayOfWeek: Int = Calendar.SATURDAY
    ): Date {
        val firstDate = this.getThisWeekFirstDate(date, firstDayOfWeek)
        val calendar = Calendar.getInstance()
        calendar.time = firstDate
        calendar.add(Calendar.DATE, -7)
        return calendar.time
    }

    fun getNextWeekFirstDate(
        date: Date = Calendar.getInstance().time,
        firstDayOfWeek: Int = Calendar.SATURDAY
    ): Date {
        val firstDate = this.getThisWeekFirstDate(date, firstDayOfWeek)
        val calendar = Calendar.getInstance()
        calendar.time = firstDate
        calendar.add(Calendar.DATE, 7)
        return calendar.time
    }
}