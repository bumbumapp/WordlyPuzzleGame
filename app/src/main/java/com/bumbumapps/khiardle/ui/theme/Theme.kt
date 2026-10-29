package com.bumbumapps.khiardle.ui.theme

import android.app.Activity
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF0000000),
    secondary = Color(0xFFFFA000),
    tertiary = Color(0xFF0288D1),
    onBackground = Color(0xFFD1D1D1),
    onPrimary = Color(0xFFFFFFFF),
    background = Color(0xFF0D0F16)
    )

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF689F38),
    secondary = Color(0xFFFFA000),
    tertiary = Color(0xFF0288D1),
    onBackground = Color(0xFFD1D1D1),
    onPrimary = Color(0xFFFFFFFF),
    background = Color(0xFF0D0F16)

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

val ColorScheme.correctBackground @Composable get() = Color(0xFF2FB36B)
val ColorScheme.wrongPositionBackground @Composable get() = Color(0xFF3D8BFF)
val ColorScheme.incorrectBackground @Composable get() = Color(0xFF2A2F3D)
val ColorScheme.enteringBackground @Composable get() = MaterialTheme.colorScheme.background
val ColorScheme.keyboard @Composable get() = Color(0xFF262B3A)
val ColorScheme.keyboardDisabled @Composable get() = Color(0xFF171A23)
val ColorScheme.onKeyboard @Composable get() = Color(0xFFE7E7E7)

@Composable
fun KhiardleTheme(
    darkTheme: Boolean = true,//isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            // Both schemes use the graphite background, so the bar matches it and keeps light icons.
            (view.context as Activity).window.statusBarColor = colorScheme.background.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}