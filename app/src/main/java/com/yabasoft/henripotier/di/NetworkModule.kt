package com.yabasoft.henripotier.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Fayssel Yabahddou on 4/14/22.
 */

private const val CONNECT_TIMEOUT = 15L
private const val WRITE_TIMEOUT = 15L
private const val READ_TIMEOUT = 15L

val networkModule = module {

    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            cache(get())
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            addInterceptor(get())
        }.build()
    }

    fun provideRetrofit(factory: GsonConverterFactory, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://henri-potier.xebia.fr/")
            .addConverterFactory(factory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    single { provideGson() }
    single { provideConverterFactory() }
    single { provideHttpClient() }
    single { provideRetrofit(get(), get()) }
    single { Cache(androidApplication().cacheDir, 10L * 1024 * 1024) }

    single {
        Interceptor { chain ->
            chain.proceed(chain.request().newBuilder().apply {
                header("Accept", "application/json")
            }.build())
        }
    }

}