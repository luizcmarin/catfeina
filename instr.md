@GEMINI este documento fala do pojeto ANTIGO Catfeina android com jetcompose e hilt que vai ser migrado para KMP. Este documento lhe dá o contexto geral do projeto Catfeina.
@GEMINI LEIA @AGENT.md para mais instruções.

os arquivos gradle/libs.versions.toml, os gradles, GEMINI.md e AGENT.md NÃO DEVEM SER MODIFICADOS SEM AUTORIZAÇÃO.


# Roteiro de Desenvolvimento: Catfeina Kotlin (TODO List)

**Legenda:** `[X]` Pendente, `[P]` Progresso Parcial, `[X]` Concluído

## Fase 0: Configuração do Projeto e Arquitetura Fundamental

* [X] **0.1. Criar novo projeto Android:**
    * Usar o Android Studio, template "Empty Activity" (com Jetpack Compose).
    * Definir nome "Catfeina" e package name `com.marin.catfeina`.

* [X] **0.2. Configurar `libs.versions.toml`:**
    * Migrar todas as dependências do `build.gradle.kts` para o arquivo `gradle/libs.versions.toml`
      para gerenciamento centralizado de versões.
    * Definir aliases para todas as bibliotecas que serão usadas (Hilt, Room, Coil, Rive,
      etc.).

* [X] **0.3. Adicionar Dependências Principais (via `libs.versions.toml`):**
    * **Hilt:** `hilt-android`, `hilt-android-compiler`, `hilt-navigation-compose`.
    * **Room:** `room-runtime`, `room-ktx`, e o processador de anotações `room-compiler`.
    * **Jetpack:** `navigation-compose`, `datastore-preferences`, `lifecycle-viewmodel-compose`,
      `lifecycle-runtime-ktx`.
    * **kotlinx-serialization:** `kotlinx-serialization`.
    * **Coil:** `coil-compose`.
    * **Rive:** `rive-android`.
    * **Testes:** remover tudo relacionado a testes, pois não serão usados.

* [X] **0.4. Configurar Hilt:**
    * [X] Adicionar os plugins do Hilt nos arquivos `build.gradle.kts` (nível de projeto e de módulo
      `app`).
    * [X] Criar a classe `CatfeinaApplication.kt` no caminho
      `app/src/main/kotlin/com/marin/catfeina/`, anotá-la com `@HiltAndroidApp`.
    * [X] Declarar `CatfeinaApplication` no `AndroidManifest.xml`.
    * [X] Anotar `MainActivity.kt` com `@AndroidEntryPoint`.

* [X] **0.5. Definir Estrutura de Pacotes (Packages):**
    * Dentro de `com.marin.catfeina`, criar a seguinte estrutura inicial:
        * `core/`: Para código compartilhado e fundamental.
            * `core/data/`: Classes base de repositório, DataStore.
            * `core/di/`: Módulos do Hilt.
            * `core/utils/`: Classes utilitárias, constantes (ex: `Icones.kt`).
        * `features/`: Cada feature principal terá seu pacote aqui.
            * `features/poesias/`
            * `features/personagens/`
            * `features/vozes/`
        * `ui/`: Elementos de UI compartilhados.
            * `ui/theme/`: Gerado pelo Android Studio (`Color.kt`, `Theme.kt`, `Type.kt`).
        
* [X] **0.6. Limpar e Preparar `MainActivity.kt`:**
    * [X] Limpar o conteúdo padrão gerado pelo template.
    * [X] Manter a estrutura básica com `setContent`, `CatfeinaTheme`, e um `Scaffold` inicial. O
      roteiro detalhará seu conteúdo posteriormente.

* [X] **0.7. Critério de Conclusão Fase 0:**
    * Projeto compila e executa em um emulador/dispositivo.
    * `libs.versions.toml` está configurado e todas as dependências principais estão adicionadas.
    * Hilt está configurado e funcionando.
    * A estrutura de pacotes base está criada.

## Fase 1: Camada de Dados Fundamental e Vozes do Usuário

* [X] **1.1. Definir as Entidades e DAOs do Banco de Dados com Room:**
    * [X] No pacote `core/data/`, crie subpacotes `entities/` e `daos/`.
    * [X] No pacote `entities/`, defina as classes de dados para cada tabela, anotando-as com
      `@Entity`. Use `@PrimaryKey`, `@ColumnInfo`, `@TypeConverters`, etc., conforme necessário.
        * `PoesiaEntity`, `PoesiaNotaEstadoEntity`, `CategoriaPoesiaEntity`, `PersonagemEntity`,
          `InformativoEntity`, `AtelierEntity`.
    * [X] No pacote `daos/`, crie as interfaces para cada DAO (Data Access Object), anotando-as com
      `@Dao`.
    * [X] Dentro das interfaces DAO, defina as funções de acesso ao banco com anotações como
      `@Query`,
      `@Insert`, `@Upsert`, `@Delete`.
        * Use `Flow<List<...>>` como tipo de retorno para queries que precisam ser reativas.
        * Crie a query `selectAllPoesiasWithEstado` com `LEFT JOIN` para buscar as poesias já com
          seu estado de usuário.

