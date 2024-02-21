import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant

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