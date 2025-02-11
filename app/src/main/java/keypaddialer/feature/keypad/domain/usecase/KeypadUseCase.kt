package keypaddialer.feature.keypad.domain.usecase

import keypaddialer.feature.keypad.data.repository.KeypadRepository
import keypaddialer.feature.keypad.domain.model.InventoryRequestModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import keypaddialer.domain.Result


class KeypadUseCase(
    private val repository: KeypadRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
): KeypadInterface {
    private val _item = MutableStateFlow("")
    val item: Flow<String> = _item

    suspend operator fun invoke() = repository.getInventory()
    suspend operator fun invoke(model: InventoryRequestModel) = repository.postInventory(model)
    fun getNumPad() = repository.getNumPad()

    override fun deleteLastDigit() {
        _item.value = _item.value.dropLast( 1)
    }

    override fun deleteCursorPosition(range: IntRange) {
        _item.value = _item.value.removeRange(range = range)
    }

    override fun validates(text: String?): Boolean {
        val result = repository.getNumPad()
        if (result is Result.Success) {
            val item = result.data
            text?.let {
                for (t in text) {
                    if (item?.contains(t.toString()) != true) {
                        return false
                    }
                }
                return true
            }
            return false
        } else return false
    }

    override fun inputField(text: String) {
        _item.value = text
    }
}