* [X] **1.2. Implementar a Carga Inicial de Dados:**
    * [X] Crie a pasta `app/src/main/assets/data/`.
    * [X] Dentro dela, adicione os arquivos JSON com os dados iniciais: `poesias.json`,
      `personagens.json`, `categorias.json`, e `informativos.json`.
    * [X] Crie `data class`es Kotlin no pacote `core/data/models/` para representar a estrutura
      desses arquivos JSON.
  
* [X] **1.3. Configurar Injeção de Dependência para a Camada de Dados:**
    * [X] No pacote `core/di/`, crie o `DataModule.kt`.
    * [X] Crie uma classe `DatabaseInitializer.kt` que implementa `RoomDatabase.Callback`. No método
      `onCreate`, coloque a lógica para ler os arquivos JSON da pasta `assets` (usando kotlinx-serialization),
      converter para os modelos de entidade e inserir no banco usando os DAOs. Injete as
      dependências necessárias (Contexto, kotlinx-serialization, DAOs via Provider) nesta classe.
    * [X] No `DataModule.kt`, crie uma função `@Provides @Singleton` para fornecer a instância do
      `AppDatabase`.
        * Esta função receberá o `Contexto` e o `DatabaseCallback` via Hilt.
        * Use `Room.databaseBuilder(...)` para construir o banco, e chame `.addCallback(...)`
          passando a instância do `DatabaseCallback`.
    * [X] No `DataModule.kt`, crie funções `@Provides` para fornecer cada DAO a partir da instância
      do
      `AppDatabase` (ex: `database.poesiaDao()`).

* [X] **1.4. Configurar Vozes do Usuário com DataStore:**
    * [X] No pacote `core/data/`, crie uma classe `UserPreferencesRepository.kt`.
    * [X] Use o `PreferenceDataStoreFactory` para criar uma instância do DataStore.
    * [X] Exponha as preferências (ex: `isDarkMode`) como um `Flow<Boolean>`.
    * [X] Crie funções `suspend` para atualizar as preferências.
    * [X] No `DataModule.kt`, adicione uma função `@Provides @Singleton` para fornecer o
      `UserPreferencesRepository`.

* [X] **1.5. Criar os Repositórios da Aplicação:**
    * [X] Para cada feature principal (ex: `poesias`), crie a interface do repositório no pacote da
      feature, ex: `features/poesias/data/PoesiaRepository.kt`.
    * [X] Crie a implementação concreta, ex: `features/poesias/data/PoesiaRepositoryImpl.kt`.
    * [X] A implementação (`PoesiaRepositoryImpl`) receberá o(s) DAO(s) necessário(s) via injeção
      de dependência no seu construtor.
    * [X] Implemente os métodos do repositório, que chamarão as funções do DAO (ex:
      `poesiaDao.selectAllPoesiasWithEstado()`). O DAO já retornará um `Flow`, que o repositório
      simplesmente repassará.
    * [X] Crie um módulo Hilt `RepositoryModule.kt` em `core/di/` para prover as implementações dos
      repositórios.

* [X] **1.6. Critério de Conclusão Fase 1:**
    * As entidades e DAOs do Room estão definidos e compilam.
    * Hilt consegue injetar com sucesso a instância do `AppDatabase`, dos DAOs e dos `Repositories`.
    * Na primeira inicialização do app, os dados dos arquivos JSON são lidos e populados no banco
      de dados Room através do `DatabaseCallback`.
    * O `UserPreferencesRepository` está funcional, permitindo ler e escrever preferências usando
      DataStore.
    * As camadas de dados estão prontas para serem consumidas pelos ViewModels nas próximas fases.

## Fase 2: Features Iniciais - Sistema de Formatação de Texto

* [X] **2.1. Desenvolver Sistema de Análise e Renderização de Texto Formatado:**
    * [X] **2.1.1. Lógica do Parser:**
        * [X] No pacote `core/utils/`, crie o arquivo `MarkupParser.kt`.
        * [X] Implemente uma função `parseMarkup` que recebe uma `String` com as tags customizadas (
          ex: `<titulo-poesia>Conteúdo</titulo-poesia>`) e a converte para uma `AnnotatedString`.
        * [X] A função deve usar Expressões Regulares (Regex) para encontrar as tags e seus
          conteúdos.
        * [X] A função deve construir a `AnnotatedString` usando `buildAnnotatedString` e
          `withStyle`, aplicando `SpanStyle`s diferentes para cada tipo de tag.
    * [X] **2.1.2. Definição dos Estilos:**
        * [X] Crie uma função `@Composable` `getMarkupStyles()` que retorna um
          `Map<String, SpanStyle>`. Isso permite que os estilos usem cores e tipografia do
          `MaterialTheme.colorScheme`.
    * [X] **2.1.3. Composable Otimizado:**
        * [X] Crie a função `@Composable rememberMarkupAnnotatedString(markupText: String)` que usa
          `remember` para garantir que o parsing só ocorra quando o texto de entrada mudar.
        * [X] Este Composable chamará `getMarkupStyles()` e `parseMarkup()` internamente.

