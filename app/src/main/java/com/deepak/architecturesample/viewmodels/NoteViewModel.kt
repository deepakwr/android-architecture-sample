package com.deepak.architecturesample.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.deepak.architecturesample.entity.Note
import com.deepak.architecturesample.repository.NoteRepository

class NoteViewModel(application: Application) :AndroidViewModel(application) {

    private var noteRepository: NoteRepository? = null

    //getter setter needed to be fixed.
    var allNotes: LiveData<List<Note>>

    init {
        noteRepository = NoteRepository(application)
        allNotes = noteRepository!!.allNotes!!
    }

    fun insertNote(note: Note){
        noteRepository?.insert(note)
    }

    fun updateNote(note: Note){
        noteRepository?.update(note)
    }

    fun deleteNote(note: Note){
        noteRepository?.delete(note)
    }

    fun deleteAll(){
        noteRepository?.deleteAllNotes()
    }
}
