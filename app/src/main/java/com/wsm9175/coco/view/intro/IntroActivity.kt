package com.wsm9175.coco.view.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import com.wsm9175.coco.view.main.MainActivity
import com.wsm9175.coco.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    private lateinit var binding : ActivityIntroBinding
    private val TAG : String? = IntroActivity::class.simpleName
    private val viewModel : IntroViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "onCreate")

        viewModel.checkFirstFlag()

        viewModel.first.observe(this, Observer {isNotFirst ->
            if(isNotFirst){
                startActivity(Intent(this, MainActivity::class.java))
            }else{
                binding.animationView.visibility = View.INVISIBLE
                binding.fragmentContainerView.visibility = View.VISIBLE
            }
        })
    }
}