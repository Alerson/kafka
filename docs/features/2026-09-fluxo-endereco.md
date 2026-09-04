# Feature: Fluxo Kafka para Endereco

## Status
`Concluído`

## Contexto / Motivação
Hoje o sistema processa apenas `Pessoa` e `Produto` via Kafka com serialização Avro. Este
arquivo nasceu como **teste do fluxo de desenvolvimento assistido** descrito em
`docs/workflow-features-com-claude.md` (Cenário B: análise do código existente → proposta →
registro em `.md` → implementação), usando `Endereco` como feature de exemplo real por ser
estruturalmente idêntica ao que já existe (baixo risco, valida o processo ponta a ponta).

## Objetivo
Adicionar um novo fluxo `Endereco` (producer → tópico Kafka → consumer), serializado via Avro,
seguindo exatamente o mesmo padrão de producer/consumer/converter/validação já usado por
`Pessoa` e `Produto`.

## Critérios de aceite
- [x] Schema Avro `endereco.avsc` criado em `src/main/resources/avro/`, com campos
      `logradouro`, `numero`, `cidade`, `estado`, `cep` (union `["tipo","null"]`, no mesmo
      estilo de `pessoa.avsc`/`produto.avsc`), e classes geradas via `./mvnw generate-sources`
- [x] `ProducerEndereco` publica mensagens no tópico configurado em `application.properties`,
      seguindo a estrutura de `ProducerPessoa`
- [x] `ConsumerEndereco` consome com `@KafkaListener` + `@KafkaHandler` + acknowledgment manual,
      seguindo a estrutura de `ConsumerPessoa`, com `errorHandler = "customKafkaErrorHandler"`
- [x] `EnderecoConverter` converte Avro `Endereco` → `EnderecoDTO` usando
      `ConversionUtils.valueOrDefault` (padrão **Adapter**, ver
      `.claude/skills/design-patterns/SKILL.md`)
- [x] `EnderecoDTO` com Bean Validation: `@NotBlank` em `logradouro`, `cidade`, `estado`, `cep`;
      `numero` como `Integer` com `@NotNull` + `@Positive` (ver decisão em "Notas técnicas")
- [x] Erros de conversão/validação tratados por `CustomKafkaErrorHandler` (mensagem é
      acknowledged, não reprocessada) — nenhuma classe nova de error handler foi criada
- [x] `application.properties` recebe `spring.kafka.consumer.topic.endereco=my-kafka-topic-endereco`
- [x] `KafkaApplication` (`CommandLineRunner` de demonstração) passa a enviar também uma
      mensagem `Endereco` de exemplo, no mesmo padrão usado para `Pessoa`/`Produto`
- [x] Testes unitários cobrindo producer, consumer e converter, espelhando os testes existentes:
      `ProducerEnderecoTest`, `ConsumerEnderecoTest`, `EnderecoConverterTest`
- [x] Cobertura JaCoCo gerada (`./mvnw jacoco:report`) — build completo com 19 testes passando (16 existentes + 3 novos), 0 falhas

## Escopo / arquivos afetados

**Novos:**
- `src/main/resources/avro/endereco.avsc`
- `producer/ProducerEndereco.java`
- `consumer/ConsumerEndereco.java`
- `converter/EnderecoConverter.java`
- `dto/EnderecoDTO.java`
- `src/test/java/.../producer/ProducerEnderecoTest.java`
- `src/test/java/.../consumer/ConsumerEnderecoTest.java`
- `src/test/java/.../converter/EnderecoConverterTest.java`

**Alterados:**
- `application.properties` — nova propriedade de tópico
- `KafkaApplication.java` — novo envio de exemplo no `CommandLineRunner`

## Fora de escopo
- Persistência em banco de dados — apenas o fluxo de mensageria (Kafka), igual a `Pessoa`/`Produto`.
- Qualquer alteração no fluxo SQS (`SQSMessageListener`) — é independente do fluxo Kafka.
- `docker-compose.yml` — nenhuma alteração esperada, reaproveita o broker Kafka já existente.

## Padrões de design a considerar
Referência: `.claude/skills/design-patterns/SKILL.md`.

- **Adapter** — em `EnderecoConverter`, convertendo o objeto Avro gerado para `EnderecoDTO`,
  igual a `PessoaConverter`/`ProdutoConverter`.
- **Template Method** — **aplicado** em `consumer/AbstractKafkaConsumer<T, D>`: o `/code-review`
  na branch confirmou a duplicação exata do esqueleto nos 3 consumers (regra dos três), então o
  refactor foi extraído (ver "Notas técnicas").

## Notas técnicas / decisões
- **[Pós-review] Template Method extraído.** O `/code-review` apontou que `ConsumerPessoa`,
  `ConsumerProduto` e `ConsumerEndereco` duplicavam exatamente o mesmo esqueleto
  (`toDto` → `ValidationUtils.validate` → log → `ack.acknowledge()`), cruzando a regra dos três.
  Foi criada `consumer/AbstractKafkaConsumer<T, D>` com o método template `processMessage`; cada
  consumer concreto agora só implementa `toDto(T)`. Comportamento observável não mudou (mesmos
  testes de consumer passam sem alteração).
- **[Pós-review] Validação de `numero` corrigida.** A validação original (`@NotNull` +
  `@Positive`) descartava silenciosamente endereços sem numeração ("S/N", comuns no Brasil) ou
  com número `0`, via `CustomKafkaErrorHandler` (sem retry/DLQ). Ajustado para `@PositiveOrZero`
  sem `@NotNull`: `numero` agora é opcional (`null` = sem numeração) e aceita `0`; apenas valores
  negativos são rejeitados. Cobrido por novos casos em `ValidationUtilsTest`.

## Referências
- Processo de geração deste arquivo: `docs/workflow-features-com-claude.md`
- Template usado como base: `docs/features/EXEMPLO-feature-template.md`
- Issue/ticket relacionado: (nenhum — feature de teste do fluxo)
- PR: https://github.com/Alerson/kafka/pull/1 (implementação inicial)
- PR: https://github.com/Alerson/kafka/pull/2 (fix pós-code-review: validação de `numero` + Template Method)
