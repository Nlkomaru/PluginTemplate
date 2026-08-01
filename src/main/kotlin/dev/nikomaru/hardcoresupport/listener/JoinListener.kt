package dev.nikomaru.hardcoresupport.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import dev.nikomaru.hardcoresupport.display.ScoreboardManager
import org.bukkit.event.player.PlayerJoinEvent

class JoinListener : Listener {
    
    @EventHandler
    fun onPlayerJoin(@Suppress("UNUSED_PARAMETER") event: PlayerJoinEvent) {
        ScoreboardManager.displayDeathCount()
    }
} 