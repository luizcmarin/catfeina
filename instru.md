GEMINI:  voce tem permissão para ler/ver/solicitar todos os arquivos do meu pojeto. deve
constantemente ler da IDE e não confiar na tua copia de memoria, porque eu estou sempre alterando os
arquivos. o projeto antigo está disponível para consulta na pasta 'legado' na raiz do projeto. agora
leia @GEMINI.md e entenda o que vamos fazer.

## @GEMINI: ⚠️ LEMBRETES IMPORTANTES PARA O ASSISTENTE GEMINI (Edição Kotlin) ⚠️

Esta seção contém instruções cruciais para a nossa colaboração. **POR FAVOR, REVISE ESTA SEÇÃO
REGULARMENTE.**

### A. Seu Papel e Como Ajudar:

* **Colaborador Ativo em Kotlin:** Você é um parceiro de desenvolvimento Kotlin. Espero sugestões
  proativas alinhadas com as melhores práticas do ecossistema Android moderno.
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
    * **NÃO PRESUMA** o conteúdo dos arquivos. Baseie-se no que foi compartilhado E o que está na
      IDE. Eu tambem altero o arquivos fontes e ficam diferentes do que voce tem na memória.
* **Minha Liderança no Roteiro:** **EU** definirei a sequência dos itens a serem trabalhados.

### C. Qualidade e Estilo do Código Kotlin:

* **Código Moderno:** **SEMPRE FORNEÇA CÓDIGOS ATUALIZADOS.** Verifique se as APIs e outras
  bibliotecas não estão depreciadas. **NÃO SUGIRA GAMBIARRAS.**
* **Documentação de Arquivos:** Todo novo arquivo significativo deve incluir um cabeçalho
  descritivo.
* **Comentários:** Código limpo e bem nomeado deve ser autoexplicativo. Use comentários apenas para
  lógicas complexas. Evite comentários supérfluos inline.
* **Formatação:** O código deve seguir as convenções garantidas pela boas práticas.

### D. Formatação das Respostas (CRUCIAL!):

* **Use UM ÚNICO Bloco de Código Principal:** Sua resposta final, contendo Markdown ou código, deve
  estar dentro de um único bloco de código para que eu possa usar o botão "copiar" da interface.
* **NÃO use três crases seguidas dentro deste bloco principal**, pois quebra a funcionalidade de
  cópia. Se precisar mostrar um exemplo de código dentro do documento, use indentação (quatro
  espaços) ou uma crase simples para `inline code`.

os arquivos gradle/libs.versions.toml e os gradles NÃO DEVEM SER MODIFICADOS SEM AUTORIZAÇÃO.

# AGENT.md: Roteiro de Migração para Kotlin Multiplatform (KMP)

## 1. Objetivo

Este documento detalha o plano para migrar o projeto **Catfeina**, atualmente um aplicativo nativo
Android, para uma arquitetura **Kotlin Multiplatform (KMP)**, visando os alvos **Android** e *
*Desktop (JVM)**. O objetivo é maximizar o compartilhamento de código entre as plataformas,
incluindo a camada de dados, lógica de negócios (ViewModels) e a camada de UI (Jetpack Compose).

## 2. Decisões Arquitetônicas para a Migração

Para alcançar a compatibilidade KMP, substituiremos as seguintes bibliotecas específicas do Android:

* **Injeção de Dependência:** **Hilt** → **Koin**
    * _Motivo:_ Hilt é exclusivo para Android. Koin é o padrão de mercado para KMP, com suporte
      total a todas as plataformas.
* **Banco de Dados:** **Room** → **SQLDelight**
    * _Motivo:_ Room é exclusivo para Android. SQLDelight é a solução KMP recomendada para
      persistência de dados SQL, gerando código Kotlin type-safe a partir de queries SQL.
* **Preferências do Usuário:** **Jetpack DataStore** → **Multiplatform-Settings**
    * _Motivo:_ DataStore não possui suporte KMP completo. `multiplatform-settings` é uma biblioteca
      leve que oferece uma API de Key-Value simples e unificada.
* **Navegação:** **Jetpack Navigation** → **Decompose**
    * _Motivo:_ Jetpack Navigation é focado no Android. Decompose é um framework que desacopla a
      lógica de navegação da UI, tratando cada tela como um componente com seu próprio ciclo de
      vida, ideal para KMP.

## 3. Roteiro de Migração Passo a Passo

### Fase 1: Estrutura do Projeto KMP

O primeiro passo é criar a nova estrutura de diretórios e configurar o Gradle.

1. [X] **Estrutura de Diretórios:**
    * `shared/`: Módulo principal do KMP.
        * `src/commonMain/kotlin/`: Código compartilhado (Lógica, UI, Repositórios).
        * `src/androidMain/kotlin/`: Implementações `actual` para Android.
        * `src/desktopMain/kotlin/`: Implementações `actual` para Desktop.
    * `androidApp/`: Módulo do aplicativo Android.
    * `desktopApp/`: Módulo do aplicativo Desktop.


