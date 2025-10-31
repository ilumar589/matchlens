package org.quarkystats.modules.fd_org_gatherer.ingest.client

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import org.quarkystats.modules.fd_org_gatherer.ingest.Competition

@RegisterRestClient(configKey = "football-data")
@RegisterClientHeaders(FootballDataHeadersFactory::class)
@Path("/competitions")
interface FootballDataClient {

    @GET
    @Path("/PL")
    @Produces(MediaType.APPLICATION_JSON)
    fun getPremierLeague(): Competition
}