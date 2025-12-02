package com.example.huertohogar20.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.huertohogar20.R
import androidx.compose.ui.text.font.FontWeight

// COLORES centrales
val FondoBlancoSuave = Color(0xFFF7F7F7)
val VerdeEsmeralda = Color(0xFF2E8B57)
val AmarilloMostaza = Color(0xFFFFD700)
val MarronClaro    = Color(0xFF8B4513)
val BlancoPuro     = Color(0xFFFFFFFF)
val TextoGrisOscuro= Color(0xFF1B1B1B)
val TextoGrisMedio = Color(0xFF404040)
val GrisClaro      = Color(0xFFE0E0E0)

// FUENTES
val MontserratFamily = FontFamily(Font(R.font.montserrat_regular))
val PlayfairDisplayFamily = FontFamily(Font(R.font.playfair_display_regular))

// ESQUEMA DE COLOR
val colorSchemeHuerto = lightColorScheme(
    primary       = VerdeEsmeralda,
    onPrimary     = BlancoPuro,
    background    = FondoBlancoSuave,
    onBackground  = TextoGrisOscuro,
    surface       = BlancoPuro,
    onSurface     = TextoGrisOscuro,
    secondary     = MarronClaro,
    onSecondary   = BlancoPuro,
    tertiary      = AmarilloMostaza,
    onTertiary    = TextoGrisOscuro,
    error         = Color(0xFFD32F2F),
    onError       = BlancoPuro,
    outline       = GrisClaro,
    surfaceVariant= GrisClaro,

    onSurfaceVariant = TextoGrisMedio
)


val TypographyHuerto = Typography(
    headlineLarge = TextStyle(
        fontFamily = PlayfairDisplayFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = PlayfairDisplayFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = PlayfairDisplayFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),


    bodyLarge = TextStyle(
        fontFamily = MontserratFamily,
        fontSize = 16.sp,
        color = TextoGrisOscuro
    ),
    bodyMedium = TextStyle(
        fontFamily = MontserratFamily,
        fontSize = 14.sp,
        color = TextoGrisOscuro
    ),
    bodySmall = TextStyle(
        fontFamily = MontserratFamily,
        fontSize = 12.sp,
        color = TextoGrisOscuro
    ),


    titleMedium = TextStyle(
        fontFamily = MontserratFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp

    )
)


@Composable
fun LaHuertaHogarTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = colorSchemeHuerto,
        typography = TypographyHuerto,
        shapes = Shapes(),
        content = content
    )
}