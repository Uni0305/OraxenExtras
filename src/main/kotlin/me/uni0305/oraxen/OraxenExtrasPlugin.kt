package me.uni0305.oraxen

import me.uni0305.oraxen.service.ExternalPackImportService
import org.bukkit.plugin.java.JavaPlugin

class OraxenExtrasPlugin : JavaPlugin() {
    companion object {
        lateinit var externalPackImportService: ExternalPackImportService private set
    }

    override fun onEnable() {
        saveDefaultConfig()
        reloadConfig()

        externalPackImportService = ExternalPackImportService(this)
        OraxenExtrasCommand(this)
    }

    override fun onDisable() {
        saveConfig()
    }
}
