package pages

import RootNode
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.operation.push
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import defaultSecondary
import getCurrentTheme
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Integer.parseInt

actual class MainMenu actual constructor(
    buildContext: BuildContext,
    private val backStack: BackStack<RootNode.NavTarget>,
    private val robotStartPosition: MutableIntState,
    private val match: MutableState<String>,
    private val teamNum: MutableIntState
) : Node(buildContext = buildContext) {
    val url = "https://www.thebluealliance.com/api/v3"
    val client = OkHttpClient()
    fun run(url: String, headers: Headers): String {
        val request = Request.Builder().url(url).headers(headers).build()

        client.newCall(request).execute().use() {
            return it.body?.string() ?: ""
        }
    }
    @Composable
    actual override fun View(modifier: Modifier) {
        var selectedPlacement by remember { mutableStateOf(false) }
        Column {
            Row (modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Button(
                    content = {
                        Text(
                            text = "i",
                            color = getCurrentTheme().secondaryVariant,
                            fontSize = 12.sp
                        )
                    },
                    onClick = {

                    },
                    modifier = Modifier.padding(3.dp),
                    contentPadding = PaddingValues(3.dp)
                )
                Text(
                    text = "Bear Metal Scout App",
                    fontSize = 30.sp
                )
                Button(
                    content = {
                        Text(
                            text = "s",
                            color = getCurrentTheme().secondaryVariant,
                            fontSize = 12.sp
                        )
                    },
                    onClick = {

                    },
                    modifier = Modifier.padding(3.dp),
                    contentPadding = PaddingValues(3.dp)
                )
            }
            Divider(color = getCurrentTheme().onSurface, thickness = 2.dp)
            OutlinedButton(
                border = BorderStroke(3.dp, Color.Yellow),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = defaultSecondary),
                contentPadding = PaddingValues(horizontal = 60.dp, vertical = 5.dp),
                onClick = {
                    selectedPlacement = true
                },
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(horizontal = 50.dp, vertical = 50.dp),
            ) {
                Text(
                    text = "Match",
                    color = getCurrentTheme().primaryVariant,
                    fontSize = 35.sp
                )
            }

            DropdownMenu(
                expanded = selectedPlacement,
                onDismissRequest = {selectedPlacement = false},
                modifier = Modifier.size(100.dp, 166.dp).background(color = Color(0,0,0))
            ) {
                Row {
                    DropdownMenuItem(onClick = {robotStartPosition.value= 0; backStack.push(RootNode.NavTarget.QuanScouting); setTeam()}, modifier = Modifier.size(50.dp,50.dp)) {Text("R1", fontSize = 11.sp)}
                    DropdownMenuItem(onClick = {robotStartPosition.value= 3; backStack.push(RootNode.NavTarget.QuanScouting); setTeam()}, modifier = Modifier.size(50.dp,50.dp)) {Text("B1", fontSize = 11.sp)}
                }
                Row {
                    DropdownMenuItem(onClick = {robotStartPosition.value= 1; backStack.push(RootNode.NavTarget.QuanScouting); setTeam()}, modifier = Modifier.size(50.dp,50.dp)) {Text("R2", fontSize = 11.sp)}
                    DropdownMenuItem(onClick = {robotStartPosition.value= 4; backStack.push(RootNode.NavTarget.QuanScouting); setTeam()}, modifier = Modifier.size(50.dp,50.dp)) {Text("B2", fontSize = 11.sp)}
                }
                Row {
                    DropdownMenuItem(onClick = {robotStartPosition.value= 2; backStack.push(RootNode.NavTarget.QuanScouting); setTeam()}, modifier = Modifier.size(50.dp,50.dp)) {Text("R3", fontSize = 11.sp)}
                    DropdownMenuItem(onClick = {robotStartPosition.value= 5; backStack.push(RootNode.NavTarget.QuanScouting); setTeam()}, modifier = Modifier.size(50.dp,50.dp)) {Text("B3", fontSize = 11.sp)}
                }
            }

            OutlinedButton(
                border = BorderStroke(3.dp, Color.Yellow),
                shape = RoundedCornerShape(25.dp),
                contentPadding = PaddingValues(horizontal = 80.dp, vertical = 5.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = defaultSecondary),
                onClick = {
                    backStack.push(RootNode.NavTarget.PitsScouting)
                },
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(horizontal = 50.dp, vertical = 50.dp),

                ) {
                Text(
                    text = "Pits",
                    color = getCurrentTheme().primaryVariant,
                    fontSize = 35.sp
                )
            }

            OutlinedButton(
                border = BorderStroke(3.dp, Color.Yellow),
                shape = RoundedCornerShape(25.dp),
                contentPadding = PaddingValues(horizontal = 80.dp, vertical = 5.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = defaultSecondary),
                onClick = {

                },
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(horizontal = 50.dp, vertical = 50.dp),
            ) {
                Text(
                    text = "Sync",
                    color = getCurrentTheme().primaryVariant,
                    fontSize = 35.sp
                )
            }
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
            teamNum.value = parseInt((teamKey as String).slice(3..<teamKey.length))
        }
    }
}

private const val apiKey = "xLTlR6TM0H8zmSr8gyVyfDTw8njfHyJeEnVgLO0QCIizAk8mx4EQUNJcZfKibK9z"