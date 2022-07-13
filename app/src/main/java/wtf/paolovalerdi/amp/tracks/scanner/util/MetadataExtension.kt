package wtf.paolovalerdi.amp.tracks.scanner.util

import com.simplecityapps.ktaglib.Metadata

fun Metadata.getTag(tag: String, fallbackTag: String = ""): String? {
    val values = propertyMap[tag] ?: propertyMap[fallbackTag]
    return values?.joinToString(", ")
}