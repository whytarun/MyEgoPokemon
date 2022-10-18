import data.MyEgoData
import data.MyPokemonData
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("api/v2/pokemon/ditto")
    fun getData(): Call<MyEgoData>

    @GET("api/v2/pokemon")
    fun getDataCount() : Call<MyPokemonData>
}