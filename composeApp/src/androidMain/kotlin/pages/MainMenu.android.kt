package pages

import android.content.Context
import android.hardware.usb.UsbManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.operation.push
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import compKey
import defaultSecondary
import getCurrentTheme
import getLastSynced
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import matchData
import nodes.RootNode
import nodes.match
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.json.JSONException
import sendData
import sendDataUSB
import setTeam
import sync
import teamData

actual class MainMenu actual constructor(
    buildContext: BuildContext,
    private val backStack: BackStack<RootNode.NavTarget>,
    private val robotStartPosition: MutableIntState,
    private val scoutName: MutableState<String>,
    private val comp: MutableState<String>,
    private val team: MutableIntState
) : Node(buildContext = buildContext) {

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
    @Composable
    actual override fun View(modifier: Modifier) {
        val context = LocalContext.current
        var selectedPlacement by remember { mutableStateOf(false) }
        var matchSyncedResource by remember { mutableStateOf(if (matchData == null) "crossmark.png" else "checkmark.png") }
        var teamSyncedResource by remember { mutableStateOf(if (teamData == null) "crossmark.png" else "checkmark.png") }
        var serverDialogOpen by remember { mutableStateOf(false) }

        var ipAddressErrorDialog by remember { mutableStateOf(false) }
        var deviceListOpen by remember { mutableStateOf(false) }
        val manager = context.getSystemService(Context.USB_SERVICE) as UsbManager

        var setEventCode by remember { mutableStateOf(false) }
        var tempCompKey by remember { mutableStateOf(compKey) }

        val deviceList = manager.deviceList


        Column (modifier = Modifier.verticalScroll(ScrollState(0))) {
            DropdownMenu(expanded = deviceListOpen, onDismissRequest = { deviceListOpen = false }) {
                deviceList.forEach { (name, _) ->
                    Log.i("USB", name)
                    DropdownMenuItem(text = { Text(name) }, onClick = { sendDataUSB(context, name) })
                }
            }
            if (setEventCode) {
                Dialog(onDismissRequest = {
                    setEventCode = false
                    compKey = tempCompKey
                }) {
                    Column {
                        Text("Enter new event code")
                        TextField(tempCompKey, {tempCompKey = it})
                    }
                }
            }
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(onClick = {backStack.push(RootNode.NavTarget.LoginPage)},modifier = Modifier
                    .scale(0.75f)
                    .align(Alignment.CenterStart)) {
                    Text(text = "Login", color = getCurrentTheme().onPrimary)
                }

                Text(
                    text = "Bear Metal Scout App",
                    fontSize = 25.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            HorizontalDivider(color = getCurrentTheme().onSurface, thickness = 2.dp)
            Text(text="Hello ${scoutName.value}",color = getCurrentTheme().onPrimary,modifier = Modifier.align(Alignment.CenterHorizontally))
            OutlinedButton(
                border = BorderStroke(3.dp, Color.Yellow),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = defaultSecondary),
                contentPadding = PaddingValues(horizontal = 60.dp, vertical = 5.dp),
                onClick = {
                    val scope = CoroutineScope(Dispatchers.Default)
                    scope.launch {
                        sync(false, context)
                    }

                    selectedPlacement = true
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 50.dp, vertical = 50.dp),
            ) {
                Text(
                    text = "Match",
                    color = getCurrentTheme().onPrimary,
                    fontSize = 35.sp
                )
            }

            Box(modifier= Modifier
                .align(Alignment.CenterHorizontally)
                .offset((-100).dp, (-50).dp)) {
                DropdownMenu(
                    expanded = selectedPlacement,
                    onDismissRequest = { selectedPlacement = false },
                    modifier = Modifier
                        .size(200.dp, 332.dp)
                        .background(color = Color(0, 0, 0))
                ) {
                    Row {
                        DropdownMenuItem(
                            onClick = {
                                robotStartPosition.intValue = 0; backStack.push(RootNode.NavTarget.MatchScouting)
                                try {
                                    setTeam(team, match, robotStartPosition.intValue)
                                } catch (e: JSONException) {
                                    openError.value = true
                                }
                            },
                            modifier = Modifier
                                .border(BorderStroke(color = Color.Yellow, width = 3.dp))
                                .size(100.dp, 100.dp)
                                .background(color = Color(60, 30, 30)),
                            text =  { Text("R1", fontSize = 22.sp, color = Color.White) }
                        )
                        DropdownMenuItem(
                            onClick = {
                                robotStartPosition.intValue = 3; backStack.push(RootNode.NavTarget.MatchScouting)
                                try {
                                    setTeam(team, match, robotStartPosition.intValue)
                                } catch (e: JSONException) {
                                    openError.value = true
                                }
                            },
                            modifier = Modifier
                                .border(BorderStroke(color = Color.Yellow, width = 3.dp))
                                .size(100.dp, 100.dp)
                                .background(color = Color(30, 30, 60)),
                            text = { Text("B1", fontSize = 22.sp, color = Color.White) }
                        )
                    }
                    Row {
                        DropdownMenuItem(
                            onClick = {
                                robotStartPosition.intValue = 1; backStack.push(RootNode.NavTarget.MatchScouting)
                                try {
                                    setTeam(team, match, robotStartPosition.intValue)
                                } catch (e: JSONException) {
                                    openError.value = true
                                }
                            },
                            modifier = Modifier
                                .border(BorderStroke(color = Color.Yellow, width = 3.dp))
                                .size(100.dp, 100.dp)
                                .background(color = Color(60, 30, 30)),
                            text = { Text("R2", fontSize = 22.sp, color = Color.White) }
                        )
                        DropdownMenuItem(
                            onClick = {
                                robotStartPosition.intValue = 4; backStack.push(RootNode.NavTarget.MatchScouting)
                                try {
                                    setTeam(team, match, robotStartPosition.intValue)
                                } catch (e: JSONException) {
                                    openError.value = true
                                }
                            },
                            modifier = Modifier
                                .border(BorderStroke(color = Color.Yellow, width = 3.dp))
                                .size(100.dp, 100.dp)
                                .background(color = Color(30, 30, 60)),
                            text = { Text("B2", fontSize = 22.sp, color = Color.White) }
                        )
                    }
                    Row {
                        DropdownMenuItem(
                            onClick = {
                                robotStartPosition.intValue = 2; backStack.push(RootNode.NavTarget.MatchScouting)
                                try {
                                    setTeam(team, match, robotStartPosition.intValue)
                                } catch (e: JSONException) {
                                    openError.value = true
                                }
                            },
                            modifier = Modifier
                                .border(BorderStroke(color = Color.Yellow, width = 3.dp))
                                .size(100.dp, 100.dp)
                                .background(color = Color(60, 30, 30)),
                            text = { Text("R3", fontSize = 22.sp, color = Color.White) }
                        )
                        DropdownMenuItem(
                            onClick = {
                                robotStartPosition.intValue = 5; backStack.push(RootNode.NavTarget.MatchScouting)
                                try {
                                    setTeam(team, match, robotStartPosition.intValue)
                                } catch (e: JSONException) {
                                    openError.value = true
                                }
                            },
                            modifier = Modifier
                                .border(BorderStroke(color = Color.Yellow, width = 3.dp))
                                .size(100.dp, 100.dp)
                                .background(color = Color(30, 30, 60)),
                            text = { Text("B3", fontSize = 22.sp, color = Color.White) }
                        )
                    }
                }
            }
            OutlinedButton(
                border = BorderStroke(3.dp, Color.Yellow),
                shape = RoundedCornerShape(25.dp),
                contentPadding = PaddingValues(horizontal = 80.dp, vertical = 5.dp),
                colors = ButtonDefaults.buttonColors(containerColor = defaultSecondary),
                onClick = {
                    backStack.push(RootNode.NavTarget.PitsScouting)
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 50.dp, vertical = 50.dp),

                ) {
                Text(
                    text = "Pits",
                    color = getCurrentTheme().onPrimary,
                    fontSize = 35.sp
                )
            }

            OutlinedButton(
                border = BorderStroke(3.dp, Color.Yellow),
                shape = RoundedCornerShape(25.dp),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 15.dp),
                colors = ButtonDefaults.buttonColors(containerColor = defaultSecondary),
                onClick = {
                    val scope = CoroutineScope(Dispatchers.Default)
                    scope.launch {
                        sync(true, context)
                        teamSyncedResource = if (teamData != null)  "checkmark.png" else "crossmark.png"
                        matchSyncedResource = if (matchData != null)  "checkmark.png" else "crossmark.png"
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 50.dp, vertical = 50.dp),
            ) {
                Column {
                    Text(
                        text = "Sync",
                        color = getCurrentTheme().onPrimary,
                        fontSize = 35.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Text(
                        text = "Last synced ${getLastSynced()}",
                        fontSize = 12.sp,
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(Modifier.fillMaxWidth(1f/2f)) {
                        Text ("Robot List")

                        Image(
                            painterResource(res = teamSyncedResource),
                            contentDescription = "status",
                            modifier = Modifier
                                .size(30.dp)
                                .align(Alignment.CenterEnd)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(Modifier.fillMaxWidth(1f/2f)) {
                        Text ("Match List")

                        Image(
                            painterResource(res = matchSyncedResource),
                            contentDescription = "status",
                            modifier = Modifier
                                .size(30.dp)
                                .align(Alignment.CenterEnd)
                        )
                    }
                }
            }

            OutlinedButton(
                border = BorderStroke(3.dp, Color.Yellow),
                shape = RoundedCornerShape(25.dp),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 15.dp),
                colors = ButtonDefaults.buttonColors(containerColor = defaultSecondary),
                onClick = {
                    serverDialogOpen = true
                    //deviceListOpen = true
                }
            ) {
                Text("Export")
            }

            Box(modifier = Modifier.fillMaxSize()){
                OutlinedButton(
                    border = BorderStroke(3.dp, Color.Yellow),
                    shape = RoundedCornerShape(25.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 15.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = defaultSecondary),
                    onClick = {
                        setEventCode = true
                        teamSyncedResource = "crossmark.png"
                        matchSyncedResource = "crossmark.png"
                    },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Text("Set custom event key", fontSize = 9.sp)
                }
            }
            Text(tempCompKey)

            if (serverDialogOpen) {
                Dialog(
                    onDismissRequest = {
                        serverDialogOpen = false
                        if (ipAddress.value.matches(Regex("^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}\$"))) {
                            CoroutineScope(Dispatchers.Default).launch {
                                sendData(context, ipAddress.value)
                            }
                        } else
                            ipAddressErrorDialog = true

                    }
                ) {
                    Column {
                        Text("Set Device Address", fontSize = 24.sp)
                        TextField(
                            ipAddress.value,
                            onValueChange = { ipAddress.value = it }
                        )
                    }
                }
            }

            if (ipAddressErrorDialog) {
                BasicAlertDialog(
                    onDismissRequest = { ipAddressErrorDialog = false }
                ) {
                    Text("Ip Address was entered incorrectly, check address and enter again")
                }
            }
        }
    }
}

var ipAddress = mutableStateOf("127.0.0.1")
val openError = mutableStateOf(false)