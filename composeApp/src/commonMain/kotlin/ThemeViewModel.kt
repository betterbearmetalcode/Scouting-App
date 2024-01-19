import androidx.compose.material.Colors
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class ThemeViewModel {
    val colors: MutableState<Colors> = mutableStateOf(currentColors)
}