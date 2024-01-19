package org.example.project


import RootNode
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumble.appyx.navigation.integration.NodeActivity
import com.bumble.appyx.navigation.integration.NodeHost
import com.bumble.appyx.navigation.platform.AndroidLifecycle
import currentColors

@ExperimentalUnitApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
class MainActivity : NodeActivity() {

    //private val navigator = Navigator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            MaterialTheme(themeViewModel.theme.value) {
                Surface(color = themeViewModel.theme.value.background) {
                    //CompositionLocalProvider(LocalNavigator provides navigator) {
                    NodeHost(
                        lifecycle = AndroidLifecycle(LocalLifecycleOwner.current.lifecycle),
                        integrationPoint = appyxV2IntegrationPoint,
                    ) {
                        RootNode(
                            buildContext = it,
                        )
                    }
                    //}
                }
            }
        }
    }
}