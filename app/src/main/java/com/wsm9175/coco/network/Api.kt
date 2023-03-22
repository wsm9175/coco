package com.wsm9175.coco.network

import com.wsm9175.coco.network.model.CurrentPriceList
import retrofit2.http.GET

interface Api {
    @GET("public/ticker/ALL_KRW")
    suspend fun getCurrentCoinList() : CurrentPriceList


}