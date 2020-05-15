package com.examsample.network.api

import com.examsample.BuildConfig
import com.examsample.network.BASE_URL
import com.examsample.network.data.model.res.ResProductListModel
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface GoodChoiceApi {

    @GET("App/json/{page}.json")
    fun getProductList(
        @Path("page") page: Int
    ): Single<Response<ResProductListModel>>

    companion object {
        fun create(): GoodChoiceApi {
            val logger = HttpLoggingInterceptor()
            logger.level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(GoodChoiceApi::class.java)
        }
    }

}