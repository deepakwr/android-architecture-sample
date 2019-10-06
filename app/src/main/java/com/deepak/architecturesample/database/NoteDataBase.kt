package com.deepak.architecturesample.database

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.deepak.architecturesample.activities.MainActivity
import com.deepak.architecturesample.dao.NoteDao
import com.deepak.architecturesample.entity.Note

@Database(entities = arrayOf(Note::class),version = 1)
abstract class NoteDataBase:RoomDatabase() {


    abstract fun noteDao(): NoteDao

    companion object{
        private var instance: NoteDataBase? = null

        fun getInstance(context: Context): NoteDataBase?{
            if(instance ==null) {
                synchronized(NoteDataBase::class){
                    instance = Room.databaseBuilder(context,
                        NoteDataBase::class.java,"note_database.db")
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallBack)
                        .build()
                }
            }
            return instance
        }

        fun destroyInstance(){
            instance = null;
        }

        private val roomCallBack = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDBAsyncTask(instance!!)
                    .execute()

                Log.i(MainActivity.TAG,"db onCreate Called")
            }

            override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                super.onDestructiveMigration(db)
                Log.i(MainActivity.TAG,"db onDestructiveMigration Called")
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                Log.i(MainActivity.TAG,"db onOpen Called" + db.path)
            }
        }

        class PopulateDBAsyncTask(db: NoteDataBase): AsyncTask<Any,Any,Any>(){

            private var noteDao: NoteDao

            init {
                noteDao = db.noteDao()
            }

            override fun doInBackground(vararg p0: Any?) {
                noteDao.insert(Note("Title 1", "Description 1", 1))
                noteDao.insert(Note("Title 2", "Description 2", 2))
                noteDao.insert(Note("Title 3", "Description 3", 3))

            }
        }
    }
}