package it.dikbudsit.stb.myappkey.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import it.dikbudsit.stb.myappkey.room.Note

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val mAllNote: LiveData<List<Note>>? = null

}
