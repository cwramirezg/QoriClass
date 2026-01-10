package com.cwramirezg.qoriclass

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.cwramirezg.design.theme.QoriClassTheme
import com.cwramirezg.qoriclass.navigation.AppNavHost
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val view = splashScreenView.view

            val scaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, 1f, 1.5f)
            val scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f, 1.5f)
            val fadeOut = ObjectAnimator.ofFloat(view, View.ALPHA, 1f, 0f)

            AnimatorSet().apply {
                playTogether(scaleX, scaleY, fadeOut)
                interpolator = AccelerateInterpolator()
                duration = 400L
                doOnEnd { splashScreenView.remove() }
                start()
            }
        }

        enableEdgeToEdge()
        setContent {
            QoriClassTheme {
                val navController = rememberNavController()
                LaunchedEffect(Unit) {
                    navController.currentBackStack.collectLatest {
                        Timber.d(
                            "currentBackStack: ${
                                it.map { entry ->
                                    entry.destination.route?.split(".")?.last()
                                }
                            }"
                        )
                    }
                }
                AppNavHost(
                    navController = navController
                )
            }
        }
    }
}
