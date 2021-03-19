package com.example.daytracker.ui.main.dayTracking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daytracker.R
import com.example.daytracker.databinding.ItemViewBinding
import com.example.daytracker.formatDay
import com.example.daytracker.formatQuality
import com.example.daytracker.ui.main.database.DayQuality


class DayAdapter(val clickListener: DayQualityListener) :
    RecyclerView.Adapter<DayAdapter.ViewHolder>() {
    var data = listOf<DayQuality>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val item = data[position]
        viewHolder.bind(item, clickListener)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        return ViewHolder.from(viewGroup)
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder private constructor(val binding: ItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DayQuality, clickListener: DayQualityListener) {
            val res = itemView.context.resources
            binding.day = item
            binding.clickListener = clickListener
            binding.textViewDay.text = item.recordTime?.let { formatDay(it, res) }
            binding.textViewQuality.text = formatQuality(item.dayQuality, res)
            binding.icon.setImageResource(
                when (item.dayQuality) {
                    1 -> R.drawable.icon_dissatisfied
                    2 -> R.drawable.icon_neutral
                    3 -> R.drawable.icon_satisfied
                    else -> R.drawable.icon_neutral
                }
            )
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }

}

class DayQualityListener(val clickListener: (day: Long) -> Unit) {
    fun onClick(day: DayQuality) = clickListener(day.dayId)
}
