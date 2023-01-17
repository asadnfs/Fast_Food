package com.xa.food_city_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.xa.food_city_test.adapter.PlaceYourOrderAdapter
import com.xa.food_city_test.model.Menus
import com.xa.food_city_test.model.RestaurentModel
import kotlinx.android.synthetic.main.activity_place_your_order.*
import kotlinx.android.synthetic.main.activity_restaurant_menu.*
import kotlinx.android.synthetic.main.placeyourorder_list_row.*
import kotlin.math.sign

class PlaceYourOrderActivity : AppCompatActivity() {
    var placeYourOrderAdapter: PlaceYourOrderAdapter? = null
    private var totalItemCartCount = 0
    var isDeliveryOn: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_your_order)

        val restaurantModel = intent.getParcelableExtra<RestaurentModel>("restaurantModel")

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setTitle(restaurantModel?.name)
        actionBar?.setSubtitle(restaurantModel?.address)
        actionBar?.setDisplayHomeAsUpEnabled(true)


        switchDelivery?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                inputAddress.visibility = View.VISIBLE
                inputCity.visibility = View.VISIBLE
                inputState.visibility = View.VISIBLE
                inputZip.visibility = View.VISIBLE
                tvDeliveryCharge.visibility = View.VISIBLE
                tvDeliveryChargeAmount.visibility = View.VISIBLE
                isDeliveryOn = true
                calculationTotalAmount(restaurantModel)

            } else {
                inputAddress.visibility = View.GONE
                inputCity.visibility = View.GONE
                inputState.visibility = View.GONE
                inputZip.visibility = View.GONE
                tvDeliveryCharge.visibility = View.GONE
                tvDeliveryChargeAmount.visibility = View.GONE
                isDeliveryOn = false
                calculationTotalAmount(restaurantModel)
            }

        }

        buttonPlaceYourOrder.setOnClickListener {
            onPlaceYourOrderClick(restaurantModel)
        }
        initRecyclerView(restaurantModel)
        calculationTotalAmount(restaurantModel)
        return
    }

    private fun onPlaceYourOrderClick(restaurantModel: RestaurentModel?) {
        if (TextUtils.isEmpty(inputName.text.toString())) {
            inputName.error = "Enter your name"
            return
        } else if (isDeliveryOn && TextUtils.isEmpty(inputAddress.text.toString())) {
            inputAddress.error = "Enter your address"
            return
        } else if (isDeliveryOn && TextUtils.isEmpty(inputCity.text.toString())) {
            inputCity.error = "Enter your City Name"
            return
        } else if (isDeliveryOn && TextUtils.isEmpty(inputZip.text.toString())) {
            inputZip.error = "Enter your Zip code"
            return
        } else if (TextUtils.isEmpty(inputCardNumber.text.toString())) {
            inputCardNumber.error = "Enter your credit card number"
            return
        } else if (TextUtils.isEmpty(inputCardExpiry.text.toString())) {
            inputCardExpiry.error = "Enter your credit card expiry"
            return
        } else if (TextUtils.isEmpty(inputCardPin.text.toString())) {
            inputCardPin.error = "Enter your credit card pin/cvv"
            return
        }
        val intent = Intent(this@PlaceYourOrderActivity, SuccessOrderActivity::class.java)
        intent.putExtra("RestaurantModel", restaurantModel)
        startActivityForResult(intent, 1000)

    }

    private fun calculationTotalAmount(restaurantModel: RestaurentModel?) {
        var subTotalAmount = 0f
        for (menu in restaurantModel?.menus!!) {
            subTotalAmount += menu?.price!! * menu.totalInCart
        }
        tvSubtotalAmount.text = "$" + String.format("%.2f", subTotalAmount)
        if (isDeliveryOn) {
            tvDeliveryChargeAmount.text =
                "$" + String.format("%.2f", restaurantModel.delivery_charge?.toFloat())
            subTotalAmount += restaurantModel?.delivery_charge?.toFloat()!!
        }
        tvTotalAmount.text = "$" + String.format("%.2f", subTotalAmount)

    }

    private fun initRecyclerView(restaurantModel: RestaurentModel?) {
        cartItemsRecyclerView.layoutManager = LinearLayoutManager(this)
        placeYourOrderAdapter = PlaceYourOrderAdapter(restaurantModel?.menus)
        cartItemsRecyclerView.adapter = placeYourOrderAdapter
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
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            setResult(RESULT_OK)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_CANCELED)
        finish()
    }




}