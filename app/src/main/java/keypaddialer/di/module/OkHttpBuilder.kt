package keypaddialer.di.module

import android.content.Context
import co.th.touchtechnologies.keypaddialer.BuildConfig
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class OkHttpBuilder(
    private val context: Context,
    private val chuckerInterceptor: ChuckerInterceptor
) {

    fun build(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    if (BuildConfig.DEBUG) {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                }
            )
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    if (BuildConfig.DEBUG) {
                        setLevel(HttpLoggingInterceptor.Level.HEADERS)
                    }
                }
            )
            .addInterceptor(chuckerInterceptor)
            .build()
    }
}