import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.BackStackModel
import com.bumble.appyx.components.backstack.operation.push
import com.bumble.appyx.components.backstack.ui.fader.BackStackFader
import com.bumble.appyx.navigation.composable.AppyxComponent
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.navigation.node.ParentNode
import com.bumble.appyx.navigation.node.node
import com.bumble.appyx.utils.multiplatform.Parcelable
import com.bumble.appyx.utils.multiplatform.Parcelize
import pages.MainMenu
import pages.PitsScout


class RootNode(
    buildContext: BuildContext,
    private val backStack: BackStack<NavTarget> = BackStack(
        model = BackStackModel(
            initialTarget = NavTarget.MainMenu,
            savedStateMap = buildContext.savedStateMap
        ),
        visualisation = { BackStackFader(it) }
    )
) : ParentNode<RootNode.NavTarget>(
    appyxComponent = backStack,
    buildContext = buildContext
) {

    /**
     * You can create this class inside the body of RootNode
     *
     * Note: You must apply the 'kotlin-parcelize' plugin to use @Parcelize
     * https://developer.android.com/kotlin/parcelize
     */
    sealed class NavTarget : Parcelable {
        @Parcelize
        data object MainMenu : NavTarget()

        @Parcelize
        data object QuanScouting : NavTarget()

        @Parcelize
        data object PitsScouting : NavTarget()

        @Parcelize
        data object QualScouting : NavTarget()
    }

    override fun resolve(interactionTarget: NavTarget, buildContext: BuildContext): Node =
        when (interactionTarget) {
            NavTarget.MainMenu -> MainMenu(buildContext, backStack)
            NavTarget.QuanScouting -> node(buildContext) { Text(text = "Placeholder for child 2") }
            NavTarget.PitsScouting -> PitsScout(buildContext)
            NavTarget.QualScouting -> node(buildContext) { Text(text = "Placeholder")}
        }

    @Composable
    override fun View(modifier: Modifier) {
        Column (modifier = modifier.background(theme.background)) {
            AppyxComponent(
                appyxComponent = backStack,
                modifier = Modifier.weight(0.9f)
            )
            Row {
                Button(
                    onClick = {
                        backStack.push(NavTarget.MainMenu)
                    },
                    content = {
                        Text(
                            text = "Main Menu"
                        )
                    }
                )
            }
        }
    }
}