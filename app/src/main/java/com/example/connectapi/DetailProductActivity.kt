package com.example.connectapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.connectapi.adapters.ProductAdapter
import com.example.connectapi.databinding.ActivityDetailProductBinding
import com.example.connectapi.models.Product
import com.example.connectapi.models.Rating
import org.json.JSONArray

class DetailProductActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id:Int = intent.getIntExtra("id",-1)

//        val product:Product = GetProductById(id)

        GetProductById(id)
//        Glide.with(binding.imgProductImage)
//            .load(product.image)
//            .into(binding.imgProductImage)
//        binding.txtProductTitle.setText(product.title)
//        binding.txtProductDesc.setText(id.toString())
    }

    private fun GetProductById(id: Int):Product {
        var productList = arrayListOf<Product>()
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://fakestoreapi.com/products"

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val responseArray = JSONArray(response)


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
                    if(product.id==id)
                    productList.add(product)
                }
                val adapter = ProductAdapter(productList)

            },
            { err ->
                err.printStackTrace()
                Toast.makeText(this, err.message, Toast.LENGTH_SHORT).show()
            })
        queue.add(stringRequest)
        return  productList[0]
    }
}