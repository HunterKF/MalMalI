package com.jaegerapps.malmali.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

val LightColorScheme = lightColorScheme(
    primary = BluePrimaryLight,
    secondary = PinkSecondaryLight,
    tertiary = SurfaceLight,
    onPrimary = SurfaceLight,
    onSecondary = SurfaceLight,
    onTertiary = OnSurfaceBlackLight,
    onTertiaryContainer = OnSurfaceBlackLight,
    background = BackgroundLight,
    onBackground = BlackOnBackGroundLight,
    surface = SurfaceLight,
    onSurface = BlackOnBackGroundLight,
    error = ErrorRedLight,
    onError = SurfaceLight,
    outline = BlackOnBackGroundLight
)

val DarkColorScheme = darkColorScheme(
    primary = BluePrimaryDark,
    secondary = PinkSecondaryDark,
    tertiary = SurfaceDark,
    onPrimary = SurfaceDark,
    onSecondary = SurfaceDark,
    onTertiary = OnSurfaceBlackDark,
    onTertiaryContainer = OnSurfaceBlackDark,
    background = BackgroundDark,
    onBackground = BlackOnBackGroundDark,
    surface = SurfaceDark,
    onSurface = BlackOnBackGroundDark,
    error = ErrorRedDark,
    onError = SurfaceDark,
    outline = BlackOnBackGroundDark

)