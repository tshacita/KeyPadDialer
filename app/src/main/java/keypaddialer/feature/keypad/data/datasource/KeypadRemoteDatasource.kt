package keypaddialer.feature.keypad.data.datasource

import co.th.touchtechnologies.keypaddialer.BuildConfig
import com.google.gson.Gson
import keypaddialer.feature.keypad.domain.model.InventoryRequestModel
import keypaddialer.feature.keypad.domain.model.InventoryResponseModel
import keypaddialer.domain.BaseResponse
import keypaddialer.domain.Result
import keypaddialer.extention.convertError
import keypaddialer.feature.keypad.data.service.KeypadService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class KeypadRemoteDatasource(
    private val okHttpClient: OkHttpClient,
    private val gson: Gson
) : KeypadDatasource {

    companion object {
        private val numPad = arrayListOf("1","2","3","4","5","6","7","8","9","*","0","#")
    }

    private val service = Retrofit.Builder()
        .baseUrl(BuildConfig.API_DOMAIN)
        .client(okHttpClient)
        .addConverterFactory(
            GsonConverterFactory.create(gson)
        )
        .build()
        .create(KeypadService::class.java)

    override suspend fun getInventory(): Result<BaseResponse<List<InventoryResponseModel>>> {
        val response = service.getInventory()
        return try {
            when (response.code()) {
                in 200..299 -> Result.Success(response.body())
                else -> {
                    Result.Errors(response.convertError(), response.code())
                }
            }
        } catch (throwable: IOException) {
            Result.Failure(throwable.message)
        }
    }

    override suspend fun postInventory(model: InventoryRequestModel): Result<BaseResponse<Nothing>> {
        val response = service.postInventory(model)
        return try {
            when (response.code()) {
                in 200..299 -> Result.Success(response.body())
                else -> {
                    Result.Errors(response.convertError(), response.code())
                }
            }
        } catch (throwable: IOException) {
            Result.Failure(throwable.message)
        }
    }

    override fun getNumPad(): Result<List<String>> {
        return Result.Success(numPad)
    }
}