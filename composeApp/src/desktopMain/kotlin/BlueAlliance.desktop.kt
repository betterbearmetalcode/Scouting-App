import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONObject
import java.io.FileNotFoundException
import java.time.Instant
import java.util.*

/**
 * Updates match data
 *
 * @param refresh
 * Force update the match data, regardless
 * if it already has data.
 *
 * @return Returns true if ping is successful,
 *         or if match data isn't null
 */
fun sync(refresh: Boolean): Boolean {

    val teamError = syncTeams(refresh)
    val matchError = syncMatches(refresh)
    if (teamError && matchError) {
        createFile()
        lastSynced.value = Instant.now()
    }
    return teamError || matchError
}

fun syncTeams(refresh: Boolean): Boolean {
    if (!refresh){
        teamData?.let {
            return true
        }
        try {
            openFile()
            return true
        } catch (e: FileNotFoundException) {
            return false
        }
    }
    val apiKey = String(Base64.getDecoder().decode(apiKeyEncoded))
    try {
        val teams = run("$url/event/$comp/teams/simple",
            Headers.headersOf("X-TBA-Auth-Key",
                apiKey
            )
        )
        teamData = JSONObject("{\"teams\":$teams}")
    } catch (e: java.net.UnknownHostException) {
        try {
            openFile()
        } catch (e: FileNotFoundException) {
            return false
        }
    }
    return true
}

fun syncMatches(refresh: Boolean): Boolean {
    if (!refresh){
        matchData?.let {
            return true
        }
        try {
            openFile()
            return true
        } catch (e: FileNotFoundException) {
            return false
        }
    }
    val apiKey = String(Base64.getDecoder().decode(apiKeyEncoded))
    try {
        val matches = run("$url/event/$comp/matches",
            Headers.headersOf("X-TBA-Auth-Key",
                apiKey
            )
        )
        matchData = JSONObject("{\"matches\":$matches}")
    } catch (e: java.net.UnknownHostException) {
        try {
            openFile()
        } catch (e: FileNotFoundException) {
            return false
        }
    }
    return true
}

actual fun setTeam(
    teamNum: MutableIntState,
    match: MutableState<String>,
    robotStartPosition: Int
) {
    var jsonObject = JSONObject()
    matchData ?.let {
        jsonObject = it
    }

    (jsonObject["matches"] as JSONArray).forEach {
        it as JSONObject
        if ((it["key"] as String).contains("qm")) {
            if ((it["key"] as String).split("qm")[1] != match.value)
                return@forEach
        } else {
            return@forEach
        }
        val redAlliance = ((it["alliances"] as JSONObject)["red"] as JSONObject)["team_keys"] as JSONArray
        val blueAlliance = ((it["alliances"] as JSONObject)["blue"] as JSONObject)["team_keys"] as JSONArray
        val teamKey = when(robotStartPosition) {
            0->redAlliance[0]
            1->redAlliance[1]
            2->redAlliance[2]
            3->blueAlliance[0]
            4->blueAlliance[1]
            5->blueAlliance[2]
            else -> {""}
        }
        teamNum.value = Integer.parseInt((teamKey as String).slice(3..<teamKey.length))
    }
}