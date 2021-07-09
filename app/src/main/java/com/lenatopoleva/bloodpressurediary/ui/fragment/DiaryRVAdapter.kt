package com.lenatopoleva.bloodpressurediary.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lenatopoleva.bloodpressurediary.R
import com.lenatopoleva.bloodpressurediary.data.entity.HealthData
import kotlinx.android.synthetic.main.item_health_data.view.*

class DiaryRVAdapter (val onClickListener: ((HealthData) -> Unit)? = null): RecyclerView.Adapter<DiaryRVAdapter.HealthDataViewHolder>() {

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
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class HealthDataViewHolder(val container: View) : RecyclerView.ViewHolder(container) {
        fun bind(healthData: HealthData)  {
            with(healthData) {
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
    }
}