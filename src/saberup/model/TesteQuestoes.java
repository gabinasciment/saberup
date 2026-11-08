/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package saberup.model;

import java.util.Arrays;
import java.util.List;
/**
 *
 * @author erika
 */
public class TesteQuestoes {
    public static void main(String[] args) {
        List<String> alternativas = Arrays.asList(
            "extends",
            "import",
            "private",
            "return"
        );
        QuestaoObjetiva questao = new QuestaoObjetiva(
            1,
            "Qual palavra-chave usamos para herança em Java?",
            10,
            alternativas,
            'A'
        );
        System.out.println("=== TESTE QUESTÃO OBJETIVA ===");
        System.out.println(questao.getEnunciado());
        System.out.println("Resposta A: " + questao.corrigir("A"));
        System.out.println("Resposta B: " + questao.corrigir("B"));
        System.out.println("Resposta a: " + questao.corrigir("a"));

        System.out.println();
        System.out.println("Questão convertida para CSV:");
        System.out.println(questao.paraCsv());

        System.out.println();
        System.out.println("=== TESTE VERDADEIRO OU FALSO ===");

        QuestaoVerdadeiroFalso questaoVF =
                new QuestaoVerdadeiroFalso(
                    2,
                    "Uma classe abstrata pode ser instanciada diretamente?",
                    5,
                    false
                );

        System.out.println(questaoVF.getEnunciado());

        System.out.println("Resposta V: " + questaoVF.corrigir("V"));
        System.out.println("Resposta F: " + questaoVF.corrigir("F"));
        System.out.println("Resposta f: " + questaoVF.corrigir("f"));

        
        System.out.println();
        System.out.println("Questão V/F convertida para CSV:");
        System.out.println(questaoVF.paraCsv());

        
        // TESTE 3 - QUESTÃO NUMÉRICA

        System.out.println();
        System.out.println("=== TESTE QUESTÃO NUMÉRICA ===");

        QuestaoNumerica questaoNUM =
                new QuestaoNumerica(
                    3,
                    "Quanto é 7 * 6?",
                    10,
                    42.0,
                    0.5
                );

        System.out.println(questaoNUM.getEnunciado());

        System.out.println("Resposta 42: "
                + questaoNUM.corrigir("42"));

        System.out.println("Resposta 41.8: "
                + questaoNUM.corrigir("41.8"));

        System.out.println("Resposta 45: "
                + questaoNUM.corrigir("45"));

        System.out.println("Resposta abc: "
                + questaoNUM.corrigir("abc"));

        System.out.println();
        System.out.println("Questão numérica convertida para CSV:");
        System.out.println(questaoNUM.paraCsv());

    } 

} 