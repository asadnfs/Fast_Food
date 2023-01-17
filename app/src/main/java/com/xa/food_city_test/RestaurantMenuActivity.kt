package com.xa.food_city_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.GridLayoutManager
import com.xa.food_city_test.adapter.MenuListAdapter
import com.xa.food_city_test.adapter.RestaurantListAdapter
import com.xa.food_city_test.model.Menus
import com.xa.food_city_test.model.RestaurentModel
import kotlinx.android.synthetic.main.activity_restaurant_menu.*

class RestaurantMenuActivity : AppCompatActivity(), MenuListAdapter.MenuListClickListener {

    private var itemInTheCartList: MutableList<Menus?>? = null
    private var totalItemCartCount = 0
    private var menuList: List<Menus?>? = null
    private var menuListAdapter: MenuListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_menu)
        checkoutButton.setOnClickListener {
            Toast.makeText(this, "empty", Toast.LENGTH_SHORT).show()
        }


        //get from mainactivity
        val restaurantModel = intent.getParcelableExtra<RestaurentModel>("restaurantModel")

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setTitle(restaurantModel?.name)
        actionBar?.setSubtitle(restaurantModel?.address)
        actionBar?.setDisplayHomeAsUpEnabled(true)



        menuList = restaurantModel?.menus

        initRecycleView(menuList)

        checkoutButton.setOnClickListener {
            if (itemInTheCartList != null && itemInTheCartList!!.size <= 0){
                Toast.makeText(this, "empty", Toast.LENGTH_SHORT).show()
            }else{
                restaurantModel?.menus = itemInTheCartList
                val intent = Intent(this,PlaceYourOrderActivity::class.java)
                intent.putExtra("restaurantModel",restaurantModel)
                startActivityForResult(intent,1000)
            }
        }

    }

    private fun initRecycleView(menus: List<Menus?>?) {
        menuRecyclerVuew.layoutManager = GridLayoutManager(this, 2)
        menuListAdapter = MenuListAdapter(menus, this)
        menuRecyclerVuew.adapter = menuListAdapter
    }

    override fun addToCartClickListener(menu: Menus) {
        if (itemInTheCartList == null) {
            itemInTheCartList = ArrayList()
        }
        itemInTheCartList?.add(menu)
        totalItemCartCount = 0
        for (menu in itemInTheCartList!!) {
            totalItemCartCount = totalItemCartCount + menu?.totalInCart!!
        }
        checkoutButton.text = "Checkout ($totalItemCartCount) Items"

    }

    override fun updateCardClickListener(menu: Menus) {
        val index = itemInTheCartList!!.indexOf(menu)
        itemInTheCartList?.removeAt(index)
        itemInTheCartList?.add(menu)
        totalItemCartCount = 0
        for (menu in itemInTheCartList!!) {
            totalItemCartCount += menu?.totalInCart!!
        }
        checkoutButton.text = "Checkout ($totalItemCartCount) Items"

    }

    override fun removeFromCartClickListener(menu: Menus) {
        if (itemInTheCartList!!.contains(menu)) {
            itemInTheCartList?.remove(menu)
            totalItemCartCount = 0
            for (menu in itemInTheCartList!!) {
                totalItemCartCount += menu?.totalInCart!!
            }
            checkoutButton.text = "checkout ($totalItemCartCount) Item"
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            else -> {}
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000 && resultCode == RESULT_OK){
            finish()
        }
    }



}