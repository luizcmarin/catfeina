# Catfeina: Documento de Projeto e Roteiro de Desenvolvimento (Edição Kotlin)

## 1. Informações Gerais do Projeto

### 1.1. Título do Projeto

Catfeina - Aplicativo Kotlin de Poesia

### 1.2. Objetivo Principal

Criar um aplicativo Android nativo, moderno, robusto e escalável para amantes de poesia, utilizando
as melhores e mais atuais práticas do ecossistema Kotlin. O aplicativo será focado na experiência do
usuário local com poesias, personagens e textos gerais, construído sobre uma arquitetura limpa e de
fácil manutenção.

### 1.3. Colaborador Principal de IA

Assistente Gemini

### 1.4. Contexto Arquitetônico

O Catfeina será desenvolvido seguindo os princípios da Arquitetura Limpa (Clean Architecture)
adaptada para Android, garantindo manutenibilidade, testabilidade e escalabilidade. Este documento
serve como o roteiro de desenvolvimento oficial (TODO list) e o registro central de decisões
arquitetônicas.

## 2. Pilha Tecnológica (Tech Stack) e Arquitetura Chave

A aplicação será construída sobre as seguintes tecnologias e princípios:

* **2.1. Linguagem e UI:**
    * **Kotlin:** Como linguagem principal.
    * **Jetpack Compose:** Para a construção de toda a Interface de Usuário (UI) de forma
      declarativa e reativa.
    * **Material Design 3:** Para um design consistente e moderno.

* **2.2. Arquitetura:**
    * **Arquitetura Limpa (Android):** Estrutura em camadas: UI (Apresentação), Domain (Lógica de
      Negócios, opcional para casos simples) e Data (Dados).
    * **MVVM (Model-View-ViewModel):** Como padrão na camada de Apresentação.
    * **Injeção de Dependência (DI):** **Hilt** para gerenciar o ciclo de vida e o fornecimento de
      dependências em todo o app.
    * **Programação Assíncrona:** **Kotlin Coroutines & Flow** para gerenciar tarefas em segundo
      plano e fluxos de dados reativos.

* **2.3. Camada de Dados:**
    * **Fonte de Dados Local:** **SQLDelight** como a fonte única da verdade (Single Source of
      Truth), proporcionando segurança de tipos em tempo de compilação a partir de queries SQL.
    * **Preferências do Usuário:** **Jetpack DataStore (Preferences)** para armazenar configurações
      simples do usuário de forma assíncrona.
    * **Análise de JSON (Assets):** **Moshi** para analisar os dados iniciais (poesias, etc.) de
      arquivos JSON armazenados na pasta `assets`.

* **2.4. Navegação:**
    * **Jetpack Navigation for Compose:** Para gerenciar a navegação entre as telas (Composables) de
      forma estruturada e type-safe.

* **2.5. Bibliotecas de Suporte:**
    * **Imagens:** **Coil** para carregamento eficiente e cache de imagens.
    * **Animações Vetoriais:** **Rive** para animações interativas e de alta performance.

## 3. ⚠️ LEMBRETES IMPORTANTES PARA O ASSISTENTE GEMINI (Edição Kotlin) ⚠️

Esta seção contém instruções cruciais para a nossa colaboração. **POR FAVOR, REVISE ESTA SEÇÃO
REGULARMENTE.**

### A. Seu Papel e Como Ajudar:

* **Colaborador Ativo em Kotlin:** Você é um parceiro de desenvolvimento Kotlin. Espero sugestões
  proativas alinhadas com as melhores práticas do ecossistema Android moderno (Jetpack, Coroutines,
  etc.).
* **Foco no Código Kotlin:** A principal forma de ajuda é através da geração, revisão e refatoração
  de código Kotlin e Composables.
* **Entendimento do Projeto Catfeina:** Esforce-se para entender o contexto do Catfeina (poesia,
  textos, experiência do usuário desejada) para que suas sugestões sejam relevantes.

### B. Interação e Comunicação:

* **Idioma:** Toda comunicação e código devem ser em **Português do Brasil**.
* **Acesso ao Código do Projeto (SIMULAÇÃO):**
    * **EU TE DAREI O CONTEÚDO DOS ARQUIVOS QUANDO SOLICITADO.** Se você precisar ver um arquivo
      `.kt` ou `.sq` para dar uma resposta precisa, **PEÇA O NOME COMPLETO DO ARQUIVO** (ex:
      `app/src/main/kotlin/com/marin/catfeina/MainActivity.kt`) e eu fornecerei o conteúdo.
    * **NÃO PRESUMA** o conteúdo dos arquivos. Baseie-se no que foi compartilhado.
* **Minha Liderança no Roteiro:** **EU** definirei a sequência dos itens a serem trabalhados.

### C. Qualidade e Estilo do Código Kotlin:

* **Código Moderno:** **SEMPRE FORNEÇA CÓDIGOS ATUALIZADOS.** Verifique se as APIs do Jetpack e
  outras bibliotecas não estão depreciadas. **NÃO SUGIRA GAMBIARRAS.**
* **Documentação de Arquivos:** Todo novo arquivo `.kt` significativo deve incluir um cabeçalho
  descritivo.
* **Comentários:** Código limpo e bem nomeado deve ser autoexplicativo. Use comentários apenas para
  lógicas complexas.
* **Formatação:** O código deve seguir as convenções garantidas pela boas práticas.

### D. Formatação das Respostas (CRUCIAL!):

* **Use UM ÚNICO Bloco de Código Principal:** Sua resposta final, contendo Markdown ou código, deve
  estar dentro de um único bloco de código para que eu possa usar o botão "copiar" da interface.
* **NÃO use três crases seguidas dentro deste bloco principal**, pois quebra a funcionalidade de
  cópia. Se precisar mostrar um exemplo de código dentro do documento, use indentação (quatro
  espaços) ou uma crase simples para `inline code`.

# Roteiro de Desenvolvimento: Catfeina Kotlin (TODO List)

**Legenda:** `[]` Pendente, `[P]` Progresso Parcial, `[X]` Concluído

## Fase 0: Configuração do Projeto e Arquitetura Fundamental

