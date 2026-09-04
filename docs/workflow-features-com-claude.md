# Como interagir com o Claude para desenvolver novas features

> Este arquivo é uma **base de conhecimento para o usuário** (não é uma skill nem uma feature).
> Ele documenta o fluxo recomendado de interação com o Claude Code neste projeto, para não
> depender da memória de uma conversa específica. Sempre que tiver dúvida "como eu devo pedir
> isso pro Claude?", volte aqui.

## Visão geral das 3 peças que trabalham juntas

| Peça | Onde fica | Papel | Quem usa |
|---|---|---|---|
| `CLAUDE.md` | raiz do projeto | Visão geral do projeto, arquitetura, comandos | Claude (carregado sempre) |
| `.claude/skills/design-patterns/SKILL.md` | `.claude/skills/` | Padrão de **como** implementar (design patterns) | Claude (carregado sob demanda) |
| `docs/features/*.md` | `docs/features/` | Especificação do **o que** implementar, feature a feature | Você escreve ou pede pro Claude gerar; Claude lê para implementar |

O arquivo que você está lendo agora descreve o **processo** de como essas três peças se
conectam durante o desenvolvimento de uma feature nova.

---

## Os dois cenários de uso

### Cenário A — Você já sabe o que quer

Você mesmo preenche uma cópia de `docs/features/EXEMPLO-feature-template.md` com a
especificação da feature e pede diretamente a implementação.

**Prompt de exemplo:**
```
Implemente a feature descrita em docs/features/2026-09-api-pessoa.md,
seguindo os padrões da skill design-patterns.
```

Use este caminho quando o escopo já está claro na sua cabeça e você só quer documentá-lo antes
de pedir o código (bom para rastreabilidade e para eu não "inventar" requisitos).

### Cenário B — Você tem uma ideia, mas não sabe como estruturar

Este é o caso descrito na conversa que originou este arquivo: você quer, por exemplo, "criar uma
API", mas não sabe como isso se encaixa na arquitetura atual do projeto. Nesse caso, o Claude
analisa o código primeiro e **gera** o `.md` da feature como resultado dessa análise — depois
você revisa antes de qualquer linha de código de produção ser escrita.

Use este caminho sempre que:
- Você não tem certeza de qual camada/pacote a feature deve tocar;
- Quer que a proposta já nasça alinhada às convenções existentes no repo;
- Quer um artefato documentado (o `.md`) que registre a decisão, em vez de só uma conversa que
  se perde no histórico do chat.

---

## Passo a passo do Cenário B (análise → plano → implementação)

### Passo 1 — Descreva a ideia, sem se preocupar com a estrutura técnica

```
Quero criar uma API REST para consultar Pessoa por CPF, mas ainda não sei
como estruturar isso dentro do projeto. Analise o código existente e me
proponha uma abordagem antes de implementar qualquer coisa.
```

Não é necessário saber nomes de classes, camadas ou padrões — essa é justamente a parte que o
Claude resolve analisando `CLAUDE.md`, a estrutura de pacotes (`consumer/`, `producer/`,
`converter/`, `dto/`, `exception/`, `sqs/`, `utils/`) e o `pom.xml`.

### Passo 2 — Deixe o Claude investigar e propor, sem implementar ainda

Nessa etapa o Claude deve:
- Ler a arquitetura atual (`CLAUDE.md` + código relevante);
- Consultar a skill `design-patterns` para identificar quais padrões (Adapter, Template Method,
  Strategy etc.) fazem sentido para o problema;
- Apresentar uma proposta de abordagem (pacotes/classes novas, dependências a adicionar,
  impacto em arquivos existentes) **em texto**, sem editar nada ainda.

Se quiser forçar esse comportamento de forma explícita, você pode pedir para o Claude entrar em
**Plan Mode** — um modo do Claude Code em que ele só pesquisa e propõe um plano, pedindo sua
aprovação antes de escrever qualquer arquivo:

```
Entre em plan mode e monte o plano de implementação para essa API de consulta de Pessoa.
```

### Passo 3 — Revise e ajuste a proposta

Trate essa etapa como uma revisão de design normal: peça para trocar uma abordagem, questionar
uma decisão, simplificar escopo, etc. Nada foi implementado ainda — é barato mudar de ideia
aqui.

### Passo 4 — Peça para registrar o plano aprovado como arquivo de feature

Só depois de alinhado, peça a persistência do plano no formato padrão:

```
Gere o arquivo em docs/features/ seguindo o template EXEMPLO-feature-template.md,
com o plano que acabamos de definir. Não implemente ainda.
```

O Claude deve nomear o arquivo como `AAAA-MM-descricao-curta.md` (ex.:
`2026-09-api-consulta-pessoa.md`) e preencher todas as seções do template (Contexto, Objetivo,
Critérios de aceite, Escopo/arquivos afetados, Fora de escopo, Padrões de design a considerar,
Notas técnicas).

### Passo 5 — Revise o arquivo gerado

Abra o `.md` gerado e ajuste manualmente o que quiser (remover escopo, adicionar critério de
aceite, corrigir uma decisão técnica) antes de pedir a implementação. É um artefato de texto
comum — pode ser editado como qualquer outro markdown.

### Passo 6 — Peça a implementação com base no arquivo já validado

```
Implemente a feature descrita em docs/features/2026-09-api-consulta-pessoa.md,
seguindo os padrões definidos na skill design-patterns.
```

Nesta etapa o Claude:
- Lê o `.md` da feature (o "o quê");
- Consulta a skill `design-patterns` automaticamente quando relevante (o "como");
- Implementa seguindo as convenções já mapeadas na tabela "Mapeamento rápido para este projeto"
  dentro da skill.

### Passo 7 — Feche o ciclo

Depois de implementado: rode os testes (`./mvnw test`), confira a cobertura (`./mvnw jacoco:report`),
e marque o `Status` do arquivo de feature como `Concluído`. Isso mantém `docs/features/` como um
histórico confiável do que foi de fato construído, não só planejado.

---

## Resumo do fluxo

```
Ideia solta (você)
      │
      ▼
"Analise o código e proponha uma abordagem" ──► Claude investiga (CLAUDE.md + código + skill design-patterns)
      │
      ▼
Discussão/ajuste da proposta (você revisa, sem código ainda)
      │
      ▼
"Gere o arquivo de feature com esse plano" ──► Claude cria docs/features/AAAA-MM-nome.md
      │
      ▼
Você revisa/edita o .md manualmente
      │
      ▼
"Implemente com base nesse arquivo" ──► Claude implementa seguindo o .md + a skill design-patterns
      │
      ▼
Testes + cobertura + Status: Concluído
```

## Dicas rápidas

- **Nunca pule direto para "implemente"** quando a ideia ainda é vaga — peça a análise/plano
  primeiro (Passo 1–3). É muito mais barato corrigir um plano em texto do que código já escrito.
- **Referencie o arquivo pelo caminho**, não só pelo nome da feature em português — isso evita
  ambiguidade quando houver múltiplos arquivos em `docs/features/`.
- Se uma abordagem se repetir em várias features (ex.: sempre criar um novo par
  producer/consumer seguindo o mesmo esqueleto), considere transformar esse padrão em uma nova
  skill dedicada em `.claude/skills/`, em vez de repetir a explicação em cada `.md` de feature.
- Este arquivo descreve o **processo**; o `.claude/skills/design-patterns/SKILL.md` descreve os
  **padrões técnicos**; `docs/features/EXEMPLO-feature-template.md` é o **molde** a ser copiado
  a cada nova feature.
