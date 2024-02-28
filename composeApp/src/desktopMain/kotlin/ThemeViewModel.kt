import androidx.compose.material.Colors
import androidx.compose.runtime.mutableStateOf

class ThemeViewModel() {
    val theme = mutableStateOf(themeDefault())
//TODO get the background color to change
    fun updateTheme(newTheme: Colors) {
        theme.value = newTheme
    }
}