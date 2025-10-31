package org.quarkystats.modules.fd_org_gatherer.ingest.client

import jakarta.inject.Inject
import jakarta.ws.rs.core.MultivaluedHashMap
import jakarta.ws.rs.core.MultivaluedMap
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory
import org.quarkystats.modules.fd_org_gatherer.ingest.config.FootballOrgDataConfig

class FootballDataHeadersFactory @Inject constructor(
    private val footballOrgDataConfig: FootballOrgDataConfig
) : ClientHeadersFactory {

    override fun update(
        incomingHeaders: MultivaluedMap<String?, String?>?,
        clientOutgoingHeaders: MultivaluedMap<String?, String?>?
    ): MultivaluedMap<String?, String?> = MultivaluedHashMap<String, String>().apply {
        add("X-Auth-Token", footballOrgDataConfig.key())
    }
}