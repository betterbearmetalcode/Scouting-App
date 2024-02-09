package pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.operation.pop
import com.bumble.appyx.components.backstack.operation.push
import com.bumble.appyx.navigation.composable.AppyxComponent
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.navigation.node.ParentNode
import com.bumble.appyx.utils.multiplatform.Parcelable
import com.bumble.appyx.utils.multiplatform.Parcelize
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject

actual class AutoTeleSelectorMenu actual constructor(
    buildContext: BuildContext,
    var robotStartPosition: MutableIntState,
    var match: MutableState<String>,
    var team: MutableIntState,
    private val backStack: BackStack<NavTarget>
) : ParentNode<AutoTeleSelectorMenu.NavTarget>(
    appyxComponent = backStack,
    buildContext = buildContext
) {

    private val autoSpeakerNum = mutableIntStateOf(0)
    private val autoAmpNum = mutableIntStateOf(0)
    private val teleSpeakerNum  =  mutableIntStateOf(0)
    private val teleAmpNum  = mutableIntStateOf(0)
    private val teleAmplified =  mutableIntStateOf(0)
    private val teleTrapNum = mutableIntStateOf(0)
    private var selectedEndPos = mutableStateOf("None")
    private val lostComms = mutableStateOf(false)
    private var autoNotes = mutableStateOf("")
    private var teleNotes = mutableStateOf("")

    actual sealed class NavTarget : Parcelable {
        @Parcelize
        actual data object AutoScouting : NavTarget()

        @Parcelize
        actual data object TeleScouting : NavTarget()
    }

    val url = "https://www.thebluealliance.com/api/v3"
    val client = OkHttpClient()
    fun run(url: String, headers: Headers): String {
        val request = Request.Builder().url(url).headers(headers).build()

        client.newCall(request).execute().use() {
            return it.body?.string() ?: ""
        }
    }

    actual override fun resolve(interactionTarget: NavTarget, buildContext: BuildContext): Node =
        when (interactionTarget) {
            NavTarget.AutoScouting -> AutoMenu(buildContext, backStack, match, team, robotStartPosition, autoSpeakerNum, autoAmpNum, autoNotes)
            NavTarget.TeleScouting -> TeleMenu(buildContext, backStack, match, team, robotStartPosition, autoSpeakerNum, autoAmpNum, autoNotes, teleSpeakerNum, teleAmpNum, teleAmplified, teleTrapNum, selectedEndPos, teleNotes, lostComms)
            else -> AutoMenu(buildContext, backStack, match, team, robotStartPosition, autoSpeakerNum, autoAmpNum, autoNotes)
        }

    @Composable
    actual override fun View(modifier: Modifier) {
        var selectAuto by remember { mutableStateOf(false) }
        var selectedPlacement by remember { mutableStateOf(false) }
        var pageName by remember { mutableStateOf("Auto") }
        var positionName by remember { mutableStateOf("") }

        when (robotStartPosition.value){
            0 -> {positionName = "Red 1"}
            1 -> {positionName = "Red 2"}
            2 -> {positionName = "Red 3"}
            3 -> {positionName = "Blue 1"}
            4 -> {positionName = "Blue 2"}
            5 -> {positionName = "Blue 3"}
        }
        pageName = if (!selectAuto) {
            "Auto"
        } else {
            "Tele"
        }


        Column {
            Row {
                Text(
                    text = "Match" + match.value
                )
                TextField(
                    value = match.value,
                    onValueChange = { value->
                        match.value = value.filter { it.isDigit() }

                    },
                    modifier = Modifier.fillMaxWidth(1f/4f)
                )
                Text(
                    text = "Team " + team.value
                )
//                TextField(
//                    value = team.value,
//                    onValueChange = { team.value = it },
//                    modifier = Modifier.fillMaxWidth(1f/2f)
//                )
            }
            Row {
                Switch(
                    checked = selectAuto,
                    onCheckedChange = {
                        selectAuto = it
                        if (!selectAuto) {
                            backStack.pop()
                        } else
                            backStack.push(NavTarget.TeleScouting)
                    },
                    colors = SwitchDefaults.colors(
                        uncheckedTrackColor = Color.Black,
                        checkedTrackColor = Color.Yellow
                    )
                )
                Text(
                    text = pageName,
                    modifier = Modifier.scale(1.25f)
                )
                Button(
                    onClick = {selectedPlacement = true}
                ){
                    Text("Position:  $positionName" )
                }

            }
            Divider(
                color = Color.Yellow,
                thickness = 2.dp
            )
            AppyxComponent(
                appyxComponent = backStack,
                modifier = Modifier.weight(0.9f)
            )
        }
    }

    private fun setTeam() {
        val matches = run("$url/event/2016nytr/matches/simple", Headers.headersOf("X-TBA-Auth-Key", apiKey))
        val jsonObject = JSONObject("{\"matches\":$matches}")
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
            var teamKey = when(robotStartPosition.value) {
                0->redAlliance[0]
                1->redAlliance[1]
                2->redAlliance[2]
                3->blueAlliance[0]
                4->blueAlliance[1]
                5->blueAlliance[2]
                else -> {""}
            }
            team.value = Integer.parseInt((teamKey as String).slice(3..<teamKey.length))
        }
    }
}

private const val apiKey = "xLTlR6TM0H8zmSr8gyVyfDTw8njfHyJeEnVgLO0QCIizAk8mx4EQUNJcZfKibK9z"