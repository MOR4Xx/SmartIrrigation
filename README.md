# SmartIrrigation | Implementação de Sistema de Irrigação Inteligente na Agricultura Sustentável

Uma solução para irrigação inteligente construída em Kotlin (Android), esse projeto surge da necessidade de uma forma mais facil de acessar os dados de um sistema de irrigação inteligente do projeto de pesquisa " Implementação de Sistema de Irrigação Inteligente na Agricultura Sustentável".
Esse projeto é uma continuação/melhoria, desenvolvido no Instituto Federal Goiano - Campus Urutai, desenvolvido no laboratório Maker do campus, pelo Aluno Jorge Afonso Rabelo de Araujo, estudante do curso de Sistemas de Informação no 5°/6° periodo

## 📋 Visão Geral

O projeto se propõe a controlar um sistema de irrigação de forma inteligente via aplicativo móvel Android, usando sensores, atuadores e comunicação adequada.

## 🚀 Funcionalidades

- Interface Android para controle ou visualização de sistema de irrigação.  
- Estrutura configurada para uso de sensores/atuadores (em integração com hardware externo).  
- Projetado para fácil expansão: adicionar lógica de sensores, MQTT, backend, etc.

## 🛠️ Tecnologias Utilizadas

- Kotlin (100 %) como linguagem principal. :contentReference[oaicite:0]{index=0}  
- Android / Gradle (gradle Kotlin DSL) como sistema de build (`build.gradle.kts`, `settings.gradle.kts`).  
- Estrutura padrão de projeto Android (diretório `app/`, etc).

## 🧩 Integração com Hardware

O sistema pode ser usado como front‐end para um sistema de irrigação inteligente com os seguintes componentes sugeridos:

- Sensor de umidade do solo.

- Sensor de temperatura/umidade (DHT11)

- Relé para acionamento de bomba/solenoide.

- Comunicação via MQTT ou outro protocolo de menssegeria.

- Lógica no backend ou microcontrolador (Raspberry Pi 4) que publique/inscreva tópicos MQTT e interaja com o app Android.

## 🤝 Contribuições

Contribuições são bem-vindas! Caso queira contribuir

## 👤 Autor

Desenvolvido por Jorge Afonso Rabelo de Araujo.

Se tiver dúvidas ou quiser entrar em contato use os canais disponíveis no GitHub.
