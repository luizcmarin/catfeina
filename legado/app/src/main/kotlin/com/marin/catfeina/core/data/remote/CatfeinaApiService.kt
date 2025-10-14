// =============================================================================
// Arquivo: com.marin.catfeina.core.data.remote.CatfeinaApiService.kt
// Descrição: Interface do Retrofit que define os endpoints da API remota.
// =============================================================================
package com.marin.catfeina.core.data.remote

import retrofit2.http.GET
import retrofit2.http.Url

interface CatfeinaApiService {

    /**
     * Busca o conteúdo do arquivo JSON de dados a partir de uma URL completa.
     * Usamos @Url para requisições dinâmicas onde a URL completa é fornecida na chamada.
     */
    @GET
    suspend fun getConteudoRemoto(@Url url: String): ConteudoRemoto
}
