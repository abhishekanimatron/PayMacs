package com.example.paymacs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), PaymentResultWithDataListener {
    //shit of retrofit
    lateinit var retroInterface: RetrofitInterface
    lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Checkout.preload(applicationContext)//razorpay's for slow connections
        retrofit = Retrofit.Builder().baseUrl("").addConverterFactory(GsonConverterFactory.create())
            .build()
        retroInterface = retrofit.create(RetrofitInterface::class.java)
        //getting amount from the text box and setting it on button click
        findViewById<Button>(R.id.btn_pay).setOnClickListener {
            val amountEdit: EditText = findViewById(R.id.et_amount_edit)
            val amount = amountEdit.text.toString()

            if (amount.isEmpty()) {
                return@setOnClickListener
            }
            getOrderId(amount)
        }
    }

    private fun getOrderId(amount: String) {
        val map = HashMap<String, String>()
        map["amount"] = amount
        retroInterface.getOrderId(map).enqueue(object : Callback<Order> {
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.body() != null) {
                    initiatePayment(amount, response.body()!!)
                }
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun initiatePayment(amount: String, order: Order) {
        //razorpay's
        val checkout = Checkout()
        checkout.setKeyID(order.getKeyId())
        checkout.setImage(R.drawable.ic_launcher_background)
        //values setting
        val paymentOptions = JSONObject()
        paymentOptions.put("name", "Mamazon")
        paymentOptions.put("amount", amount)
        paymentOptions.put("order_id", order.getOrderId())
        paymentOptions.put("currency", "USD")
        paymentOptions.put("description", "Reference no: #666")
        //opening checkout page
        checkout.open(
            this,
            paymentOptions
        )
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        TODO("Not yet implemented")
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        TODO("Not yet implemented")
    }
}