package com.wsm9175.coco.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.wsm9175.coco.db.entity.InterestCoinEntity
import com.wsm9175.coco.repository.DBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val dbRepository = DBRepository()
    lateinit var selectedCoinList : LiveData<List<InterestCoinEntity>>


    fun getAllInterestCoinData() = viewModelScope.launch {
        val coinList = dbRepository.getAllInterestCoinData().asLiveData()
        selectedCoinList = coinList
    }

    fun updateInterestCoinData(interestCoinEntity: InterestCoinEntity) = viewModelScope.launch(Dispatchers.IO) {
        if(interestCoinEntity.selected) {
            interestCoinEntity.selected = false
        }else{
            interestCoinEntity.selected = true
        }
        dbRepository.updateInterestCoinData(interestCoinEntity)
    }
}