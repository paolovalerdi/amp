package wtf.paolovalerdi.amp.tracks.scanner.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import wtf.paolovalerdi.amp.tracks.scanner.ScanResult
import wtf.paolovalerdi.amp.tracks.scanner.Scanner

@Composable
fun ScannerScreen(scanner: Scanner) {
    var scanResult by remember { mutableStateOf(ScanResult()) }
    val scope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocumentTree()
    ) { uri: Uri? ->
        if (uri == null) return@rememberLauncherForActivityResult
        scope.launch {
            scanner.scan(uri).collect {
                scanResult = it
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Button(onClick = { launcher.launch(null) }) {
            Text(text = "Select folder")
        }
        Text(scanResult.toString())
    }
}