package keypaddialer.feature.keypad.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData

import androidx.lifecycle.viewModelScope
import keypaddialer.feature.keypad.domain.model.InventoryRequestModel
import keypaddialer.feature.keypad.domain.model.InventoryResponseModel
import keypaddialer.domain.BaseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import keypaddialer.domain.Result
import keypaddialer.feature.keypad.domain.model.Manufacturer
import keypaddialer.feature.keypad.domain.usecase.KeypadUseCase

class KeypadViewModel(
    private val useCase: KeypadUseCase,
) : ViewModel() {

    private val _mainResponse = MutableLiveData<Result<BaseResponse<List<InventoryResponseModel>>>>()
    val mainResponse: LiveData<Result<BaseResponse<List<InventoryResponseModel>>>> = _mainResponse

    private val _saveResponse = MutableLiveData<Result<BaseResponse<Nothing>>>()
    val saveResponse: LiveData<Result<BaseResponse<Nothing>>> = _saveResponse

    private var _numPad =  MutableLiveData<List<String?>>()
    val numPad: LiveData<List<String?>> = _numPad

    val item = useCase.item.asLiveData()

    init {
        fetchItems()
    }

    private fun fetchItems() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                useCase.getNumPad()
            }.apply {
                if (this is Result.Success) {
                    _numPad.value = this.data ?: arrayListOf()
                }
            }
        }
    }

    private fun mockData(): InventoryRequestModel {
        return InventoryRequestModel(
            id = "d290f1ee-6c54-4b01-90e6-d701748f0852",
            name = "Police Hotline",
            manufacturer = Manufacturer(
                name = "Police Hotline",
                homePage = "https://www.royalthaipolice.go.th",
                phone = "191"
            )
        )
    }

    fun deleteLastDigit() {
        useCase.deleteLastDigit()
    }

    fun deleteCursorPosition(range: IntRange) {
        useCase.deleteCursorPosition(range = range)
    }

    fun validates(text: String?): Boolean {
        return useCase.validates(text = text)
    }

    fun inputField(text: String) {
        useCase.inputField(text = text)
    }
}
