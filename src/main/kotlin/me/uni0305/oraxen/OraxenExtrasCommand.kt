package me.uni0305.oraxen

import io.th0rgal.oraxen.api.OraxenPack
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.plugin.java.JavaPlugin

class OraxenExtrasCommand(private val plugin: JavaPlugin) : BukkitCommand("oraxen-extras") {
    init {
        this.permission = "op"
        plugin.server.commandMap.register("uni0305", this)
    }

    override fun execute(sender: CommandSender, label: String, args: Array<String>): Boolean {
        if (args.isEmpty()) {
            sender.sendRichMessage("<yellow>Usage: <white>/$label reload")
            sender.sendRichMessage("<yellow>Usage: <white>/$label pack <reload|upload>")
            return true
        }

        when (args[0].lowercase()) {
            "reload" -> {
                plugin.saveDefaultConfig()
                plugin.reloadConfig()
                sender.sendRichMessage("<gold>Configuration reloaded.")
                return true
            }
            "pack" -> {
                if (args.size < 2) {
                    sender.sendRichMessage("<yellow>Usage: <white>/$label pack <reload|upload>")
                    return false
                }

                when (args[1].lowercase()) {
                    "reload" -> {
                        sender.sendRichMessage("<gold>Reloading Oraxen pack...")
                        OraxenExtrasPlugin.externalPackImportService.importPacks().thenAcceptAsync {
                            OraxenPack.reloadPack()
                            sender.sendRichMessage("<gold>Reloaded Oraxen pack.")
                        }
                        return true
                    }
                    "upload" -> {
                        OraxenPack.uploadPack()
                        sender.sendRichMessage("<gold>Uploaded Oraxen pack.")
                        return true
                    }
                    else -> {
                        sender.sendRichMessage("<red>Unknown subcommand: ${args[1]}")
                        return false
                    }
                }
            }
            else -> {
                sender.sendRichMessage("<red>Unknown subcommand: ${args[0]}")
                return false
            }
        }
    }

    override fun tabComplete(sender: CommandSender, label: String, args: Array<String>): List<String> {
        return if (args.size <= 1 || args[0].isEmpty()) listOf("reload", "pack")
        else if (args.size == 2 && args[0].lowercase() == "pack") listOf("reload", "upload")
        else emptyList()
    }
}