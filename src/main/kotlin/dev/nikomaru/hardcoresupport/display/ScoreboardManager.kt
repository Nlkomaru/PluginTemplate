package dev.nikomaru.hardcoresupport.display

import dev.nikomaru.hardcoresupport.data.PlayerDeathDAO
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.scoreboard.Criteria
import org.bukkit.scoreboard.DisplaySlot

object ScoreboardManager {
    
    /**
     * すべてのプレイヤーに死亡回数のScoreboardを表示する
     */
    fun displayDeathCount() {
        val manager = Bukkit.getScoreboardManager()
        val board = manager.newScoreboard
        val objective = board.registerNewObjective("death_count", Criteria.DUMMY, MiniMessage.miniMessage().deserialize("死亡回数"))

        objective.displaySlot = DisplaySlot.SIDEBAR

        val deathData = PlayerDeathDAO.getAllDeathCounts()
        deathData
            .sortedByDescending { it.deaths }
            .take(15) // スコアボードは最大15行まで表示
            .forEachIndexed { index, playerData ->
                val playerName = playerData.name ?: "不明なプレイヤー"
                val score = objective.getScore("§f${playerName} §c${playerData.deaths}回")
                score.score = deathData.size - index // 降順で表示するため
            }

        val onlinePlayers = Bukkit.getOnlinePlayers()
        onlinePlayers.forEach { it.scoreboard = board }
    }
    
    /**
     * Scoreboardを非表示にする
     */
    fun hideScoreboard() {
        val manager = Bukkit.getScoreboardManager()
        val emptyBoard = manager.newScoreboard
        
        Bukkit.getOnlinePlayers().forEach { it.scoreboard = emptyBoard }
    }
} 