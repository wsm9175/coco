package com.wsm9175.coco.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.wsm9175.coco.datamodel.UpDownDataSet
import com.wsm9175.coco.db.entity.InterestCoinEntity
import com.wsm9175.coco.repository.DBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    private val dbRepository = DBRepository()
    lateinit var selectedCoinList: LiveData<List<InterestCoinEntity>>
    private val _arr15min = MutableLiveData<List<UpDownDataSet>>()
    val arr15min : LiveData<List<UpDownDataSet>>
        get() = _arr15min
    private val _arr30min = MutableLiveData<List<UpDownDataSet>>()
    val arr30min : LiveData<List<UpDownDataSet>>
        get() = _arr30min
    private val _arr45min = MutableLiveData<List<UpDownDataSet>>()
    val arr45min : LiveData<List<UpDownDataSet>>
        get() = _arr45min

    fun getAllInterestCoinData() = viewModelScope.launch {
        val coinList = dbRepository.getAllInterestCoinData().asLiveData()
        selectedCoinList = coinList
    }

    fun updateInterestCoinData(interestCoinEntity: InterestCoinEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            if (interestCoinEntity.selected) {
                interestCoinEntity.selected = false
            } else {
                interestCoinEntity.selected = true
            }
            dbRepository.updateInterestCoinData(interestCoinEntity)
        }

    // 1. 관심있다고 선택한 코인 리스트를 가져옴
    // 2. 관심있다고 선택한 코인 리스트를 반복분을 통해 하나씩 가져옴
    // 3. 저장된 코인 가격 리스트를 가져와서
    // 4. 시간대마다 어떻게 변경되었는지를 알려주는 로직을 작성
    fun getAllSelectedCoinData() = viewModelScope.launch(Dispatchers.IO) {
        val selectedCoinList = dbRepository.getAllInterestSelectedCoinData()

        val arr15min = ArrayList<UpDownDataSet>()
        val arr30min = ArrayList<UpDownDataSet>()
        val arr45min = ArrayList<UpDownDataSet>()

        for (data in selectedCoinList){
            val coinName = data.coin_name
            val oneCoinData = dbRepository.getOneSelectedCoinData(coinName).reversed()
            val size = oneCoinData.size

            if(size > 1){
                val changedPrice = oneCoinData[0].price.toDouble() - oneCoinData[1].price.toDouble()
                val upDownDataSet = UpDownDataSet(
                    coinName,
                    changedPrice.toString()
                )
                arr15min.add(upDownDataSet)
            }
            if(size > 2){
                val changedPrice = oneCoinData[0].price.toDouble() - oneCoinData[2].price.toDouble()
                val upDownDataSet = UpDownDataSet(
                    coinName,
                    changedPrice.toString()
                )
                arr30min.add(upDownDataSet)
            }
            if(size > 3){
                val changedPrice = oneCoinData[0].price.toDouble() - oneCoinData[3].price.toDouble()
                val upDownDataSet = UpDownDataSet(
                    coinName,
                    changedPrice.toString()
                )
                arr45min.add(upDownDataSet)
            }
        }
        withContext(Dispatchers.Main){
            _arr15min.value = arr15min
            _arr30min.value = arr30min
            _arr45min.value = arr45min
        }
    }
}