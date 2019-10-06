package com.deepak.architecturesample.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.deepak.architecturesample.entity.Note

@Dao
interface NoteDao {

    @Insert
    fun insert(note: Note);

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("DELETE FROM node_table")
    fun deleteAll();

    @Query("SELECT * FROM node_table ORDER BY priority DESC")
    fun getAllNotes():LiveData<List<Note>>
}