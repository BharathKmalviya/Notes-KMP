package app.architect.notes.utils

import app.academy.utils.getPlatform

class Greeting {
    private val platform = getPlatform()

    fun greet(text:String): String {
        return "$text, ${platform.name}!"
    }

    fun hello(): String {
        return "I am all native in shared module"
    }
}
