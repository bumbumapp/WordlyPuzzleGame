package com.bumbumapps.khiardle.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.bumbumapps.khiardle.R
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Jakarta = FontFamily(
    Font(R.font.jakarta_regular, FontWeight.Normal),
    Font(R.font.jakarta_medium, FontWeight.Medium),
    Font(R.font.jakarta_semibold, FontWeight.SemiBold),
    Font(R.font.jakarta_bold, FontWeight.Bold),
    Font(R.font.jakarta_extrabold, FontWeight.ExtraBold),
    Font(R.font.jakarta_extrabold, FontWeight.Black),
)

// Bento: Plus Jakarta Sans everywhere (Material default styles fall back to Jakarta).
val Typography = Typography().let { t ->
    Typography(
        displayLarge = t.displayLarge.copy(fontFamily = Jakarta), displayMedium = t.displayMedium.copy(fontFamily = Jakarta),
        displaySmall = t.displaySmall.copy(fontFamily = Jakarta), headlineLarge = t.headlineLarge.copy(fontFamily = Jakarta),
        headlineMedium = t.headlineMedium.copy(fontFamily = Jakarta), headlineSmall = t.headlineSmall.copy(fontFamily = Jakarta),
        titleLarge = t.titleLarge.copy(fontFamily = Jakarta), titleMedium = t.titleMedium.copy(fontFamily = Jakarta),
        titleSmall = t.titleSmall.copy(fontFamily = Jakarta), bodyMedium = t.bodyMedium.copy(fontFamily = Jakarta),
        bodySmall = t.bodySmall.copy(fontFamily = Jakarta), labelLarge = t.labelLarge.copy(fontFamily = Jakarta),
        labelMedium = t.labelMedium.copy(fontFamily = Jakarta), labelSmall = t.labelSmall.copy(fontFamily = Jakarta),
    bodyLarge = TextStyle(
        fontFamily = Jakarta,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = Jakarta,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Jakarta,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)
}
