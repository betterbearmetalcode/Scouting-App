import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Colors
import androidx.compose.material.darkColors

fun themeDefault(): Colors {

    val theme = darkColors(
        primary = defaultPrimary,
        primaryVariant = defaultPrimaryVariant,
        secondary = defaultSecondary,
        secondaryVariant = defaultSecondaryVariant,
        background = defaultBackground,
        surface = defaultSurface,
        error = defaultError,
        onPrimary = defaultOnPrimary,
        onSecondary = defaultOnSecondary,
        onBackground = defaultOnBackground,
        onSurface = defaultOnSurface,
        onError = defaultOnError
    )

    return theme
}

var currentColors: Colors = themeDefault()