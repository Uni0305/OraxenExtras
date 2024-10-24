package me.uni0305.oraxen.service

import io.th0rgal.oraxen.OraxenPlugin
import me.uni0305.oraxen.config.ExternalPackImportConfig
import org.bukkit.plugin.java.JavaPlugin
import org.slf4j.LoggerFactory
import java.util.concurrent.CompletableFuture

class ExternalPackImportService(private val plugin: JavaPlugin) {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    private val config: ExternalPackImportConfig
        get() = ExternalPackImportConfig(plugin.config)

    fun getPacks() = if (config.isEnabled()) config.getPacks() else emptyList()

    fun importPacks(): CompletableFuture<Void> = CompletableFuture.runAsync {
        if (!config.isEnabled()) {
            logger.warn("External pack import is disabled")
            return@runAsync
        }

        val directories = config.getPacks()
        if (directories.isEmpty()) {
            logger.warn("No external packs to import")
            return@runAsync
        }

        try {
            val packFolder = OraxenPlugin.get().resourcePack.packFolder
            for (directory in directories) {
                val files = directory.walkTopDown().filter { it.isFile && it.exists() }
                for (file in files) {
                    val relativePath = directory.toPath().relativize(file.toPath()).toString()
                    val targetFile = packFolder.resolve(relativePath)
                    if (targetFile.exists() && targetFile.readBytes().contentEquals(file.readBytes())) continue
                    file.copyTo(targetFile, true)
                }
            }
            logger.info("Successfully imported external packs")
        } catch (e: Exception) {
            logger.error("Failed to import external packs", e)
        }
    }
}