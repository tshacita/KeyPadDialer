package keypaddialer.domain

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("success")
    val success: Boolean = false,
    @SerializedName("code")
    val statusCode: Int = 0,
    @SerializedName("message")
    val msg: String = "",
    @SerializedName("data")
    val response: T,
    @SerializedName("error")
    val error: String = "",
)
