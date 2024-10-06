package com.okunev.alertsample

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.ClipData
import android.content.ClipboardManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.okunev.alertsample.notification.AlertSampleTokenDataStore
import com.okunev.alertsample.player.AlertSamplePlayer
import kotlinx.coroutines.delay
import org.koin.android.ext.android.inject

internal class MainActivity : ComponentActivity() {

    private val alertPlayer by inject<AlertSamplePlayer>()
    private val dataStore by inject<AlertSampleTokenDataStore>()

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) showToast(R.string.permission_granted) else showToast(R.string.permission_denied)
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(POST_NOTIFICATIONS)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Button(onClick = {
                        alertPlayer.stopAlert()
                        showToast(R.string.alert_turned_off)
                    }) {
                        Text(text = stringResource(id = R.string.alert_turn_off_text))
                    }
                    TokenView()
                }
            }
        }
        askNotificationPermission()
    }

    @Composable
    private fun BoxScope.TokenView() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            val interactionSource = remember { MutableInteractionSource() }
            val token = produceState(initialValue = dataStore.getToken()) {
                while (dataStore.getToken().isEmpty()) {
                    delay(1000)
                    value = dataStore.getToken()
                }
            }
            Text(modifier = Modifier.padding(horizontal = 12.dp), text = stringResource(id = R.string.token_title))
            Text(
                modifier = Modifier
                    .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 12.dp)
                    .clickable(interactionSource, null) {
                        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                        clipboard.setPrimaryClip(ClipData.newPlainText("", token.value))
                        showToast(R.string.token_copied)
                    },
                text = token.value,
                fontSize = 10.sp
            )
        }
    }

    private fun showToast(text: Int) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}
