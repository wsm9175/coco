package com.wsm9175.coco.background

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.wsm9175.coco.db.entity.SelectedCoinPriceEntity
import com.wsm9175.coco.network.model.RecentCoinPriceList
import com.wsm9175.coco.repository.DBRepository
import com.wsm9175.coco.repository.NetWorkRepository
import java.util.Calendar
import java.util.Date

class GetCoinPriceRecentContractedWorkManager(
    val context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    private val dbRepository = DBRepository()
    private val netWorkRepository = NetWorkRepository()

    override suspend fun doWork(): Result {
        Log.d("doWork", "doWork")

        getAllInterestSelectedCoinData()

        return Result.success()
    }

    suspend fun getAllInterestSelectedCoinData() {
        val selectedCoinList = dbRepository.getAllInterestSelectedCoinData()

        val timestamp = Calendar.getInstance().time

        for (coinData in selectedCoinList) {
            Log.d("doWork", coinData.toString())
            val recentCoinPriceList = netWorkRepository.getInterestCoinPriceData(coinData.coin_name)
            Log.d("doWork", recentCoinPriceList.toString())

            saveSelectedCoinPrice(coinData.coin_name, recentCoinPriceList, timestamp)
        }
    }

    fun saveSelectedCoinPrice(
        coinName: String,
        recentCoinPriceList: RecentCoinPriceList,
        timestamp: Date
    ) {
        val selectedCoinPriceEntity = SelectedCoinPriceEntity(
            0,
            coinName,
            recentCoinPriceList.data[0].transaction_date,
            recentCoinPriceList.data[0].type,
            recentCoinPriceList.data[0].units_traded,
            recentCoinPriceList.data[0].price,
            recentCoinPriceList.data[0].total,
            timestamp
        )

        dbRepository.insertCoinPriceData(selectedCoinPriceEntity)
    }
}