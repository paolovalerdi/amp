package wtf.paolovalerdi.amp.albums.ui

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

data class AlbumCardData(
    val title: String = "ALBUM",
    val albumArtist: String = "ALBUMARTIST",
    val releaseDate: String = "RELEASEDATE",
    val albumArt: Uri = Uri.EMPTY,
)

@Preview
@Composable
fun AlbumCard(
    modifier: Modifier = Modifier,
    data: AlbumCardData = AlbumCardData(),
) {
    Box(modifier = modifier.padding(6.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            AsyncImage(
                model = data.albumArt,
                contentDescription = data.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1f)
            )
            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                Text(text = data.title)
                Box(modifier = Modifier.height(2.dp))
                Text(text = data.albumArtist)
                Box(modifier = Modifier.height(3.dp))
                Text(text = data.releaseDate)
                Box(modifier = Modifier.height(1.dp))
            }
        }
    }
}