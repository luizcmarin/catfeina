// =============================================================================
// Arquivo: com.marin.catfeina.core.data.remote.RemoteDataSource.kt
// Descrição: Fonte de dados remota para buscar o conteúdo da API.
// =============================================================================
package com.marin.catfeina.core.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: CatfeinaApiService
) {

    // A URL completa do arquivo JSON no Google Drive
    private val remoteUrl = "https://drive.google.com/uc?export=download&id=1KYX3fTUaJ1bFNINE4tYrXPqn_V_ihTH1"

    suspend fun getConteudo(): Result<ConteudoRemoto> {
        return try {
            val conteudo = apiService.getConteudoRemoto(remoteUrl)
            Result.success(conteudo)
        } catch (e: Exception) {
            // Em um app real, aqui teríamos um log mais detalhado (Timber, Crashlytics)
            e.printStackTrace()
            Result.failure(e)
        }
    }
}
