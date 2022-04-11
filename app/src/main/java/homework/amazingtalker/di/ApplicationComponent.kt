package homework.amazingtalker.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, SubcomponentModule::class])
interface ApplicationComponent {
    fun calendarComponent(): ScheduleComponent.Factory
}