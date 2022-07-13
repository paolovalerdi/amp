package wtf.paolovalerdi.amp.ui.theme

import androidx.compose.ui.text.TextStyle

data class Typography2(
    val headline: Style,
    val title: Style,
    val body: Style,
    val label: Style,
    val caption: Style
) {

    data class Style(
        val xs: TextStyle?,
        val s: TextStyle?,
        val m: TextStyle?,
        val l: TextStyle?,
        val xl: TextStyle?
    )
}