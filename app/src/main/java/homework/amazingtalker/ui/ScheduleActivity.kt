package homework.amazingtalker.ui

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import homework.amazingtalker.R
import homework.amazingtalker.application.MyApplication
import homework.amazingtalker.di.ScheduleComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScheduleActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    @Inject
    lateinit var viewModel: ScheduleViewModel
    lateinit var scheduleComponent: ScheduleComponent

    private lateinit var tvTitle: TextView
    private lateinit var ibPreviousWeek: ImageButton
    private lateinit var ibNextWeek: ImageButton
    private lateinit var tlDate: TabLayout
    private lateinit var vpSchedule: ViewPager2
    private lateinit var scheduleWeekPageAdapter: ScheduleWeekPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        scheduleComponent =
            (applicationContext as MyApplication).appComponent.calendarComponent().create()
        scheduleComponent.inject(this)

        setContentView(R.layout.activity_calendar)

        tvTitle = findViewById(R.id.tvTitle)
        ibPreviousWeek = findViewById(R.id.ibPreviousWeek)
        ibNextWeek = findViewById(R.id.ibNextWeek)
        tlDate = findViewById(R.id.tlDate)
        vpSchedule = findViewById(R.id.vpSchedule)

        launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    tvTitle.text = it.weekAreaText
                    ibPreviousWeek.isEnabled = it.canToPreviousWeek
                    ibNextWeek.isEnabled = it.canToNextWeek
                    for (i in 0 until it.weekDates.size) {
                        tlDate.getTabAt(i)!!.text = it.weekDates[i]
                    }
                }
            }
        }

        vpSchedule.apply {
            scheduleWeekPageAdapter = ScheduleWeekPageAdapter(this@ScheduleActivity)
            adapter = scheduleWeekPageAdapter
            offscreenPageLimit = 6
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    tlDate.selectTab(tlDate.getTabAt(position), true)
                }
            })
        }

        tlDate.apply {
            for (i in 0 until 7) {
                val tab = newTab()
                addTab(tab)
            }
            addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    vpSchedule.currentItem = tlDate.selectedTabPosition
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }
            })
        }

        ibPreviousWeek.setOnClickListener {
            launch(Dispatchers.IO)
            {
                viewModel.fetchPreviousWeekSchedule()
            }
        }

        ibNextWeek.setOnClickListener {
            launch(Dispatchers.IO)
            {
                viewModel.fetchNextWeekSchedule()
            }
        }

        viewModel.initialize("jamie-coleman")
        launch(Dispatchers.IO)
        {
            viewModel.fetchThisWeekSchedule()
        }
    }

    class ScheduleWeekPageAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

        override fun getItemCount(): Int {
            return 7
        }

        override fun createFragment(position: Int): Fragment {
            return ScheduleDateFragment.newInstance(position)
        }
    }
}

