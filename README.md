# Catfeina - Aplicativo Multiplataforma ☕

[![Kotlin Multiplatform](https://img.shields.io/badge/Kotlin-Multiplatform-7F52FF?style=flat-square&logo=kotlin)](https://kotlinlang.org/docs/multiplatform-started.html)
[![Compose Multiplatform](https://img.shields.io/badge/Compose-Multiplatform-4285F4?style=flat-square&logo=jetpackcompose)](https://www.jetbrains.com/lp/compose-multiplatform/)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg?style=flat-square)](https://www.gnu.org/licenses/gpl-3.0)
[![Build Status](https://github.com/luizcmarin/Catfeina/actions/workflows/build.yml/badge.svg)](https://github.com/luizcmarin/Catfeina/actions/workflows/build.yml)

**Catfeina** é um aplicativo **multiplataforma (KMP)**, construído com Kotlin e Compose
Multiplatform, projetado para oferecer uma experiência elegante, fluida e agradável para amantes de
poesia em **Android, iOS, Desktop e Web**.

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
* **Design Moderno com Material 3:** Interface de usuário limpa e intuitiva construída com Compose
  Multiplatform.
* **Arquitetura Robusta:** Segue princípios da Arquitetura Limpa.

## 🛠️ Tecnologias Utilizadas

* **Linguagem Principal:** [Kotlin](https://kotlinlang.org/)
* **Estrutura:
  ** [Kotlin Multiplatform (KMP)](https://kotlinlang.org/docs/multiplatform-mobile-getting-started.html)
* **Framework de UI:** [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
* **Arquitetura:** Arquitetura Limpa com **MVVM**.
* **Navegação e ViewModels:** [Precompose](https://github.com/Tlaster/PreCompose)
* **Programação Assíncrona:
  ** [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://kotlinlang.org/docs/flow.html).
* **Persistência de Dados:** [SQLDelight](https://cash.app/sqldelight)
* **Requisições de Rede:** [Ktor](https://ktor.io/)
* **Preferências:** [Multiplatform-Settings](https://github.com/russhwolf/multiplatform-settings)
* **Análise de JSON:** [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization)
* **Imagens:** [Sketch](https://github.com/panpf/sketch)
* **Logging:** [Kermit](https://github.com/touchlab/Kermit)

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
