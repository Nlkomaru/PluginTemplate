package dev.nikomaru.hardcoresupport.data

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

object PlayerDeathDAO {

    /**
     * プレイヤーの死亡回数を取得する
     * @param playerUuid プレイヤーのUUID
     * @return 死亡回数
     */
    fun getDeathCount(playerUuid: UUID): Int {
        return transaction {
            PlayerData.selectAll().where { PlayerData.playerUuid eq playerUuid }
                .map { it[PlayerData.deathCount] }
                .firstOrNull() ?: 0
        }
    }

    /**
     * プレイヤーの死亡回数を1増やす
     * @param playerUuid プレイヤーのUUID
     * @return 更新後の死亡回数
     */
    fun incrementDeathCount(playerUuid: UUID): Int {
        return transaction {
            val currentCount = getDeathCount(playerUuid)
            val newCount = currentCount + 1

            val existingRow = PlayerData.selectAll().where { PlayerData.playerUuid eq playerUuid }.firstOrNull()

            if (existingRow == null) {
                // 新規プレイヤーの場合は挿入
                PlayerData.insert {
                    it[PlayerData.playerUuid] = playerUuid
                    it[deathCount] = newCount
                }
            } else {
                // 既存プレイヤーの場合は更新
                PlayerData.update({ PlayerData.playerUuid eq playerUuid }) {
                    it[deathCount] = newCount
                }
            }

            newCount
        }
    }

    /**
     * プレイヤーの死亡回数をリセットする
     * @param playerUuid プレイヤーのUUID
     */
    fun resetDeathCount(playerUuid: UUID) {
        transaction {
            PlayerData.update({ PlayerData.playerUuid eq playerUuid }) {
                it[deathCount] = 0
            }
        }
    }

    /**
     * 全プレイヤーの死亡データを取得する
     * @return プレイヤーUUIDと死亡回数のマップ
     */
    fun getAllDeathCounts(): Map<UUID, Int> {
        return transaction {
            PlayerData.selectAll()
                .associate {
                    it[PlayerData.playerUuid] to it[PlayerData.deathCount]
                }
        }
    }

    /**
     * 全プレイヤーの死亡データをリセットする
     */
    fun resetAllDeathCounts() {
        transaction {
            PlayerData.deleteAll()
        }
    }
}