package it.dikbudsit.stb.myappkey.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.material.tabs.TabLayout
import java.util.*

class MainViewModel(application: Application)
    : AndroidViewModel(application) {

    private val TAG = this.javaClass.simpleName
    private var randomId : MutableLiveData<String>? = null
    private var random : Random? = null

    fun getrandomId(): MutableLiveData<String> {
        Log.i(TAG, "Get Number")
        if (randomId == null) {
            randomId = MutableLiveData()
            createNumber()
        }
        return randomId as MutableLiveData<String>
    }

    fun createNumber() {
        Log.i(TAG, "Create new Number")
        random = Random()
        randomId!!.value = "Number : " + random!!.nextInt(10 - 1 + 1)
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "ViewModel Destroyed")
    }
}