---
name: design-patterns
description: Use SEMPRE ao projetar ou revisar código neste projeto (novos consumers/producers, converters, validações, error handlers) — cataloga os 23 padrões GoF (Criacionais, Estruturais, Comportamentais) com quando aplicar, prós/contras e como cada um mapeia para as classes existentes deste repositório Kafka/Spring Boot. Fonte: refactoring.guru/design-patterns/java.
---

# Design Patterns — guia de referência do projeto

Esta skill é a referência padrão de design patterns (GoF) a ser consultada **durante o
desenvolvimento** deste projeto — ao criar uma nova classe, decidir como estruturar uma
solução, ou revisar se o código está seguindo um padrão conhecido em vez de reinventar uma
solução ad-hoc. Conteúdo extraído e resumido de https://refactoring.guru/design-patterns/java.

**Regra prática:** não force um padrão onde não há problema real (over-engineering é pior que
não usar padrão nenhum). Use esta lista para reconhecer o problema e escolher a solução já
testada pela comunidade — não para aplicar padrões por aplicar.

---

## Como usar esta skill

1. Antes de implementar algo nas camadas `consumer/`, `producer/`, `converter/`, `dto/`,
   `exception/`, `sqs/` ou `utils/`, verifique se o problema que você está resolvendo se
   encaixa em algum padrão abaixo.
2. Prefira os padrões já em uso implícito no projeto (seção "Mapeamento para este projeto")
   para manter consistência de estilo entre os módulos existentes e o novo código.
3. Ao introduzir um padrão novo no projeto, documente a decisão no PR/commit e, se for um
   padrão que deve se repetir, considere transformar isso em uma nova skill dedicada
   (ex.: `add-kafka-consumer`) em vez de descrever tudo aqui.

---

## 1. Padrões Criacionais (Creational)

Tratam de como objetos são instanciados, escondendo a lógica de criação da lógica de uso.

### Abstract Factory
- **Intenção:** produzir famílias de objetos relacionados sem especificar suas classes concretas.
- **Quando usar:** quando você precisa criar variantes de um mesmo grupo de objetos (ex.: diferentes serializadores/deserializadores por tipo de mensagem) sem acoplar o código cliente às classes concretas.
- **Prós:** garante que objetos de uma mesma família sejam compatíveis entre si; evita acoplamento forte.
- **Contras:** aumenta a complexidade com várias interfaces/classes novas.

### Builder
- **Intenção:** construir objetos complexos passo a passo, permitindo representações diferentes com o mesmo código de construção.
- **Quando usar:** quando um construtor teria muitos parâmetros opcionais ("telescoping constructor"), ou quando a montagem de um objeto (ex.: um DTO ou uma mensagem Avro) envolve várias etapas.
- **Prós:** isola a lógica de construção complexa da lógica de negócio (SRP); permite reaproveitar passos de construção para variações do produto.
- **Contras:** adiciona classes extras; não compensa para objetos simples.
- **Nota Java:** alternativa mais limpa que construtores sobrecarregados; combina bem com `record`/DTOs imutáveis.

### Factory Method
- **Intenção:** fornece uma interface para criar objetos numa superclasse, mas deixa as subclasses decidirem qual classe concreta instanciar.
- **Quando usar:** quando o tipo exato de objeto a criar não é conhecido de antemão (ex.: escolher a implementação de um converter/handler baseado no tipo de mensagem recebida), ou para reaproveitar recursos custosos (conexões, clients) em vez de recriá-los.
- **Prós:** desacopla criador de produto concreto; centraliza a criação; segue SRP e Open/Closed.
- **Contras:** exige subclasses/implementações extras.

### Prototype
- **Intenção:** copiar objetos existentes sem depender de suas classes concretas.
- **Quando usar:** quando clonar um objeto (ex.: um payload Avro/DTO já populado) é mais barato ou seguro do que reconstruí-lo do zero.
- **Prós:** evita reinicializar código de construção; reduz subclasses.
- **Contras:** clonagem de objetos com referências circulares pode ser complicada.

### Singleton
- **Intenção:** garantir que uma classe tenha uma única instância, com ponto de acesso global.
- **Quando usar:** para recursos compartilhados (conexão, cache, configuração). **Neste projeto, o Spring já resolve isso via `@Component`/`@Service`/`@Bean` com escopo singleton — não implemente Singleton manualmente**; deixe o container gerenciar o ciclo de vida.
- **Contras:** viola SRP (resolve "instância única" + "acesso global" ao mesmo tempo); dificulta testes unitários (mocks); cuidado com concorrência se implementado à mão.

