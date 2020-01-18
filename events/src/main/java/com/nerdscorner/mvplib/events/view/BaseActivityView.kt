package com.nerdscorner.mvplib.events.view

import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.nerdscorner.mvplib.events.bus.Bus
import org.greenrobot.eventbus.ThreadMode
import java.lang.ref.WeakReference

abstract class BaseActivityView @JvmOverloads constructor(
        activity: AppCompatActivity,
        @JvmField protected var bus: Bus = Bus.defaultBus
) : BaseView() {

    override val activity: AppCompatActivity?
        get() = activityRef.get()

    private val activityRef: WeakReference<AppCompatActivity> = WeakReference(activity)

    override val fragmentManager: FragmentManager?
        get() = activityRef.get()?.supportFragmentManager

    @JvmName("busSetter")
    fun setBus(bus: Bus) {
        this.bus = bus
    }

    fun onDestroy() {}

    fun onClick(@IdRes id: Int, event: Any, threadMode: ThreadMode = ThreadMode.POSTING) {
        activity?.findViewById<View>(id)?.setOnClickListener {
            bus.post(event, threadMode)
        }
    }
}
