package com.lenatopoleva.bloodpressurediary.ui.fragment

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lenatopoleva.bloodpressurediary.R
import com.lenatopoleva.bloodpressurediary.data.entity.HealthData
import com.lenatopoleva.bloodpressurediary.utils.ui.getColorInt
import kotlinx.android.synthetic.main.item_health_data.view.*
import com.lenatopoleva.bloodpressurediary.data.entity.Color as Colors


class DiaryRVAdapter(val onClickListener: ((HealthData) -> Unit)? = null): RecyclerView.Adapter<DiaryRVAdapter.HealthDataViewHolder>() {

    var data: List<HealthData> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HealthDataViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_health_data, parent, false)
        return HealthDataViewHolder(view)
    }

    override fun onBindViewHolder(holder: HealthDataViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class HealthDataViewHolder(val container: View) : RecyclerView.ViewHolder(container) {
        fun bind(healthData: HealthData, position: Int)  {
            with(healthData) {
                if ( position > 0 && healthData.date != data[position - 1].date ) {
                    container.group_date.visibility = View.VISIBLE
                }
                if (position == 0) container.group_date.visibility = View.VISIBLE
                container.group_data.let {
                    val gradient = GradientDrawable(
                        GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(
                            Color.WHITE,
                            getDataColor(upperBloodPressure, container.context),
                            Color.WHITE)
                    )
//                    gradient.cornerRadius = 0f

                    it.background = gradient
                }
                container.tv_date.text = date
                container.tv_time.text = time
                container.tv_up_pressure.text = upperBloodPressure.toString()
                container.tv_low_pressure.text = lowerBloodPressure.toString()
                container.tv_pulse.text = pulse.toString()

                itemView.setOnClickListener {
                    onClickListener?.invoke(healthData)
                }
            }
        }

        private fun getDataColor(upperBloodPressure: String, context: Context): Int{
            return try {
                when (upperBloodPressure.toInt()){
                    in 0..99 -> Colors.BLUE.getColorInt(context)
                    in 100..119 -> Colors.DARK_GREEN.getColorInt(context)
                    in 120..129 -> Colors.GREEN.getColorInt(context)
                    in 130..139 -> Colors.LIGHT_GREEN.getColorInt(context)
                    in 140..159 -> Colors.YELLOW.getColorInt(context)
                    in 160..179 -> Colors.ORANGE.getColorInt(context)
                    in 180..1000 -> Colors.RED.getColorInt(context)
                    else -> Color.WHITE
                }
            } catch (e: Throwable) {
                println(e.message)
                Color.WHITE
            }
        }
    }
}