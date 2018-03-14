package scarlet.soft.hit.listeningdemo

import android.app.*
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.util.Log
import java.lang.Thread.sleep
import java.text.SimpleDateFormat


/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 *
 *
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
class ListeningService : IntentService("ListeningService") {

    override fun onHandleIntent(p0: Intent?) {
        //必须在安全设置中打开有权查看使用情况的应用
        Log.d("OJBK","Online!")
        while (true) {
            getTopActivtyFromLolipopOnwards()
            sleep(5000)
        }
    }


    fun getClassName() {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val info = manager.getRunningTasks(1)[0]
//        val shortClassName = info.topActivity.shortClassName    //类名
//        val className = info.topActivity.className              //完整类名
//        val packageName = info.topActivity.packageName          //包名
        val sDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        Log.e("LISTENING TAG", "时间${sDateFormat.format(java.util.Date())}\n" +
                "类名${info.topActivity.shortClassName}\n" +
                "完整类名${info.topActivity.className}\n" +
                "包名${info.topActivity.packageName}")
    }

    fun getTopActivtyFromLolipopOnwards() {

        val mId = 10086

        val usageStatsManager = this.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

        val queryEvents = usageStatsManager.queryEvents(System.currentTimeMillis() - 5000, System.currentTimeMillis())

        if (queryEvents != null) {
            var event = UsageEvents.Event()

            while (queryEvents.hasNextEvent()) {
                val eventAux = UsageEvents.Event()
                queryEvents.getNextEvent(eventAux)

                if (eventAux.eventType == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                    event = eventAux
                }
            }

            if (event.className != null) {
                val sDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val style = NotificationCompat.BigTextStyle()
                style.bigText("完整类名${event.className}\n" +
                        "时间${sDateFormat.format(java.util.Date())}\n" +
                        "包名${event.packageName}")
                style.setBigContentTitle("完整内容")

                val pIntent = PendingIntent.getActivity(this, 1, Intent(this, MainActivity::class.java), 0)

                val mBuilder = NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.abc_ic_go_search_api_material)
                        .setContentTitle("My notification")
                        .setAutoCancel(true)
                        .setStyle(style)
                        .setContentText("完整类名${event.className}")
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setContentIntent(pIntent)
                        .setFullScreenIntent(pIntent, true)

                val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                mNotificationManager.notify(mId, mBuilder.build())

                Log.e("LISTENING TAG", "时间${sDateFormat.format(java.util.Date())}\n" +
                        "完整类名${event.className}\n" +
                        "包名${event.packageName}")
            }
        }
    }
}
