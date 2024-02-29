package pages

import nodes.RootNode
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.operation.push
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import composables.InternetErrorAlert
import defaultOnPrimary
import defaultOnSurface
import defaultSecondary
//import getCurrentTheme
import getLastSynced
import matchData
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import sync
import teamData
actual class MainMenu actual constructor(
    buildContext: BuildContext,
    private val backStack: BackStack<RootNode.NavTarget>,
    private val robotStartPosition: MutableIntState,
    private val scoutName: MutableState<String>,
    private val comp: MutableState<String>
) : Node(buildContext = buildContext) {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    actual override fun View(modifier: Modifier) {
        var selectedPlacement by remember { mutableStateOf(false) }
        val openError = remember { mutableStateOf(false) }
        var matchSyncedResource by remember { mutableStateOf(if (matchData == null) "crossmark.png" else "checkmark.png") }
        var teamSyncedResource by remember { mutableStateOf(if (teamData == null) "crossmark.png" else "checkmark.png") }
        when {
            openError.value -> {
                InternetErrorAlert { openError.value = false }
            }
        }
        Column (modifier = Modifier.verticalScroll(ScrollState(0))) {
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(onClick = {backStack.push(RootNode.NavTarget.LoginPage)},modifier = Modifier.scale(0.75f).align(Alignment.CenterStart)) {
                    Text(text = "Login", color = defaultOnPrimary)
                }

                Text(
                    text = "Bear Metal Scout App",
                    fontSize = 25.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
                OutlinedButton(onClick = {backStack.push(RootNode.NavTarget.SettingsPage)},modifier = Modifier.scale(0.75f).align(Alignment.CenterEnd)) {
                    Text(text = "Settings", color = defaultOnPrimary)
                }
            }
            Divider(color = defaultOnSurface, thickness = 2.dp)
            OutlinedButton(
                border = BorderStroke(3.dp, Color.Yellow),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = defaultSecondary),
                contentPadding = PaddingValues(horizontal = 60.dp, vertical = 5.dp),
                onClick = {
                    openError.value = !sync(false)
                    if (!openError.value)
                        selectedPlacement = true
                    else
                        matchSyncedResource = "checkmark.png"
                },
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(horizontal = 50.dp, vertical = 50.dp),
            ) {
                Text(
                    text = "Match",
                    color = defaultOnPrimary,
                    fontSize = 35.sp
                )
            }

            Box(modifier=Modifier.align(Alignment.CenterHorizontally).offset((-100).dp, (-50).dp)) {
                DropdownMenu(
                    expanded = selectedPlacement,
                    onDismissRequest = { selectedPlacement = false },
                    modifier = Modifier.size(200.dp, 332.dp).background(color = Color(0, 0, 0))
                ) {
                    Row {
                        DropdownMenuItem(
                            onClick = {
                                robotStartPosition.value = 0; backStack.push(RootNode.NavTarget.MatchScouting)
                            },
                            modifier = Modifier.border(BorderStroke(color = Color.Yellow, width = 3.dp))
                                .size(100.dp, 100.dp).background(color = Color(60, 30, 30))
                        ) { Text("R1", fontSize = 22.sp) }
                        DropdownMenuItem(
                            onClick = {
                                robotStartPosition.value = 3; backStack.push(RootNode.NavTarget.MatchScouting)
                            },
                            modifier = Modifier.border(BorderStroke(color = Color.Yellow, width = 3.dp))
                                .size(100.dp, 100.dp).background(color = Color(30, 30, 60))
                        ) { Text("B1", fontSize = 22.sp) }
                    }
                    Row {
                        DropdownMenuItem(
                            onClick = {
                                robotStartPosition.value = 1; backStack.push(RootNode.NavTarget.MatchScouting)
                            },
                            modifier = Modifier.border(BorderStroke(color = Color.Yellow, width = 3.dp))
                                .size(100.dp, 100.dp).background(color = Color(60, 30, 30))
                        ) { Text("R2", fontSize = 22.sp) }
                        DropdownMenuItem(
                            onClick = {
                                robotStartPosition.value = 4; backStack.push(RootNode.NavTarget.MatchScouting)
                            },
                            modifier = Modifier.border(BorderStroke(color = Color.Yellow, width = 3.dp))
                                .size(100.dp, 100.dp).background(color = Color(30, 30, 60))
                        ) { Text("B2", fontSize = 22.sp) }
                    }
                    Row {
                        DropdownMenuItem(
                            onClick = {
                                robotStartPosition.value = 2; backStack.push(RootNode.NavTarget.MatchScouting)
                            },
                            modifier = Modifier.border(BorderStroke(color = Color.Yellow, width = 3.dp))
                                .size(100.dp, 100.dp).background(color = Color(60, 30, 30))
                        ) { Text("R3", fontSize = 22.sp) }
                        DropdownMenuItem(
                            onClick = {
                                robotStartPosition.value = 5; backStack.push(RootNode.NavTarget.MatchScouting)
                            },
                            modifier = Modifier.border(BorderStroke(color = Color.Yellow, width = 3.dp))
                                .size(100.dp, 100.dp).background(color = Color(30, 30, 60))
                        ) { Text("B3", fontSize = 22.sp) }
                    }
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
                    color = defaultOnPrimary,
                    fontSize = 35.sp
                )
            }

            OutlinedButton(
                border = BorderStroke(3.dp, Color.Yellow),
                shape = RoundedCornerShape(25.dp),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 15.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = defaultSecondary),
                onClick = {
                    openError.value = !sync(true)
                    if (teamData != null) teamSyncedResource = "checkmark.png"
                    if (matchData != null) matchSyncedResource = "checkmark.png"
                },
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(horizontal = 50.dp, vertical = 50.dp),
            ) {
                Column {
                    Text(
                        text = "Sync",
                        color = defaultOnPrimary,
                        fontSize = 35.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Text(
                        text = "Last synced ${getLastSynced()}",
                        fontSize = 12.sp,
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row {
                        Text ("Robot List")

                        Image(
                            painterResource(res = teamSyncedResource),
                            contentDescription = "status",
                            modifier = Modifier.size(30.dp).offset(x=100.dp, y=(-5).dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row {
                        Text ("Match List")

                        Image(
                            painterResource(res = matchSyncedResource),
                            contentDescription = "status",
                            modifier = Modifier.size(30.dp).offset(x=(98.5).dp),
                        )
                    }
                }
            }
        }
    }
}
