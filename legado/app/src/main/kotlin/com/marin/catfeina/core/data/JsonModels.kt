// =============================================================================
// Arquivo: com.marin.catfeina.core.data.models.JsonModels.kt
// Descrição: Data classes para a desserialização do JSON.
//            CORRIGIDO: Removidas as anotações @SerialName para espelhar
//            exatamente as Entities (camelCase).
// =============================================================================
package com.marin.catfeina.core.data.models

import kotlinx.serialization.Serializable

// ================== POESIAS ==================
@Serializable
data class PoesiasJson(
    val poesias: List<PoesiaJsonItem>
)

@Serializable
data class PoesiaJsonItem(
    val id: Long,
    val categoria: String,
    val titulo: String,
    val textoBase: String,
    val texto: String,
    val textoFinal: String?,
    val imagem: String?,
    val autor: String?,
    val nota: String?,
    val campoUrl: String?,
    val dataCriacao: Long
)

// ================== PERSONAGENS ==================
@Serializable
data class PersonagensJson(
    val personagens: List<PersonagemJsonItem>
)

@Serializable
data class PersonagemJsonItem(
    val id: Long,
    val nome: String,
    val descricao: String,
    val imagem: String?,
    val dataCriacao: Long
)

// ================== INFORMATIVOS ==================
@Serializable
data class InformativosJson(
    val informativos: List<InformativoJsonItem>
)

@Serializable
data class InformativoJsonItem(
    val chave: String,
    val conteudo: String,
    val dataAtualizacao: Long
)

// ================== ATELIER ==================
@Serializable
data class AtelierJson(
    val notas: List<AtelierJsonItem>
)

@Serializable
data class AtelierJsonItem(
    val id: Long,
    val titulo: String,
    val conteudo: String,
    val dataAtualizacao: Long,
    val fixada: Boolean
)