* [X] **2.4. Implementar a Tela de Abertura (Splash Screen):**
    * [X] **2.4.1. Lógica:**
        * [X] Crie `SplashScreen.kt` como a rota inicial do aplicativo.
        * [X] Use um `LaunchedEffect` para iniciar um `delay` (para tempo mínimo de exibição) e
          quaisquer pré-carregamentos necessários (ex: carregar a primeira preferência).
        * [X] Após a conclusão, navegue para a tela principal (`HomeScreen`) usando
          `navController.navigate(...)` com `popUpTo(splash_route) { inclusive = true }` para
          remover a splash da pilha de navegação.
    * [X] **2.4.2. UI:**
        * [X] A UI pode ser uma animação **Rive** ou uma imagem estática. Adicione os assets
          necessários (arquivo `.riv` em `res/raw/` ou imagem em `res/drawable/`).
        * [X] Use o Composable `RiveAnimation` ou `Image` para exibir o visual.

* [X] **2.5. Critério de Conclusão Fase 2:**
    * O sistema de parsing e renderização de texto com tags customizadas está funcional.
    * O usuário pode navegar para a tela de Configurações, visualizar e alterar preferências, e
      estas são persistidas e refletidas no app (ex: tema escuro).
    * O usuário pode visualizar telas de textos gerais (como "Sobre") com conteúdo formatado vindo
      do banco de dados.
    * Uma splash screen funcional é exibida na inicialização do aplicativo.
    * A arquitetura MVVM com Hilt, ViewModel e Compose está validada e funcionando para as primeiras
      features.

## Fase 3: Feature Principal - Poesias (Listagem, Detalhes, Interações)

* [X] **3.1. Definir Modelos de Dados e Queries Adicionais:**
    * [X] **Modelos de Estado do Usuário:** Certifique-se de que as entidades para armazenar o estado
      gerado pelo usuário foram definidas na Fase 1.
        * `PoesiaNotaEstadoEntity`: `poesiaId` (chave primária/estrangeira), `ehFavorita`,
          `foiLida`.
        * `AnotacaoPoesiaEntity`: `poesiaId` (chave primária/estrangeira), `textoAnotacao`,
          `dataAtualizado`.
    * [X] **Queries de Junção (Join):** No DAO correspondente (`PoesiaDao.kt`), certifique-se de que
      a query que busca a lista de poesias já faz a junção com `PoesiaNotaEstadoEntity`.
        * O retorno deve ser um modelo de dados combinado (uma `data class` com `@Embedded`)
          para encapsular a poesia e seu estado. Ex:
          `@Query("SELECT * FROM poesias LEFT JOIN poesias_usuario_estado ON poesias.id = poesias_usuario_estado.poesiaId") fun getPoesiasComEstado(): Flow<List<PoesiaComEstado>>`

* [X] **3.2. Estender a Camada de Dados:**
    * [X] **Repositório:** No `PoesiaRepository.kt` e sua implementação, adicione os métodos para
      interagir com o estado do usuário.
        * `fun getPoesiasComEstado(): Flow<List<PoesiaComEstado>>` (usando a query de join).
        * `suspend fun toggleFavorita(poesiaId: Long)`
        * `suspend fun marcarComoLida(poesiaId: Long)`
        * `fun getAnotacao(poesiaId: Long): Flow<AnotacaoPoesia?>`
        * `suspend fun salvarAnotacao(poesiaId: Long, texto: String)`

* [X] **3.3. Implementar os ViewModels da Feature:**
    * [X] **`PoesiaListViewModel.kt`:**
        * [X] Anote com `@HiltViewModel` e injete o `PoesiaRepository`.
        * [X] Crie um `StateFlow` para a UI, `val uiState: StateFlow<PoesiaListUiState>`, que
          representará os diferentes estados (Carregando, Sucesso com a lista de poesias, Erro).
        * [X] Use o operador `stateIn` para converter o `Flow` do repositório em um `StateFlow` de
          forma eficiente.
    * [X] **`PoesiaDetailViewModel.kt`:**
        * [X] Anote com `@HiltViewModel` e injete o `PoesiaRepository` e o `SavedStateHandle`.
        * [X] Use o `SavedStateHandle` para obter o `poesiaId` passado pela navegação.
        * [X] Combine múltiplos `Flows` do repositório (detalhes da poesia, estado do usuário,
          anotação) usando `combine` para criar um único `StateFlow` para a UI de detalhes.
        * [X] Crie funções para os eventos da UI (ex: `onToggleFavorita()`,
          `onSaveAnotacao(texto: String)`), que chamarão os métodos `suspend` do repositório em um
          `viewModelScope.launch`.

