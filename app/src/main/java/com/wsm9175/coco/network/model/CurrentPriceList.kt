package com.wsm9175.coco.network.model

data class CurrentPriceList(
    val status : String,
    val data : Map<String, Any>
)