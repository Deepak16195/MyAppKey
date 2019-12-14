package it.dikbudsit.stb.myappkey.ViewModel

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import it.dikbudsit.stb.myappkey.room.Note
import it.dikbudsit.stb.myappkey.room.NoteDao
import it.dikbudsit.stb.myappkey.room.NoteRoomDbase
import it.dikbudsit.stb.myappkey.room.NoteRoomDbase.Companion.getDatabse

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = this.javaClass.simpleName
    private val noteDao: NoteDao?
    private val noteDB: NoteRoomDbase?
    private val mAllNote: LiveData<List<Note?>?>?
    /*insert data*/
    fun insert(note: Note?) {
        insertAsyncTask(noteDao).execute(note)
    }

    /*read all data*/
    fun getmAllNote(): LiveData<List<Note?>?>? {
        return mAllNote
    }

    /*read single Note*/
    fun getNote(noteId: String?): LiveData<Note?>? {
        return noteDao!!.getNote(noteId)
    }

    /*update note*/
    fun updateNote(note: Note?) {
        updateAsyncTask(noteDao).execute(note)
    }

    /*delete note*/
    fun deleteNote(note: Note?) {
        deleteAsyncTask(noteDao).execute(note)
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "ViewModel Destroyed")
    }

    private inner class insertAsyncTask(var mNoteDao: NoteDao?) :
        AsyncTask<Note?, Void?, Void?>() {
        override fun doInBackground(vararg notes: Note?): Void? {
            mNoteDao!!.insert(notes[0])
            return null
        }

    }

    private inner class updateAsyncTask(var mNoteDao: NoteDao?) :
        AsyncTask<Note?, Void?, Void?>() {
        override fun doInBackground(vararg notes: Note?): Void? {
            mNoteDao!!.updateNote(notes[0])
            return null
        }

    }

    private inner class deleteAsyncTask(private val mNoteDao: NoteDao?) :
        AsyncTask<Note?, Void?, Void?>() {
        override fun doInBackground(vararg notes: Note?): Void? {
            mNoteDao!!.deleteNote(notes[0])
            return null
        }

    }

    init {
        noteDB = getDatabse(application)
        noteDao = noteDB!!.noteDao()
        mAllNote = noteDao!!.getallNote()
    }
}