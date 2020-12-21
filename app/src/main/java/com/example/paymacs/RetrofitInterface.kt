package com.example.paymacs
//server call
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitInterface {
    //app.js
    @POST("/getOrderId")
    fun getOrderId(@Body map: HashMap<String, String>): Call<Order>

    @POST("/updateTransactionStatus")
    fun updateTransaction(@Body map: HashMap<String, String>): Call<String>
}