package com.wsm9175.coco.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.*

@Entity(tableName = "selected_coin_price_table")
data class SelectedCoinPriceEntity (
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    val coinName : String,
    val transaction_data:String,
    val type : String,
    val units_traded:String,
    val price:String,
    val total:String,
    val timestamp: Date
)

class DateConverter{
    @TypeConverter
    fun fromTimestamp(value : Long) : Date{
        return Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date : Date): Long{
        return date.time
    }
}
