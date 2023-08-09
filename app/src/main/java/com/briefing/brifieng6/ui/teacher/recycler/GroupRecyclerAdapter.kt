package com.briefing.brifieng6.ui.teacher.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.briefing.brifieng6.ui.student.recycler.model.ItemGroupData
import com.briefing.test.databinding.ItemGroupBinding

class GroupRecyclerAdapter(private val onGroupListener: OnGroupListener, internal var data: List<ItemGroupData>) :
    RecyclerView.Adapter<GroupRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false),
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

    class ViewHolder(private val binding: ItemGroupBinding, onGroupListener: OnGroupListener)
        : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private var data: ItemGroupData? = null
        private val _onGroupListener: OnGroupListener = onGroupListener
        @SuppressLint("SetTextI18n")
        fun bind(data: ItemGroupData) {
            this.data = data
            binding.quantityTextView.text = "Человек в группе: " + data.numberOfStudent
            binding.groupTextView.text = "Группа: " + data.group_
            binding.univTextView.text = "Университет: " + data.university
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            _onGroupListener.onGroupClick(data)
        }

    }
}

interface OnGroupListener {
    fun onGroupClick(data: ItemGroupData?)
}