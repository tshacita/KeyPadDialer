package keypaddialer.feature.keypad.domain.usecase

interface KeypadInterface {
    fun deleteLastDigit()
    fun deleteCursorPosition(range: IntRange)
    fun validates(text: String?): Boolean
    fun inputField(text: String)
}