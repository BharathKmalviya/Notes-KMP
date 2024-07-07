package app.academy.utils

/**
 * A class that is used to generate a unique UUID (Universally Unique Identifier) string.
 */
actual object UUID {
    actual fun randomUUIDString(): String = java.util.UUID.randomUUID().toString()
}
