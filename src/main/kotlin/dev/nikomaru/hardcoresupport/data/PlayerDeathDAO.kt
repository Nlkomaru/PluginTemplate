package dev.nikomaru.hardcoresupport.data

import dev.nikomaru.hardcoresupport.HardcoreSupport
import dev.nikomaru.hardcoresupport.utils.Utils
import java.io.File
import java.util.UUID
import org.bukkit.Bukkit

object PlayerDeathDAO {

    private val dataFile: File
        get() = HardcoreSupport.plugin.dataFolder.resolve("data.json")

    /**
     * データファイルを読み込む
     * ファイルが存在しない場合は新規作成する
     */
    private fun loadData(): DeathData {
        if (!dataFile.exists()) {
            dataFile.parentFile.mkdirs()
            dataFile.createNewFile()
            val emptyData = DeathData()
            dataFile.writeText(Utils.json.encodeToString(emptyData))
            return emptyData
        }
        return Utils.json.decodeFromString<DeathData>(dataFile.readText())
    }

    /**
     * データファイルに保存する
     */
    private fun saveData(data: DeathData) {
        dataFile.writeText(Utils.json.encodeToString(data))
    }

    /**
     * プレイヤーデータを取得または作成する
     */
    private fun getOrCreatePlayerData(playerUuid: UUID, data: DeathData): PlayerData {
        return data.data.find { it.uuid == playerUuid }
            ?: PlayerData(playerUuid, Bukkit.getOfflinePlayer(playerUuid).name, 0)
    }

    /**
     * プレイヤーの死亡回数を取得する
     * @param playerUuid プレイヤーのUUID
     * @return 死亡回数
     */
    fun getDeathCount(playerUuid: UUID): Int {
        val data = loadData()
        val playerData = getOrCreatePlayerData(playerUuid, data)
        return playerData.deaths
    }

    /**
     * プレイヤーの死亡回数を1増やす
     * @param playerUuid プレイヤーのUUID
     * @return 更新後の死亡回数
     */
    fun incrementDeathCount(playerUuid: UUID): Int {
        val data = loadData()
        val playerData = getOrCreatePlayerData(playerUuid, data)
        val newDeaths = playerData.deaths + 1
        
        val newList = data.data.toMutableList()
        newList.removeIf { it.uuid == playerUuid }
        newList.add(playerData.copy(deaths = newDeaths))
        
        saveData(DeathData(ArrayList(newList)))
        return newDeaths
    }

    /**
     * プレイヤーの死亡回数を設定する
     * @param playerUuid プレイヤーのUUID
     * @param deaths 設定する死亡回数
     */
    fun setDeathCount(playerUuid: UUID, deaths: Int) {
        val data = loadData()
        val playerData = getOrCreatePlayerData(playerUuid, data)
        
        val newList = data.data.toMutableList()
        newList.removeIf { it.uuid == playerUuid }
        newList.add(playerData.copy(deaths = deaths))
        
        saveData(DeathData(ArrayList(newList)))
    }

    /**
     * プレイヤーの死亡回数をリセットする
     * @param playerUuid プレイヤーのUUID
     */
    fun resetDeathCount(playerUuid: UUID) {
        setDeathCount(playerUuid, 0)
    }

    /**
     * 全プレイヤーの死亡データを取得する
     * @return プレイヤーデータのリスト
     */
    fun getAllDeathCounts(): ArrayList<PlayerData> {
        val data = loadData()
        return data.data
    }

    /**
     * 全プレイヤーの死亡データをリセットする
     */
    fun resetAllDeathCounts() {
        saveData(DeathData())
    }
}