package com.wsm9175.coco.network.model

import com.wsm9175.coco.datamodel.RecentPriceData

data class RecentCoinPriceList(
    val status: String,
    val data : List<RecentPriceData>
)