package me.uni0305.oraxen.config

import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import java.io.File

class ExternalPackImportConfig(private val config: FileConfiguration) {
    companion object {
        private const val SECTION_PATH = "import-external-packs"
    }

    fun isEnabled() = config.getBoolean("$SECTION_PATH.enabled", false)

    fun getPacks(): List<File> = config.getStringList("$SECTION_PATH.packs")
        .map { File(Bukkit.getServer().pluginsFolder, it).resolve("assets") }
        .filter { it.exists() }
}