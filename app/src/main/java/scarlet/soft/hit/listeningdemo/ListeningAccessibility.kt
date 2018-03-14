package scarlet.soft.hit.listeningdemo

import android.accessibilityservice.AccessibilityService
import android.support.annotation.UiThread
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

/**
 * Created by Scarlet on 2018/3/12.
 */
class ListeningAccessibility : AccessibilityService() {
    override fun onInterrupt() {
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
            val nodeInfo: AccessibilityNodeInfo? = rootInActiveWindow

            val title = nodeInfo?.findAccessibilityNodeInfosByViewId("android:id/text1")?.firstOrNull()

            val aprList = nodeInfo?.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/apx")
            val aptList = nodeInfo?.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/apz")

            aprList?.forEach({ Log.d("${title?.text}-APX", "${it.text}") })
            aptList?.forEach({ Log.d("${title?.text}-APZ", "${it.text}") })


            //时间
            var result = ""


//            while (nodeInfo != null && nodeInfo.childCount != 0) {
//                if (nodeInfo.className == "android.widget.ListView") {
//                    for (i in 0 until nodeInfo.childCount) {
//                        val node = nodeInfo.getChild(i)
//                        for(j in 0 until node.childCount){
////                            val myApx = node.findAccessibilityNodeInfosByViewId("apx")
//                            Log.d("OJBK", "${node.getChild(j).className}")
//                        }
//
//
//                    }
//                }
//                nodeInfo = nodeInfo.getChild(0)
//            }

//            if (nodeInfo != null) {
//                for (i in 0 until nodeInfo.childCount) {
//                    val myList = nodeInfo.getChild(i)
//                    Log.d("OJBK", "${nodeInfo.childCount}-${myList.className}")
//                    if (myList.className.equals("android.widget.ListView")) {
//                        val myApx = myList.findAccessibilityNodeInfosByViewId("apx")
//                        Log.d("OJBK", "${myApx.size}")
//                    }
//                }
//            }


//            for (i in 0..nodeInfo.childCount) {
//                Log.d("OJBK", "${event.className}\n${nodeInfo.getChild(i).text}")
        }
//        val tp:Int = event.eventType
//        when (tp) {
//            AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED -> {
//                val nodeInfo: AccessibilityNodeInfo = rootInActiveWindow
//                for (i in 0..nodeInfo.childCount) {
//                    Log.d("OJBK", "${event.className}\n${nodeInfo.getChild(i).text}")
//                }
//            }
    }
}