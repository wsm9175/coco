package com.wsm9175.coco.view.intro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsm9175.coco.datastore.MyDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class IntroViewModel : ViewModel(){
    private val _first = MutableLiveData<Boolean>()
    val first : LiveData<Boolean>
        get() = _first

    fun checkFirstFlag() = viewModelScope.launch {
        delay(2000)
        val getData = MyDataStore().getFirstData()
        _first.value = getData
    }
}