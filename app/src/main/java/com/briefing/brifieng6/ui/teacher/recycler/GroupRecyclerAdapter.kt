package com.briefing.brifieng6.ui.student.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.briefing.brifieng6.ui.student.recycler.model.ItemGroupData
import com.briefing.test.databinding.TasksLayoutBinding

class GroupRecyclerAdapter(private val onGroupListener: OnGroupListener, internal var data: List<ItemGroupData>) :
    RecyclerView.Adapter<GroupRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TasksLayoutBinding.inflate( LayoutInflater.from(parent.context), parent, false),
            onGroupListener
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
    fun setItemsInStart(response: List<ItemGroupData>) {
        data = response.reversed()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(response: List<ItemGroupData>) {
        data = response
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: TasksLayoutBinding, onGroupListener: OnGroupListener)
        : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private var data: ItemGroupData? = null
        private val onGroupListenerListener: OnGroupListener = onGroupListener
        @SuppressLint("SetTextI18n")
        fun bind(data: ItemGroupData) {
            this.data = data
            binding.dateTextView.text = "Человек в группе: " + data.numberOfStudent
            binding.titleTextView.text = "Группа: " + data.group_
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
            onGroupListenerListener.onGroupClick(data)
        }
    }
}

interface OnGroupListener {
    fun onGroupClick(data: ItemGroupData?)
}