package dev.nikomaru.hardcoresupport

import dev.nikomaru.hardcoresupport.commands.HardcoreSupportCommand
import dev.nikomaru.hardcoresupport.listener.DeathListener
import dev.nikomaru.hardcoresupport.listener.JoinListener
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import org.incendo.cloud.annotations.AnnotationParser
import org.incendo.cloud.execution.ExecutionCoordinator
import org.incendo.cloud.paper.LegacyPaperCommandManager
import org.incendo.cloud.setting.ManagerSetting
import org.koin.core.context.GlobalContext
import org.koin.dsl.module

open class HardcoreSupport : JavaPlugin() {

    companion object {
        lateinit var plugin: HardcoreSupport
            private set
    }

    override fun onEnable() {
        // Plugin startup logic
        plugin = this
        setCommand()
        setupKoin()
        registerListeners()
    }

    private fun setupKoin() {
        val appModule = module {
            single<HardcoreSupport> { this@HardcoreSupport }
        }

        GlobalContext.getOrNull() ?: GlobalContext.startKoin {
            modules(appModule)
        }
    }

    private fun registerListeners() {
        server.pluginManager.registerEvents(DeathListener(), this)
        server.pluginManager.registerEvents(JoinListener(), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    fun setCommand() {
        val commandManager = LegacyPaperCommandManager.createNative(
            this,
            ExecutionCoordinator.simpleCoordinator()
        )

        commandManager.settings().set(ManagerSetting.ALLOW_UNSAFE_REGISTRATION, true)

        val annotationParser = AnnotationParser<CommandSender>(commandManager, CommandSender::class.java)

        annotationParser.parse(HardcoreSupportCommand())
    }
}