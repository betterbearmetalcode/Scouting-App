package org.example.project

import androidx.compose.material.Colors
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import themeDefault

class ThemeViewModel : ViewModel() {
    val theme = mutableStateOf(themeDefault())

    fun updateTheme(newTheme: Colors) {
        theme.value = newTheme
    }
}