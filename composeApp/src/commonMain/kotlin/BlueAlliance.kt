import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


var comp = "2024week0"

var matchData: JSONObject? = null
var teamData: JSONObject? = null

const val url = "https://www.thebluealliance.com/api/v3"
private val client = OkHttpClient()

fun run(url: String, headers: Headers): String {
    val request = Request.Builder().url(url).headers(headers).build()

    client.newCall(request).execute().use {
        return it.body?.string() ?: ""
    }
}

expect fun setTeam(teamNum: MutableIntState, match: MutableState<String>, robotStartPosition: Int)

var lastSynced = mutableStateOf(Instant.now())

fun getLastSynced() : String {
    val formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
        .withZone(ZoneId.systemDefault())

    return formatter.format(lastSynced.value)
}

private const val PATTERN_FORMAT = "dd/MM/yyyy @ hh:mm"




const val apiKeyEncoded = "eEtWS2RkemlRaTlJWkJhYXMxU0M0cUdlVkVTMXdaams3VDhUckZ1amFSODFmQlFIUXgybTdzTGJoZ0lnQVNPRw=="