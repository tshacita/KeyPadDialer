package keypaddialer.feature.keypad.data.service

import keypaddialer.feature.keypad.domain.model.InventoryRequestModel
import keypaddialer.feature.keypad.domain.model.InventoryResponseModel
import keypaddialer.domain.BaseResponse
import retrofit2.Response
import retrofit2.http.*


interface KeypadService {
    companion object {
        const val BASE_URL = "inventory"
    }

    @GET(BASE_URL)
    suspend fun getInventory(): Response<BaseResponse<List<InventoryResponseModel>>>

    @POST(BASE_URL)
    suspend fun postInventory(@Body model: InventoryRequestModel): Response<BaseResponse<Nothing>>
}