* [X] **3.4. Desenvolver a UI da Feature (Composables):**
    * [X] **Tela de Lista (`PoesiasScreen`):**
        * [X] Obtenha o `PoesiaListViewModel` com `hiltViewModel()`.
        * [X] Colete o `uiState` com `collectAsStateWithLifecycle()` (requer dependência
          `lifecycle-runtime-compose`).
        * [X] Use `LazyColumn` para exibir a lista de poesias. Cada item será um `Card` customizado
          mostrando imagem (com **Coil**), título e um ícone para favoritar.
        * [X] A ação de clique em um item navega para `PoesiaDetalhesScreen` com o `poesiaId`.
    * [X] **Tela de Detalhes (`PoesiaDetalhesScreen`):**
        * [X] Obtenha o `PoesiaDetailViewModel`.
        * [X] Exiba a imagem da poesia com **Coil**.
        * [X] Use o `rememberMarkupAnnotatedString` (da Fase 2) para renderizar o conteúdo da poesia.
        * [X] No `LaunchedEffect`, chame a função do ViewModel para marcar a poesia como "lida".
        * [X] Crie uma seção com um `TextField` para o usuário digitar sua anotação, vinculando o
          valor ao estado do ViewModel.
        * [X] Adicione botões para "Salvar Anotação" e outras ações (Copiar, Compartilhar) que
          disparam eventos no ViewModel.

* [X] **3.5. Integração com a Navegação:**
    * [X] No seu `AppNavHost`, adicione as rotas para `PoesiasScreen` e `PoesiaDetalhesScreen`.
    * [X] A rota de detalhes deve aceitar o `poesiaId` como argumento (ex:
      `route = "poesia_detalhe/{poesiaId}"`).

* [X] **3.6. Critério de Conclusão Fase 3:**
    * O usuário pode ver uma lista de todas as poesias.
    * A funcionalidade de favoritar/desfavoritar funciona na lista e na tela de detalhes, com o
      estado persistido.
    * Ao visualizar uma poesia, ela é marcada como "lida".
    * O usuário pode ler, criar, editar e deletar anotações pessoais para cada poesia.
    * A navegação entre a lista e os detalhes está funcional.

## Fase 4: Features Adicionais - Personagens, Histórico e Atalhos

* [X] **4.1. Desenvolver a Feature "Personagens":**
    * [X] **4.1.1. Camada de Dados:**
        * [X] Garanta que a entidade `Personagem.sq` e suas queries (`selectAll`, `selectById`) foram
          criadas na Fase 1.
        * [X] Implemente o `PersonagemRepository.kt` e sua implementação, seguindo o mesmo padrão do
          `PoesiaRepository`.
    * [X] **4.1.2. ViewModels:**
        * [X] Crie `PersonagemListViewModel.kt` para buscar a lista de todos os personagens.
        * [X] Crie `PersonagemDetailViewModel.kt` para buscar os detalhes de um único personagem,
          usando o `personagemId` injetado pelo `SavedStateHandle`.
    * [X] **4.1.3. UI (Telas):**
        * [X] Crie `PersonagemListScreen.kt` que exibirá uma `LazyColumn` ou `LazyVerticalGrid` de
          personagens, cada um com sua foto (usando **Coil**) e nome. O clique em um item navega
          para a tela de detalhes.
        * [X] Crie `PersonagemDetailScreen.kt` para exibir a foto principal, nome, e a descrição
          formatada usando o `rememberMarkupAnnotatedString`.
    * [X] **4.1.4. Navegação:**
        * [X] Adicione as rotas para `PersonagemListScreen` e `PersonagemDetailScreen` no
          `AppNavHost`.

* [X] **4.2. Implementar Funcionalidade de "Histórico de Visitas":**
    * [X] **4.2.1. Camada de Dados:**
        * [X] Crie o arquivo `HistoricoVisita.sq` com a tabela para armazenar o histórico (`id`,
          `tipoConteudo`, `conteudoId`, `tituloDisplay`, `dataVisita`).
        * [X] Implemente `HistoricoRepository.kt` para adicionar, listar e limpar o histórico.
    * [X] **4.2.2. Lógica de Registro:**
        * [X] Nos ViewModels de detalhes (ex: `PoesiaDetailViewModel`, `PersonagemDetailViewModel`),
          injete o `HistoricoRepository`.
        * [X] No `init` block do ViewModel, chame a função do repositório para registrar a visita
          àquele conteúdo.
    * [X] **4.2.3. ViewModel e UI da Tela de Histórico:**
        * [X] Crie `HistoricoViewModel.kt` para carregar a lista de itens do histórico.
        * [X] Crie `HistoricoScreen.kt` para exibir os itens. Ao clicar em um item, use o
          `tipoConteudo` e `conteudoId` para navegar para a tela de detalhe correta.