*   [X] **0.1. Criar novo projeto Android:**
    * Usar o Android Studio, template "Empty Activity" (com Jetpack Compose).
    * Definir nome "Catfeina" e package name `com.marin.catfeina`.

* [] **0.2. Configurar `libs.versions.toml`:**
    * Migrar todas as dependências do `build.gradle.kts` para o arquivo `gradle/libs.versions.toml`
      para gerenciamento centralizado de versões.
    * Definir aliases para todas as bibliotecas que serão usadas (Hilt, SQLDelight, Coil, Rive,
      etc.).

* [] **0.3. Adicionar Dependências Principais (via `libs.versions.toml`):**
    * **Hilt:** `hilt-android`, `hilt-android-compiler`, `hilt-navigation-compose`.
    * **SQLDelight:** `sqldelight-android-driver`, `sqldelight-coroutines-extensions`, e o plugin
      `sqldelight-gradle-plugin`.
    * **Jetpack:** `navigation-compose`, `datastore-preferences`, `lifecycle-viewmodel-compose`,
      `lifecycle-runtime-ktx`.
    * **Moshi:** `moshi-kotlin`.
    * **Coil:** `coil-compose`.
    * **Rive:** `rive-android`.
    * **Testes:** remover tudo relacionado a testes, pois não serão usados.

* [] **0.4. Configurar Hilt:**
    * [] Adicionar os plugins do Hilt nos arquivos `build.gradle.kts` (nível de projeto e de módulo
      `app`).
    * [] Criar a classe `CatfeinaApplication.kt` no caminho
      `app/src/main/kotlin/com/marin/catfeina/`, anotá-la com `@HiltAndroidApp`.
    * [] Declarar `CatfeinaApplication` no `AndroidManifest.xml`.
    * [] Anotar `MainActivity.kt` com `@AndroidEntryPoint`.

* [] **0.5. Definir Estrutura de Pacotes (Packages):**
    * Dentro de `com.marin.catfeina`, criar a seguinte estrutura inicial:
        * `core/`: Para código compartilhado e fundamental.
            * `core/data/`: Classes base de repositório, DataStore.
            * `core/di/`: Módulos do Hilt.
            * `core/utils/`: Classes utilitárias, constantes (ex: `Icones.kt`).
        * `features/`: Cada feature principal terá seu pacote aqui.
            * `features/poesias/`
            * `features/personagens/`
            * `features/preferencias/`
        * `ui/`: Elementos de UI compartilhados.
            * `ui/theme/`: Gerado pelo Android Studio (`Color.kt`, `Theme.kt`, `Type.kt`).
            * `ui/composables/`: Widgets reutilizáveis (ex: `FormattedTextRenderer.kt`).

* [] **0.6. Limpar e Preparar `MainActivity.kt`:**
    * [] Limpar o conteúdo padrão gerado pelo template.
    * [] Manter a estrutura básica com `setContent`, `CatfeinaTheme`, e um `Scaffold` inicial. O
      roteiro detalhará seu conteúdo posteriormente.

* [] **0.7. Critério de Conclusão Fase 0:**
    * Projeto compila e executa em um emulador/dispositivo.
    * `libs.versions.toml` está configurado e todas as dependências principais estão adicionadas.
    * Hilt está configurado e funcionando.
    * A estrutura de pacotes base está criada.

## Fase 1: Camada de Dados Fundamental e Preferências do Usuário

* [] **1.1. Definir o Schema do Banco de Dados com SQLDelight:**
    * [] No caminho `app/src/main/sqldelight/`, criar o arquivo único `Catfeina.sq`.
    * [] Dentro do arquivo, definir as seguintes tabelas com seus tipos customizados (ex:
      `LocalDateTime`, `List`, `Boolean`):
        * `Poesia`: Para os dados estáticos das poesias.
        * `PoesiaUsuarioEstado`: Para os dados do usuário (favorito, lido, avaliação, anotação).
        * `CategoriaPoesia`: Para as categorias das poesias.
        * `Personagem`: Para os dados dos personagens.
        * `TextoGeral`: Para textos de apoio do app (sobre, etc.).
        * `AtelieItem`: Para itens criados pelo usuário.
    * [] Adicionar queries `CREATE INDEX` para otimizar buscas nas colunas `Poesia(titulo)` e
      `Poesia(categoriaNome)`.
    * [] Para cada tabela, definir as queries de acesso necessárias, como:
        * `selectAll...`, `select...ById`, `select...ByNome`.
        * `upsert...`: Usar `INSERT OR REPLACE` para inserir ou atualizar registros, facilitando a
          carga de dados inicial e as atualizações de estado do usuário.
        * `delete...ById`: Para a tabela `AtelieItem`.
        * `selectAllPoesiasWithEstado`: Query com `LEFT JOIN` para buscar as poesias já com seu
          estado de usuário.

* [] **1.2. Implementar a Carga Inicial de Dados:**
    * [] Crie a pasta `app/src/main/assets/data/`.
    * [] Dentro dela, adicione os arquivos JSON com os dados iniciais: `poesias.json`,
      `personagens.json`, `categorias.json`, e `textos_gerais.json`.
    * [] Crie `data class`es Kotlin no pacote `core/data/models/` para representar a estrutura
      desses arquivos JSON. Anote-as com `@JsonClass(generateAdapter = true)` do Moshi.

* [] **1.3. Configurar Injeção de Dependência para a Camada de Dados:**
    * [] No pacote `core/di/`, crie o `DataModule.kt`.
    * [] Dentro deste módulo, crie uma função `@Provides @Singleton` para fornecer o `SqlDriver`.
    * [] Crie outra função `@Provides @Singleton` que depende do `SqlDriver` para fornecer a
      instância do `Database` (gerada pelo SQLDelight).
        * Nesta função, use o parâmetro `schema.create` para implementar o callback de primeira
          criação do banco. A lógica dentro deste callback será responsável por ler os arquivos JSON
          da pasta `assets` (usando Moshi), converter para os modelos de dados e inserir no banco
          usando as funções geradas pelo SQLDelight.

.

