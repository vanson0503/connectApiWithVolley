package com.example.connectapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.connectapi.adapters.ProductAdapter
import com.example.connectapi.databinding.ActivityMainBinding
import com.example.connectapi.models.Product
import com.example.connectapi.models.Rating
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        callProductApi(binding.rvListData,binding.pbLoading)
    }

    private fun callProductApi(rvListData: RecyclerView, pbLoading: ProgressBar) {
        pbLoading.visibility = View.VISIBLE

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://fakestoreapi.com/products"

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val productList =  callManuallyData(response)

                val adapter = ProductAdapter(productList)
                rvListData.adapter = adapter
                rvListData.visibility = View.VISIBLE
                pbLoading.visibility = View.GONE

            },
            { err ->
                err.printStackTrace()
                pbLoading.visibility = View.GONE
                Toast.makeText(this, err.message, Toast.LENGTH_SHORT).show()
            })
        queue.add(stringRequest)
    }

    private fun callManuallyData(response: String?):ArrayList<Product>{
        val responseArray = JSONArray(response)
        var productList = arrayListOf<Product>()

        for(index in 0 until responseArray.length()){
            val productObj = responseArray.getJSONObject(index)
            val ratingObj = productObj.getJSONObject("rating")


            val product = Product(productObj.getInt("id"),
                productObj.getString("title"),
                productObj.getString("description"),
                productObj.getString("category"),
                productObj.getString("image"),
                productObj.getDouble("price"),
                Rating(
                    ratingObj.getInt("count"),
                    ratingObj.getDouble("rate")
                )
            )
            productList.add(product)
        }
        return  productList
    }
}