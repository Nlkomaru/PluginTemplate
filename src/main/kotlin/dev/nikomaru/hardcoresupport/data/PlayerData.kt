package dev.nikomaru.hardcoresupport.data

import dev.nikomaru.hardcoresupport.utils.UUIDSerializer
import java.util.UUID
import kotlinx.serialization.Serializable

@Serializable
data class PlayerData(
        val uuid: @Serializable(with = UUIDSerializer::class) UUID,
        val name: String?,
        val deaths: Int
)

@Serializable
data class DeathData(
        val data: ArrayList<PlayerData> = arrayListOf(),
)
