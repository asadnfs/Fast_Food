package com.xa.food_city_test

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.xa.food_city_test.adapter.RestaurantListAdapter
import com.xa.food_city_test.model.RestaurentModel
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.io.StringWriter
import java.io.Writer

class MainActivity : AppCompatActivity() ,RestaurantListAdapter.RestaurantClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setTitle("Food app")


        val restaurantModel = getRestaurantData()
        initRecycleView(restaurantModel)


    }


    private fun initRecycleView(restaurantList:List<RestaurentModel?>?) {
        val recyclerViewRestaurant = findViewById<RecyclerView>(R.id.recycle_viewRestaurant)
        recyclerViewRestaurant.layoutManager = GridLayoutManager(this,2)
        val adapter = RestaurantListAdapter(restaurantList,this)
        recyclerViewRestaurant.adapter = adapter


    }

    private fun getRestaurantData(): List<RestaurentModel?>? {

        val inputStream: InputStream = resources.openRawResource(R.raw.restaurent)
        val writer: Writer = StringWriter()
        val buffer = CharArray(1024)
        try {
            val reader: Reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
            var n: Int
            while (reader.read(buffer).also { n = it } != -1) {
                writer.write(buffer, 0, n)
            }
        } catch (e: Exception) {
        }
        val jsonStr: String = writer.toString()
        val gson = Gson()
        val restaurantModel = gson.fromJson<Array<RestaurentModel>>(
            jsonStr,
            Array<RestaurentModel>::class.java).toList()
        return restaurantModel

    }

    override fun onItemClick(restaurentModel: RestaurentModel) {
        val intent  = Intent(this,RestaurantMenuActivity::class.java)
        intent.putExtra("restaurantModel",restaurentModel)
        startActivity(intent)
    }



}