package xyz.teamgravity.datastore_proto

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.datastore.dataStore
import kotlinx.coroutines.launch
import xyz.teamgravity.datastore_proto.ui.theme.DatastoreprotoTheme

val Context.dataStore by dataStore("settings.json", SettingsSerializer)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DatastoreprotoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val settings = dataStore.data.collectAsState(initial = SettingsModel()).value
                    val scope = rememberCoroutineScope()

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Language.values().forEach { language ->

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = language == settings.language,
                                    onClick = {
                                        scope.launch {
                                            setLanguage(language)
                                        }
                                    }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = language.toString())
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun setLanguage(language: Language) {
        dataStore.updateData { it.copy(language = language) }
    }
}