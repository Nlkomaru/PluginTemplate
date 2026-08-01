package dev.nikomaru.hardcoresupport.commands

import dev.nikomaru.hardcoresupport.data.PlayerDeathDAO
import dev.nikomaru.hardcoresupport.display.ScoreboardManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.incendo.cloud.annotations.Argument
import org.incendo.cloud.annotations.Command
import org.incendo.cloud.annotations.CommandDescription


@Command("hardcore-support")
class HardcoreSupportCommand {

    @Command("reset <player>")
    @CommandDescription("そのプレイヤーの死んだ回数をリセットします。")
    fun reset(sender: CommandSender, @Argument("player") player: Player) {
        PlayerDeathDAO.resetDeathCount(player.uniqueId)
        sender.sendMessage("§a${player.name}の死亡回数をリセットしました。")
        ScoreboardManager.displayDeathCount()
    }

    @Command("clear")
    @CommandDescription("すべての死んだ回数をリセットします。")
    fun clear(sender: CommandSender) {
        PlayerDeathDAO.resetAllDeathCounts()
        sender.sendMessage("§aすべての死亡回数をリセットしました。")
        ScoreboardManager.displayDeathCount()
    }

    @Command("check <player>")
    @CommandDescription("そのプレイヤーの死んだ回数を確認します。")
    fun check(sender: CommandSender, @Argument("player") player: Player) {
        val deathCount = PlayerDeathDAO.getDeathCount(player.uniqueId)
        sender.sendMessage("§a${player.name}の死亡回数: ${deathCount}回")
    }

    @Command("set <player> <amount>")
    @CommandDescription("そのプレイヤーの死んだ回数を設定します。")
    fun set(sender: CommandSender, @Argument("player") player: Player, @Argument("amount") amount: Int) {
        if (amount < 0) {
            sender.sendMessage("§c死亡回数は0以上の値を指定してください。")
            return
        }
        
        PlayerDeathDAO.setDeathCount(player.uniqueId, amount)
        sender.sendMessage("§a${player.name}の死亡回数を${amount}回に設定しました。")
        ScoreboardManager.displayDeathCount()
    }

    @Command("display")
    @CommandDescription("サーバーにいるすべての人にScoreboardで表示します。")
    fun display(sender: CommandSender) {
        ScoreboardManager.displayDeathCount()
        sender.sendMessage("§a死亡回数をスコアボードに表示しました。")
    }
    
    @Command("display hide")
    @CommandDescription("スコアボードを非表示にします。")
    fun hideDisplay(sender: CommandSender) {
        ScoreboardManager.hideScoreboard()
        sender.sendMessage("§a死亡回数のスコアボードを非表示にしました。")
    }
    
    @Command("stats")
    @CommandDescription("全プレイヤーの死亡統計を表示します。")
    fun stats(sender: CommandSender) {
        val allDeathCounts = PlayerDeathDAO.getAllDeathCounts()
        
        if (allDeathCounts.isEmpty()) {
            sender.sendMessage("§a死亡データがありません。")
            return
        }
        
        sender.sendMessage("§6=== 死亡統計 ===")
        allDeathCounts
            .sortedByDescending { it.deaths }
            .forEachIndexed { index, playerData ->
                val playerName = playerData.name ?: "不明なプレイヤー"
                sender.sendMessage("§7${index + 1}. §f${playerName}: §c${playerData.deaths}回")
            }
    }
}