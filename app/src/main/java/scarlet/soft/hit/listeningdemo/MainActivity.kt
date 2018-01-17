package scarlet.soft.hit.listeningdemo

import android.app.ActivityManager
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start.setOnClickListener({
            val startIntent = Intent(this, ListeningService::class.java)
            startService(startIntent)
        })
    }

    fun getTopActivtyFromLolipopOnwards() {
        val topPackageName: String
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val mUsageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
            val time = System.currentTimeMillis()
            // We get usage stats for the last 10 seconds
            val stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 10, time)
            // Sort the stats by the last time used
            if (stats != null) {
                val mySortedMap = TreeMap<Long, UsageStats>()
                for (usageStats in stats) {
                    mySortedMap.put(usageStats.lastTimeUsed, usageStats)
                }
                if (mySortedMap.isNotEmpty()) {
                    topPackageName = mySortedMap[mySortedMap.lastKey()]!!.packageName
                    Log.e("TopPackage Name", topPackageName)
                }
            }
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
}
