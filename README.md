# Talent Guard API

Este projeto é uma API RESTful desenvolvida com **Quarkus**, para fornecer inteligência ao setor de Recursos Humanos focada na retenção de talentos.
A API é responsável pelo gerenciamento de cargos, funcionários, dados de mercado (benchmarks) e pela realização de análises salariais estratégicas para identificar riscos de perda de talentos.

## 🚀 Funcionalidades Principais (Endpoints)

A API expõe os seguintes recursos REST, cada um com as operações CRUD (Criação, Leitura, Atualização, Exclusão) e regras de negócio específicas (BO - Business Objects):

| Recurso | Endpoint Base | Descrição |
| :--- | :--- | :--- |
| **Cargos (Roles)** | `/role` | Gerencia a estrutura hierárquica da empresa. Permite definir níveis de senioridade obrigatórios (`JUNIOR`, `PLENO`, `SENIOR`). |
| **Funcionários** | `/employee` | Gerencia as informações cadastrais, departamento e salário atual dos colaboradores. Valida a não redução salarial em atualizações. |
| **Benchmarks** | `/benchmark` | Gerencia os dados de mercado ("régua" salarial). Armazena Piso, Média e Teto de mercado para um cargo específico. |
| **Análise Salarial** | `/analysis` | Realiza o cruzamento entre o salário do funcionário e o benchmark. Classifica automaticamente o risco em: `BELOW_FLOOR` (Abaixo do Piso), `ON_TARGET` (Na Meta) ou `ABOVE_CEILING` (Acima do Teto). |

## ⚙️ Configuração Técnica

### Tecnologias

  * **Framework:** Quarkus
  * **Linguagem:** Java 17
  * **Build Tool:** Apache Maven
  * **APIs Web:** Quarkus REST (JAX-RS) e Jackson para serialização JSON.
  * **Validação:** Bean Validation (via `quarkus-hibernate-validator`) para anotações nos TOs (ex: `@NotNull`, `@Positive`, `@Past`).

### Banco de Dados

A camada de persistência utiliza **JDBC** com um design pattern **DAO** (Data Access Object) e o driver **Oracle** (`ojdbc11`).

A conexão é estabelecida por meio de variáveis de ambiente, que devem ser configuradas antes da execução:

  * `DB_URL`
  * `DB_USER`
  * `DB_PASSWORD`

### Configuração CORS

O projeto inclui um `CorsFilter` que habilita as seguintes configurações para facilitar o desenvolvimento e integração com front-end:

  * **Access-Control-Allow-Origin:** `*` (Permite todas as origens)
  * **Access-Control-Allow-Methods:** `GET`, `POST`, `PUT`, `DELETE`, `OPTIONS`, `HEAD`

## ▶️ Como Executar a Aplicação

### Modo Desenvolvimento

Para iniciar a aplicação em modo de desenvolvimento com *live coding* (recarregamento automático em mudanças de código):

```shell
./mvnw quarkus:dev
```
