package com.an.deviceinfo.ads

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.IBinder
import android.os.IInterface
import android.os.Looper
import android.os.Parcel
import android.os.RemoteException
import android.util.Log

import java.io.IOException
import java.util.concurrent.LinkedBlockingQueue

object AdvertisingIdClient {

    class AdIdInfo internal constructor(val id: String, val isLimitAdTrackingEnabled: Boolean)

    @Throws(Exception::class)
    fun getAdvertisingIdInfo(context: Context): AdIdInfo {
        if (Looper.myLooper() == Looper.getMainLooper()) throw IllegalStateException("Cannot be called from the main thread")

        try {
            val pm = context.packageManager
            pm.getPackageInfo("com.android.vending", 0)
        } catch (e: Exception) {
            throw e
        }

        val connection = AdvertisingConnection()
        val intent = Intent("com.google.android.gms.ads.identifier.service.START")
        intent.`package` = "com.google.android.gms"
        if (context.bindService(intent, connection, Context.BIND_AUTO_CREATE)) {
            try {
                val adInterface = AdvertisingInterface(connection.binder)
                val adIdInfo = AdIdInfo(adInterface.id, adInterface.isLimitAdTrackingEnabled(true))
                Log.d("*****ADINFO***", adInterface.id)
                return adIdInfo
            } catch (exception: Exception) {
                throw exception
            } finally {
                context.unbindService(connection)
            }
        }
        throw IOException("Google Play connection failed")
    }

    private class AdvertisingConnection : ServiceConnection {
        internal var retrieved = false
        private val queue = LinkedBlockingQueue<IBinder>(1)

        val binder: IBinder
            @Throws(InterruptedException::class)
            get() {
                if (this.retrieved) throw IllegalStateException()
                this.retrieved = true
                return this.queue.take() as IBinder
            }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            try {
                this.queue.put(service)
            } catch (localInterruptedException: InterruptedException) {
            }

        }

        override fun onServiceDisconnected(name: ComponentName) {}
    }

    private class AdvertisingInterface(private val binder: IBinder) : IInterface {

        val id: String
            @Throws(RemoteException::class)
            get() {
                val data = Parcel.obtain()
                val reply = Parcel.obtain()
                val id: String
                try {
                    data.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService")
                    binder.transact(1, data, reply, 0)
                    reply.readException()
                    id = reply.readString()
                } finally {
                    reply.recycle()
                    data.recycle()
                }
                return id
            }

        override fun asBinder(): IBinder {
            return binder
        }

        @Throws(RemoteException::class)
        fun isLimitAdTrackingEnabled(paramBoolean: Boolean): Boolean {
            val data = Parcel.obtain()
            val reply = Parcel.obtain()
            val limitAdTracking: Boolean
            try {
                data.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService")
                data.writeInt(if (paramBoolean) 1 else 0)
                binder.transact(2, data, reply, 0)
                reply.readException()
                limitAdTracking = 0 != reply.readInt()
            } finally {
                reply.recycle()
                data.recycle()
            }
            return limitAdTracking
        }
    }
}