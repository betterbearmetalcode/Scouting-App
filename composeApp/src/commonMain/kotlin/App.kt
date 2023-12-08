import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun App() {
    MaterialTheme(colors = theme) {
        Box(Modifier.fillMaxSize().background(theme.background))
        Column(modifier = Modifier.background(theme.background)) {

            Text(
                text = "Bear Metal Scout App",
                fontSize = 40.sp
            )
            Divider(color = theme.secondary, thickness = 2.dp)
            Button(
                content = {
                    Text(
                        text = "Quantitative Scouting",
                        color = theme.primaryVariant,
                        fontSize = 23.sp
                        )
                },
                onClick = {
                    loadQuantitativeScouting()
                },
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(horizontal = 50.dp, vertical = 50.dp),

                )

            Button(
                content = {
                    Text(
                        text = "Pits Scouting",
                        color = theme.primaryVariant,
                        fontSize = 22.sp
                    )
                },
                onClick = {
                    loadPitsScouting()
                },
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(horizontal = 50.dp, vertical = 50.dp),

                )
            Button(
                content = {
                    Text(
                        text = "Qualitative Scouting",
                        color = theme.primaryVariant,
                        fontSize = 22.sp
                    )
                },
                onClick = {
                    loadQualitativeScouting()
                },
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(horizontal = 50.dp, vertical = 50.dp),

            )
        }
    }
}

val theme = themeDefault()

fun loadQuantitativeScouting() {

}

fun loadPitsScouting() {

}

fun loadQualitativeScouting() {

}

fun themeDefault(): Colors {
    val primary = Color.Black
    val secondary = Color.Yellow
    val background = Color(50, 50, 50)
    val error = Color.Red;
    val theme = darkColors(primary = primary,
        primaryVariant = secondary,
        secondary = secondary,
        background = background,
        error = error)



    return theme
}