import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val defaultPrimary = Color(39, 42, 89)
val defaultPrimaryVariant = Color(3, 23, 58)
val defaultSecondary = Color(25, 26, 38)
val defaultSecondaryVariant = Color(239, 234, 0)
val defaultBackground = Color(45, 46, 64)
val defaultSurface = Color(8, 47, 113)
val defaultError = Color(191, 0, 26)
val defaultOnPrimary = Color(242, 191, 94)
val defaultOnSecondary = Color(115, 50, 50)
val defaultOnBackground = Color(242, 191, 94)
val defaultOnSurface = Color(149, 147, 0)
val defaultOnError = Color(177, 173, 0)

fun getThemes() : List<Theme> {
    return listOf(
        Theme("default", themeDefault()),
        Theme("dark", darkColors()),
        Theme("light", lightColors())
    )
}

data class Theme(val name: String, val colors: Colors)