* [] **1.4. Configurar Preferências do Usuário com DataStore:**
    * [] No pacote `core/data/`, crie uma classe `UserPreferencesRepository.kt`.
    * [] Use o `PreferenceDataStoreFactory` para criar uma instância do DataStore.
    * [] Exponha as preferências (ex: `isDarkMode`) como um `Flow<Boolean>`.
    * [] Crie funções `suspend` para atualizar as preferências.
    * [] No `DataModule.kt`, adicione uma função `@Provides @Singleton` para fornecer o
      `UserPreferencesRepository`.

* [] **1.5. Criar os Repositórios da Aplicação:**
    * [] Para cada feature principal (ex: `poesias`), crie a interface do repositório no pacote da
      feature, ex: `features/poesias/data/PoesiaRepository.kt`.
    * [] Crie a implementação concreta, ex: `features/poesias/data/PoesiaRepositoryImpl.kt`.
    * [] A implementação (`PoesiaRepositoryImpl`) receberá a instância do `Database` (gerada pelo
      SQLDelight) via injeção de dependência no seu construtor.
    * [] Implemente os métodos do repositório, que chamarão as funções geradas pelo SQLDelight (ex:
      `database.poesiaQueries.selectAll()`). Use a extensão `.asFlow().mapToList()` para expor
      listas reativas.
    * [] Crie um módulo Hilt `RepositoryModule.kt` em `core/di/` para prover as implementações dos
      repositórios.

* [] **1.6. Critério de Conclusão Fase 1:**
    * O schema do banco de dados está definido nos arquivos `.sq`.
    * Hilt consegue injetar com sucesso a instância do `Database` e dos `Repositories`.
    * Na primeira inicialização do app, os dados dos arquivos JSON são lidos e populam o banco de
      dados SQLDelight.
    * O `UserPreferencesRepository` está funcional, permitindo ler e escrever preferências usando
      DataStore.
    * As camadas de dados estão prontas para serem consumidas pelos ViewModels nas próximas fases.

## Fase 2: Features Iniciais - Sistema de Formatação de Texto e Tela de Configurações

* [] **2.1. Desenvolver Sistema de Análise e Renderização de Texto Formatado:**
    * [] **2.1.1. Lógica do Parser:**
        * [] No pacote `core/utils/`, crie o arquivo `MarkupParser.kt`.
        * [] Implemente uma função `parseMarkup` que recebe uma `String` com as tags customizadas (
          ex: `<titulo-poesia>Conteúdo</titulo-poesia>`) e a converte para uma `AnnotatedString`.
        * [] A função deve usar Expressões Regulares (Regex) para encontrar as tags e seus
          conteúdos.
        * [] A função deve construir a `AnnotatedString` usando `buildAnnotatedString` e
          `withStyle`, aplicando `SpanStyle`s diferentes para cada tipo de tag.
    * [] **2.1.2. Definição dos Estilos:**
        * [] Crie uma função `@Composable` `getMarkupStyles()` que retorna um
          `Map<String, SpanStyle>`. Isso permite que os estilos usem cores e tipografia do
          `MaterialTheme.colorScheme`.
    * [] **2.1.3. Composable Otimizado:**
        * [] Crie a função `@Composable rememberMarkupAnnotatedString(markupText: String)` que usa
          `remember` para garantir que o parsing só ocorra quando o texto de entrada mudar.
        * [] Este Composable chamará `getMarkupStyles()` e `parseMarkup()` internamente.

* [] **2.2. Desenvolver a Tela de Configurações (`PreferenciasScreen`):**
    * [] **2.2.1. ViewModel:**
        * [] No pacote `features/preferencias/`, crie `PreferenciasViewModel.kt`.
        * [] Anote a classe com `@HiltViewModel`.
        * [] Injete o `UserPreferencesRepository` via construtor com `@Inject`.
        * [] Exponha os valores das preferências (ex: `isDarkMode`) como um `StateFlow`, coletando
          os dados do repositório.
        * [] Crie funções para atualizar as preferências (ex: `setDarkMode(Boolean)`), que chamarão
          as funções `suspend` do repositório dentro de um `viewModelScope.launch`.
    * [] **2.2.2. UI (Tela):**
        * [] No pacote `features/preferencias/ui/`, crie `PreferenciasScreen.kt`.
        * [] O Composable `PreferenciasScreen` receberá os valores de estado e as funções de evento
          do `ViewModel` como parâmetros.
        * [] Crie os controles de UI para cada preferência:
            * **Modo de Exibição:** `Switch` ou `SegmentedButton` para Claro/Escuro.
            * **Tamanho do Texto:** `Slider` para ajustar um fator de escala de fonte.
            * Cada controle, ao ser alterado, chama a função correspondente no ViewModel.
    * [] **2.2.3. Navegação e Integração:**
        * [] No seu grafo de navegação (`AppNavHost`), na rota de preferências, obtenha o ViewModel
          usando `val viewModel: PreferenciasViewModel = hiltViewModel()`.
        * [] Colete o estado do ViewModel com `collectAsState()` e passe os valores e funções para o
          `PreferenciasScreen`.

* [] **2.3. Desenvolver a Tela de Textos Gerais (Sobre, Termos, etc.):**
    * [] **2.3.1. Dados:** Garanta que os textos (sobre, termos de uso) estejam no banco de dados,
      carregados na Fase 1 a partir de um JSON. Crie as queries necessárias no SQLDelight para
      buscar um texto por uma chave/ID única.
    * [] **2.3.2. ViewModel:**
        * [] Crie um `TextoGeralViewModel.kt`. Ele receberá o ID do texto a ser exibido (via
          `SavedStateHandle` do Hilt) e usará o `Repository` correspondente para buscar os dados.
    * [] **2.3.3. UI (Tela):**
        * [] Crie `TextoGeralScreen.kt`.
        * [] A tela receberá o ID do texto como argumento de navegação.
        * [] Usará o `TextoGeralViewModel` para obter os dados (título e conteúdo).
        * [] Exibirá o conteúdo usando o `rememberMarkupAnnotatedString` criado no item 2.1.3 para
          renderizar o texto formatado corretamente.