3. [X] **Configuração do `shared/build.gradle.kts`:**
    * Aplicar plugins do KMP, SQLDelight, Decompose.
    * Definir os `sourceSets` (`commonMain`, `androidMain`, `desktopMain`).
    * Declarar as dependências KMP no `commonMain` e os drivers específicos nos `sourceSets` de
      plataforma.

4. [X] **Migração de globais:** Migrar funções e arquivos globais.

5. [X] **Revisão do .github:** Revisar os arquivos da pasta .github para adequar ao KMP.

### Fase 2: Migração da Camada de Dados (`shared/commonMain`)

1. [X] **Migrar Room para SQLDelight:**
    * Criar o diretório `shared/src/commonMain/sqldelight/`.
    * Dentro, criar um arquivo `CatfeinaDatabase.sq`.
    * Converter cada `@Entity` do Room para uma declaração `CREATE TABLE` em SQL.
    * Converter cada função do `@Dao` para uma query nomeada no arquivo `.sq`.

2. [X] **Migrar DataStore para Multiplatform-Settings:**
    * A API é muito similar. Onde antes usávamos `dataStore.data.map { ... }`, usaremos
      `settings.getStringFlow(...)`, etc.

3. [X] **Refatorar Repositórios:**
    * Mover todos os `RepositoryImpl.kt` para `shared/commonMain`.
    * Atualizar as implementações para usar as novas fontes de dados (SQLDelight e
      Multiplatform-Settings).

### Fase 3: Migração da Arquitetura (`shared/commonMain`)

1. [] **Substituir Hilt por Koin:**
    * Criar um `KoinModule.kt` em `commonMain`.
    * Converter os `@Provides` do Hilt para definições `factory` ou `single` no Koin.

2. [] **Atualizar ViewModels:**
    * Remover a anotação `@HiltViewModel`.
    * Os `ViewModels` se tornarão classes simples, recebendo suas dependências (repositórios) no
      construtor.

### Fase 4: Migração da UI e Navegação (`shared/commonMain`)

1. **Substituir Navegação por Decompose:**
    * A lógica de navegação será encapsulada em "Componentes". Criaremos uma interface
      `RootComponent` que gerencia a pilha de navegação.
    * Cada tela se torna um componente filho (ex: `InicioComponent`).
    * A UI irá observar a pilha de componentes do `RootComponent` e renderizar o Composable do
      componente ativo.

2. **Mover Telas (Composables):**
    * Mover todos os arquivos das telas para `shared/commonMain`.
    * Substituir referências diretas a recursos do Android (ex: `R.string.app_name`) para a
      abordagem integrada do Compose for Multiplatform .

3. **Recursos e Bibliotecas de Suporte (Detalhado):**
    * **Recursos (Strings, Drawables, Fonts):** Serão gerenciados com a abordagem integrada do
      Compose for Multiplatform. Os recursos são definidos em `shared/src/commonMain/resources/` e
      acessados de forma type-safe (ex: `MR.strings.app_name`, `MR.images.my_icon`).
    * **Carregamento de Imagens (Coil):** Usaremos a versão KMP do Coil. A chamada
      `AsyncImage(model = ..., contentDescription = ...)` funcionará nativamente no código
      compartilhado.
    * **Animações (Rive):** Os arquivos `.riv` serão colocados em
      `shared/src/commonMain/resources/raw/`. A biblioteca `rive-compose` para KMP acessará esses
      arquivos.
    * **Parsing JSON (kotlinx.serialization):** Esta biblioteca já é KMP-nativa. A lógica de parsing
      para a população inicial do banco de dados será movida para `commonMain` sem grandes
      alterações.

### Fase 5: Implementações de Plataforma (`androidMain` e `desktopMain`)

1. **TTS (Text-to-Speech):**
    * **`commonMain`:** Definir `expect class TextToSpeechPlayer { fun say(text: String) }`.
    * **`androidMain`:** Criar `actual class TextToSpeechPlayer` que usa o
      `android.speech.tts.TextToSpeech`.
    * **`desktopMain`:** Criar `actual class TextToSpeechPlayer` que usa uma biblioteca Java (ex:
      FreeTTS) ou fica vazia por enquanto.

2. **Splash Screen (Android):**
    * A configuração da Splash Screen (`themes.xml`, `installSplashScreen()` na `MainActivity`)
      permanecerá **exclusivamente** no módulo `androidApp`.

3. **Inicialização Koin e UI:**
    * **`androidApp`:** No `onCreate` da `Application`, chamar `startKoin { ... }` e carregar os
      módulos. A `MainActivity` chamará a UI compartilhada.
    * **`desktopApp`:** No `main` do Desktop, criar a `Window`, chamar `startKoin`, e renderizar a
      UI compartilhada.

Este roteiro nos dará um caminho claro e seguro para a migração, permitindo-nos testar a cada fase.
