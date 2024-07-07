package app.academy.utils

class AndroidPlatform : Platform {
    override val name: String
        get() = "Android"
}

actual fun getPlatform(): Platform {
    return AndroidPlatform()
}
