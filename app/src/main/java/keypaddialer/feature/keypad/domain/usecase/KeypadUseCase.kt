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

    private val regex1 = """(\d{3})""".toRegex()
    private val regex2 = """(\d{3})(\d{3})""".toRegex()
    private val regex3 = """(\d{3})(\d{3})(\d{4})""".toRegex()

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
            val data = result.data
            val item = arrayListOf<String>()
            data?.let {
                item.addAll(it)
            }
            item.add("(")
            item.add(")")
            item.add("-")

            text?.let {
                for (t in text) {
                    if (!item.contains(t.toString())) {
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

    override fun formatNum(text: String?, index: Int): String {
        if (text?.isEmpty() == true) {
            return ""
        } else {
            when (index) {
                3 -> {
                    return  regex1.replace(text?.replace("(","")
                        ?: "","($1)")
                }
                8 -> {
                    return regex2.replace(text?.replace("(","")
                        ?.replace(")","")
                        ?: "", "($1)-$2")
                }
                13 -> {
                    return regex3.replace(text?.replace("(","")
                        ?.replace(")","")
                        ?.replace("-", "")
                        ?: "", "($1)-$2-$3")
                }
                else -> {
                    return text ?: ""
                }
            }
        }
    }
}