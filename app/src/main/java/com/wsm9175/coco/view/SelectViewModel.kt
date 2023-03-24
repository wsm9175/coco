package com.wsm9175.coco.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.wsm9175.coco.datamodel.CurrentPrice
import com.wsm9175.coco.datamodel.CurrentPriceResult
import com.wsm9175.coco.datastore.MyDataStore
import com.wsm9175.coco.db.entity.InterestCoinEntity
import com.wsm9175.coco.repository.DBRepository
import com.wsm9175.coco.repository.NetWorkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SelectViewModel : ViewModel() {
    private val TAG = SelectViewModel::class.simpleName
    private val netWorkRepository = NetWorkRepository()
    private val dbRepository = DBRepository()
    private lateinit var currentPriceResultList: ArrayList<CurrentPriceResult>
    private val _currentPriceResult = MutableLiveData<List<CurrentPriceResult>>()
    val currentPriceResult : LiveData<List<CurrentPriceResult>>
        get() = _currentPriceResult

    private val _saved = MutableLiveData<String>()
    val save : LiveData<String>
        get() = _saved

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

    fun setUpFirstFlag() = viewModelScope.launch {
        MyDataStore().setupFirstData()
    }

    //DB에 데이터 저장
    //Dispatchers.IO - 기본 스레드 외부에서 디스크 또는 네트워크 I/O를 실행하도록 최적화됨
    fun saveSelectedCoinList(selectedCoinList: ArrayList<String>) = viewModelScope.launch(Dispatchers.IO){
        // 1. 전체 코인 데이터를 가져옴
        for (coin in currentPriceResultList){
            // 2. 내가 선택한 코인인지 아닌지 구분해서
            val selected = selectedCoinList.contains(coin.coinName)

            val interestCoinEntity = InterestCoinEntity(
                0,
                coin.coinName,
                coin.coinInfo.opening_price,
                coin.coinInfo.closing_price,
                coin.coinInfo.min_price,
                coin.coinInfo.max_price,
                coin.coinInfo.units_traded,
                coin.coinInfo.acc_trade_value,
                coin.coinInfo.prev_closing_price,
                coin.coinInfo.units_traded_24H,
                coin.coinInfo.acc_trade_value_24H,
                coin.coinInfo.fluctate_24H,
                coin.coinInfo.fluctate_rate_24H,
                selected
            )
            // 3. 저장
            interestCoinEntity.let {
                dbRepository.insertInterestCoinData(it)
            }
        }
        withContext(Dispatchers.Main){
            _saved.value = "done"
        }

    }
}