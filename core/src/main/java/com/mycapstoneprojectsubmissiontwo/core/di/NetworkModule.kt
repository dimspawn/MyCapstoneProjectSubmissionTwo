package com.mycapstoneprojectsubmissiontwo.core.di

import android.content.Context
import com.mycapstoneprojectsubmissiontwo.core.R
import com.mycapstoneprojectsubmissiontwo.core.data.source.remote.network.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {
    @Provides
    fun provideOkHttpClient(context: Context): OkHttpClient {
        val hostname = context.resources.getString(R.string.host_name)
        val certificate = CertificatePinner.Builder()
            .add(hostname, context.resources.getString(R.string.pin_certificate_1))
            .add(hostname, context.resources.getString(R.string.pin_certificate_2))
            .add(hostname, context.resources.getString(R.string.pin_certificate_3))
            .build()
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificate)
            .build()
    }

    @Provides
    fun provideApiService(client: OkHttpClient, context: Context): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(context.resources.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}