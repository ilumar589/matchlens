package org.quarkystats.modules.fd_org_gatherer.ingest.client

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.JsonNode
import java.time.Instant
import java.time.LocalDate


// Your sample also shows SUPER_CUP, so we include it too.
enum class CompetitionType {
    LEAGUE, LEAGUE_CUP, CUP, PLAYOFFS, SUPER_CUP,

    @JsonEnumDefaultValue
    UNKNOWN
}

// Not listed in docs, but present in your sample: TIER_ONE..TIER_FOUR.
enum class PlanTier {
    TIER_ONE, TIER_TWO, TIER_THREE, TIER_FOUR,

    @JsonEnumDefaultValue
    UNKNOWN
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class CompetitionsResponse(
    val count: Int?,
    val filters: JsonNode?,               // {} in your sample; keep flexible
    val competitions: List<Competition>?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Competition(
    val id: Long?,
    val area: Area?,
    val name: String?,
    val code: String?,                    // sometimes null
    val type: CompetitionType?,           // e.g. "LEAGUE", "CUP", "PLAYOFFS"
    val emblem: String?,                  // url or null
    val plan: PlanTier?,                  // e.g. "TIER_ONE"
    val currentSeason: Season?,
    val numberOfAvailableSeasons: Int?,
    val lastUpdated: Instant?             // ISO-8601 instant
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Area(
    val id: Long?,
    val name: String?,
    val code: String?,
    val flag: String?                     // url or null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Season(
    val id: Long?,
    val startDate: LocalDate?,            // "YYYY-MM-DD"
    val endDate: LocalDate?,              // "YYYY-MM-DD"
    val currentMatchday: Int?,            // may be null
    val winner: TeamSummary?              // null or object
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TeamSummary(
    val id: Long?,
    val name: String?,
    val shortName: String?,
    val tla: String?,
    val crest: String?,                   // url
    val address: String?,
    val website: String?,
    val founded: Int?,
    val clubColors: String?,
    val venue: String?,
    val lastUpdated: Instant?
)