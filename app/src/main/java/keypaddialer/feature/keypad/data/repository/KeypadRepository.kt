package keypaddialer.feature.keypad.data.repository

import keypaddialer.feature.keypad.data.datasource.KeypadDatasource
import keypaddialer.feature.keypad.domain.model.InventoryRequestModel

class KeypadRepository(private val remoteDatasource: KeypadDatasource) {
    suspend fun getInventory() = remoteDatasource.getInventory()
    suspend fun postInventory(model: InventoryRequestModel) = remoteDatasource.postInventory(model)
    fun getNumPad() = remoteDatasource.getNumPad()
}