package com.example.myegopokemon.app.main.java

import ApiInterface
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import data.MyEgoData
import data.MyPokemonData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    companion object {
        const val BASE_URL = "https://pokeapi.co/"
        const val TAG ="MyEgoPokemon"
        const val URL ="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/1.png"
    }
    private lateinit var tvImageCount :TextView
    private lateinit var ivPokemonImage :ImageView
    private lateinit var tvPokemonImageName :TextView
    private lateinit var btnChange :Button
    private var urlList  = mutableListOf<String>()
    private lateinit var jsonRetrofitApi :ApiInterface
    var pokemonList = mutableListOf<String>()
    var pokemonMap: HashMap<String, String> = HashMap()
    var btnClickCnt =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvImageCount = findViewById(R.id.tvImageCount)
        ivPokemonImage = findViewById(R.id.ivPokemonImage)
        tvPokemonImageName = findViewById(R.id.tvPokemonImageName)
        btnChange = findViewById(R.id.btnChange)
        if(isOnline()){
            initiateRetrofit()
            getDataCount()
            getAllData()
            changePokemonImage()
        }

    }
    /*
        initiate retrofit instance to consume web services
     */
    private fun initiateRetrofit(){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
        jsonRetrofitApi =retrofitBuilder.create(ApiInterface::class.java)

    }
    /*
        display total no of count
     */
    private fun getDataCount(){
        val retrofitDataCount =jsonRetrofitApi.getDataCount()
        retrofitDataCount.enqueue(object : Callback<MyPokemonData?> {
            override fun onResponse(
                call: Call<MyPokemonData?>,
                response: Response<MyPokemonData?>
            ) {
                val responseBody =response?.body()
                tvImageCount.setText("Count is "+responseBody?.count.toString())
                val data =responseBody?.results
                if (data != null) {
                    for(value in data){
                        pokemonList.add(value.name)
                        pokemonMap[value.name] =value.url
                    }
                    tvPokemonImageName.setText(pokemonList.first())
                }
            }

            override fun onFailure(call: Call<MyPokemonData?>, t: Throwable) {
                Log.e(TAG, "onFailure "+t.message)
                tvImageCount.setText("0")
            }
        })
    }
    /*
        get all the data from api
     */
    private fun getAllData(){
        val retrofitData =jsonRetrofitApi.getData()
        retrofitData.enqueue(object : Callback<MyEgoData> {
            override fun onResponse(
                call: Call<MyEgoData>,
                response: Response<MyEgoData>?
            ) {
                val responseBody =response?.body()
                if (responseBody != null) {
                    /*
                        getting all the .png images from sprites object as provided in the json object
                     */
                    addImageUrlToList(responseBody.sprites.back_default ?: URL)
                    addImageUrlToList(responseBody.sprites.back_female ?: URL)
                    addImageUrlToList(responseBody.sprites.back_shiny_female ?: URL)
                    addImageUrlToList(responseBody.sprites.front_default?: URL)
                    addImageUrlToList(responseBody.sprites.front_female ?: URL)
                    addImageUrlToList(responseBody.sprites.front_shiny ?: URL)
                    addImageUrlToList(responseBody.sprites.front_shiny_female ?: URL)
                    addImageUrlToList(responseBody.sprites.other.dream_world.front_default?: URL)
                    addImageUrlToList(responseBody.sprites.other.home.front_default ?: URL)
                    addImageUrlToList(responseBody.sprites.other.home.front_shiny?: URL)
                    addImageUrlToList(responseBody.sprites.other.officialartwork.front_default?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationi.redblue.back_default?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationi.redblue.back_gray ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationi.redblue.back_transparent?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationi.redblue.front_default?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationi.redblue.front_gray ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationi.redblue.front_transparent ?: URL)

                    addImageUrlToList(responseBody.sprites.versions.generationi.yellow.back_default ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationi.yellow.back_gray ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationi.yellow.back_transparent ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationi.yellow.front_default ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationi.yellow.front_gray ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationi.yellow.front_transparent ?: URL)

                    addImageUrlToList(responseBody.sprites.versions.generationii.crystal.back_default ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationii.crystal.back_shiny ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationii.crystal.back_transparent ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationii.crystal.front_default ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationii.crystal.front_shiny ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationii.crystal.front_transparent ?: URL)

                    addImageUrlToList(responseBody.sprites.versions.generationii.gold.back_default ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationii.gold.back_shiny ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationii.gold.front_default ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationii.gold.front_shiny ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationii.gold.front_transparent ?: URL)

                    addImageUrlToList(responseBody.sprites.versions.generationii.silver.back_default ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationii.silver.back_shiny ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationii.silver.front_default ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationii.silver.front_shiny ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationii.silver.front_transparent?: URL)

                    addImageUrlToList(responseBody.sprites.versions.generationiii.emerald.front_default ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationiii.emerald.front_shiny ?: URL)

                    addImageUrlToList(responseBody.sprites.versions.generationiii.fireredleafgreen.front_default ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationiii.fireredleafgreen.front_shiny ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationiii.fireredleafgreen.back_default ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationiii.fireredleafgreen.back_shiny ?: URL)

                    addImageUrlToList(responseBody.sprites.versions.generationiii.rubysapphire.front_default ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationiii.rubysapphire.front_shiny ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationiii.rubysapphire.back_default ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationiii.rubysapphire.back_shiny ?: URL)

                    addImageUrlToList(responseBody.sprites.versions.generationiv.diamondpearl.front_default ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationiv.diamondpearl.front_shiny ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationiv.diamondpearl.front_female ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationiv.diamondpearl.front_shiny_female ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationiv.diamondpearl.back_default ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationiv.diamondpearl.back_shiny ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationiv.diamondpearl.back_shiny_female ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationiv.diamondpearl.back_female ?: URL)

                    addImageUrlToList(responseBody.sprites.versions.generationiv.heartgoldsoulsilver.front_default ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationiv.heartgoldsoulsilver.front_shiny ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationiv.heartgoldsoulsilver.front_female ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationiv.heartgoldsoulsilver.front_shiny_female ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationiv.heartgoldsoulsilver.back_default ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationiv.heartgoldsoulsilver.back_shiny ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationiv.heartgoldsoulsilver.back_shiny_female ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationiv.heartgoldsoulsilver.back_female ?: URL)

                    addImageUrlToList(responseBody.sprites.versions.generationv.blackwhite.front_default ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationv.blackwhite.front_shiny ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationv.blackwhite.front_female ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationv.blackwhite.front_shiny_female ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationv.blackwhite.back_default ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationv.blackwhite.back_shiny ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationv.blackwhite.back_shiny_female ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationv.blackwhite.back_female ?: URL)

                    addImageUrlToList(responseBody.sprites.versions.generationv.blackwhite.animated.front_default ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationv.blackwhite.animated.front_shiny ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationv.blackwhite.animated.front_female ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationv.blackwhite.animated.front_shiny_female ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationv.blackwhite.animated.back_default ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationv.blackwhite.animated.back_shiny ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationv.blackwhite.animated.back_shiny_female ?: URL)
                    addImageUrlToList(responseBody.sprites.versions.generationv.blackwhite.animated.back_female ?: URL)

                }
            }

            override fun onFailure(call: Call<MyEgoData>, t: Throwable) {
                Log.e(TAG, "onFailure "+t.message)
            }
        })
    }
    /*
        add all urls to list
     */
    private fun addImageUrlToList(url :Any){
        urlList.add(url.toString())
        if(urlList.isNotEmpty()){
            Glide.with(this).load(urlList.first()).into(ivPokemonImage)
        }
    }

    /*
        on button click new images will be reflected on image view
     */
    private fun changePokemonImage(){
        btnChange.setOnClickListener {
            btnClickCnt ++
            Glide.with(this).load(urlList.get(btnClickCnt)).into(ivPokemonImage)

        }
    }
    /*
        validate Network Available or not
     */
    fun isOnline() :Boolean {
        val ConnectionManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = ConnectionManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            //Toast.makeText(this@MainActivity, "Network Available", Toast.LENGTH_LONG).show()
            return true
        } else {
            Toast.makeText(this@MainActivity, "Network Not Available", Toast.LENGTH_LONG).show()
            return false
        }
    }
}