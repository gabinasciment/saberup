# SaberUp 

**Aprenda. Jogue. Evolua!**

O SaberUp é um quiz de conhecimentos gerais feito em Java, com interface gráfica em Swing (NetBeans).
O jogador responde perguntas de três tipos, soma pontos e, no fim, informa o nome para entrar no ranking.

## Equipe

| Integrante | Responsabilidade |
|---|---|
| Érika | Modelos (`Questao` e subclasses, `Resultado`) e regras do jogo (`Quiz`) |
| Laysa | Leitura do banco de questões (CSV) e exceções |
| Gabriela | Interface gráfica (telas) |

## Como abrir e rodar

1. Tenha o **JDK 26 ou mais novo** instalado. O projeto está configurado com `javac.source=26`.
2. No NetBeans, vá em **File → Open Project** e escolha esta pasta (`saberup`).
3. Aperte **F6** (Run Project). O programa abre na `TelaMenu`.

O arquivo `dados/questoes.csv` é lido a partir da pasta do projeto. Por isso, rode sempre pelo NetBeans ou com a pasta do projeto como diretório de trabalho.

## Telas

| Tela | O que faz |
|---|---|
| `TelaMenu` | Botões Jogar, Ranking e Sair |
| `TelaQuiz` | Mostra a questão, lê a resposta, corrige e mostra se acertou ou errou |
| `TelaResultado` | Mostra os pontos e os acertos e pede o nome do jogador |
| `TelaRanking` | Tabela com os melhores resultados |

Cada tela é um **JFrame Form**. O visual (cores, fontes e textos) fica na aba **Design**, e o comportamento fica nos métodos dos botões.

## Estrutura do código

```
src/saberup/
├── model/         Questao (abstrata), QuestaoObjetiva, QuestaoVerdadeiroFalso,
│                  QuestaoNumerica e Resultado
├── servico/       Quiz: controla a rodada, a questão atual e os pontos
├── persistencia/  QuestaoRepository: lê as questões do CSV
├── excecao/       FormatoInvalidoException e RespostaInvalidaException
└── ui/            As quatro telas
dados/
└── questoes.csv   Banco de questões
```

Conceitos de orientação a objetos usados:

- **Herança e classe abstrata:** os três tipos de questão estendem `Questao`.
- **Polimorfismo:** o `Quiz` chama `corrigir()` sem saber o tipo da questão, e cada subclasse corrige do seu jeito.
- **Exceções:** um erro no CSV vira uma `FormatoInvalidoException`, e a tela mostra a mensagem ao usuário.

## Banco de questões

O arquivo `dados/questoes.csv` tem uma questão por linha, com os campos separados por `;`:

```
OBJ;enunciado;pontos;alternativa1|alternativa2|alternativa3|alternativa4;letra certa
VF;enunciado;pontos;V ou F
NUM;enunciado;pontos;resposta;tolerância
```

Exemplos:

```
OBJ;Qual palavra-chave faz uma classe herdar de outra?;2;extends|implements|super|this;A
VF;Uma classe abstrata pode ser instanciada com new.;1;F
NUM;Quanto é 7 * 6?;2;42;0
```

Para acrescentar questões, basta incluir novas linhas no arquivo. As telas se ajustam sozinhas à quantidade de questões.


