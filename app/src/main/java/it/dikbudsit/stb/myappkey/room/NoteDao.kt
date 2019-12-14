package it.dikbudsit.stb.myappkey.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    fun insert(note: Note?)

    @Query("SELECT * FROM notes")
    fun getallNote(): LiveData<List<Note?>?>?

    @Query("SELECT * FROM notes WHERE id =:noteId")
    fun getNote(noteId: String?): LiveData<Note?>?


    @Update
    fun updateNote(note: Note?)

    @Delete
    fun deleteNote(note: Note?): Int

}