package keypaddialer.domain

sealed class Result<out T : Any> {
    data class Success<T : Any>(val data: T?) : Result<T>()
    data class Errors<T : Any>(val data: T?, val code: Int? = 0) : Result<T>()
    data class Failure(val error: String?, val code: Int? = 0) : Result<Nothing>()
    data class Loading(val isLoad: Boolean) : Result<Nothing>()
    data class Process(val isProcess: Boolean, val process: Int) : Result<Nothing>()
}