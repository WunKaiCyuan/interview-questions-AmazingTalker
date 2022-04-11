package homework.amazingtalker.domain

import homework.amazingtalker.domain.WeekDateUseCase
import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class WeekDateUseCaseUnitTest {

    private val useCase = WeekDateUseCase()

    @Test
    fun getThisWeekFirstDate() {
        val c = Calendar.getInstance()
        c.set(2022, 4 - 1, 3)

        val c2 = Calendar.getInstance()
        c2.set(2022, 3 - 1, 28)

        val weekFirstDate = useCase.getThisWeekFirstDate(c.time, Calendar.MONDAY)

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val actual = sdf.format(weekFirstDate)
        val expected = sdf.format(c2.time)

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun getThisWeekLastDate() {
        val c = Calendar.getInstance()
        c.set(2022, 4 - 1, 3)

        val c2 = Calendar.getInstance()
        c2.set(2022, 4 - 1, 3)

        val weekFirstDate = useCase.getThisWeekLastDate(c.time, Calendar.MONDAY)

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val actual = sdf.format(weekFirstDate)
        val expected = sdf.format(c2.time)

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun getPreviousWeekFirstDate() {
        val c = Calendar.getInstance()
        c.set(2022, 4 - 1, 3)

        val c2 = Calendar.getInstance()
        c2.set(2022, 3 - 1, 21)

        val weekFirstDate = useCase.getPreviousWeekFirstDate(c.time, Calendar.MONDAY)

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val actual = sdf.format(weekFirstDate)
        val expected = sdf.format(c2.time)

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun getNextWeekFirstDate() {
        val c = Calendar.getInstance()
        c.set(2022, 4 - 1, 3)

        val c2 = Calendar.getInstance()
        c2.set(2022, 4 - 1, 4)

        val weekFirstDate = useCase.getNextWeekFirstDate(c.time, Calendar.MONDAY)

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val actual = sdf.format(weekFirstDate)
        val expected = sdf.format(c2.time)

        Assert.assertEquals(expected, actual)
    }
}