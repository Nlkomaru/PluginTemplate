package dev.nikomaru.template

import dev.nikomaru.template.commands.HelpCommand
import org.bukkit.plugin.java.JavaPlugin
import revxrsal.commands.bukkit.BukkitCommandHandler
import revxrsal.commands.ktx.supportSuspendFunctions

class Template : JavaPlugin() {

    companion object {
        lateinit var plugin: Template
            private set
    }
    override fun onEnable() {
        // Plugin startup logic
        plugin = this
        setCommand()
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    fun setCommand() {
        val handler = BukkitCommandHandler.create(this)

        handler.setSwitchPrefix("--")
        handler.setFlagPrefix("--")
        handler.supportSuspendFunctions()

        handler.setHelpWriter { command, actor ->
            java.lang.String.format(
                """
                <color:yellow>コマンド: <color:gray>%s
                <color:yellow>使用方法: <color:gray>%s
                <color:yellow>説明: <color:gray>%s
                
                """.trimIndent(),
                command.path.toList(),
                command.usage,
                command.description,
            )
        }

        with(handler) {
            register(HelpCommand())
        }
    }
}