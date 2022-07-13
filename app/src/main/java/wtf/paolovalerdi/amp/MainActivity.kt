package wtf.paolovalerdi.amp

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.map
import org.koin.android.ext.android.inject
import wtf.paolovalerdi.amp.albums.ui.AlbumCardData
import wtf.paolovalerdi.amp.albums.ui.AlbumGrid
import wtf.paolovalerdi.amp.tracks.scanner.Scanner
import wtf.paolovalerdi.amp.tracks.scanner.ui.ScannerScreen
import wtf.paolovalerdi.amp.ui.theme.AmpTheme

class MainActivity : ComponentActivity() {

    private val scanner: Scanner by inject()

    private val database: Database by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val albums = database.trackQueries.albums()
            .asFlow()
            .mapToList()
            .map { list ->
                list.groupBy { item -> item.album }.values.flatten().map { album ->
                    AlbumCardData(
                        album.album ?: "",
                        album.albumArtist ?: "",
                        album.releaseDate ?: "",
                        Uri.parse(album.albumArtId)
                    )
                }
            }
        setContent {
            AmpTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val items = albums.collectAsState(initial = emptyList())
                    Column {
                        ScannerScreen(scanner)
                        AlbumGrid(albums = items.value)
                    }
                }
            }
        }
    }
}