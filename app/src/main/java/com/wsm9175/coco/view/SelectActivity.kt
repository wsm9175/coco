package com.wsm9175.coco.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wsm9175.coco.view.main.MainActivity
import com.wsm9175.coco.databinding.ActivitySelectBinding
import com.wsm9175.coco.view.adapter.SelectRVAdapter

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
            }
        })

    }
}