package com.example.dispositivos.ui.viewmodels

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.dispositivos.logic.data.MarvelChars
import com.example.dispositivos.logic.marvelLogic.MarvelLogic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProgressViewModel : ViewModel() {
    val progressState =  MutableLiveData<Int>()

    val items = MutableLiveData<List<MarvelChars>>()
    fun processBackground(values:Long){
        viewModelScope.launch(Dispatchers.IO) {
            val state= View.VISIBLE
            progressState.postValue(state)
            delay(3000)
            val state1= View.GONE
            progressState.postValue(state1)

    }
}
    fun sumInBackground1(){
        viewModelScope.launch(Dispatchers.IO) {
            val state= View.VISIBLE
            progressState.postValue(state)
            var total=0


            for (i in 1 .. 30000){
                total +=i
        }
            delay(3000)
            val state1= View.GONE
            progressState.postValue(state1)

        }
    }

    suspend fun getMarvelChars(offset:Int, limit: Int){
        progressState.postValue(View.VISIBLE)
        val newitems = MarvelLogic().getAllMarvelChars(offset, limit)
        items.postValue(newitems)
        progressState.postValue(View.GONE)
    }

}