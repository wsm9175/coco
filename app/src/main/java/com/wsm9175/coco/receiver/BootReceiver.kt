package com.wsm9175.coco.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.wsm9175.coco.service.PriceForegroundService

class BootReceiver : BroadcastReceiver(){
    private val TAG = BroadcastReceiver::class.java.simpleName
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action.equals(Intent.ACTION_BOOT_COMPLETED)){
            Log.d(TAG, "boot complete")
            val foreground = Intent(context, PriceForegroundService::class.java.javaClass)
            foreground.action = "START"

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                context?.startForegroundService(foreground)
            }else{
                context?.startService(foreground)
            }
        }
    }
}