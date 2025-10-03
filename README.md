# Catfeina (Edição Kotlin) ☕

[![Kotlin Version](https://img.shields.io/badge/Kotlin-1.9.x-7F52FF?style=flat-square&logo=kotlin)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-1.6.x-4285F4?style=flat-square&logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg?style=flat-square)](https://www.gnu.org/licenses/gpl-3.0)
<!-- Adicione mais badges se relevante (ex: build status, code coverage, versão do app) -->

**Catfeina** é um aplicativo Android nativo, construído com Kotlin, projetado para oferecer uma
experiência elegante, fluida e agradável para amantes de poesia. Explore, descubra e interaja com um
universo de conteúdo poético enriquecido visualmente e com formatação rica.

<!-- Opcional: Adicionar um screenshot ou GIF do app aqui quando a UI estiver mais desenvolvida -->
<!-- <p align="center">
  <img src="path/to/your/kotlin_screenshot.png" alt="Catfeina Kotlin Screenshot" width="300"/>
</p> -->

## ✨ Funcionalidades Principais (Planejadas e em Desenvolvimento)

* **Exploração de Poesias:** Navegue por uma coleção crescente de poesias, apresentadas de forma
  clara e legível.
* **Detalhes Enriquecidos:** Visualize cada poesia com informações adicionais.
* **Conteúdo Formatado com Sistema Customizado:** Textos como biografias de personagens e poesias
  serão renderizados a partir de um sistema de tags customizadas para `AnnotatedString`.
* **Favoritos:** Marque suas poesias preferidas para acesso rápido.
* **Temas Dinâmicos:** Personalize sua experiência visual (claro/escuro).
* **Design Moderno com Material 3:** Interface de usuário limpa e intuitiva construída com Jetpack
  Compose e os princípios do Material Design 3.
* **Arquitetura Robusta:** Segue princípios da Arquitetura Limpa, utilizando:
    * **SQLDelight:** Para persistência de dados local (SQLite).
    * **Hilt:** Para gerenciamento de estado e injeção de dependências.
    * **Navigation for Compose:** Para uma navegação declarativa entre telas.
    * **Coil:** Para carregamento de imagens.
    * **Jetpack DataStore:** Para armazenamento de preferências simples do usuário.
* **População Inicial de Dados:** Conteúdo base carregado localmente na primeira inicialização (a
  partir de JSON com Moshi).
* **(Opcional) Mascote Interativo "Cashito":** Um mascote animado com Rive.

## 🛠️ Tecnologias Utilizadas

* **Linguagem Principal:** [Kotlin](https://kotlinlang.org/)
* **Framework de UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose)
* **Arquitetura:** Arquitetura Limpa com **MVVM**.
* **Gerenciamento de Estado / Injeção de Dependência:
  ** [Hilt](https://developer.android.com/training/dependency-injection/hilt-android).
* **Persistência de Dados:** [SQLDelight](https://cashapp.github.io/sqldelight/) (SQLite).
* **Navegação:** [Navigation for Compose](https://developer.android.com/jetpack/compose/navigation).
* **Preferências:
  ** [Jetpack DataStore](https://developer.android.com/topic/libraries/architecture/datastore).
* **Análise de JSON:** [Moshi](https://github.com/square/moshi).
* **Animações:**
    * [Rive](https://rive.app/)
    * Animações nativas do Jetpack Compose.
* **Imagens:** [Coil](https://coil-kt.github.io/coil/).
* **Linting:** `ktlint`.

## 🚀 Como Começar (Build & Run)

**Pré-requisitos de Ambiente para Desenvolvimento:**

* Android Studio (versão Hedgehog ou mais recente).
* SDK do Android.
* JDK 17 ou superior.
* Git instalado na sua máquina.

1. **Clone o repositório:**
   `git clone https://github.com/luizcmarin/Catfeina.git`
2. **Abra o projeto no Android Studio.**
3. **Aguarde o Gradle sincronizar** e baixar todas as dependências definidas no arquivo
   `gradle/libs.versions.toml`.
4. **Execute o aplicativo** em um emulador ou dispositivo físico clicando no botão "Run 'app'".

## 📝 Documentação Interna e Decisões de Projeto

Para entender melhor a colaboração com o assistente de IA, as decisões de arquitetura e a evolução
do projeto:

* **`GEMINI.md`**: Detalhes da colaboração com o Assistente Gemini, prompts utilizados, e o roteiro
  de desenvolvimento completo (TODO list) para a versão Kotlin.

## 🤝 Contribuições

Contribuições são muito bem-vindas! Se você tiver ideias para novas funcionalidades, melhorias ou
correções de bugs, por favor, siga estes passos:

1. Faça um Fork do projeto.
2. Crie uma nova Branch (`git checkout -b feature/sua-feature-incrivel`).
3. Faça commit de suas mudanças (`git commit -m 'Adiciona funcionalidade X'`).
4. Faça Push para a sua Branch (`git push origin feature/sua-feature-incrivel`).
5. Abra um Pull Request detalhando suas alterações.

## 🐛 Reportando Bugs

Se encontrar algum bug, por favor, abra uma [Issue](https://github.com/luizcmarin/Catfeina/issues)
no repositório do projeto. Inclua:

* Uma descrição clara e concisa do bug.
* Passos para reproduzir o comportamento.
* Qual comportamento você esperava.
* Qual comportamento realmente aconteceu.
* Screenshots ou GIFs, se ajudar a ilustrar o problema.
* Versão do aplicativo (se souber), versão do Android e modelo do dispositivo.

## 📜 Licença

Este projeto é licenciado sob a **GNU General Public License v3.0**. Veja o arquivo `LICENSE` para
mais detalhes.

---

**Desenvolvido com ❤️ e Kotlin por Marin, com a colaboração e assistência do Assistente Gemini.**

