package dev.nikomaru.hardcoresupport.utils

import kotlinx.serialization.json.Json

object Utils {
    val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        encodeDefaults = true
    }
}