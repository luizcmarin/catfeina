/*
 * Arquivo: com.marin.catfeina.ui.preferencias.PreferenciasScreen.kt
 * Salva preferencias de usuário no DataStore.
 */
package com.marin.catfeina.ui.preferencias

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PreferenciasScreen(
    isDarkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Modo Escuro", style = MaterialTheme.typography.bodyLarge)
        Switch(
            checked = isDarkMode,
            onCheckedChange = onDarkModeChange
        )
    }
}
