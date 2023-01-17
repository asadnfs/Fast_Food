package com.xa.food_city_test.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xa.food_city_test.R
import com.xa.food_city_test.model.Menus
import kotlinx.android.synthetic.main.activity_restaurant_menu.*
import kotlinx.android.synthetic.main.placeyourorder_list_row.*

class PlaceYourOrderAdapter(
    val menuList: List<Menus?>?
) : RecyclerView.Adapter<PlaceYourOrderAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.placeyourorder_list_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(menuList?.get(position)!!)

    }

    override fun getItemCount(): Int {
        return if (menuList == null) 0 else menuList!!.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val thumbImage: ImageView = view.findViewById(R.id.thumbImage)
        val menuName: TextView = view.findViewById(R.id.menuName)
        val menuPrice: TextView = view.findViewById(R.id.menuPrice)
        val menuQty: TextView = view.findViewById(R.id.menuQty)


        fun bind(menu: Menus) {

            menuName.text = menu?.name!!
            menuPrice.text = "Price $" + String.format("%.2f", menu.price * menu.totalInCart)
            menuQty.text = "Qty: " + menu?.totalInCart


            Glide.with(thumbImage)
                .load(menu.url)
                .into(thumbImage)


        }

    }


}