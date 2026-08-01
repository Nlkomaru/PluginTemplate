package dev.nikomaru.hardcoresupport.data

import java.util.UUID
import kotlinx.serialization.Serializable

@Serializable
data class PlayerData(
    val uuid: UUID,
    val name: String,
    val deaths: Int
)
