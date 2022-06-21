package com.example.seventh_home_task.data.network

import com.example.seventh_home_task.domain.models.Form
import com.example.seventh_home_task.domain.models.Meta
import com.example.seventh_home_task.domain.models.ResultSend
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

private const val BASE_URL =
    "http://test.clevertec.ru/tt/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ClevertecApiService {
    @GET("meta")
    suspend fun getMeta(): Meta

    @POST("data/")
    suspend fun getResult(@Body form: Form?): ResultSend?
}

object ClevertecApi {
    val retrofitService: ClevertecApiService by lazy {
        retrofit.create(ClevertecApiService::class.java)
    }
}