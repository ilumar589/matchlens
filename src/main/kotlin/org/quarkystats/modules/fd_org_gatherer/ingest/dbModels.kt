package org.quarkystats.modules.fd_org_gatherer.ingest


import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.LocalDate
import java.time.OffsetDateTime
import com.fasterxml.jackson.databind.JsonNode

/* ===== fd_raw_ingest ===== */
@Entity
@Table(
    name = "fd_raw_ingest",
    uniqueConstraints = [
        UniqueConstraint(
            name = "fd_raw_ingest_dedup",
            columnNames = ["source", "endpoint", "external_key"]
        )
    ],
    indexes = [
        Index(name = "fd_raw_ingest_payload_gin", columnList = "payload"),
        Index(name = "fd_raw_ingest_recent_idx", columnList = "source, endpoint, external_key, fetched_at DESC")
    ]
)
class RawIngest : PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null

    @Column(name = "source", nullable = false)
    lateinit var source: String

    @Column(name = "endpoint", nullable = false)
    lateinit var endpoint: String

    @Column(name = "external_key", nullable = false)
    lateinit var externalKey: String

    @Column(name = "fetched_at", nullable = false)
    var fetchedAt: OffsetDateTime? = null

    @Column(name = "last_modified")
    var lastModified: OffsetDateTime? = null

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "payload")
    var payload: JsonNode? = null
}

/* ===== fd_competition ===== */
@Entity
@Table(name = "fd_competition")
class Competition : PanacheEntityBase {
    @Id
    @Column(name = "id")
    var id: Long? = null  // comes from upstream API

    @Column(name = "code", unique = true)
    var code: String? = null

    @Column(name = "name", nullable = false)
    lateinit var name: String

    @Column(name = "type")
    var type: String? = null

    @Column(name = "emblem")
    var emblem: String? = null

    @Column(name = "area_id")
    var areaId: Long? = null

    @Column(name = "area_name")
    var areaName: String? = null

    @Column(name = "last_updated")
    var lastUpdated: OffsetDateTime? = null
}

/* ===== fd_team ===== */
@Entity
@Table(name = "fd_team")
class Team : PanacheEntityBase {
    @Id
    @Column(name = "id")
    var id: Long? = null

    @Column(name = "name", nullable = false)
    lateinit var name: String

    @Column(name = "short_name")
    var shortName: String? = null

    @Column(name = "tla")
    var tla: String? = null

    @Column(name = "crest")
    var crest: String? = null

    @Column(name = "founded")
    var founded: Int? = null

    @Column(name = "last_updated")
    var lastUpdated: OffsetDateTime? = null
}

/* ===== fd_season ===== */
@Entity
@Table(name = "fd_season")
class Season : PanacheEntityBase {
    @Id
    @Column(name = "id")
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "competition_id", nullable = false)
    lateinit var competition: Competition

    @Column(name = "start_date")
    var startDate: LocalDate? = null

    @Column(name = "end_date")
    var endDate: LocalDate? = null

    @Column(name = "current_matchday")
    var currentMatchday: Int? = null

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "stages")
    var stages: JsonNode? = null

    // not enforced as FK in your DDL, but it’s useful to map
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_team_id")
    var winnerTeam: Team? = null
}

/* ===== fd_match ===== */
@Entity
@Table(
    name = "fd_match",
    indexes = [
        Index(name = "fd_match_competition_date", columnList = "competition_id, utc_date"),
        Index(name = "fd_match_team_date", columnList = "home_team_id, utc_date"),
        Index(name = "fd_match_score_gin", columnList = "score_json")
    ]
)
class Match : PanacheEntityBase {
    @Id
    @Column(name = "id")
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "competition_id", nullable = false)
    lateinit var competition: Competition

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id")
    var season: Season? = null

    @Column(name = "utc_date")
    var utcDate: OffsetDateTime? = null

    @Column(name = "status")
    var status: String? = null

    @Column(name = "matchday")
    var matchday: Int? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_team_id")
    var homeTeam: Team? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "away_team_id")
    var awayTeam: Team? = null

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "score_json")
    var scoreJson: JsonNode? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "raw_ref_id")
    var rawRef: RawIngest? = null
}