* [X] **4.3. Implementar "Pontos de Retorno Salvo" (Atalhos Globais):**
    * [X] **4.3.1. Camada de Dados:**
        * [X] Crie `PontoRetorno.sq` com a tabela para 10 slots fixos (`slotId` [1-10],
          `tituloDisplay`, `rotaNavegacao`, `dataSalvo`).
        * [X] Implemente `PontoRetornoRepository.kt` para obter, salvar e limpar os slots.
    * [X] **4.3.2. Lógica de Salvamento:**
        * [X] Nas telas de detalhe, adicione um botão "Salvar Atalho".
        * [X] Ao clicar, mostre um `Dialog` ou `BottomSheet` que lista os 10 slots. O usuário pode
          selecionar um slot para salvar a rota atual (`navController.currentDestination?.route`).
    * [X] **4.3.3. ViewModel e UI da Tela de Atalhos:**
        * [X] Crie `PontosRetornoViewModel.kt` para carregar os 10 slots.
        * [X] Crie `PontosRetornoScreen.kt` para exibir os slots. Um clique em um slot configurado
          usa o `navController` para navegar para a rota salva.

* [X] **4.4. Critério de Conclusão Fase 4:**
    * A feature "Personagens" (listagem e detalhes) está funcional.
    * O sistema de "Histórico de Visitas" está registrando e exibindo o conteúdo visitado.
    * O usuário pode salvar até 10 atalhos para telas específicas e navegar para eles a partir de
      uma tela dedicada.
    * A arquitetura MVVM continua a ser aplicada consistentemente.

## Fase 5: Tela Principal, Navegação Global e Pesquisa

* [X] **5.1. Desenvolver a Tela Principal (`HomeScreen`):**
    * [X] **Layout:** A tela será um `Scaffold` com uma `LazyColumn` como corpo principal.
    * [X] **Seções da Tela:** Cada bloco de conteúdo (Poesia Aleatória, Favoritos, Novidades, Lista
      de Poesias) será um Composable separado dentro da `LazyColumn`.
        * `item { PoesiaAleatoriaCard() }`
        * `item { FavoritosRow() }`
        * `items(poesias) { poesia -> PoesiaListItem(poesia) }`
    * [X] **ViewModels:** A `HomeScreen` pode ter um `HomeViewModel` para orquestrar os dados, ou
      cada seção pode usar seu próprio ViewModel injetado (abordagem mais modular).

* [X] **5.2. Implementar a Navegação Principal (Barras e Drawer):**
    * [X] **`MainActivity.kt`:** Será o ponto central que conterá o `ModalNavigationDrawer`, o
      `Scaffold` com `TopAppBar` e `BottomAppBar`, e o `NavHost`.
    * [X] **TopAppBar:** Conterá um ícone para abrir o `NavigationDrawer` e um botão para navegar
      para a `SearchScreen`.
    * [X] **BottomAppBar:** Conterá os `IconButton` para as principais seções do app (ex: Início,
      Marcadores, Atelier), gerenciando a navegação com o `navController`.
    * [X] **NavigationDrawer:** Conterá a `SettingsScreen` (desenvolvida na Fase 2) e outros links
      para seções menos frequentes (ex: Sobre, Termos de Uso).
* [X] **5.3. Implementar a Funcionalidade de Pesquisa Global:**
    * [X] **5.3.1. Camada de Dados:**
        * [X] Crie uma nova interface `SearchDao.kt` em `core/data/daos/` e adicione-a ao
          `AppDatabase`.
        * [X] No `SearchDao.kt`, crie métodos `suspend` para pesquisar em cada tabela relevante (ex:
          poesias, personagens) usando queries com `LIKE :query`. Ex:
          `@Query("SELECT * FROM poesias WHERE titulo LIKE :query OR texto LIKE :query") suspend fun searchPoesias(query: String): List<PoesiaEntity>`
        * [X] Crie um `SearchRepository.kt` que recebe os DAOs necessários. Nele, implemente um
          método `search(query: String)` que chama os métodos de busca dos DAOs de forma
          concorrente (usando `coroutineScope` e `async`) e combina os resultados em uma lista
          unificada (ex: `Flow<List<SearchResult>>`).
    * [X] **5.3.2. ViewModel e UI:**
        * [X] Crie `SearchViewModel.kt` que usa o `SearchRepository`.
        * [X] Crie `SearchScreen.kt` com um `TextField` para a busca. Conforme o usuário digita (com
          um `debounce` para evitar buscas excessivas), o ViewModel executa a pesquisa.
        * [X] Exiba os resultados em uma `LazyColumn`. O clique em um resultado navega para a tela de
          detalhe apropriada.

* [X] **5.4. Implementar Text-to-Speech (TTS):**
    * [X] **Dependência:** Adicione uma biblioteca TTS para Android (ex: a nativa
      `android.speech.tts.TextToSpeech`).
    * [X] **Serviço Wrapper:** Crie uma classe `TtsService.kt` injetável via Hilt para encapsular a
      lógica do TTS (inicializar, falar, pausar, parar).
    * [X] **Integração:** No `PoesiaDetailViewModel`, injete o `TtsService`. Adicione eventos para
      `play()`, `pause()`, `stop()`. Antes de passar o texto para o TTS, use uma função no
      `MarkupParser` para remover as tags de formatação.
    * [X] **UI:** Adicione `IconButton`s na tela de detalhes da poesia para controlar a reprodução.

