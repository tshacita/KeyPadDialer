package keypaddialer.feature.keypad.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class InventoryRequestModel (
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("releaseDate")
    val releaseDate: String? = null,
    @SerializedName("manufacturer")
    val manufacturer: Manufacturer? = null,
)

data class Manufacturer(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("homePage")
    val homePage: String? = null,
    @SerializedName("phone")
    val phone: String? = null,
)
