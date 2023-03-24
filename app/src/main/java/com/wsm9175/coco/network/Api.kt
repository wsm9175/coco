package com.wsm9175.coco.network

import com.wsm9175.coco.network.model.CurrentPriceList
import com.wsm9175.coco.network.model.RecentCoinPriceList
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("public/ticker/ALL_KRW")
    suspend fun getCurrentCoinList() : CurrentPriceList

    //https://api.bithumb.com/public/transaction_history/{order_currency}_{payment_currency}
    @GET("public/transaction_history/{coin}_KRW")
    suspend fun getRecentCoinPrice(@Path("coin") coin : String) : RecentCoinPriceList
}