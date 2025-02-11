package keypaddialer.feature.keypad.data.datasource

import keypaddialer.feature.keypad.domain.model.InventoryRequestModel
import keypaddialer.feature.keypad.domain.model.InventoryResponseModel
import keypaddialer.domain.BaseResponse
import keypaddialer.domain.Result

interface KeypadDatasource {
    suspend fun getInventory(): Result<BaseResponse<List<InventoryResponseModel>>>
    suspend fun postInventory(model: InventoryRequestModel): Result<BaseResponse<Nothing>>
    fun getNumPad(): Result<List<String>>
}