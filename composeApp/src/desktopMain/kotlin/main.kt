import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Typography
import androidx.compose.runtime.remember
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.bumble.appyx.navigation.integration.DesktopNodeHost
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.test.setMain
import nodes.RootNode


@OptIn(ExperimentalCoroutinesApi::class)
fun main() = application {
    val mainThreadSurrogate:CoroutineDispatcher = Dispatchers.Default
    Dispatchers.setMain(mainThreadSurrogate)

    val events: Channel<Events> = Channel()
    val windowState = rememberWindowState(size = DpSize(480.dp, 680.dp))
    val eventScope = remember { CoroutineScope(SupervisorJob() + Dispatchers.Main) }

    Window(
        state = windowState,
        onCloseRequest = ::exitApplication,
        onKeyEvent = {
            // See back handling section in the docs below!
            onKeyEvent(it, events, eventScope)
        },
    ) {
        MaterialTheme(
            colors = getCurrentTheme(),
            typography = Typography(
                defaultFontFamily = FontFamily(
                    Font("font/Xolonium-Regular.otf")
                )
            )
        ) {
            Surface(color = MaterialTheme.colors.background) {
                DesktopNodeHost(
                    windowState = windowState,
                    onBackPressedEvents = events.receiveAsFlow().mapNotNull {
                        if (it is Events.OnBackPressed) Unit else null
                    }
                ) {
                    RootNode(buildContext = it)
                }
            }
        }
    }
}

private fun onKeyEvent(
    keyEvent: KeyEvent,
    events: Channel<Events>,
    coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main),
): Boolean =
    when {
        // You can also register e.g. Key.Escape instead of BackSpace:
        keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.Backspace -> {
            coroutineScope.launch { events.send(Events.OnBackPressed) }
            true
        }

        else -> false
    }
