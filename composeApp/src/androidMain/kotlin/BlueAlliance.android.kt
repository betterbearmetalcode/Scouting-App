import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONObject
import java.io.FileNotFoundException
import java.lang.Integer.parseInt
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
@RequiresApi(Build.VERSION_CODES.O)
fun sync(refresh: Boolean, context: Context)  {
    val scope = CoroutineScope(Dispatchers.Default)

    scope.launch {
        val teamError = syncTeams(refresh, context)
        val matchError = syncMatches(refresh, context)
        if (teamError && matchError) {
            createFile(context)
            lastSynced.value = Instant.now()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun syncTeams(refresh: Boolean, context: Context): Boolean {
    if (!refresh){
        teamData?.let {
            return true
        }
        try {
            openFile(context)
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
            openFile(context)
        } catch (e: FileNotFoundException) {
            return false
        }
    }
    return true
}

@RequiresApi(Build.VERSION_CODES.O)
fun syncMatches(refresh: Boolean, context: Context): Boolean {
    if (!refresh){
        matchData?.let {
            return true
        }
        try {
            openFile(context)
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
            openFile(context)
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
    matchData?.let {
        jsonObject = it
    }

    for (i in 0..<(jsonObject["matches"] as JSONArray).length()) {
        val it = (jsonObject["matches"] as JSONArray)[i]
        it as JSONObject
        if ((it["comp_level"] as String) == "qm") {
            if (
                (it["match_number"] as Int) !=
                    parseInt(
                        when (match.value) {
                            "" -> "1"
                            else -> match.value
                        }
                    )
                )
                continue
        } else {
            continue
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
        teamNum.intValue = parseInt((teamKey as String).slice(3..<teamKey.length))
    }
}