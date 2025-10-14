// =============================================================================
// Arquivo: com.marin.catfeina.core.data.repository.HistoricoRepositoryImpl.kt
// Descrição: Implementação do repositório de histórico de visitas.
// =============================================================================
package com.marin.catfeina.core.data.repository

import com.marin.catfeina.core.data.daos.HistoricoVisitaDao
import com.marin.catfeina.core.data.entities.HistoricoVisita
import com.marin.catfeina.core.data.entities.TipoConteudo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HistoricoRepositoryImpl @Inject constructor(
    private val historicoVisitaDao: HistoricoVisitaDao
) : HistoricoRepository {

    override suspend fun adicionarVisita(tipo: TipoConteudo, id: Long, titulo: String, imagem: String?) {
        val novaVisita = HistoricoVisita(
            tipoConteudo = tipo,
            conteudoId = id,
            tituloDisplay = titulo,
            imagemDisplay = imagem,
            dataVisita = System.currentTimeMillis()
        )
        historicoVisitaDao.upsert(novaVisita)
    }

    override fun getHistoricoCompleto(): Flow<List<HistoricoVisita>> {
        return historicoVisitaDao.getHistoricoCompleto()
    }

    override suspend fun limparHistorico() {
        historicoVisitaDao.limparHistorico()
    }
}