* [] **2.4. Implementar a Tela de Abertura (Splash Screen):**
    * [] **2.4.1. Lógica:**
        * [] Crie `SplashScreen.kt` como a rota inicial do aplicativo.
        * [] Use um `LaunchedEffect` para iniciar um `delay` (para tempo mínimo de exibição) e
          quaisquer pré-carregamentos necessários (ex: carregar a primeira preferência).
        * [] Após a conclusão, navegue para a tela principal (`HomeScreen`) usando
          `navController.navigate(...)` com `popUpTo(splash_route) { inclusive = true }` para
          remover a splash da pilha de navegação.
    * [] **2.4.2. UI:**
        * [] A UI pode ser uma animação **Rive** ou uma imagem estática. Adicione os assets
          necessários (arquivo `.riv` em `res/raw/` ou imagem em `res/drawable/`).
        * [] Use o Composable `RiveAnimation` ou `Image` para exibir o visual.

* [] **2.5. Critério de Conclusão Fase 2:**
    * O sistema de parsing e renderização de texto com tags customizadas está funcional.
    * O usuário pode navegar para a tela de Configurações, visualizar e alterar preferências, e
      estas são persistidas e refletidas no app (ex: tema escuro).
    * O usuário pode visualizar telas de textos gerais (como "Sobre") com conteúdo formatado vindo
      do banco de dados.
    * Uma splash screen funcional é exibida na inicialização do aplicativo.
    * A arquitetura MVVM com Hilt, ViewModel e Compose está validada e funcionando para as primeiras
      features.

## Fase 3: Feature Principal - Poesias (Listagem, Detalhes, Interações)

* [] **3.1. Definir Modelos de Dados e Queries Adicionais:**
    * [] **Modelos de Estado do Usuário:** No SQLDelight, crie as tabelas para armazenar o estado
      que o usuário gera.
        * `PoesiaUsuarioEstado.sq`:
          `CREATE TABLE PoesiaUsuarioEstado (poesiaId INTEGER NOT NULL PRIMARY KEY, ehFavorita INTEGER AS Boolean DEFAULT 0, foiLida INTEGER AS Boolean DEFAULT 0);`
        * `AnotacaoPoesia.sq`:
          `CREATE TABLE AnotacaoPoesia (poesiaId INTEGER NOT NULL PRIMARY KEY, textoAnotacao TEXT NOT NULL, dataAtualizado INTEGER NOT NULL);`
    * [] **Queries de Junção (Join):** No arquivo `Poesia.sq`, crie queries que juntem a tabela
      `Poesia` com `PoesiaUsuarioEstado` para obter uma visão combinada dos dados. Isso evita
      múltiplas chamadas ao banco.
        * Ex:
          `selectAllWithEstado: SELECT P.*, PUE.ehFavorita, PUE.foiLida FROM Poesia AS P LEFT JOIN PoesiaUsuarioEstado AS PUE ON P.id = PUE.poesiaId;`

* [] **3.2. Estender a Camada de Dados:**
    * [] **Repositório:** No `PoesiaRepository.kt` e sua implementação, adicione os métodos para
      interagir com o estado do usuário.
        * `fun getPoesiasComEstado(): Flow<List<PoesiaComEstado>>` (usando a query de join).
        * `suspend fun toggleFavorita(poesiaId: Long)`
        * `suspend fun marcarComoLida(poesiaId: Long)`
        * `fun getAnotacao(poesiaId: Long): Flow<AnotacaoPoesia?>`
        * `suspend fun salvarAnotacao(poesiaId: Long, texto: String)`

* [] **3.3. Implementar os ViewModels da Feature:**
    * [] **`PoesiaListViewModel.kt`:**
        * [] Anote com `@HiltViewModel` e injete o `PoesiaRepository`.
        * [] Crie um `StateFlow` para a UI, `val uiState: StateFlow<PoesiaListUiState>`, que
          representará os diferentes estados (Carregando, Sucesso com a lista de poesias, Erro).
        * [] Use o operador `stateIn` para converter o `Flow` do repositório em um `StateFlow` de
          forma eficiente.
    * [] **`PoesiaDetailViewModel.kt`:**
        * [] Anote com `@HiltViewModel` e injete o `PoesiaRepository` e o `SavedStateHandle`.
        * [] Use o `SavedStateHandle` para obter o `poesiaId` passado pela navegação.
        * [] Combine múltiplos `Flows` do repositório (detalhes da poesia, estado do usuário,
          anotação) usando `combine` para criar um único `StateFlow` para a UI de detalhes.
        * [] Crie funções para os eventos da UI (ex: `onToggleFavorita()`,
          `onSaveAnotacao(texto: String)`), que chamarão os métodos `suspend` do repositório em um
          `viewModelScope.launch`.

* [] **3.4. Desenvolver a UI da Feature (Composables):**
    * [] **Tela de Lista (`PoesiasScreen`):**
        * [] Obtenha o `PoesiaListViewModel` com `hiltViewModel()`.
        * [] Colete o `uiState` com `collectAsStateWithLifecycle()` (requer dependência
          `lifecycle-runtime-compose`).
        * [] Use `LazyColumn` para exibir a lista de poesias. Cada item será um `Card` customizado
          mostrando imagem (com **Coil**), título e um ícone para favoritar.
        * [] A ação de clique em um item navega para `PoesiaDetailScreen` com o `poesiaId`.
    * [] **Tela de Detalhes (`PoesiaDetailScreen`):**
        * [] Obtenha o `PoesiaDetailViewModel`.
        * [] Exiba a imagem da poesia com **Coil**.
        * [] Use o `rememberMarkupAnnotatedString` (da Fase 2) para renderizar o conteúdo da poesia.
        * [] No `LaunchedEffect`, chame a função do ViewModel para marcar a poesia como "lida".
        * [] Crie uma seção com um `TextField` para o usuário digitar sua anotação, vinculando o
          valor ao estado do ViewModel.
        * [] Adicione botões para "Salvar Anotação" e outras ações (Copiar, Compartilhar) que
          disparam eventos no ViewModel.

* [] **3.5. Integração com a Navegação:**
    * [] No seu `AppNavHost`, adicione as rotas para `PoesiasScreen` e `PoesiaDetailScreen`.
    * [] A rota de detalhes deve aceitar o `poesiaId` como argumento (ex:
      `route = "poesia_detalhe/{poesiaId}"`).

