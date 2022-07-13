package wtf.paolovalerdi.amp.tracks.scanner

import android.net.Uri

data class MediaFile(
    val id: String,
    val treeUri: Uri,
    val uri: Uri,
    val albumArtId: String,
    val displayName: String,
    val size: Long,
    val lastModified: Long,
)