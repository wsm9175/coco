package com.wsm9175.coco.view.setting

import android.Manifest
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.wsm9175.coco.R
import com.wsm9175.coco.databinding.ActivitySelectBinding
import com.wsm9175.coco.databinding.ActivitySettingBinding
import com.wsm9175.coco.service.PriceForegroundService

class SettingActivity : AppCompatActivity() {
    companion object {
        const val DENIED = "denied"
        const val EXPLAINED = "explained"
    }

    private lateinit var binding : ActivitySettingBinding
    @RequiresApi(Build.VERSION_CODES.M)
    private val registerForActivityResult = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        val deniedPermissionList = permissions.filter { !it.value }.map { it.key }
        when {
            deniedPermissionList.isNotEmpty() -> {
                val map = deniedPermissionList.groupBy { permission ->
                    if (shouldShowRequestPermissionRationale(permission)) DENIED else EXPLAINED
                }
                map[DENIED]?.let {
                    // 단순히 권한이 거부 되었을 때
                }
                map[EXPLAINED]?.let {
                    // 권한 요청이 완전히 막혔을 때(주로 앱 상세 창 열기)
                }
            }
            else -> {
                // 모든 권한이 허가 되었을 때
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startForeground.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                registerForActivityResult.launch(
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS)
                )
            }
            Toast.makeText(this, "start", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, PriceForegroundService::class.java)
            intent.action = "START"
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                startForegroundService(intent)
            }else{
                startService(intent)
            }
        }

        binding.stopForeground.setOnClickListener {
            Toast.makeText(this, "stop", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, PriceForegroundService::class.java)
            intent.action = "STOP"
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                startForegroundService(intent)
            }else{
                startService(intent)
            }
        }
    }
}