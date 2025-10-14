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
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

/**
 * Esta classe usa a biblioteca App Startup para popular o banco de dados
 * de forma síncrona na inicialização do aplicativo, somente se necessário.
 * O uso de `runBlocking` é intencional aqui para garantir que a UI não seja
 * criada antes que o banco de dados esteja pronto.
 */
class DatabaseInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        // runBlocking é usado aqui para garantir que a população do banco de dados
        // seja concluída ANTES que a aplicação continue. Isso previne race conditions
        // onde a UI tenta ler dados que ainda não foram escritos.
        runBlocking {
            val database = DataModule.provideAppDatabase(context)
            val json = DataModule.provideJson()

            val isDatabaseEmpty = database.poesiaDao().count() == 0
            if (isDatabaseEmpty) {
                preencherPoesias(context, database.poesiaDao(), json)
                preencherPersonagens(context, database.personagemDao(), json)
                preencherInformativos(context, database.informativoDao(), json)
                preencherAtelier(context, database.atelierDao(), json)
            }
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }

    private suspend fun preencherPoesias(context: Context, dao: PoesiaDao, json: Json) {
        val jsonString = context.assets.open("data/poesias.json").bufferedReader().use { it.readText() }
        val data = json.decodeFromString<PoesiasJson>(jsonString)
        val entities = data.poesias.map { PoesiaEntity(it.id, it.categoria, it.titulo, it.textoBase, it.texto, it.textoFinal, it.imagem, it.autor, it.nota, it.campoUrl, it.dataCriacao) }
        entities.forEach { dao.upsertPoesia(it) }
    }

    private suspend fun preencherPersonagens(context: Context, dao: PersonagemDao, json: Json) {
        val jsonString = context.assets.open("data/personagens.json").bufferedReader().use { it.readText() }
        val data = json.decodeFromString<PersonagensJson>(jsonString)
        val entities = data.personagens.map { PersonagemEntity(it.id, it.nome, it.descricao, it.imagem, it.dataCriacao) }
        dao.upsertAll(entities)
    }

    private suspend fun preencherInformativos(context: Context, dao: InformativoDao, json: Json) {
        val jsonString = context.assets.open("data/informativos.json").bufferedReader().use { it.readText() }
        val data = json.decodeFromString<InformativosJson>(jsonString)
        val entities = data.informativos.map { InformativoEntity(it.chave, it.conteudo, it.dataAtualizacao) }
        dao.upsertAll(entities)
    }

    private suspend fun preencherAtelier(context: Context, dao: AtelierDao, json: Json) {
        val jsonString = context.assets.open("data/atelier.json").bufferedReader().use { it.readText() }
        val data = json.decodeFromString<AtelierJson>(jsonString)
        val entities = data.notas.map { AtelierEntity(it.id, it.titulo, it.conteudo, it.dataAtualizacao, it.fixada) }
        dao.upsertAll(entities)
    }
}
