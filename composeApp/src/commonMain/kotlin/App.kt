import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.ui.graphics.Color
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
fun catppuccinMocha(): Colors {

    val theme = darkColors(
        primary = Color(0xFF181825),
        primaryVariant = Color(0xFFB4BEFE),
        secondary = defaultSecondary,//Only one to stay the same
        secondaryVariant = Color(0xFF74C7EC),
        background = Color(200,200,200), // 0x181825
        surface = Color(0xff1E1E2E),
        error = Color(0xFF38BA8),
        onPrimary = Color(0xFFC6D0F5),
        onSecondary = Color(0xFFC6D0F5),
        onBackground = Color(0xFF696D86),
        onSurface = Color(0xFF43465A),
        onError = Color(0xFF8839EF)
    )

    return theme
}

fun spareMetal(): Colors {

    val theme = darkColors(
    primary = Color(6,9,13),
    primaryVariant = Color(0xFF181825),
    secondary = Color(15,31,47),
    secondaryVariant = Color.Yellow,
    background = Color(175,175,175),
    surface = Color(6,9,13),
    error = Color(255, 209, 220),
    onPrimary = Color(15,15,15),
    onSecondary = Color.Black ,
    onBackground = Color.Yellow,
    onSurface = Color(150,150,150),
    onError = Color(6,9,13),
    )
    return theme
}


var currentColors: Colors = themeDefault()
