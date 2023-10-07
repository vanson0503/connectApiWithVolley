package com.example.connectapi.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.connectapi.DetailProductActivity
import com.example.connectapi.R
import com.example.connectapi.models.Product
import com.google.android.material.card.MaterialCardView

class ProductAdapter(private val productList: ArrayList<Product>):RecyclerView.Adapter<ProductAdapter.ProductViewHolder>()
{

    class ProductViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val productTitle = itemView.findViewById<TextView>(R.id.txtProductTitle)
        val productImgage = itemView.findViewById<ImageView>(R.id.imgProductImage)
        val productPrice = itemView.findViewById<TextView>(R.id.txtProductPrice)
        val productDetail = itemView.findViewById<MaterialCardView>(R.id.loProductDetail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_product,parent,false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.itemView.apply {
            Glide.with(holder.productImgage)
                .load(product.image)
                .into(holder.productImgage)
            holder.productTitle.setText(product.title)
            holder.productPrice.setText("${product.price.toString()}$")
            holder.productDetail.setOnClickListener{
//                val intent = Intent(holder.itemView.context,DetailProductActivity::class.java).apply {
//                    putExtra("id",productList[position].id)
//                }
//                holder.itemView.context.startActivity(intent)
                
            }
        }
    }

}