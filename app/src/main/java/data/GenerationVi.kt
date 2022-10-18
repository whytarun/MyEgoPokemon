package data

import com.google.gson.annotations.SerializedName

data class GenerationVi(
    @SerializedName("omegarubyalphasapphire")
    val omegarubyalphasapphire: OmegarubyAlphasapphire,
    @SerializedName("x-y")
    val xy: XY
)