package data

data class MyPokemonData(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)