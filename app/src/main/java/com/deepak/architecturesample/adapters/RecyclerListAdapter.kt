package com.deepak.architecturesample.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deepak.architecturesample.entity.Note
import com.deepak.architecturesample.service.OnItemClickListener
import com.deepak.architecturesample.R
import kotlinx.android.synthetic.main.note_item.view.*

class RecyclerListAdapter: ListAdapter<Note, RecyclerListAdapter.NoteHolder>(
    DiffCallback()
) {

    lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)
        return NoteHolder(itemView)
    }


    fun getNoteAt(position:Int): Note {
        return getItem(position)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        var currentNote: Note = getItem(position)//notes.get(position)
        holder.textViewTitle?.setText(currentNote.title)
        holder.textViewDescription?.setText(currentNote.description)
        holder.textViewPriority?.setText(currentNote.priority.toString())

        holder.itemView.setOnClickListener(View.OnClickListener {
            var position:Int = position
            listener.onItemClick(currentNote);
        })
    }

    class NoteHolder(view: View):RecyclerView.ViewHolder(view) {
        var textViewTitle: TextView? = null
        var textViewDescription: TextView? = null
        var textViewPriority: TextView? = null

        init {
            textViewTitle = view.text_view_title
            textViewDescription = view.text_view_description
            textViewPriority = view.text_view_priority
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

    class DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.title == newItem.title && oldItem.description == newItem.description && oldItem.priority == newItem.priority
        }

        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }
    }
}