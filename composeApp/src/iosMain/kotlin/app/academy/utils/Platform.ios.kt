package app.academy.utils

class IOSPlatform : Platform {
    override val name: String
        get() = "iOS"
}

actual fun getPlatform(): Platform {
    return IOSPlatform()
}
