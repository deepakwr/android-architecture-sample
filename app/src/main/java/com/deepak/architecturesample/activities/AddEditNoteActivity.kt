package com.deepak.architecturesample.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.deepak.architecturesample.R
import kotlinx.android.synthetic.main.activity_add_note.*

class AddEditNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        number_picker_priority.minValue = 1
        number_picker_priority.maxValue = 10

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)


        intent.extras?.get(NOTE_ID)?.let{
            setTitle(it.toString())
            edit_text_title.setText(intent.extras?.getString(NOTE_TITLE))
            edit_text_description.setText(intent.extras?.getString(NOTE_DESCRIPTION))
            number_picker_priority.value = intent.getIntExtra(NOTE_PRIORITY,0);
        } ?: run{
            setTitle("Add Note")
        }




    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.add_note_menu,menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.save_note -> {
                saveNote()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }


    }

    private fun saveNote() {
        if(edit_text_title.text.trim().isEmpty() ||
                edit_text_description.text.trim().isEmpty()){
            Toast.makeText(this,"Please enter title and description",Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent()
            .putExtra(NOTE_TITLE,edit_text_title.text.toString())
            .putExtra(NOTE_DESCRIPTION,edit_text_description.text.toString())
            .putExtra(NOTE_PRIORITY,number_picker_priority.value)

        var id:Int = intent.getIntExtra(NOTE_ID,-1)
        if(id!=-1){
            data.putExtra(NOTE_ID,id)
        }

        setResult(RESULT_OK,data)
        finish()
    }

    companion object{

        val NOTE_ID = "NOTE_ID"

        val NOTE_TITLE = "NOTE_TITLE_HERE"

        val NOTE_DESCRIPTION = "NOTE_DESCRIPTION_HERE"

        val NOTE_PRIORITY = "NOTE_PRIORITY_HERE"
    }
}
