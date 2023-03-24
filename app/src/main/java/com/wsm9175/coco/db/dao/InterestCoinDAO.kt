package com.wsm9175.coco.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.wsm9175.coco.db.entity.InterestCoinEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface InterestCoinDAO {
    // getAllData
    @Query("SELECT * FROM interest_coin_table")
    fun getAllData() : Flow<List<InterestCoinEntity>>

    // INSERT
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(interestCoinEntity: InterestCoinEntity)

    // update
    @Update
    fun update(interestCoinEntity: InterestCoinEntity)

    @Query("SELECT * FROM interest_coin_table WHERE selected =:selected")
    fun getSelectedData(selected :Boolean = true): List<InterestCoinEntity>
}