* [X] **5.5. Critério de Conclusão Fase 5:**
    * A `HomeScreen` está montada e exibe dinamicamente as diferentes seções de conteúdo.
    * A navegação principal via `BottomAppBar` e `NavigationDrawer` está funcional e consistente.
    * A Pesquisa Global funciona, retornando resultados de diferentes tipos de conteúdo.
    * A funcionalidade de Text-to-Speech está implementada e funcional nas telas de poesia.

## Fase 6: Feature "Atelier", Polimento e Testes

* [X] **6.1. Desenvolver a Feature "Atelier do Usuário":**
    * [X] **6.1.1. Camada de Dados:**
        * [X] Crie `Atelier.sq` com a tabela para notas do usuário (`id`, `titulo`, `conteudo`,
          `dataAtualizado`, `fixada`).
        * [X] Implemente `AtelierRepository.kt` com os métodos para CRUD completo (listar,
          buscar por id, salvar, deletar).
    * [X] **6.1.2. ViewModels:**
        * [X] Crie `AtelierListViewModel.kt` para carregar a lista de todas as notas.
        * [X] Crie `AtelierEditViewModel.kt` para carregar uma nota existente ou preparar uma nova, e
          para salvar ou deletar a nota.
    * [X] **6.1.3. UI (Telas):**
        * [X] Crie `AtelierListScreen.kt`. Use `LazyColumn` para exibir a lista de notas. Cada item
          deve mostrar título, trecho do conteúdo e data. Adicione um `FloatingActionButton` (FAB)
          para navegar para a tela de edição.
        * [X] Crie `AtelierEditScreen.kt`. Conterá `TextFields` para o título e o conteúdo. O botão "
          Salvar" na `TopAppBar` deve disparar a função de salvamento no ViewModel.
    * [X] **6.1.4. Navegação:**
        * [X] Adicione as rotas para `AtelierListScreen` e `AtelierEditScreen` no `AppNavHost`.
        * [X] Conecte o item "Atelier" da sua `BottomAppBar` (a ser adicionado) para navegar até
          `AtelierListScreen`.

