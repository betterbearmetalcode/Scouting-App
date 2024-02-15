package pages

import RootNode
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import composables.InternetErrorAlert
import defaultSecondary
import getCurrentTheme
import sync

class MainMenu(
    buildContext: BuildContext,
    private val backStack: BackStack<RootNode.NavTarget>,
    private val robotStartPosition: MutableIntState
) : Node(buildContext = buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        var selectedPlacement by remember { mutableStateOf(false) }
        var pitsSelectedPlacement by remember { mutableStateOf(false) }
        val openError = remember { mutableStateOf(false) }

        when {
            openError.value -> {
                InternetErrorAlert { openError.value = false }
            }
        }
        Column {
            Text(
                text = "Bear Metal Scout App",
                fontSize = 30.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Divider(color = getCurrentTheme().onSurface, thickness = 2.dp)
            OutlinedButton(
                border = BorderStroke(3.dp, Color.Yellow),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = defaultSecondary),
                contentPadding = PaddingValues(horizontal = 60.dp, vertical = 5.dp),
                onClick = {
                    openError.value = !sync(false)
                    if (!openError.value)
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
                    color = getCurrentTheme().primaryVariant,
                    fontSize = 35.sp
                )
            }
            DropdownMenu(
                expanded = pitsSelectedPlacement,
                onDismissRequest = { pitsSelectedPlacement = false },
                modifier = Modifier.size(200.dp, 332.dp).background(color = Color(0, 0, 0))
            ) {
                DropdownMenuItem(
                    onClick = {
                        robotStartPosition.value = 0; backStack.push(RootNode.NavTarget.PitsScouting)
                    },
                    modifier = Modifier.border(BorderStroke(color = Color.Yellow, width = 3.dp))
                        .size(100.dp, 100.dp).background(color = Color(60, 30, 30))
                ) { Text("R1", fontSize = 22.sp) }
            }
            OutlinedButton(
                border = BorderStroke(3.dp, Color.Yellow),
                shape = RoundedCornerShape(25.dp),
                contentPadding = PaddingValues(horizontal = 80.dp, vertical = 5.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = defaultSecondary),
                onClick = {
                    openError.value = !sync(true)
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
}
