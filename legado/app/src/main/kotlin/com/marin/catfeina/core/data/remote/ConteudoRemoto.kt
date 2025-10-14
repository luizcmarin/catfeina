// =============================================================================
// Arquivo: com.marin.catfeina.core.data.remote.ConteudoRemoto.kt
// Descrição: Modelo de dados para o conteúdo JSON baixado da API.
// =============================================================================
package com.marin.catfeina.core.data.remote

import com.marin.catfeina.core.data.entities.AtelierEntity
import com.marin.catfeina.core.data.entities.InformativoEntity
import com.marin.catfeina.core.data.entities.PersonagemEntity
import com.marin.catfeina.core.data.entities.PoesiaEntity
import kotlinx.serialization.Serializable

@Serializable
data class ConteudoRemoto(
    val poesias: List<PoesiaEntity> = emptyList(),
    val personagens: List<PersonagemEntity> = emptyList(),
    val informativos: List<InformativoEntity> = emptyList(),
    val atelier: List<AtelierEntity> = emptyList()
)
