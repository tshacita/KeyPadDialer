package keypadlib

class NativeLib {
    companion object {
        // Used to load the 'keypadlib' library on application startup.
        init {
            System.loadLibrary("keypadlib")
        }
    }
}