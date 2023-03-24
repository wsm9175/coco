package com.wsm9175.coco.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wsm9175.coco.db.entity.SelectedCoinPriceEntity

@Dao
interface SelectedCoinPriceDAO {
    // getAllData
    @Query("SELECT * FROM selected_coin_price_table")
    fun getAllData() : List<SelectedCoinPriceEntity>

    // insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(selectedCoinPriceEntity: SelectedCoinPriceEntity)

    // 하나의 코인에 대해서 저장된 정보를 가져옴
    // BTC 15 30 45 -> List<BTC> -> 현재 가격 15, 30, 45 어떻게 변화했는지 DB에 저장된 값과 비교 용도
    @Query("SELECT * FROM selected_coin_price_table WHERE coinName = :coinName")
    fun getOneCoinData(coinName : String) : List<SelectedCoinPriceEntity>
}