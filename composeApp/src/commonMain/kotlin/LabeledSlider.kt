import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Slider
import androidx.compose.material.SliderColors
import androidx.compose.material.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun LabeledSlider(
    label: @Composable RowScope.() -> Unit,
    sliderValue: Float,
    onValueChange: (Float) -> Unit,
    sliderModifier: Modifier = Modifier,
    valueRange: ClosedFloatingPointRange<Float> = 0f .. 10f,
    steps: Int = 9,
    enabled: Boolean = true,
    onValueChangeFinished: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: SliderColors = SliderDefaults.colors()
) {
    Row (content = label)
    Slider(
        value = sliderValue,
        onValueChange = onValueChange,
        valueRange = valueRange,
        modifier = sliderModifier,
        steps = steps,
        enabled = enabled,
        onValueChangeFinished = onValueChangeFinished,
        interactionSource = interactionSource,
        colors = colors
        )
}