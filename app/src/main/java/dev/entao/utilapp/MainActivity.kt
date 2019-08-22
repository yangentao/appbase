package dev.entao.utilapp

import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.telephony.SmsManager
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        sendSms("15098760059", "Hello Yang")

    }

    fun sendSms(phone: String, body: String) {

        val f = IntentFilter(SentReceiver.ACTION)
        this.registerReceiver(r, f)

        val sm = SmsManager.getDefault()
        val i = Intent(SentReceiver.ACTION)
        val pi = PendingIntent.getBroadcast(this, 50, i, PendingIntent.FLAG_UPDATE_CURRENT)

        try {
            sm.sendTextMessage(phone, null, body, pi, null)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        this.unregisterReceiver(r)
    }

    val r = SentReceiver()

}

class SentReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ACTION) {
            val ok = this.resultCode == Activity.RESULT_OK
            print("Send OK? $ok ")
        }
    }

    companion object {
        const val ACTION = "action.send.sms"
    }
}
