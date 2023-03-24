package com.wsm9175.coco.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.wsm9175.coco.background.GetCoinPriceRecentContractedWorkManager
import com.wsm9175.coco.view.main.MainActivity
import com.wsm9175.coco.databinding.ActivitySelectBinding
import com.wsm9175.coco.view.adapter.SelectRVAdapter
import java.util.concurrent.TimeUnit

class SelectActivity : AppCompatActivity() {
    private val TAG = SelectActivity::class.simpleName
    private val viewModel: SelectViewModel by viewModels()
    private lateinit var selectRVAdapter: SelectRVAdapter
    private lateinit var binding: ActivitySelectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getCurrentCoinList()
        viewModel.currentPriceResult.observe(this, Observer {
            selectRVAdapter = SelectRVAdapter(this, it)
            binding.coinListRV.adapter = selectRVAdapter
            binding.coinListRV.layoutManager = LinearLayoutManager(this)
        })

        binding.laterTextArea.setOnClickListener{
            viewModel.setUpFirstFlag()
            viewModel.saveSelectedCoinList(selectRVAdapter.selectedCoinList)
        }

        viewModel.save.observe(this, Observer {
            if(it.equals("done")){
                startActivity(Intent(this, MainActivity::class.java))
                //가장 처음으로 우리가 저장한 코인 정보가 저장되는 시점
                saveInterestCoinDataPeriodic()
            }
        })
    }
    private fun saveInterestCoinDataPeriodic(){
        val myWork = PeriodicWorkRequest.Builder(
            GetCoinPriceRecentContractedWorkManager::class.java,
            15,
            TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "GetCoinPriceRecentContractedWorkManager",
            ExistingPeriodicWorkPolicy.KEEP,
            myWork
        )
    }
}