* [] **3.6. Critério de Conclusão Fase 3:**
    * O usuário pode ver uma lista de todas as poesias.
    * A funcionalidade de favoritar/desfavoritar funciona na lista e na tela de detalhes, com o
      estado persistido.
    * Ao visualizar uma poesia, ela é marcada como "lida".
    * O usuário pode ler, criar, editar e deletar anotações pessoais para cada poesia.
    * A navegação entre a lista e os detalhes está funcional.

## Fase 4: Features Adicionais - Personagens, Histórico e Atalhos

* [] **4.1. Desenvolver a Feature "Personagens":**
    * [] **4.1.1. Camada de Dados:**
        * [] Garanta que a entidade `Personagem.sq` e suas queries (`selectAll`, `selectById`) foram
          criadas na Fase 1.
        * [] Implemente o `PersonagemRepository.kt` e sua implementação, seguindo o mesmo padrão do
          `PoesiaRepository`.
    * [] **4.1.2. ViewModels:**
        * [] Crie `PersonagemListViewModel.kt` para buscar a lista de todos os personagens.
        * [] Crie `PersonagemDetailViewModel.kt` para buscar os detalhes de um único personagem,
          usando o `personagemId` injetado pelo `SavedStateHandle`.
    * [] **4.1.3. UI (Telas):**
        * [] Crie `PersonagemListScreen.kt` que exibirá uma `LazyColumn` ou `LazyVerticalGrid` de
          personagens, cada um com sua foto (usando **Coil**) e nome. O clique em um item navega
          para a tela de detalhes.
        * [] Crie `PersonagemDetailScreen.kt` para exibir a foto principal, nome, e a descrição
          formatada usando o `rememberMarkupAnnotatedString`.
    * [] **4.1.4. Navegação:**
        * [] Adicione as rotas para `PersonagemListScreen` e `PersonagemDetailScreen` no
          `AppNavHost`.

* [] **4.2. Implementar Funcionalidade de "Histórico de Visitas":**
    * [] **4.2.1. Camada de Dados:**
        * [] Crie o arquivo `HistoricoVisita.sq` com a tabela para armazenar o histórico (`id`,
          `tipoConteudo`, `conteudoId`, `tituloDisplay`, `dataVisita`).
        * [] Implemente `HistoricoRepository.kt` para adicionar, listar e limpar o histórico.
    * [] **4.2.2. Lógica de Registro:**
        * [] Nos ViewModels de detalhes (ex: `PoesiaDetailViewModel`, `PersonagemDetailViewModel`),
          injete o `HistoricoRepository`.
        * [] No `init` block do ViewModel, chame a função do repositório para registrar a visita
          àquele conteúdo.
    * [] **4.2.3. ViewModel e UI da Tela de Histórico:**
        * [] Crie `HistoricoViewModel.kt` para carregar a lista de itens do histórico.
        * [] Crie `HistoricoScreen.kt` para exibir os itens. Ao clicar em um item, use o
          `tipoConteudo` e `conteudoId` para navegar para a tela de detalhe correta.

* [] **4.3. Implementar "Pontos de Retorno Salvo" (Atalhos Globais):**
    * [] **4.3.1. Camada de Dados:**
        * [] Crie `PontoRetorno.sq` com a tabela para 10 slots fixos (`slotId` [1-10],
          `tituloDisplay`, `rotaNavegacao`, `dataSalvo`).
        * [] Implemente `PontoRetornoRepository.kt` para obter, salvar e limpar os slots.
    * [] **4.3.2. Lógica de Salvamento:**
        * [] Nas telas de detalhe, adicione um botão "Salvar Atalho".
        * [] Ao clicar, mostre um `Dialog` ou `BottomSheet` que lista os 10 slots. O usuário pode
          selecionar um slot para salvar a rota atual (`navController.currentDestination?.route`).
    * [] **4.3.3. ViewModel e UI da Tela de Atalhos:**
        * [] Crie `PontosRetornoViewModel.kt` para carregar os 10 slots.
        * [] Crie `PontosRetornoScreen.kt` para exibir os slots. Um clique em um slot configurado
          usa o `navController` para navegar para a rota salva.

* [] **4.4. Critério de Conclusão Fase 4:**
    * A feature "Personagens" (listagem e detalhes) está funcional.
    * O sistema de "Histórico de Visitas" está registrando e exibindo o conteúdo visitado.
    * O usuário pode salvar até 10 atalhos para telas específicas e navegar para eles a partir de
      uma tela dedicada.
    * A arquitetura MVVM continua a ser aplicada consistentemente.

## Fase 5: Tela Principal, Navegação Global e Pesquisa

* [] **5.1. Desenvolver a Tela Principal (`HomeScreen`):**
    * [] **Layout:** A tela será um `Scaffold` com uma `LazyColumn` como corpo principal.
    * [] **Seções da Tela:** Cada bloco de conteúdo (Poesia Aleatória, Favoritos, Novidades, Lista
      de Poesias) será um Composable separado dentro da `LazyColumn`.
        * `item { PoesiaAleatoriaCard() }`
        * `item { FavoritosRow() }`
        * `items(poesias) { poesia -> PoesiaListItem(poesia) }`
    * [] **ViewModels:** A `HomeScreen` pode ter um `HomeViewModel` para orquestrar os dados, ou
      cada seção pode usar seu próprio ViewModel injetado (abordagem mais modular).

* [] **5.2. Implementar a Navegação Principal (Barras e Drawer):**
    * [] **`MainActivity.kt`:** Será o ponto central que conterá o `ModalNavigationDrawer`, o
      `Scaffold` com `TopAppBar` e `BottomAppBar`, e o `NavHost`.
    * [] **TopAppBar:** Conterá um ícone para abrir o `NavigationDrawer` e um botão para navegar
      para a `SearchScreen`.
    * [] **BottomAppBar:** Conterá os `IconButton` para as principais seções do app (ex: Início,
      Marcadores, Ateliê), gerenciando a navegação com o `navController`.
    * [] **NavigationDrawer:** Conterá a `SettingsScreen` (desenvolvida na Fase 2) e outros links
      para seções menos frequentes (ex: Sobre, Termos de Uso).

