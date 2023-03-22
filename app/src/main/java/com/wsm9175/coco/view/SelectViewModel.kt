package com.wsm9175.coco.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.wsm9175.coco.datamodel.CurrentPrice
import com.wsm9175.coco.datamodel.CurrentPriceResult
import com.wsm9175.coco.repository.NetWorkRepository
import kotlinx.coroutines.launch

class SelectViewModel : ViewModel() {
    private val TAG = SelectViewModel::class.simpleName
    private val netWorkRepository = NetWorkRepository()
    private lateinit var currentPriceResultList: ArrayList<CurrentPriceResult>
    private val _currentPriceResult = MutableLiveData<List<CurrentPriceResult>>()
    val currentPriceResult : LiveData<List<CurrentPriceResult>>
        get() = _currentPriceResult

    fun getCurrentCoinList() = viewModelScope.launch {
        val result = netWorkRepository.getCurrentCoinList()
        currentPriceResultList = ArrayList()
        for (coin in result.data){
            try{
                var gson = Gson()
                val gsonToJson = gson.toJson(result.data.get(coin.key))
                val gsonFromJson = gson.fromJson(gsonToJson, CurrentPrice::class.java)
                val currentPriceResult = CurrentPriceResult(coin.key, gsonFromJson)
                Log.d(TAG, currentPriceResult.toString())
                currentPriceResultList.add(currentPriceResult)
            }catch (e : java.lang.Exception){

            }
        }
        _currentPriceResult.value = currentPriceResultList
    }


}