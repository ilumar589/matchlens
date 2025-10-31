package org.quarkystats.modules.fd_org_gatherer.ingest

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.eclipse.microprofile.rest.client.inject.RestClient
import org.quarkystats.modules.fd_org_gatherer.ingest.client.FootballDataClient

@ApplicationScoped
class FootballDataService @Inject constructor(
    @RestClient private val client: FootballDataClient
) {
    fun getPremierLeague(): Competition = client.getPremierLeague()
}