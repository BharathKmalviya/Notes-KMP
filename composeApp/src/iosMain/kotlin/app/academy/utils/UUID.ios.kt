package app.academy.utils

import platform.Foundation.NSUUID

/**
 * A class that is used to generate a unique UUID (Universally Unique Identifier) string.
 */
actual object UUID {
    actual fun randomUUIDString(): String = NSUUID().UUIDString
}
