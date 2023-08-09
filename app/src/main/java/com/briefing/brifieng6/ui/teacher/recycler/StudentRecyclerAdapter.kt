package com.briefing.brifieng6.ui.teacher.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.briefing.brifieng6.api.callback.AnswerCallback
import com.briefing.brifieng6.api.domain.AnswerController
import com.briefing.brifieng6.api.model.AnswerDTO
import com.briefing.brifieng6.api.model.AnswerResponseRemote
import com.briefing.brifieng6.ui.student.recycler.model.ItemTaskData
import com.briefing.brifieng6.ui.student.viewmodel.AnswerViewModel
import com.briefing.brifieng6.ui.teacher.recycler.model.ItemStudentData
import com.briefing.test.databinding.ItemStudentBinding

class StudentRecyclerAdapter(
    private val onStudentListener: OnStudentListener,
    internal var data: List<ItemStudentData>,
    private val answerViewModel: AnswerViewModel,
    private val lifecycleOwner: LifecycleOwner,
    private val dataTask: ItemTaskData
    ) :
    RecyclerView.Adapter<StudentRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentRecyclerAdapter.ViewHolder {
        return ViewHolder(
            ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onStudentListener,
            answerViewModel,
            lifecycleOwner,
            dataTask
        )
    }

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
    fun setItemsInStart(response: List<ItemStudentData>) {
        data = response.reversed()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(response: List<ItemStudentData>) {
        data = response
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: ItemStudentBinding,
        onStudentListener: OnStudentListener,
        private val answerViewModel: AnswerViewModel,
        private val lifecycleOwner: LifecycleOwner,
        private val dataTask: ItemTaskData
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private var data: ItemStudentData? = null
        private val _onStudentListener: OnStudentListener = onStudentListener

        @SuppressLint("SetTextI18n")
        fun bind(data: ItemStudentData) {
            this.data = data
            val resultData = getResultData()
            binding.nameText.text = data.name_ + " " + data.surname
        }

        @SuppressLint("SetTextI18n")
        private fun getResultData(){
            val answerController = AnswerController()
            answerViewModel.fetchAnswer(
                answerController,
                login = data!!.login,
                title = dataTask.title
            )
            answerViewModel.dataAnswer.observe(lifecycleOwner){
                binding.resultTextView.text = "Результат: ${it.result_}"
                binding.statusTextView.text = "Статус: ${it.solved}"
            }
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            _onStudentListener.onStudentClick(data)
        }

    }
}

interface OnStudentListener {
    fun onStudentClick(data: ItemStudentData?)
}