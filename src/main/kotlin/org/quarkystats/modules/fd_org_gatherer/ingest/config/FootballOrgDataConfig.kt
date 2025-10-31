package org.quarkystats.modules.fd_org_gatherer.ingest.config

import io.smallrye.config.ConfigMapping

@ConfigMapping(prefix = "footballdata.api")
interface FootballOrgDataConfig {
    fun key(): String?
}