package com.deepak.architecturesample.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.deepak.architecturesample.entity.Note
import com.deepak.architecturesample.dao.NoteDao
import com.deepak.architecturesample.database.NoteDataBase

class NoteRepository {

    private var noteDao: NoteDao? = null

    var allNotes: LiveData<List<Note>>? = null
        get() = field

//    fun getAllNotes() = allNotes

    constructor(application: Application){
        var noteDatabse = NoteDataBase.getInstance(application)
        noteDao = noteDatabse?.noteDao()
        allNotes = noteDao?.getAllNotes()
    }

    fun insert(note: Note){
        NoteAyncTask(noteDao!!).execute(note,"insert")
    }

    fun update(note: Note){
        NoteAyncTask(noteDao!!).execute(note,"update")
    }

    fun delete(note: Note){
        NoteAyncTask(noteDao!!).execute(note,"delete")
    }

    fun deleteAllNotes(){
        NoteAyncTask(noteDao!!).execute(null,"deleteall")
    }

    //Need to check if this is required.
    companion object{
        class NoteAyncTask(var noteDao: NoteDao) : AsyncTask<Any, Any, Any>()
        {

            override fun doInBackground(vararg params: Any?) {
                var update:String = params[1] as String

                var note: Note? = params[0] as Note?

                if(update == "insert"){
                    note?.let { noteDao.insert(it) }
                }
                if(update == "update"){
                    note?.let { noteDao.update(it) }
                }
                if(update == "delete"){
                    note?.let { noteDao.delete(it) }
                }
                if(update == "deleteall"){
                     noteDao.deleteAll()
                }
//                notes[0]?.let { noteDao.insert(it) }
            }

        }
    }

}