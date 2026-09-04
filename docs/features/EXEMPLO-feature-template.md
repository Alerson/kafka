# Feature: [Nome curto e descritivo da feature]

> Este arquivo é um **exemplo/template**. Copie-o para criar uma nova feature, renomeando com o
> padrão `AAAA-MM-descricao-curta.md` (ex.: `2026-09-endereco-bulk-import.md`) e preenchendo
> cada seção. Não é uma skill — é a especificação do que deve ser construído; a skill
> [[design-patterns]] (`.claude/skills/design-patterns/SKILL.md`) deve ser consultada durante a
> implementação para manter o padrão de código do projeto.

## Status
`Planejado` | `Em andamento` | `Concluído`

## Contexto / Motivação
Descreva o problema de negócio ou técnico que motivou a feature. Por que ela é necessária agora?

_Exemplo: hoje o sistema processa apenas `Pessoa` e `Produto` via Kafka. Precisamos adicionar um
novo fluxo de `Endereco`, seguindo o mesmo padrão de producer/consumer já existente, para
permitir o cadastro de endereços associados a uma Pessoa._

## Objetivo
Uma frase clara descrevendo o resultado esperado.

_Exemplo: permitir a publicação e o consumo de mensagens `Endereco` via Kafka com serialização
Avro, seguindo o mesmo fluxo de validação e tratamento de erro usado por `Pessoa` e `Produto`._

## Critérios de aceite
- [ ] Schema Avro `endereco.avsc` criado em `src/main/resources/avro/` e classes geradas via `./mvnw generate-sources`
- [ ] `ProducerEndereco` publica mensagens no tópico configurado em `application.properties`
- [ ] `ConsumerEndereco` consome com acknowledgment manual, seguindo o padrão de `ConsumerPessoa`
- [ ] `EnderecoConverter` converte Avro → `EnderecoDTO` (padrão **Adapter**, ver skill de design patterns)
- [ ] `EnderecoDTO` com validações Bean Validation (`@NotNull`, `@NotBlank`, etc.)
- [ ] Erros de validação/consumo tratados por `CustomKafkaErrorHandler` (mensagem é acknowledged, não reprocessada)
- [ ] Testes unitários cobrindo producer, consumer e converter (`EnderecoProducerTest`, `EnderecoConsumerTest`, etc.)
- [ ] Cobertura JaCoCo mantida/melhorada (`./mvnw jacoco:report`)

## Escopo / arquivos afetados
- `src/main/resources/avro/endereco.avsc` (novo)
- `producer/ProducerEndereco.java` (novo)
- `consumer/ConsumerEndereco.java` (novo)
- `converter/EnderecoConverter.java` (novo)
- `dto/EnderecoDTO.java` (novo)
- `application.properties` (novo tópico `spring.kafka.consumer.topic.endereco`)
- `docker-compose.yml` — nenhuma alteração esperada (reaproveita broker existente)

## Fora de escopo
Liste explicitamente o que **não** será feito nesta feature, para evitar scope creep.

_Exemplo: não inclui integração com o fluxo de SQS; não inclui endpoint REST para consulta de
endereços — apenas o fluxo assíncrono via Kafka._

## Padrões de design a considerar
Referencie a skill [[design-patterns]] e aponte quais padrões se aplicam a esta feature
especificamente:

- **Adapter** — no `EnderecoConverter`, ao converter o objeto Avro gerado para `EnderecoDTO`.
- **Template Method** — avaliar se o fluxo de `ConsumerEndereco` pode reaproveitar um esqueleto
  comum com `ConsumerPessoa`/`ConsumerProduto` em vez de duplicar a lógica de ack/erro.

## Notas técnicas / decisões
Qualquer decisão de implementação relevante que não seja óbvia pelo código (trade-offs,
alternativas descartadas e por quê).

## Referências
- Issue/ticket relacionado: (link)
- PR: (link, preencher ao abrir)
