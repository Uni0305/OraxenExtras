package me.uni0305.oraxen

import me.uni0305.oraxen.config.ExternalPackImportConfig
import me.uni0305.oraxen.service.ExternalPackImportService
import org.bukkit.plugin.java.JavaPlugin

class OraxenExtrasPlugin : JavaPlugin() {
    companion object {
        lateinit var externalPackImportService: ExternalPackImportService private set
    }

    override fun onEnable() {
        saveDefaultConfig()
        reloadConfig()

        val externalPackImportConfig = ExternalPackImportConfig(config)
        externalPackImportService = ExternalPackImportService(externalPackImportConfig)
        OraxenExtrasCommand(this)
    }

    override fun onDisable() {
        saveConfig()
    }
}
