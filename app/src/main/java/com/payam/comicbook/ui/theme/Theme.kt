package com.payam.comicbook.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = ColorPrimary,
    background = DarkGray,
    onBackground = TextWhite,
    onPrimary = DarkGray,
    primaryVariant = ColorPrimaryLight,
    secondary = ColorPrimaryDark
)

private val LightColorPalette = lightColors(
    primary = ColorPrimary,
    background = BackgroundWhite,
    onBackground = MediumGray,
    onPrimary = DarkGray,
    primaryVariant = ColorPrimaryLight,
    secondary = ColorPrimaryDark
)

@Composable
fun ComicBookTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()
    if (!darkTheme)
        SideEffect {
            systemUiController.setStatusBarColor(
                color = ColorPrimaryLight,
                darkIcons = useDarkIcons
            )
        }
    else
        SideEffect {
            systemUiController.setStatusBarColor(
                color = MediumGray,
                darkIcons = useDarkIcons
            )
        }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}