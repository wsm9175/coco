package com.wsm9175.coco.repository

import com.wsm9175.coco.network.Api
import com.wsm9175.coco.network.RetrofitInstance

class NetWorkRepository {
    private val client = RetrofitInstance.getInstance().create(Api::class.java)

    suspend fun getCurrentCoinList() = client.getCurrentCoinList()
}