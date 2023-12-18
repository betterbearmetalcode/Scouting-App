import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.ui.graphics.Color

val theme = themeDefault()

fun themeDefault(): Colors {
    val primary = Color.Black
    val primaryVarient = Color.Yellow
    val secondary = Color.Yellow
    val secondaryVarient = Color.Blue
    val background = Color(50, 50, 50)
    val error = Color.Red;
    val theme = darkColors(primary = primary,
        primaryVariant = primaryVarient,
        secondary = secondary,
        secondaryVariant = secondaryVarient,
        background = background,
        error = error)



    return theme
}

fun getCurrentTheme() : Colors {
    return theme
}