package com.briefing.brifieng6.ui.student.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.briefing.brifieng6.ui.student.recycler.model.ItemTaskData
import com.briefing.test.databinding.TasksLayoutBinding

class RecyclerViewAdapter(private val onLessonListener: OnTaskListener, internal var data: List<ItemTaskData>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TasksLayoutBinding.inflate( LayoutInflater.from(parent.context), parent, false),
            onLessonListener
        )
    }



    //insert fields of the ViewHolder class into the recycler
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItemsInStart(response: List<ItemTaskData>) {
        data = response.reversed()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(response: List<ItemTaskData>) {
        data = response
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: TasksLayoutBinding, onLessonListener: OnTaskListener)
        : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private var data: ItemTaskData? = null
        private val onTaskListener: OnTaskListener = onLessonListener
        @SuppressLint("SetTextI18n")
        fun bind(data: ItemTaskData) {
            this.data = data
            binding.timeTextView.text = "Дается времени: " + getCorrectTime(data.time_)
            binding.dateTextView.text = "Срок до: " + data.date_
            binding.titleTextView.text = data.title
            binding.groupTextView.text = "Задача для группы: " + data.group_
        }

        private fun getCorrectTime(time_: Int): String {
            val minutes = time_ / 60
            val seconds = time_ % 60
            return String.format("%02d:%02d", minutes, seconds)
        }

        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View) {
            onTaskListener.onTaskClick(data)
        }
    }
}

interface OnTaskListener {
    fun onTaskClick(data: ItemTaskData?)
}