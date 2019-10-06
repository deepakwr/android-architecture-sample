package com.deepak.architecturesample.service

import com.deepak.architecturesample.entity.Note

interface OnItemClickListener {
    fun onItemClick(note: Note)
}