---

## 2. Padrões Estruturais (Structural)

Tratam de como compor classes/objetos em estruturas maiores, mantendo-as flexíveis e eficientes.

### Adapter
- **Intenção:** permitir que objetos com interfaces incompatíveis colaborem, atuando como tradutor.
- **Quando usar:** para integrar uma classe existente (lib de terceiros, formato legado) cuja interface não bate com o que seu código espera — **é exatamente o papel dos `converter/` deste projeto**, que adaptam objetos Avro gerados (`Pessoa`, `Produto`) para os DTOs internos (`PessoaDTO`, `ProdutoDTO`).
- **Prós:** isola a lógica de conversão (SRP); permite adicionar novos adapters sem quebrar clientes existentes (Open/Closed).
- **Contras:** mais uma camada/classe; às vezes é mais simples alterar a classe de origem diretamente.

### Bridge
- **Intenção:** separar uma abstração da sua implementação para que ambas variem independentemente.
- **Quando usar:** quando uma classe tem múltiplas dimensões de variação (ex.: tipo de mensagem × canal de transporte) que, combinadas via herança, explodiriam em subclasses.

### Composite
- **Intenção:** compor objetos em estruturas de árvore e tratá-los uniformemente (objeto único vs. grupo de objetos).
- **Quando usar:** quando o domínio é naturalmente hierárquico (ex.: validações compostas, regras aninhadas).

### Decorator
- **Intenção:** anexar novos comportamentos a um objeto dinamicamente, envolvendo-o em wrappers, sem alterar sua classe.
- **Quando usar:** para adicionar responsabilidades opcionais e combináveis (ex.: camadas de log, retry, métricas em torno de um listener/handler) sem criar uma subclasse para cada combinação, ou quando a classe é `final`.
- **Prós:** comportamento extensível em runtime; respeita SRP (uma responsabilidade por decorator).
- **Contras:** ordem dos decorators importa; pilha de wrappers pode ficar difícil de depurar.

### Facade
- **Intenção:** fornecer uma interface simplificada para um subsistema complexo.
- **Quando usar:** ao integrar com frameworks/bibliotecas complexas (ex.: Spring Kafka, AWS SDK/LocalStack) — expor uma classe de fachada simples em vez de espalhar chamadas de baixo nível pelo código de negócio. Útil para isolar o impacto de upgrades de dependências (ex.: troca de versão do Spring Cloud AWS).
- **Contras:** cuidado para a fachada não virar um "god object" acoplado a tudo.

### Flyweight
- **Intenção:** compartilhar estado comum entre muitos objetos para economizar memória.
- **Quando usar:** alto volume de objetos pequenos e repetitivos (ex.: processar milhões de mensagens Kafka reaproveitando metadados comuns).

### Proxy
- **Intenção:** fornecer um substituto que controla o acesso ao objeto real (lazy loading, controle de acesso, cache, logging).
- **Quando usar:** para interceptar chamadas a um recurso custoso ou sensível sem o cliente perceber. O próprio Spring usa proxies extensivamente (AOP, `@Transactional`).

---

## 3. Padrões Comportamentais (Behavioral)

Tratam de comunicação e atribuição de responsabilidades entre objetos.

### Chain of Responsibility
- **Intenção:** passar uma requisição por uma cadeia de handlers; cada um decide processar ou repassar adiante.
- **Quando usar:** quando os tipos/sequência de validação ou tratamento não são conhecidos de antemão, ou quando há múltiplas etapas de verificação (ex.: pipeline de validação de `PessoaDTO`/`ProdutoDTO` antes de acionar o `CustomKafkaErrorHandler`). Útil em pipelines de validação e tratamento de erros.
- **Contras:** uma requisição pode acabar não sendo tratada por nenhum handler — sempre trate o caso "ninguém pegou".

### Command
- **Intenção:** transformar uma requisição em um objeto autônomo, permitindo parametrizar, enfileirar ou desfazer ações.
- **Quando usar:** quando uma ação (ex.: reprocessar uma mensagem, disparar um producer) precisa ser tratada como objeto de primeira classe — passada, enfileirada ou logada.

### Iterator
- **Intenção:** percorrer elementos de uma coleção sem expor sua representação interna.
- **Quando usar:** raramente necessário à mão em Java (já coberto por `Iterable`/streams), mas relevante ao expor uma estrutura de dados customizada.

### Mediator
- **Intenção:** reduzir dependências caóticas entre objetos, centralizando a comunicação.
- **Quando usar:** quando vários componentes (ex.: múltiplos listeners/consumers) precisam coordenar-se sem se conhecerem diretamente.