* [] **5.3. Implementar a Funcionalidade de Pesquisa Global:**
    * [] **5.3.1. Camada de Dados:**
        * [] No `SearchRepository.kt`, implemente um método `search(query: String)` que consulta
          múltiplos repositórios (`PoesiaRepository`, `PersonagemRepository`, etc.).
        * [] Use queries `LIKE '%query%'` no SQLDelight para buscar em títulos e conteúdos.
        * [] Combine os resultados em um único `Flow<List<SearchResult>>`.
    * [] **5.3.2. ViewModel e UI:**
        * [] Crie `SearchViewModel.kt` que usa o `SearchRepository`.
        * [] Crie `SearchScreen.kt` com um `TextField` para a busca. Conforme o usuário digita, o
          ViewModel executa a pesquisa.
        * [] Exiba os resultados em uma `LazyColumn`. O clique em um resultado navega para a tela de
          detalhe apropriada.

* [] **5.4. Implementar Text-to-Speech (TTS):**
    * [] **Dependência:** Adicione uma biblioteca TTS para Android (ex: a nativa
      `android.speech.tts.TextToSpeech`).
    * [] **Serviço Wrapper:** Crie uma classe `TtsService.kt` injetável via Hilt para encapsular a
      lógica do TTS (inicializar, falar, pausar, parar).
    * [] **Integração:** No `PoesiaDetailViewModel`, injete o `TtsService`. Adicione eventos para
      `play()`, `pause()`, `stop()`. Antes de passar o texto para o TTS, use uma função no
      `MarkupParser` para remover as tags de formatação.
    * [] **UI:** Adicione `IconButton`s na tela de detalhes da poesia para controlar a reprodução.

* [] **5.5. Critério de Conclusão Fase 5:**
    * A `HomeScreen` está montada e exibe dinamicamente as diferentes seções de conteúdo.
    * A navegação principal via `BottomAppBar` e `NavigationDrawer` está funcional e consistente.
    * A Pesquisa Global funciona, retornando resultados de diferentes tipos de conteúdo.
    * A funcionalidade de Text-to-Speech está implementada e funcional nas telas de poesia.

## Fase 6: Feature "Ateliê", Polimento e Testes

* [] **6.1. Desenvolver a Feature "Ateliê do Usuário":**
    * [] **6.1.1. Camada de Dados:**
        * [] Crie `AtelieUsuario.sq` com a tabela para notas do usuário (`id`, `titulo`, `conteudo`,
          `dataAtualizado`, `fixada`).
        * [] Implemente `AtelieUsuarioRepository.kt` com os métodos para CRUD completo (listar,
          buscar por id, salvar, deletar).
    * [] **6.1.2. ViewModels:**
        * [] Crie `AtelieListViewModel.kt` para carregar a lista de todas as notas.
        * [] Crie `AtelieEditViewModel.kt` para carregar uma nota existente ou preparar uma nova, e
          para salvar ou deletar a nota. Use o `SavedStateHandle` para receber o `notaId` (que pode
          ser nulo para uma nova nota).
    * [] **6.1.3. UI (Telas):**
        * [] Crie `AtelieListScreen.kt`. Use `LazyColumn` para exibir a lista de notas. Cada item
          deve mostrar título, trecho do conteúdo e data. Adicione um `FloatingActionButton` (FAB)
          para navegar para a tela de edição.
        * [] Crie `AtelieEditScreen.kt`. Conterá `TextFields` para o título e o conteúdo. O botão "
          Salvar" na `TopAppBar` deve disparar a função de salvamento no ViewModel.
    * [] **6.1.4. Navegação:**
        * [] Adicione as rotas para `AtelieListScreen` e `AtelieEditScreen` no `AppNavHost`.
        * [] Conecte o item "Ateliê" da sua `BottomAppBar` (a ser adicionado) para navegar até
          `AtelieListScreen`.

