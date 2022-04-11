package homework.amazingtalker.di

import dagger.Subcomponent
import homework.amazingtalker.ui.ScheduleActivity
import homework.amazingtalker.ui.ScheduleDateFragment

@Subcomponent
interface ScheduleComponent {
    fun inject(activity: ScheduleActivity)
    fun inject(fragment: ScheduleDateFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ScheduleComponent
    }
}

