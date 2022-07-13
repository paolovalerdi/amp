package wtf.paolovalerdi.amp.tracks.scanner

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.DocumentsContract.*
import android.provider.DocumentsContract.Document.*
import com.simplecityapps.ktaglib.KTagLib
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import wtf.paolovalerdi.amp.Database
import wtf.paolovalerdi.amp.tracks.scanner.util.buildTrack
import java.util.*

data class ScanResult(
    val scanned: Int = 0,
    val failed: Int = 0,
)

object MediaFileHasNoMetadata : Throwable()

class Scanner(
    private val context: Context,
    private val taglib: KTagLib,
    private val database: Database,
) {
    private val contentResolver by lazy { context.contentResolver }

    private val projection = arrayOf(
        COLUMN_DOCUMENT_ID,
        COLUMN_DISPLAY_NAME,
        COLUMN_SIZE,
        COLUMN_LAST_MODIFIED,
        COLUMN_MIME_TYPE,
    )
    private val read = "r"

    fun scan(uri: Uri) = flow {
        var scanned = 0
        var failed = 0
        emit(ScanResult(scanned, failed))
        listMediaFilesFrom(uri).buffer(5).collect { mediaFile ->
            val result = runCatching {
                val fd = contentResolver.openFileDescriptor(
                    mediaFile.uri,
                    read
                ) ?: throw NullPointerException()

                fd.use { taglib.getMetadata(it.detachFd()) } ?: throw MediaFileHasNoMetadata
            }.fold(
                onFailure = {
                    failed++
                    emit(ScanResult(scanned, failed))
                },
                onSuccess = { metadata ->
                    database.trackQueries.insert(buildTrack(mediaFile, metadata))
                    scanned++
                    emit(ScanResult(scanned, failed))
                }
            )
        }
    }.flowOn(Dispatchers.IO)

    private fun listMediaFilesFrom(tree: Uri) = flow {
        val stack = mutableListOf(buildChildDocumentsUriUsingTree(tree, getTreeDocumentId(tree)))
        while (stack.isNotEmpty()) {
            val folder = stack.removeLast()
            val folderId = getDocumentId(folder)
            println(folderId)
            getCursor(folder, projection)?.use { cursor ->
                while (cursor.moveToNext()) {
                    val id: String = cursor.getString(0)
                    val displayName: String = cursor.getString(1)
                    val size: Long = cursor.getLong(2)
                    val lastModified: Long = cursor.getLong(3)
                    val mimeType: String = cursor.getString(4)
                    when {
                        displayName.isAudio() -> {
                            val uri = buildDocumentUriUsingTree(getTreeUri(tree), id)
                            val cover = buildDocumentUriUsingTree(
                                getTreeUri(tree),
                                "${folderId}/albumart.jpg"
                            )
                            emit(
                                MediaFile(
                                    id,
                                    tree,
                                    uri,
                                    cover.toString(),
                                    displayName,
                                    size,
                                    lastModified
                                )
                            )
                        }
                        mimeType == MIME_TYPE_DIR -> stack.add(
                            buildChildDocumentsUriUsingTree(
                                tree,
                                id
                            )
                        )
                    }
                }
            }
        }
    }.flowOn(Dispatchers.IO)


    private fun getTreeUri(uri: Uri): Uri? {
        val isDocument = isDocumentUri(context, uri)
        return buildDocumentUriUsingTree(
            uri,
            if (isDocument) getDocumentId(uri) else getTreeDocumentId(uri)
        )
    }

    private fun getCursor(uri: Uri, projection: Array<String>): Cursor? {
        return contentResolver.query(
            uri,
            projection,
            null,
            null,
            null
        )
    }

    private fun String.isAudio(): Boolean {
        val extensions = arrayOf(
            "mp3",
            "3gp",
            "mp4",
            "m4a",
            "m4b",
            "aac",
            "ts",
            "flac",
            "mid",
            "xmf",
            "mxmf",
            "midi",
            "rtttl",
            "rtx",
            "ota",
            "imy",
            "ogg",
            "mkv",
            "wav",
            "opus"
        )
        val extension = substringAfterLast(".").lowercase(Locale.getDefault())
        return extensions.contains(extension)
    }
}