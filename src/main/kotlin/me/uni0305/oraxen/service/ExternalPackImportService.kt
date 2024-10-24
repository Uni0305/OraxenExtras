package me.uni0305.oraxen.service

import io.th0rgal.oraxen.api.OraxenPack
import me.uni0305.oraxen.config.ExternalPackImportConfig
import org.slf4j.LoggerFactory

class ExternalPackImportService(private val config: ExternalPackImportConfig) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun getPacks() = if (config.isEnabled()) config.getPacks() else emptyList()

    fun importPacks() {
        if (!config.isEnabled()) {
            logger.warn("External pack import is disabled")
            return
        }

        val packs = config.getPacks()
        if (packs.isEmpty()) {
            logger.warn("No external packs to import")
            return
        }

        try {
            OraxenPack.addFilesToPack(packs.toTypedArray())
            logger.info("Imported ${packs.size} external packs")
        } catch (e: Exception) {
            logger.error("Failed to import external packs", e)
        }
    }
}