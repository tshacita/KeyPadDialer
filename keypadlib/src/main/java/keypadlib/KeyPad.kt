package keypadlib

import android.content.Context

interface KeyPad {
    fun init(context: Context)
    fun setListener(listener: KeyPadListener)
}