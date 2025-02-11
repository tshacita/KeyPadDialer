package keypaddialer.extention

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response

inline fun <reified T> Response<T>.convertError(): T {
    return Gson().fromJson(this.errorBody()?.string() ?: "")
}

inline fun <reified T> Gson.fromJson(json: String): T = this.fromJson<T>(json, object : TypeToken<T>() {}.type)