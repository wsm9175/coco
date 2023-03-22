package com.wsm9175.coco.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.wsm9175.coco.R

class SelectActivity : AppCompatActivity() {
    private val TAG = SelectActivity::class.simpleName
    private val viewModel : SelectViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)
        viewModel.getCurrentCoinList()
        viewModel.currentPriceResult.observe(this, Observer {
            Log.d(TAG, it.toString())
        })
    }
}