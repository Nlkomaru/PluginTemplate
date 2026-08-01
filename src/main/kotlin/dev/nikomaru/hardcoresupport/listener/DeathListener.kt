package dev.nikomaru.hardcoresupport.listener

import dev.nikomaru.hardcoresupport.data.PlayerDeathDAO
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import dev.nikomaru.hardcoresupport.display.ScoreboardManager

class DeathListener : Listener {
    
    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val player = event.entity
        val uuid = player.uniqueId
        
        // プレイヤーの死亡回数を増やす
        val newDeathCount = PlayerDeathDAO.incrementDeathCount(uuid)
        
        // プレイヤーに死亡回数を通知
        player.sendMessage("§cあなたは${newDeathCount}回死亡しました。")
        ScoreboardManager.displayDeathCount()
    }
} 