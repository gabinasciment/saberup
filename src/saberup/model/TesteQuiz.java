/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package saberup.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import saberup.servico.Quiz;
/**
 *
 * @author erika
 */
public class TesteQuiz {
     public static void main(String[] args) {

        // Criando a lista de questões
        List<Questao> questoes = new ArrayList<>();

        // Questão objetiva: vale 10 pontos
        questoes.add(new QuestaoObjetiva(
                1,
                "Qual palavra-chave usamos para herança em Java?",
                10,
                Arrays.asList("extends", "import", "private", "return"),
                'A'
        ));

        // Questão verdadeiro ou falso: vale 5 pontos
        questoes.add(new QuestaoVerdadeiroFalso(
                2,
                "Uma classe abstrata pode ser instanciada diretamente?",
                5,
                false
        ));

        // Questão numérica: vale 15 pontos
        questoes.add(new QuestaoNumerica(
                3,
                "Quanto é 20 + 22,5?",
                15,
                42.5,
                0.5
        ));

        // Criando a partida
        Quiz quiz = new Quiz(questoes);

        System.out.println("=== INICIO DO QUIZ ===");

        // Percorrendo as questões
        while (true) {
            Questao atual = quiz.questaoAtual();

            System.out.println();
            System.out.println(atual.getEnunciado());

            String resposta;

            // Identificando o tipo de questão
            if (atual instanceof QuestaoObjetiva) {
                resposta = "B"; // Resposta incorreta
            } 
            else if (atual instanceof QuestaoVerdadeiroFalso) {
                resposta = "F"; // Resposta correta
            } 
            else if (atual instanceof QuestaoNumerica) {
                resposta = "42,5"; // Resposta correta
            } 
            else {
                throw new IllegalStateException(
                        "Tipo de questão desconhecido."
                );
            }

            boolean acertou = quiz.responder(resposta);

            System.out.println("Resposta: " + resposta);
            System.out.println("Acertou? " + acertou);
            System.out.println("Pontos acumulados: " + quiz.getPontos());

            // Testando bloqueio de resposta duplicada
            try {
                quiz.responder(resposta);
            } catch (IllegalStateException e) {
                System.out.println("Bloqueio de resposta duplicada: OK");
            }

            // Verificando se existe outra questão
            if (quiz.temProxima()) {
                quiz.avancar();
            } else {
                break;
            }
        }

        // Resultado final
        System.out.println();
        System.out.println("=== RESULTADO FINAL ===");
        System.out.println("Pontos conquistados: " + quiz.getPontos());
        System.out.println("Total possivel: " + quiz.getPontosPossiveis());

        
    System.out.println();
    System.out.println("=== TESTE DE LISTA VAZIA ===");

    try {
        Quiz quizVazio = new Quiz(new ArrayList<>());
    } catch (IllegalArgumentException e) {
        System.out.println("Bloqueio de quiz vazio: OK");
    }

    }
}
    

