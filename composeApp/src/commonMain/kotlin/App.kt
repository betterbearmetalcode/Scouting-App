import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi

@Composable
fun App() {
    MaterialTheme {
        Column {
            Text(
                text = "Bear Metal Scout App",
                fontSize = 40.sp
            )
        }
    }
}