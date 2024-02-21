import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
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
@RequiresApi(Build.VERSION_CODES.O)
fun sync(refresh: Boolean, context: Context)  {
    val scope = CoroutineScope(Dispatchers.Default)

    val job = scope.launch {
        val teamError = syncTeams(refresh)
        val matchError = syncMatches(refresh)
        if (teamError && matchError) {
            createFile(context)
            lastSynced.value = Instant.now()
        }
    }
}