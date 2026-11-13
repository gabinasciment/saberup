/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package saberup.servico;
    import java.util.List;
    import saberup.model.Questao;
    import java.util.ArrayList;
    import java.util.Collections;

/**
 *
 * @author erika
 */

public class Quiz {

    private List<Questao> questoes;
    private int indiceAtual;
    private int pontos;
    private boolean respondida;


public Quiz(List<Questao> questoes) {
    if (questoes == null || questoes.isEmpty()) {
        throw new IllegalArgumentException(
            "O quiz precisa ter pelo menos uma questão."
        );
    }
    this.questoes = new ArrayList<>(questoes);

    Collections.shuffle(this.questoes);

    this.indiceAtual = 0;
    this.pontos = 0;
    this.respondida = false;
}
    public Questao questaoAtual() {
        return questoes.get(indiceAtual);
    }


public boolean responder(String resposta) {
    if (respondida) {
        throw new IllegalStateException(
            "Esta questão já foi respondida."
        );
    }
    Questao questao = questaoAtual();

    boolean acertou = questao.corrigir(resposta);
    if (acertou) {
        pontos = pontos + questao.getPontos();
    }
    respondida = true;
    return acertou;
}

public boolean temProxima() {
    return indiceAtual < questoes.size() - 1;
}

public void avancar() {
    if (!respondida) {
        throw new IllegalStateException(
            "Responda à questão antes de avançar."
        );
    }
    if (temProxima()) {
        indiceAtual++;
        respondida = false;
    }
}


public int getPontos() {
    return pontos;
}

public int getPontosPossiveis() {

    int total = 0;

    for (Questao questao : questoes) {
        total += questao.getPontos();
    }

    return total;
}


}

   