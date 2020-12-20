package com.example.paymacs
//server call
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitInterface {
    @POST("/getOrderId")
    fun getOrderId(@Body map: HashMap<String, String>): Call<Order>
}