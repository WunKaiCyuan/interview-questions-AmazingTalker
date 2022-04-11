package homework.amazingtalker.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import homework.amazingtalker.R
import homework.amazingtalker.event.ScheduleChangeEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class ScheduleDateFragment : Fragment(), CoroutineScope by MainScope() {

    @Inject
    lateinit var viewModel: ScheduleDateViewModel

    private lateinit var tvLocalTimeZone: TextView

    private lateinit var rvList: RecyclerView
    private lateinit var listAdapter: ScheduleTimeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.fragment_schedule, container)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvLocalTimeZone = view.findViewById(R.id.tvLocalTimeZone)
        /*
        rvMorning = view.findViewById(R.id.rvMorning)
        rvEvening = view.findViewById(R.id.rvEvening)
        rvNight = view.findViewById(R.id.rvNight)
         */
        rvList = view.findViewById(R.id.rvList)

        tvLocalTimeZone.apply {
            text = getString(
                R.string.your_local_time_zone,
                Calendar.getInstance().timeZone.displayName
            )
        }

        /*
        rvMorning.apply {
            morningAdapter = ScheduleTimeAdapter()
            adapter = morningAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        rvEvening.apply {
            eveningAdapter = ScheduleTimeAdapter()
            adapter = eveningAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        rvNight.apply {
            nightAdapter = ScheduleTimeAdapter()
            adapter = nightAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
         */

        rvList.apply {
            listAdapter = ScheduleTimeAdapter()
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    /*
                    morningAdapter.dataList.clear()
                    morningAdapter.dataList.addAll(it.mornings)
                    morningAdapter.notifyDataSetChanged()

                    eveningAdapter.dataList.clear()
                    eveningAdapter.dataList.addAll(it.evening)
                    eveningAdapter.notifyDataSetChanged()

                    nightAdapter.dataList.clear()
                    nightAdapter.dataList.addAll(it.night)
                    nightAdapter.notifyDataSetChanged()
                     */

                    listAdapter.dataList.clear()

                    if (it.mornings.isNotEmpty()) {
                        listAdapter.dataList.add(ScheduleDateUiState.ScheduleTimeType(getString(R.string.morning)))
                        listAdapter.dataList.addAll(it.mornings)
                    }

                    if (it.evening.isNotEmpty()) {
                        listAdapter.dataList.add(ScheduleDateUiState.ScheduleTimeType(getString(R.string.evening)))
                        listAdapter.dataList.addAll(it.evening)
                    }

                    if (it.night.isNotEmpty()) {
                        listAdapter.dataList.add(ScheduleDateUiState.ScheduleTimeType(getString(R.string.night)))
                        listAdapter.dataList.addAll(it.night)
                    }
                    listAdapter.notifyDataSetChanged()
                }
            }
        }

        viewModel.initialize(requireArguments().getInt("diffDate"))
        viewModel.refreshScheduleTime()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as ScheduleActivity).scheduleComponent.inject(this)
        EventBus.getDefault().register(this)
    }

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onScheduleChangeEvent(event: ScheduleChangeEvent) {
        viewModel.refreshScheduleTime()
    }

    companion object {
        fun newInstance(diffDate: Int): ScheduleDateFragment {
            val args = Bundle()
            args.putInt("diffDate", diffDate)

            val fragment = ScheduleDateFragment()
            fragment.arguments = args
            return fragment
        }
    }

    class ScheduleTimeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private val VIEW_TYPE_SCHEDULE_TIME = 1
        private val VIEW_TYPE_SCHEDULE_TIME_TYPE = 2

        val dataList: ArrayList<Any> = ArrayList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            if (viewType == VIEW_TYPE_SCHEDULE_TIME) {
                val view = inflater.inflate(R.layout.rv_item_schedule_time, parent, false)
                return ScheduleTimeViewHolder(view)
            } else if (viewType == VIEW_TYPE_SCHEDULE_TIME_TYPE) {
                val view = inflater.inflate(R.layout.rv_item_schedule_time_type, parent, false)
                return ScheduleTimeTypeViewHolder(view)
            } else {
                throw Exception("not match type")
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val data = dataList[position]

            if (holder is ScheduleTimeViewHolder) {
                holder.setData(data as ScheduleDateUiState.ScheduleTime)
            } else if (holder is ScheduleTimeTypeViewHolder) {
                holder.setData(data as ScheduleDateUiState.ScheduleTimeType)
            }
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        override fun getItemViewType(position: Int): Int {
            if (dataList[position] is ScheduleDateUiState.ScheduleTime)
                return VIEW_TYPE_SCHEDULE_TIME
            else if (dataList[position] is ScheduleDateUiState.ScheduleTimeType)
                return VIEW_TYPE_SCHEDULE_TIME_TYPE
            else
                throw Exception("not match view type")
        }
    }

    class ScheduleTimeTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvType: TextView = itemView.findViewById(R.id.tvType)

        fun setData(data: ScheduleDateUiState.ScheduleTimeType) {
            tvType.text = data.typeName
        }
    }

    class ScheduleTimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val btnTime: Button = itemView.findViewById(R.id.btnTime)

        fun setData(data: ScheduleDateUiState.ScheduleTime) {
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
            btnTime.text = sdf.format(data.start)

            if (data.isBooked) {
                btnTime.apply {
                    setTextColor(
                        itemView.resources.getColor(
                            R.color.dark_color,
                            itemView.context.theme
                        )
                    )
                    setBackgroundResource(R.drawable.schedule_time_btn_booked_selector)
                }

            } else {
                btnTime.apply {
                    setTextColor(
                        itemView.resources.getColor(
                            R.color.primary_dark_grey_color,
                            itemView.context.theme
                        )
                    )
                    setBackgroundResource(R.drawable.schedule_time_btn_available_selector)
                }
            }
        }
    }
}

