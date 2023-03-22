package com.wsm9175.coco.view.intro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.wsm9175.coco.R

class IntroActivity : AppCompatActivity() {
    private val TAG : String? = IntroActivity::class.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        Log.d(TAG, "onCreate")
    }
}