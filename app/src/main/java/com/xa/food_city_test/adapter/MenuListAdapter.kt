package com.xa.food_city_test.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xa.food_city_test.R
import com.xa.food_city_test.model.Menus
import kotlinx.android.synthetic.main.menu_list_row.view.*
import kotlinx.android.synthetic.main.recycle_restaurant_list_raw.view.*

class MenuListAdapter(val menuList: List<Menus?>?, val clickListener: MenuListClickListener) :
    RecyclerView.Adapter<MenuListAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.menu_list_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(menuList?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (menuList == null) 0 else menuList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val thumbImage = view.findViewById(R.id.thumbImage) as ImageView
        val menuName = view.menuName as TextView
        val menuPrice = view.menuPrice as TextView
        val addToCartButton = view.addToCartButton as TextView
        val addMoreLayout = view.addMoreLayout as LinearLayout
        val imageMinus = view.imageMinus as ImageView
        val imageAddOne = view.imageAddOne as ImageView
        val tvCount = view.tvCount as TextView
        fun bind(menus: Menus) {
            menuName.text = menus?.name
            menuPrice.text = "Price: "+menus?.price
            addToCartButton.setOnClickListener {
                menus.totalInCart = 1
                clickListener.addToCartClickListener(menus)
                addMoreLayout.visibility = View.VISIBLE
                addToCartButton.visibility = View.GONE
                tvCount.text = menus.totalInCart.toString()
            }
            imageMinus.setOnClickListener {
                var total:Int = menus.totalInCart
                total--
                if (total>0){
                    menus.totalInCart = total
                    clickListener.updateCardClickListener(menus)
                    tvCount.text = menus.totalInCart.toString()
                }else{
                    menus.totalInCart = total
                    clickListener.removeFromCartClickListener(menus)
                    addMoreLayout.visibility = View.GONE
                    addToCartButton.visibility = View.VISIBLE
                }
            }
            imageAddOne.setOnClickListener {
                var total:Int = menus.totalInCart
                total++
                if (total<=10){
                    menus.totalInCart = total
                    clickListener.updateCardClickListener(menus)
                    tvCount.text = menus.totalInCart.toString()
                }else{
                    menus.totalInCart = total
                    clickListener.removeFromCartClickListener(menus)
                    addMoreLayout.visibility = View.GONE
                    addToCartButton.visibility = View.VISIBLE
                }
            }

            Glide.with(thumbImage)
                .load(menus?.url)
                .into(thumbImage)

        }
    }

    interface MenuListClickListener {
        fun addToCartClickListener(menus: Menus)
        fun updateCardClickListener(menus: Menus)
        fun removeFromCartClickListener(menus: Menus)
    }
}