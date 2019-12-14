package it.dikbudsit.stb.myappkey.ui.inputCodeFragment

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import it.dikbudsit.stb.myappkey.room.Note
import it.dikbudsit.stb.myappkey.room.NoteDao
import it.dikbudsit.stb.myappkey.room.NoteRoomDbase

class InputCodeViewModel(application: Application) : AndroidViewModel(application) {
    private var noteDao: NoteDao? = null
    private var noteDB: NoteRoomDbase? = null


    init {
        noteDB = NoteRoomDbase.getDatabse(application)
        noteDao = noteDB?.noteDao()
    }

    fun insert(note: Note) {
        insertAsyncTask(this!!.noteDao!!).execute(note)
    }

    private class insertAsyncTask(noteDao: NoteDao) : AsyncTask<Note?, Void?, Void?>() {
        var mNoteDao: NoteDao
        override fun doInBackground(vararg notes: Note?): Void? {
            mNoteDao.insert(notes[0])
            return null
        }
        init {
            mNoteDao = noteDao
        }
    }
}
