package dev.nikomaru.template.commands

import org.bukkit.command.CommandSender
import org.incendo.cloud.annotations.Command
import org.incendo.cloud.annotations.CommandDescription

//TODO replaced with the name of the plugin
@Command("plugin-template")
class HelpCommand {

    @Command("test")
    @CommandDescription("Test command")
    fun help(sender: CommandSender) {
        sender.sendMessage("Hello, World!")
    }
}