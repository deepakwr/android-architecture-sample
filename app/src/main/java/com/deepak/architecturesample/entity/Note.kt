package com.deepak.architecturesample.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName= "node_table")
data class Note(@PrimaryKey(autoGenerate = true) var id:Int?,
                @ColumnInfo() val title:String,
                @ColumnInfo() val description:String,
                @ColumnInfo() val priority:Int) {


    constructor(title:String,description:String,priority:Int) : this(null,title,description,priority) {

    }

    fun setId(id:Int){
        this.id = id
    }


//    @PrimaryKey(autoGenerate = true)
//    private var id:Int = 0
//        get() = field
//        set(value) {
//            field = value
//        }
//    private var title:String = ""
//        get() = field
//        set(value) {
//            field = value
//        }
//    private var description:String = ""
//        get() = field
//        set(value) {
//            field = value
//        }
//    private var priority:Int = 0
//        get() = field
//        set(value) {
//            field = value
//        }

}