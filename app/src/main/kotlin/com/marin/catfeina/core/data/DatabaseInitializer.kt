// =============================================================================
// Arquivo: com.marin.catfeina.core.data.DatabaseInitializer.kt
// Descrição: Inicializador que popula o banco de dados na primeira execução do app.
// =============================================================================
package com.marin.catfeina.core.data

import android.content.Context
import androidx.startup.Initializer
import com.marin.catfeina.core.data.daos.AtelierDao
import com.marin.catfeina.core.data.daos.InformativoDao
import com.marin.catfeina.core.data.daos.PersonagemDao
import com.marin.catfeina.core.data.daos.PoesiaDao
import com.marin.catfeina.core.data.entities.AtelierEntity
import com.marin.catfeina.core.data.entities.InformativoEntity
import com.marin.catfeina.core.data.entities.PersonagemEntity
import com.marin.catfeina.core.data.entities.PoesiaEntity
import com.marin.catfeina.core.data.models.AtelierJson
import com.marin.catfeina.core.data.models.InformativosJson
import com.marin.catfeina.core.data.models.PersonagensJson
import com.marin.catfeina.core.data.models.PoesiasJson
import com.marin.catfeina.core.di.DataModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

/**
 * Esta classe usa a biblioteca App Startup para popular o banco de dados
 * de forma assíncrona na inicialização do aplicativo, somente se necessário.
 * Isso desacopla a lógica de população da criação do banco, resolvendo o ciclo de DI.
 */
class DatabaseInitializer : Initializer<Unit> {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun create(context: Context) {
        scope.launch {
            // Recriamos as dependências manualmente aqui. É seguro porque isso
            // roda depois que o Hilt já está configurado.
            val database = DataModule.provideAppDatabase(context)
            val json = DataModule.provideJson()

            // Verificamos se o banco já foi populado para não repetir a operação
            val isDatabaseEmpty = database.poesiaDao().count() == 0
            if (isDatabaseEmpty) {
                preencherPoesias(context, database.poesiaDao(), json)
                preencherPersonagens(context, database.personagemDao(), json)
                preencherInformativos(context, database.informativoDao(), json)
                preencherAtelier(context, database.atelierDao(), json)
            }
        }
    }

    // Não depende de nada, então a lista é vazia.
    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }

    // Lógica de população copiada do antigo DatabaseCallback
    private suspend fun preencherPoesias(context: Context, dao: PoesiaDao, json: Json) {
        val jsonString = context.assets.open("poesias.json").bufferedReader().use { it.readText() }
        val data = json.decodeFromString<PoesiasJson>(jsonString)
        val entities = data.poesias.map { PoesiaEntity(it.id, it.categoria, it.titulo, it.textoBase, it.texto, it.textoFinal, it.imagem, it.autor, it.nota, it.campoUrl, it.dataCriacao) }
        entities.forEach { dao.upsertPoesia(it) }
    }

    private suspend fun preencherPersonagens(context: Context, dao: PersonagemDao, json: Json) {
        val jsonString = context.assets.open("personagens.json").bufferedReader().use { it.readText() }
        val data = json.decodeFromString<PersonagensJson>(jsonString)
        val entities = data.personagens.map { PersonagemEntity(it.id, it.nome, it.descricao, it.imagem, it.dataCriacao) }
        dao.upsertAll(entities)
    }

    private suspend fun preencherInformativos(context: Context, dao: InformativoDao, json: Json) {
        val jsonString = context.assets.open("informativos.json").bufferedReader().use { it.readText() }
        val data = json.decodeFromString<InformativosJson>(jsonString)
        val entities = data.informativos.map { InformativoEntity(it.chave, it.conteudo, it.dataAtualizacao) }
        dao.upsertAll(entities)
    }

    private suspend fun preencherAtelier(context: Context, dao: AtelierDao, json: Json) {
        val jsonString = context.assets.open("atelier.json").bufferedReader().use { it.readText() }
        val data = json.decodeFromString<AtelierJson>(jsonString)
        val entities = data.notas.map { AtelierEntity(it.id, it.titulo, it.conteudo, it.dataAtualizacao, it.fixada) }
        dao.upsertAll(entities)
    }
}
