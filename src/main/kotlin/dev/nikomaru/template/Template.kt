package dev.nikomaru.template

import dev.nikomaru.template.commands.HelpCommand
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import org.incendo.cloud.annotations.AnnotationParser
import org.incendo.cloud.execution.ExecutionCoordinator
import org.incendo.cloud.paper.LegacyPaperCommandManager
import org.incendo.cloud.setting.ManagerSetting
import org.koin.core.context.GlobalContext
import org.koin.dsl.module

open class Template : JavaPlugin() {

    companion object {
        lateinit var plugin: Template
            private set
    }

    override fun onEnable() {
        // Plugin startup logic
        plugin = this
        setCommand()
        setupKoin()
    }

    private fun setupKoin() {
        val appModule = module {
            single<Template> { this@Template }
        }

        GlobalContext.getOrNull() ?: GlobalContext.startKoin {
            modules(appModule)
        }
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

        annotationParser.parse(HelpCommand())
    }
}