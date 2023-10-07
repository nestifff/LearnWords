package com.nestifff.learnwords.presentation.ui.theme.values.typography

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.nestifff.learnwords.R

val robotoFontFamily = FontFamily(
    Font(R.font.roboto_thin_100, weight = FontWeight.Thin),
    Font(R.font.roboto_light_300, weight = FontWeight.Light),
    Font(R.font.roboto_regular_400, weight = FontWeight.Normal),
    Font(R.font.roboto_medium_500, weight = FontWeight.Medium),
    Font(R.font.roboto_bold_700, weight = FontWeight.Bold),
    Font(R.font.roboto_black_900, weight = FontWeight.Black),
)

val wordsTypography = TypographyCommon(
    h1RegularTextStyle = TextStyle(
        fontFamily = robotoFontFamily,
        fontSize = 20.sp,
        fontWeight = FontWeight.Normal
    ),
    h1MediumTextStyle = TextStyle(
        fontFamily = robotoFontFamily,
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium
    ),
    h1BoldTextStyle = TextStyle(
        fontFamily = robotoFontFamily,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    ),
    h2RegularTextStyle = TextStyle(
        fontFamily = robotoFontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    ),
    h2MediumTextStyle = TextStyle(
        fontFamily = robotoFontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium
    ),
    h2BoldTextStyle = TextStyle(
        fontFamily = robotoFontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    ),
    h3RegularTextStyle = TextStyle(
        fontFamily = robotoFontFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal
    ),
    h3MediumTextStyle = TextStyle(
        fontFamily = robotoFontFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium
    ),
    h3BoldTextStyle = TextStyle(
        fontFamily = robotoFontFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold
    ),
)
