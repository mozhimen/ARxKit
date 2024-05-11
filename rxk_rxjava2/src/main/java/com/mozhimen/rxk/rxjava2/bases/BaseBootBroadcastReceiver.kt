package com.mozhimen.rxk.rxjava2.bases

import android.content.Context
import android.content.Intent
import com.mozhimen.basick.elemk.android.content.bases.BaseBroadcastReceiver
import com.mozhimen.basick.elemk.android.content.cons.CIntent
import com.mozhimen.basick.lintk.optins.permission.OPermission_RECEIVE_BOOT_COMPLETED
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.rxk.rxjava2.commons.IObserver
import com.mozhimen.rxk.rxjava2.utils.TransformerUtil
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * @ClassName BaseAutoRunBroadcastReceiver
 * @Description

 * 权限:
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />

 * 继承:
class ElemKAutoRunReceiver : BaseAutoRunReceiver(BasicKActivity::class.java, 5000)

 * 静态注册(AndroidManifest.xml):
<receiver
android:name=".elemk.receiver.ElemKAutoRunReceiver"
android:enabled="true"
android:exported="true">
<intent-filter android:priority="1000">
<action android:name="android.intent.action.BOOT_COMPLETED" />
</intent-filter>
</receiver>

 * @Author Kolin Zhao / Mozhimen
 * @Version 1.0
 */
@OPermission_RECEIVE_BOOT_COMPLETED
open class BaseBootBroadcastReceiver(private val clazz: Class<*>, private val _delayTime: Long = 0L) : BaseBroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            CIntent.ACTION_BOOT_COMPLETED -> {
                if (_delayTime != 0L) {
                    Observable.just("").delay(_delayTime, TimeUnit.MILLISECONDS).compose(TransformerUtil.getObservableTransformer_ofIo2main()).subscribe(object : IObserver<String> {
                        override fun onComplete() {
                            context.startContext(clazz)
                        }
                    })
                } else {
                    context.startContext(clazz)
                }
            }
        }
    }
}