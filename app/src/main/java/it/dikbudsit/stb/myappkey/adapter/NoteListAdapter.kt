package it.dikbudsit.stb.myappkey.adapter

import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import it.dikbudsit.stb.myappkey.R
import it.dikbudsit.stb.myappkey.adapter.NoteListAdapter.NoteViewHolder
import it.dikbudsit.stb.myappkey.databinding.ItemKeyBinding
import it.dikbudsit.stb.myappkey.room.Note


class NoteListAdapter(context: Context, click: onDeleteClickListeners) : RecyclerView.Adapter<NoteViewHolder>() {

    private val inflater: LayoutInflater
    private val context: Context
    private var notes: ArrayList<Note>? = null
    private val click : onDeleteClickListeners

    interface onDeleteClickListeners {
        fun updateDelete(myNote: Note?, TAG : String)
        fun isVisiblePassword(mTextPwd : TextView, boolean: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemKeyBinding =
            DataBindingUtil.inflate<ItemKeyBinding>(inflater, R.layout.item_key, parent, false)
        return NoteViewHolder(itemKeyBinding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        if (notes != null) {
            val note = notes!![position]
            holder.setData(note, position)
            holder.listenActionUser(note)
        }
    }

    override fun getItemCount(): Int {
        return if (notes != null) notes!!.size else 0
    }

    fun setNotes(notes: List<Note>?) {
        this.notes = notes as ArrayList<Note>?
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(var binding: ItemKeyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var mposition = 0
        var isVisible = false
        fun setData(note: Note?, position: Int) {
            binding.data = note
            mposition = position

        }
        fun listenActionUser(note: Note) {

            binding.texpasw.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.texpasw.setOnClickListener{
                when {
                    !isVisible -> {
                        click.isVisiblePassword(binding.texpasw, true)
                        isVisible = true
                    }
                    else -> {
                        click.isVisiblePassword(binding.texpasw, false)
                        isVisible = false

                    }
                }
            }

            binding.update.setOnClickListener{
                click.updateDelete(note, "Update")
            }
            binding.delete.setOnClickListener{
                if (click != null) click.updateDelete(note, "Delete")
            }
        }

    }

    init {
        inflater = LayoutInflater.from(context)
        this.context = context
        this.click = click
    }
}