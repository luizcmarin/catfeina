// ===================================================================================
// Arquivo: com.marin.catfeina.core.formatador.TextoFormatado.kt
//
// Descrição: Composable de alto nível que orquestra o parsing e a renderização
//            de texto formatado.
//
// Propósito:
// Este é o ponto de entrada principal para exibir texto formatado na UI. Ele
// recebe o texto cru, utiliza o `ParserTextoFormatado` para dividi-lo em uma
// lista de `ElementoConteudo` e, em seguida, itera sobre essa lista, delegando
// a renderização de cada elemento individual para o `RenderizarElementoConteudo`.
// ===================================================================================
package com.marin.catfeina.core.formatador

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.marin.catfeina.core.formatador.parser.ParserTextoFormatado

@Composable
fun TextoFormatado(
    textoCru: String,
    parser: ParserTextoFormatado,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 16.sp // Valor padrão
) {
    var tooltipText by remember { mutableStateOf<String?>(null) }

    val tooltipHandler = remember { 
        TooltipHandler { texto ->
            tooltipText = texto
        }
    }

    // 1. Usa o parser para converter o texto cru em uma lista de elementos estruturados.
    val elementos = parser.parse(textoCru)

    // 2. Itera sobre a lista de elementos e renderiza o Composable correto para cada um.
    Column(modifier = modifier) {
        elementos.forEach { elemento ->
            RenderizarElementoConteudo(
                elemento = elemento,
                fontSize = fontSize,
                tooltipHandler = tooltipHandler
            )
        }
    }

    // 3. Exibe o AlertDialog se houver um texto de tooltip.
    tooltipText?.let {
        AlertDialog(
            onDismissRequest = { tooltipText = null },
            title = { Text("Dica") },
            text = { Text(it) },
            confirmButton = {
                TextButton(onClick = { tooltipText = null }) {
                    Text("OK")
                }
            }
        )
    }
}
