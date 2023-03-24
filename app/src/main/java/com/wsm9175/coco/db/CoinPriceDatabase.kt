package com.wsm9175.coco.db

import android.content.Context
import androidx.room.*
import com.wsm9175.coco.db.dao.InterestCoinDAO
import com.wsm9175.coco.db.dao.SelectedCoinPriceDAO
import com.wsm9175.coco.db.entity.DateConverter
import com.wsm9175.coco.db.entity.InterestCoinEntity
import com.wsm9175.coco.db.entity.SelectedCoinPriceEntity

@Database(entities = [InterestCoinEntity::class, SelectedCoinPriceEntity::class], version = 2)
@TypeConverters(DateConverter::class)
abstract class CoinPriceDatabase :RoomDatabase(){
    abstract fun interestCoinDAO() : InterestCoinDAO
    abstract fun selectedCoinDAO() : SelectedCoinPriceDAO

    companion object{
        @Volatile
        private var INSTANCE : CoinPriceDatabase? = null

        fun getDatabase(context: Context): CoinPriceDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CoinPriceDatabase::class.java,
                    "coin_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}