* [] **6.2. Polimento Geral da UI/UX:**
    * [] **Consistência:** Revise todas as telas para garantir consistência visual (espaçamentos,
      tipografia, cores do Material 3).
    * [] **Feedback Visual:** Melhore o feedback das interações (efeitos de "ripple", estados de
      botões pressionados/desabilitados).
    * [] **Estados de UI:** Garanta que todas as telas tratem adequadamente os estados de
      Carregamento (ex: `CircularProgressIndicator`), Erro (mensagem clara com opção de "Tentar
      Novamente") e Vazio (ex: "Nenhuma poesia favorita encontrada").
    * [] **Performance:** Otimize o desempenho de listas longas. Verifique e otimize reconstruções
      desnecessárias de Composables (use `remember` com chaves, `derivedStateOf`, e passe lambdas em
      vez de estados sempre que possível).

* [] **6.3. Testes Abrangentes:**
    * [] **NÃO SERÃO CRIADOS TESTES. VAMOS TESTAR NO ANDAMENTO DO PROJETO**

* [] **6.4. Configuração para Release:**
    * [] Revise o arquivo `app/build.gradle.kts` para configurar `versionCode` e `versionName`.
    * [] Adicione os ícones do aplicativo para diferentes densidades de tela na pasta `res/mipmap`.
    * [] Configure a ofuscação de código com ProGuard/R8 para builds de release.
    * [] Gere e teste um App Bundle de release (`./gradlew bundleRelease`).

* [] **6.5. Critério de Conclusão Fase 6:**
    * A feature "Ateliê" está completamente funcional.
    * O aplicativo tem uma UI/UX polida, com bom tratamento de todos os estados.
    * Uma base sólida de testes unitários e de UI foi criada para as features críticas.
    * O aplicativo está configurado e pronto para ser publicado.

## Fase 7: Evolução Pós-Lançamento

* [] **7.1. Monitoramento:**
    * [] Integre o **Firebase Crashlytics** para monitorar travamentos em produção. É uma
      configuração simples que fornece insights valiosos sobre a estabilidade do app.

* [] **7.2. Atualização Dinâmica de Conteúdo (Opcional):**
    * [] **Fonte Remota:** Defina uma API externa ou um arquivo JSON em um local público (como
      Google Drive ou GitHub) como fonte dos dados.
    * [] **Lógica de Sincronização:** Crie um `RemoteDataSource` usando uma biblioteca como **Ktor**
      ou **Retrofit**. No repositório, implemente a lógica para buscar dados remotos, comparar com
      os locais (usando versões ou timestamps) e atualizar o banco de dados SQLDelight.
    * [] **Trabalho em Background:** Use o **WorkManager** do Android para agendar verificações
      periódicas de atualização de conteúdo.

* [] **7.3. Mascote Interativo "Catshito" (com Rive):**
    * [] **Design e Animação:** Crie as animações do Catshito (ex: ocioso, reagindo a um toque,
      celebrando uma conquista) no editor do **Rive**. Use a "State Machine" do Rive para controlar
      as transições.
    * [] **Integração:** Adicione o Composable `RiveAnimation` em uma área da UI (ex: `HomeScreen`).
    * [] **Lógica de Interação:** Crie um `CatshitoViewModel` para decidir qual estado da animação
      ativar. Por exemplo, ao detectar que o usuário favoritou uma poesia, o ViewModel pode disparar
      o gatilho "celebrar" na State Machine do Rive.

* [] **7.4. Gamificação (Conquistas Poéticas):**
    * [] **Lógica:** Crie um `ConquistasService` que observa as ações do usuário (poesias lidas,
      notas criadas).
    * [] **Dados:** Armazene o progresso e as conquistas desbloqueadas no DataStore ou em uma nova
      tabela SQLDelight.
    * [] **UI:** Crie uma `ConquistasScreen` para listar as conquistas. Ao desbloquear uma, mostre
      uma animação do Catshito (Rive) como celebração.

## Fase 8: Itens Adicionais do Documento Original

* [] **8.1. Onboarding:**
    * [] Crie uma sequência de telas de onboarding usando `HorizontalPager`.
    * [] Use o `UserPreferencesRepository` (DataStore) para salvar um booleano `onboardingCompleto`.
      A rota inicial do app verificará esse valor para decidir se mostra o onboarding ou vai direto
      para a `HomeScreen`.

* [] **8.2. Explicações Contextuais (Tooltips):**
    * [] No seu `MarkupParser`, adicione suporte para uma tag de tooltip, ex:
      `<tooltip text="Explicação aqui">palavra</tooltip>`.
    * [] O parser deve criar uma anotação (`StringAnnotation`) na `AnnotatedString` para o trecho de
      texto.
    * [] No Composable que renderiza o texto, use um `ClickableText` e verifique se o offset do
      clique corresponde a uma anotação de tooltip. Se corresponder, exiba um `AlertDialog` ou um
      Composable de tooltip customizado.

* [] **8.3. Ambientes Sonoros:**
    * [] Use a classe `MediaPlayer` nativa do Android, encapsulada em um serviço (`SoundService.kt`)
      gerenciado pelo Hilt.
    * [] Adicione controles de UI na tela de detalhes da poesia para tocar/pausar os sons (arquivos
      de áudio em `res/raw/`). Gerencie o ciclo de vida do `MediaPlayer` para liberar recursos
      corretamente.

* [] **8.4. Atualização do Tema Baseado nas Preferências:**
    * [] No seu `MainActivity.kt`, onde você chama `CatfeinaTheme`, você precisará obter o estado do
      tema (claro/escuro) do `UserPreferencesRepository`.
    * [] Uma abordagem limpa é injetar o repositório em um `MainViewModel` e coletar o estado do
      tema lá.
    * [] Passe o valor `isDarkMode` para o seu `CatfeinaTheme { ... }`. Dentro do `CatfeinaTheme`,
      use esse booleano para decidir qual `colorScheme` aplicar (o `DarkColorScheme` ou o
      `LightColorScheme`).

* [] **8.5. Refatorar a Criação do `PreferenciasViewModel` com Hilt:**
    * [] **Ação Crítica:** O passo mais importante agora é integrar o Hilt ao seu `AppNavHost`.
      Atualmente, você está criando as dependências manualmente:
      `val userPreferencesRepository = UserPreferencesRepository(context)`.
    * [] **Passo 1:** Anote `PreferenciasViewModel` com `@HiltViewModel` e injete o
      `UserPreferencesRepository` no construtor com `@Inject`.
    * [] **Passo 2:** Remova a `provideFactory` do seu ViewModel.
    * [] **Passo 3:** No `composable(AppDestinations.PREFERENCIAS_ROUTE)`, substitua toda a lógica
      de criação manual por uma única linha:
      `val viewModel: PreferenciasViewModel = hiltViewModel()`.
    * [] **Resultado:** Isso validará toda a sua configuração do Hilt e servirá de modelo para todos
      os outros ViewModels do aplicativo.

* [] **8.6. Implementar a `HomeScreen` Real:**
    * [] Crie `features/inicio/ui/InicioScreen.kt` e seu `features/inicio/InicioViewModel.kt`.
    * [] O `InicioViewModel` irá injetar os repositórios necessários (ex: `PoesiaRepository`) para
      buscar os dados das diferentes seções (poesia aleatória, favoritos, etc.).
    * [] No `AppNavHost`, substitua o comentário `// InicioScreen()` pela chamada real, usando
      `hiltViewModel()` para obter o `InicioViewModel`.
    * [] A `InicioScreen` será um `Scaffold` com uma `LazyColumn` que renderiza as diferentes
      seções, conforme planejado na Fase 5.

* [] **8.7. Finalizar a Estrutura de Navegação:**
    * [] **BottomAppBar:** No seu `MainActivity.kt`, adicione os itens de menu que faltam para a
      `bottomBarItems`, como "Ateliê" e "Marcadores", cada um com seu ícone e rota.
    * [] **Rotas com Argumentos:** Para telas de detalhes (como `PoesiaDetailScreen`), defina a rota
      no `AppNavHost` para aceitar um argumento, como você comentou no seu código:
      `composable("poesia_detalhe/{poesiaId}") { ... }`.
    * [] **Navegação a partir de Listas:** Nas suas telas de lista (`PoesiasScreen`,
      `PersonagemListScreen`), a ação de clique em um item chamará
      `navController.navigate("poesia_detalhe/${item.id}")`.

* [] **8.8. Adicionar Ícones ao `Icones.kt`:**
    * [] Conforme novas features são adicionadas (Pesquisa, Histórico, Ateliê, Marcadores), adicione
      os `ImageVector`s correspondentes ao seu objeto `core/utils/Icones.kt` para manter a
      consistência.

* [] **8.9. NÃO DEFINIDA:**
    * [] Tomar café.

* [] **8.10. Integrar Gerenciador de Tema Dinâmico ("Vendoring"):**
    * [] **8.10.1. Copiar Arquivos-Fonte:**
        * [] Crie a estrutura de pacotes `io/github/seyoungcho2/dynamictheme/material3/` dentro de
          `app/src/main/java/`.
        * [] Copie todos os arquivos-fonte da biblioteca `ComposeDynamicTheme` para dentro da
          estrutura de pacotes criada.
        * [] Garanta que a dependência do Gradle para esta biblioteca (
          `io.github.seyoungcho2:dynamic-theme-m3`) **NÃO** esteja presente no arquivo
          `app/build.gradle.kts`.
    * [] **8.10.2. Definir os Temas do Catfeina:**
        * [] Crie um novo arquivo em
          `app/src/main/java/com/marin/catfeina/ui/theme/CatfeinaThemes.kt`.
        * [] Dentro deste arquivo, crie um `object CatfeinaThemeKeys` para definir as chaves únicas
          de cada tema (ex: `TEMA_CLARO`, `TEMA_ESCURO`).
        * [] Crie um `object CatfeinaThemeModels` para definir os `ThemeModel` correspondentes a
          cada chave, utilizando os `lightColorScheme` e `darkColorScheme` já existentes no seu
          projeto.
    * [] **8.10.3. Inicializar o Serviço de Temas:**
        * [] Na classe `CatfeinaApp.kt` (já existente e anotada com `@HiltAndroidApp`), localize o
          método `onCreate()`.
        * [] Dentro do `onCreate()`, inicialize o serviço chamando `DynamicThemeService.init(this)`.
        * [] Use a função `registerThemeModels` para registrar os temas criados no passo anterior.
        * [] Defina um tema padrão (ex: `TEMA_CLARO_MODEL`) usando `setDefaultThemeModel`.
    * [] **8.10.4. Aplicar o Provedor de Tema na UI:**
        * [] Abra o arquivo `MainActivity.kt`.
        * [] No `setContent`, substitua o `CatfeinaTheme { ... }` existente pelo
          `DynamicThemeService.get().ProvidesTheme { ... }`.
        * [] Mova a `Surface` e o `AppNavHost` para dentro do lambda do `ProvidesTheme`.

* [] **8.11. Critério de Conclusão Final (MVP - Produto Mínimo Viável):**
    * O roteiro de desenvolvimento foi completamente revisado e adaptado para a arquitetura
      Kotlin/Compose/Hilt.
    * A refatoração com Hilt foi concluída, eliminando a criação manual de dependências na UI.
    * As features principais (Configurações, Poesias, Personagens, Ateliê) estão implementadas e
      acessíveis através da estrutura de navegação principal.
    * O aplicativo está estável, testado e pronto para ser usado e evoluído com as fases de
      polimento e pós-lançamento.

## Fase 9: Consolidação, Documentação Final e Próximos Passos

* [] **9.1. Revisão Final do Código e da Arquitetura:**
    * [] Realize uma passagem completa por todo o código do projeto. Verifique a aderência aos
      princípios da Arquitetura Limpa, consistência no uso de ViewModels, Repositórios e Módulos
      Hilt.
    * [] Garanta que não há alertas pendentes, assegurando um código limpo e padronizado.
    * [] Verifique se todos os recursos (strings, cores, dimensões) estão definidos em arquivos de
      recursos (`res/values`) e não "hardcoded" no código.

* [] **9.2. Atualizar Documentação do Projeto:**
    * [] **README.md:** Crie ou atualize o arquivo `README.md` na raiz do projeto. Ele deve conter:
        * Uma breve descrição do Catfeina.
        * A pilha tecnológica utilizada (Kotlin, Compose, Hilt, SQLDelight, etc.).
        * Instruções de como compilar e rodar o projeto.
    * [] **Atualizar `GEMINI.md`:** Marque todos os itens concluídos no roteiro de desenvolvimento
      com `[X]`. Revise as seções para garantir que elas reflitam o estado final do projeto MVP.
    * [] **Changelog:** Crie um arquivo `CHANGELOG.md` na raiz do projeto, documentando as
      principais features implementadas na versão 1.0.

* [] **9.3. Preparar Materiais para a Loja de Aplicativos:**
    * [] Escreva os textos para a listagem na Google Play Store (título, descrição curta, descrição
      completa).
    * [] Prepare as capturas de tela (`screenshots`) das principais telas do aplicativo (Início,
      Detalhes da Poesia, Configurações, Ateliê).

* [] **9.4. Execução de Testes Finais:**
    * [] Rode todos os testes unitários e de UI (`./gradlew test connectedCheck`) uma última vez
      para garantir que nenhuma regressão foi introduzida.
    * [] Realize testes manuais de ponta a ponta nos fluxos mais críticos do aplicativo (ciclo de
      vida completo de uma nota no Ateliê, favoritar uma poesia, mudar um tema, etc.).

* [] **9.5. Gerar o Artefato Final para Publicação:**
    * [] Gere o App Bundle de release final e assinado (`./gradlew bundleRelease`).
    * [] Envie o App Bundle para a Google Play Console e preencha todos os metadados necessários
      para o lançamento.

* [] **9.6. Critério de Conclusão Final do Projeto (Versão 1.0):**
    * Todo o roteiro de desenvolvimento no `GEMINI.md` foi concluído ou conscientemente adiado para
      uma versão futura.
    * O código está limpo, documentado e testado.
    * A documentação do projeto (`README.md`, `CHANGELOG.md`) está atualizada.
    * Um App Bundle assinado e pronto para publicação foi gerado com sucesso.
    * **O aplicativo Catfeina atingiu o status de Produto Mínimo Viável (MVP) e está pronto para o
      seu lançamento inicial.**

--- Fim do Roteiro de Desenvolvimento ---