* [X] **6.2. Polimento Geral da UI/UX:**
    * [X] **Consistência:** Revise todas as telas para garantir consistência visual (espaçamentos,
      tipografia, cores do Material 3).
    * [X] **Feedback Visual:** Melhore o feedback das interações (efeitos de "ripple", estados de
      botões pressionados/desabilitados).
    * [X] **Estados de UI:** Garanta que todas as telas tratem adequadamente os estados de
      Carregamento (ex: `CircularProgressIndicator`), Erro (mensagem clara com opção de "Tentar
      Novamente") e Vazio (ex: "Nenhuma poesia favorita encontrada").
    * [X] **Performance:** Otimize o desempenho de listas longas. Verifique e otimize reconstruções
      desnecessárias de Composables (use `remember` com chaves, `derivedStateOf`, e passe lambdas em
      vez de estados sempre que possível).

* [X] **6.3. Testes Abrangentes:**
    * [X] **NÃO SERÃO CRIADOS TESTES. VAMOS TESTAR NO ANDAMENTO DO PROJETO**

* [X] **6.4. Configuração para Release:**
    * [X] Revise o arquivo `app/build.gradle.kts` para configurar `versionCode` e `versionName`.
    * [X] Adicione os ícones do aplicativo para diferentes densidades de tela na pasta `res/mipmap`.
    * [X] Configure a ofuscação de código com ProGuard/R8 para builds de release.
    * [X] Gere e teste um App Bundle de release (`./gradlew bundleRelease`).

* [X] **6.5. Critério de Conclusão Fase 6:**
    * A feature "Atelier" está completamente funcional.
    * O aplicativo tem uma UI/UX polida, com bom tratamento de todos os estados.
    * Uma base sólida de testes unitários e de UI foi criada para as features críticas.
    * O aplicativo está configurado e pronto para ser publicado.

## Fase 7: Evolução Pós-Lançamento

* [X] **7.1. Monitoramento:**
    * [X] Integre o **Firebase Crashlytics** para monitorar travamentos em produção. É uma
      configuração simples que fornece insights valiosos sobre a estabilidade do app.

* [X] **7.2. Atualização Dinâmica de Conteúdo (Opcional):**
    * [X] **Fonte Remota:** Defina uma API externa ou um arquivo JSON em um local público (como
      Google Drive ou GitHub) como fonte dos dados.
    * [X] **Lógica de Sincronização:** Crie um `RemoteDataSource` usando uma biblioteca como **Ktor**
      ou **Retrofit**. No repositório, implemente a lógica para buscar dados remotos, comparar com
      os locais (usando versões ou timestamps) e atualizar o banco de dados Room.
    * [X] **Trabalho em Background:** Use o **WorkManager** do Android para agendar verificações
      periódicas de atualização de conteúdo.

* [X] **7.3. Mascote Interativo "Catshito" (com Rive):**
    * [X] **Design e Animação:** Crie as animações do Catshito (ex: ocioso, reagindo a um toque,
      celebrando uma conquista) no editor do **Rive**. Use a "State Machine" do Rive para controlar
      as transições.
    * [X] **Integração:** Adicione o Composable `RiveAnimation` em uma área da UI (ex: `HomeScreen`).
    * [X] **Lógica de Interação:** Crie um `CatshitoViewModel` para decidir qual estado da animação
      ativar. Por exemplo, ao detectar que o usuário favoritou uma poesia, o ViewModel pode disparar
      o gatilho "celebrar" na State Machine do Rive.

* [X] **7.4. Gamificação (Conquistas Poéticas):**
    * [X] **Lógica:** Crie um `ConquistasService` que observa as ações do usuário (poesias lidas,
      notas criadas).
    * [X] **Dados:** Armazene o progresso e as conquistas desbloqueadas no DataStore ou em uma nova
      tabela Room.
    * [X] **UI:** Crie uma `ConquistasScreen` para listar as conquistas. Ao desbloquear uma, mostre
      uma animação do Catshito (Rive) como celebração.

## Fase 8: Itens Adicionais do Documento Original

* [X] **8.1. Onboarding:**
    * [X] Crie uma sequência de telas de onboarding usando `HorizontalPager`.
    * [X] Use o `UserPreferencesRepository` (DataStore) para salvar um booleano `onboardingCompleto`.
      A rota inicial do app verificará esse valor para decidir se mostra o onboarding ou vai direto
      para a `HomeScreen`.

* [X] **8.2. Explicações Contextuais (Tooltips):**
    * [X] No seu `MarkupParser`, adicione suporte para uma tag de tooltip, ex:
      `<tooltip text="Explicação aqui">palavra</tooltip>`.
    * [X] O parser deve criar uma anotação (`StringAnnotation`) na `AnnotatedString` para o trecho de
      texto.
    * [X] No Composable que renderiza o texto, use um `ClickableText` e verifique se o offset do
      clique corresponde a uma anotação de tooltip. Se corresponder, exiba um `AlertDialog` ou um
      Composable de tooltip customizado.

* [X] **8.3. Ambientes Sonoros:**
    * [X] Use a classe `MediaPlayer` nativa do Android, encapsulada em um serviço (`SoundService.kt`)
      gerenciado pelo Hilt.
    * [X] Adicione controles de UI na tela de detalhes da poesia para tocar/pausar os sons (arquivos
      de áudio em `res/raw/`). Gerencie o ciclo de vida do `MediaPlayer` para liberar recursos
      corretamente.

* [X] **8.4. Atualização do Tema Baseado nas Vozes:**
    * [X] No seu `MainActivity.kt`, onde você chama `CatfeinaTheme`, você precisará obter o estado do
      tema (claro/escuro) do `UserPreferencesRepository`.
    * [X] Uma abordagem limpa é injetar o repositório em um `MainViewModel` e coletar o estado do
      tema lá.
    * [X] Passe o valor `isDarkMode` para o seu `CatfeinaTheme { ... }`. Dentro do `CatfeinaTheme`,
      use esse booleano para decidir qual `colorScheme` aplicar (o `DarkColorScheme` ou o
      `LightColorScheme`).

* [X] **8.5. Refatorar a Criação do `VozesViewModel` com Hilt:**
    * [X] **Ação Crítica:** O passo mais importante agora é integrar o Hilt ao seu `AppNavHost`.
      Atualmente, você está criando as dependências manualmente:
      `val userPreferencesRepository = UserPreferencesRepository(context)`.
    * [X] **Passo 1:** Anote `VozesViewModel` com `@HiltViewModel` e injete o
      `UserPreferencesRepository` no construtor com `@Inject`.
    * [X] **Passo 2:** Remova a `provideFactory` do seu ViewModel.
    * [X] **Passo 3:** No `composable(AppDestinations.VOZES_ROUTE)`, substitua toda a lógica
      de criação manual por uma única linha:
      `val viewModel: VozesViewModel = hiltViewModel()`.
    * [X] **Resultado:** Isso validará toda a sua configuração do Hilt e servirá de modelo para todos
      os outros ViewModels do aplicativo.

* [X] **8.6. Implementar a `HomeScreen` Real:**
    * [X] Crie `features/inicio/ui/InicioScreen.kt` e seu `features/inicio/InicioViewModel.kt`.
    * [X] O `InicioViewModel` irá injetar os repositórios necessários (ex: `PoesiaRepository`) para
      buscar os dados das diferentes seções (poesia aleatória, favoritos, etc.).
    * [X] No `AppNavHost`, substitua o comentário `// InicioScreen()` pela chamada real, usando
      `hiltViewModel()` para obter o `InicioViewModel`.
    * [X] A `InicioScreen` será um `Scaffold` com uma `LazyColumn` que renderiza as diferentes
      seções, conforme planejado na Fase 5.

* [X] **8.7. Finalizar a Estrutura de Navegação:**
    * [X] **BottomAppBar:** No seu `MainActivity.kt`, adicione os itens de menu que faltam para a
      `bottomBarItems`, como "Atelier" e "Marcadores", cada um com seu ícone e rota.
    * [X] **Rotas com Argumentos:** Para telas de detalhes (como `PoesiaDetalhesScreen`), defina a rota
      no `AppNavHost` para aceitar um argumento, como você comentou no seu código:
      `composable("poesia_detalhe/{poesiaId}") { ... }`.
    * [X] **Navegação a partir de Listas:** Nas suas telas de lista (`PoesiasScreen`,
      `PersonagemListScreen`), a ação de clique em um item chamará
      `navController.navigate("poesia_detalhe/${item.id}")`.

* [X] **8.8. Adicionar Ícones ao `Icones.kt`:**
    * [X] Conforme novas features são adicionadas (Pesquisa, Histórico, Atelier, Marcadores), adicione
      os `ImageVector`s correspondentes ao seu objeto `core/utils/Icones.kt` para manter a
      consistência.

* [X] **8.9. NÃO DEFINIDA:**
    * [X] Tomar café.

* [X] **8.10. Critério de Conclusão Final (MVP - Produto Mínimo Viável):**
    * O roteiro de desenvolvimento foi completamente revisado e adaptado para a arquitetura
      Kotlin/Compose/Hilt.
    * A refatoração com Hilt foi concluída, eliminando a criação manual de dependências na UI.
    * As features principais (Configurações, Poesias, Personagens, Atelier) estão implementadas e
      acessíveis através da estrutura de navegação principal.
    * O aplicativo está estável, testado e pronto para ser usado e evoluído com as fases de
      polimento e pós-lançamento.

## Fase 9: Consolidação, Documentação Final e Próximos Passos

* [X] **9.1. Revisão Final do Código e da Arquitetura:**
    * [X] Realize uma passagem completa por todo o código do projeto. Verifique a aderência aos
      princípios da Arquitetura Limpa, consistência no uso de ViewModels, Repositórios e Módulos
      Hilt.
    * [X] Garanta que não há alertas pendentes, assegurando um código limpo e padronizado.
    * [X] Verifique se todos os recursos (strings, cores, dimensões) estão definidos em arquivos de
      recursos (`res/values`) e não "hardcoded" no código.

* [X] **9.2. Atualizar Documentação do Projeto:**
    * [X] **README.md:** Crie ou atualize o arquivo `README.md` na raiz do projeto. Ele deve conter:
        * Uma breve descrição do Catfeina.
        * A pilha tecnológica utilizada (Kotlin, Compose, Hilt, Room, etc.).
        * Instruções de como compilar e rodar o projeto.
    * [X] **Atualizar `GEMINI.md`:** Marque todos os itens concluídos no roteiro de desenvolvimento
      com `[X]`. Revise as seções para garantir que elas reflitam o estado final do projeto MVP.
    * [X] **Changelog:** Crie um arquivo `CHANGELOG.md` na raiz do projeto, documentando as
      principais features implementadas na versão 1.0.

* [X] **9.3. Preparar Materiais para a Loja de Aplicativos:**
    * [X] Escreva os textos para a listagem na Google Play Store (título, descrição curta, descrição
      completa).
    * [X] Prepare as capturas de tela (`screenshots`) das principais telas do aplicativo (Início,
      Detalhes da Poesia, Configurações, Atelier).

* [X] **9.4. Execução de Testes Finais:**
    * [X] Rode todos os testes unitários e de UI (`./gradlew test connectedCheck`) uma última vez
      para garantir que nenhuma regressão foi introduzida.
    * [X] Realize testes manuais de ponta a ponta nos fluxos mais críticos do aplicativo (ciclo de
      vida completo de uma nota no Atelier, favoritar uma poesia, mudar um tema, etc.).

* [X] **9.5. Gerar o Artefato Final para Publicação:**
    * [X] Gere o App Bundle de release final e assinado (`./gradlew bundleRelease`).
    * [X] Envie o App Bundle para a Google Play Console e preencha todos os metadados necessários
      para o lançamento.

* [X] **9.6. Critério de Conclusão Final do Projeto (Versão 1.0):**
    * Todo o roteiro de desenvolvimento no `GEMINI.md` foi concluído ou conscientemente adiado para
      uma versão futura.
    * O código está limpo, documentado e testado.
    * A documentação do projeto (`README.md`, `CHANGELOG.md`) está atualizada.
    * Um App Bundle assinado e pronto para publicação foi gerado com sucesso.
    * **O aplicativo Catfeina atingiu o status de Produto Mínimo Viável (MVP) e está pronto para o
      seu lançamento inicial.**

--- Fim do Roteiro de Desenvolvimento ---