### Memento
- **Intenção:** salvar e restaurar o estado anterior de um objeto sem expor os detalhes internos.
- **Quando usar:** cenários de "desfazer"/replay de estado — pouco comum neste projeto, mas relevante se for implementado reprocessamento com rollback de estado.

### Observer
- **Intenção:** definir um mecanismo de assinatura para notificar múltiplos objetos sobre eventos de outro objeto.
- **Quando usar:** quando várias partes do sistema precisam reagir a uma mudança de estado sem acoplamento direto ao publicador. **É o modelo conceitual do próprio Kafka (pub/sub)**; dentro da aplicação, considere Spring `ApplicationEventPublisher`/`@EventListener` para desacoplar efeitos colaterais (ex.: métricas, auditoria) do fluxo principal do consumer.
- **Contras:** ordem de notificação dos observers não é garantida — não dependa de ordem.

### State
- **Intenção:** permitir que um objeto altere seu comportamento quando seu estado interno muda.
- **Quando usar:** quando uma entidade tem um ciclo de vida com estados bem definidos e comportamento distinto por estado (ex.: status de processamento de uma mensagem: recebida → validada → processada → erro).

### Strategy
- **Intenção:** definir uma família de algoritmos, encapsular cada um em uma classe e torná-los intercambiáveis.
- **Quando usar:** quando há múltiplas formas de realizar a mesma tarefa (ex.: diferentes regras de validação por tipo de DTO, ou diferentes estratégias de conversão em `ConversionUtils`/`ValidationUtils`) e você quer eliminar cadeias de `if/else` ou `switch` por tipo. Evita conflitos de merge quando múltiplas pessoas adicionam algoritmos novos na mesma classe.
- **Contras:** não compensa para 1-2 algoritmos simples e estáveis; em Java moderno, uma lambda/`Function` pode substituir uma classe de estratégia inteira.

### Template Method
- **Intenção:** definir o esqueleto de um algoritmo na superclasse, deixando subclasses sobrescreverem passos específicos.
- **Quando usar:** quando múltiplos consumers/producers (ex.: `ConsumerPessoa` e `ConsumerProduto`) seguem exatamente o mesmo fluxo (receber → converter → validar → ack/erro) variando apenas o tipo de dado — extrair esse esqueleto evita duplicação entre as classes.
- **Contras:** pode violar Liskov se subclasses "pularem" passos do template; muitos passos tornam a manutenção mais difícil.

### Visitor
- **Intenção:** separar algoritmos dos objetos sobre os quais operam, permitindo adicionar operações novas sem alterar as classes dos objetos.
- **Quando usar:** quando é preciso executar operações variadas sobre uma hierarquia de classes estável (ex.: diferentes exportações/transformações sobre os modelos Avro gerados) sem poluir essas classes geradas.

---

## Mapeamento rápido para este projeto

| Camada / classe existente | Padrão já aplicado (ou recomendado) |
|---|---|
| `converter/PessoaConverter`, `converter/ProdutoConverter` | **Adapter** (Avro → DTO) |
| `exception/errorhandler/CustomKafkaErrorHandler` | **Chain of Responsibility** (ponto único de tratamento; pode evoluir para cadeia de handlers por tipo de erro) |
| `utils/ValidationUtils`, `utils/ConversionUtils` | Candidatos a **Strategy** se surgirem múltiplas variações de validação/conversão por tipo |
| `consumer/ConsumerPessoa`, `consumer/ConsumerProduto` | Fluxo repetido → candidato a **Template Method** se um terceiro consumer for adicionado |
| Beans gerenciados pelo Spring (`@Component`, `@Service`) | **Singleton** (gerenciado pelo container — não implementar manualmente) |
| Integração com Spring Kafka / AWS SDK (LocalStack) | Ponto natural para uma **Facade**, se a integração crescer em complexidade |

## Quando **não** aplicar um padrão

- Se a solução direta (um método, um `if`) resolve com clareza, não introduza uma hierarquia de classes só para "seguir um padrão".
- Padrões criacionais (Factory, Builder, Singleton manual) raramente são necessários nas camadas gerenciadas pelo Spring — o container já resolve ciclo de vida e injeção de dependência.
- Prefira o padrão mais simples que resolve o problema atual; refatore para um padrão mais robusto quando a duplicação/complexidade real aparecer (regra do "rule of three").

---

**Fonte:** https://refactoring.guru/design-patterns/java (catálogo completo com exemplos de código Java, diagramas UML e explicações aprofundadas de cada padrão acima).
