package com.example.pokemon_app

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var pkm: ImageView
    private lateinit var pkmName: TextView
    private lateinit var pkmHeight: TextView
    private lateinit var btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pkm = findViewById(R.id.Pokemon_Image)
        pkmName = findViewById(R.id.Pokemon_Name)
        pkmHeight = findViewById(R.id.Pokemon_Height)
        btn = findViewById(R.id.Next_Btn)

        getpokemonURL()
        getNextImage()

    }

    private fun getNextImage() {
        btn.setOnClickListener {
            val random = Random.nextInt(1,1000)
            getpokemonURL(random)
        }

    }

    private fun getpokemonURL(random: Int = 1) {
        val client = AsyncHttpClient()
        client["https://pokeapi.co/api/v2/pokemon/$random", object:JsonHttpResponseHandler(){
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String,
                throwable: Throwable?
            ) {
                Log.d("Pokemon URL error",response)
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                Log.d("Pokemon URL success","$json")
                if (json != null) {
                    pkmName.text = json.jsonObject.getString("name")
                    pkmHeight.text = json.jsonObject.getString("height")
                    var image = json.jsonObject.getJSONObject("sprites").getString("front_default")

                    Glide.with(this@MainActivity)
                        .load(image)
                        .fitCenter()
                        .into(pkm)

                }


            }

        }]
    }
}