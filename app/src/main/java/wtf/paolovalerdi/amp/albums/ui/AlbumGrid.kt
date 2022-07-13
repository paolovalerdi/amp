package wtf.paolovalerdi.amp.albums.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val dummy = List(9) { AlbumCardData() }

@Preview
@Composable
fun AlbumGrid(
    albums: List<AlbumCardData> = dummy,
    spanCount: Int = 2
) {
    val rows = albums.chunked(spanCount)
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(10.dp)
    ) {
        items(rows) { columns ->
            Row {
                val width = Modifier.weight(1f)
                repeat(spanCount) { index ->
                    val data = columns.getOrNull(index)
                    if (data != null) {
                        AlbumCard(
                            modifier = width,
                            data = data
                        )
                    } else {
                        Box(modifier = width)
                    }
                }
            }
        }
    }
}