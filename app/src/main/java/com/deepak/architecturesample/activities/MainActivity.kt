package com.deepak.architecturesample.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deepak.architecturesample.entity.Note
import com.deepak.architecturesample.service.OnItemClickListener
import com.deepak.architecturesample.R
import com.deepak.architecturesample.adapters.RecyclerAdapter
import com.deepak.architecturesample.adapters.RecyclerListAdapter
import com.deepak.architecturesample.viewmodels.NoteViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)


//        recyclerView.adapter = RecyclerAdapter()
        recyclerView.adapter = RecyclerListAdapter()

        button_add_note.setOnClickListener {
            openAddNoteActivity()
        }


        var listener = object :
            OnItemClickListener {
            override fun onItemClick(note: Note) {
                var intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)

                intent.putExtra(AddEditNoteActivity.NOTE_ID,note.id)
                intent.putExtra(AddEditNoteActivity.NOTE_TITLE,note.title)
                intent.putExtra(AddEditNoteActivity.NOTE_DESCRIPTION,note.description)
                intent.putExtra(AddEditNoteActivity.NOTE_PRIORITY,note.priority)
                startActivityForResult(intent,
                    EDIT_NOTE_REQUEST
                )
            }

        }


        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        noteViewModel.allNotes.observe(this,Observer{
//            Toast.makeText(applicationContext,"onChanged " + it.toString(),Toast.LENGTH_SHORT).show()

            if(recyclerView.adapter is RecyclerAdapter) {
                (recyclerView.adapter as RecyclerAdapter).notes = it
                (recyclerView.adapter as RecyclerAdapter).setOnItemClickListener(listener)
            }else {
                (recyclerView.adapter as RecyclerListAdapter).submitList(it)
                (recyclerView.adapter as RecyclerListAdapter).setOnItemClickListener(listener)
            }
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, viewHolder1: RecyclerView.ViewHolder): Boolean {
                //2
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                //3
                val position = viewHolder.adapterPosition
                if(recyclerView.adapter is RecyclerAdapter) {
                    noteViewModel.deleteNote((recyclerView.adapter as RecyclerAdapter).getNoteAt(position))
                }
                else{
                    noteViewModel.deleteNote((recyclerView.adapter as RecyclerListAdapter).getNoteAt(position))
                }
                Toast.makeText(applicationContext,"Deleted Notes.",Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(recyclerView)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.delete_all_notes -> {
                noteViewModel.deleteAll()
                Toast.makeText(applicationContext,"Deleting all Notes.",Toast.LENGTH_LONG).show();
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }


    }

    private fun openAddNoteActivity(){
        var intent = Intent(this, AddEditNoteActivity::class.java)
        startActivityForResult(intent, ADD_NOTE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {


        when(resultCode){
            Activity.RESULT_OK -> {
                var title = ""+ data?.getStringExtra(AddEditNoteActivity.NOTE_TITLE)!!
                var description=""+ data?.getStringExtra(AddEditNoteActivity.NOTE_DESCRIPTION)!!
                var priority= data?.getIntExtra(AddEditNoteActivity.NOTE_PRIORITY,1)!!

                when(requestCode){
                    ADD_NOTE_REQUEST -> {
                        var note= Note(title, description, priority)
                        noteViewModel.insertNote(note)
                        Toast.makeText(applicationContext,"Note Saved ",Toast.LENGTH_SHORT).show()
                    }
                    EDIT_NOTE_REQUEST ->{
                        var note= Note(title, description, priority)
                        note.id = data?.getIntExtra(AddEditNoteActivity.NOTE_ID,1)!!
                        noteViewModel.updateNote(note)
                        Toast.makeText(applicationContext,"Note Updated ",Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else -> { Toast.makeText(applicationContext,"Note not Saved ",Toast.LENGTH_SHORT).show() }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object{
        val ADD_NOTE_REQUEST:Int = 1
        val EDIT_NOTE_REQUEST:Int = 2

        val TAG:String = "ArchitectureSample"

    }

}
