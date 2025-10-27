package org.quarkystats.modules.fd_org_gatherer.ingest

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import java.time.OffsetDateTime

@ApplicationScoped
class CompetitionRepository : PanacheRepositoryBase<Competition, Long>

@ApplicationScoped
class TeamRepository : PanacheRepositoryBase<Team, Long>

@ApplicationScoped
class SeasonRepository : PanacheRepositoryBase<Season, Long> {
    fun findCurrentByCompetition(competitionId: Long) =
        find("competition.id = ?1 ORDER BY endDate DESC NULLS LAST", competitionId)
            .project(Season::class.java) //TODO make a record of what we want
            .firstResult()
}

@ApplicationScoped
class RawIngestRepository : PanacheRepositoryBase<RawIngest, Long> {
    fun findLatest(source: String, endpoint: String, key: String) =
        find(
            "source = ?1 and endpoint = ?2 and externalKey = ?3 ORDER BY fetchedAt DESC",
            source, endpoint, key
        ).firstResult()
}

@ApplicationScoped
class MatchRepository : PanacheRepositoryBase<Match, Long> {
    fun findByCompetitionAndDateRange(
        competitionId: Long,
        from: OffsetDateTime,
        to: OffsetDateTime
    ) = list("competition.id = ?1 and utcDate between ?2 and ?3 order by utcDate",
        competitionId, from, to)

    fun findTeamRecent(teamId: Long, limit: Int = 10) =
        list("homeTeam.id = ?1 or awayTeam.id = ?1 order by utcDate desc", teamId)
            .take(limit)
}