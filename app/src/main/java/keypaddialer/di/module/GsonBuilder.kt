package keypaddialer.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder

class GsonBuilder {
    fun build(): Gson {
        return GsonBuilder()
            .setPrettyPrinting()
            .create()
    }
}