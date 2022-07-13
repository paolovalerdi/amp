package wtf.paolovalerdi.amp.tracks.scanner.util

import com.simplecityapps.ktaglib.Metadata
import wtf.paolovalerdi.amp.data.Track
import wtf.paolovalerdi.amp.tracks.scanner.MediaFile

fun buildTrack(mediaFile: MediaFile, metadata: Metadata): Track {
    return Track(
        id = mediaFile.id,
        treeUri = mediaFile.treeUri.toString(),
        uri = mediaFile.uri.toString(),
        albumArtist = metadata.getTag("ALBUMARTIST"),
        albumArtistSort = metadata.getTag("ALBUMARTISTSORT"),
        album = metadata.getTag("ALBUM"),
        albumSort = metadata.getTag("ALBUMSORT"),
        discSubtitle = metadata.getTag("DISCSUBTITLE"),
        discNumber = 0,
        discTotal = 0,
        releaseDate = metadata.getTag("RELEASEDATE"),
        publisher = metadata.getTag("PUBLISHER"),
        label = metadata.getTag("LABEL"),
        copyright = metadata.getTag("COPYRIGHT"),
        lyricist = metadata.getTag("LYRICIST"),
        composer = metadata.getTag("COMPOSER"),
        composerSort = metadata.getTag("COMPOSERSORT"),
        arranger = metadata.getTag("ARRANGER"),
        artist = metadata.getTag("ARTIST"),
        artistSort = metadata.getTag("ARTISTSORT"),
        conductor = metadata.getTag("CONDUCTOR"),
        work = metadata.getTag("WORK"),
        grouping = metadata.getTag("GROUPING"),
        title = metadata.getTag("TITLE"),
        titleSort = metadata.getTag("TITLESORT"),
        subtitle = metadata.getTag("SUBTITLE"),
        trackNumber = 0,
        trackTotal = 0,
        originalData = metadata.getTag("ORIGINALDATE"),
        date = metadata.getTag("DATE"),
        comment = metadata.getTag("COMMENT"),
        lyrics = metadata.getTag("LYRICS"),
        mood = metadata.getTag("MOOD"),
        rating = metadata.getTag("RATING"),
        albumArtId = mediaFile.albumArtId,
    )
}