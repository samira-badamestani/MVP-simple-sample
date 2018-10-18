package simple.mvp.foodlist.core

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import simple.mvp.foodlist.data.network.ApiService
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates


class App : Application() {

    companion object {
         var api: ApiService? = null
        private val TAG = App::class.java.simpleName
        var instance: App by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        MultiDex.install(this)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()


        val client = httpClient.connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(180, TimeUnit.SECONDS)
            .connectTimeout(180, TimeUnit.SECONDS)
            .writeTimeout(180, TimeUnit.SECONDS).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Config.HOST)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(ApiService::class.java)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }


}