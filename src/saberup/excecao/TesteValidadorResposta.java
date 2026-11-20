
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package saberup.excecao;

import java.util.Arrays;
import saberup.model.Questao;
import saberup.model.QuestaoObjetiva;
import saberup.model.QuestaoVerdadeiroFalso;
import saberup.model.QuestaoNumerica;

/**
 * Testa a validação das respostas dos jogadores.
 *
 * @author Laysa
 */
public class TesteValidadorResposta {

    public static void main(String[] args) {

        Questao objetiva = new QuestaoObjetiva(
                1, "Quanto é 2 + 2?", 10,
                Arrays.asList("2", "3", "4", "5"), 'C'
        );

        Questao vf = new QuestaoVerdadeiroFalso(
                2, "O Brasil fica na América do Sul?", 10, true
        );

        Questao numerica = new QuestaoNumerica(
                3, "Quanto é 5 + 5?", 10, 10, 0
        );

        // Testa respostas válidas
        testar(objetiva, "C");
        testar(vf, "V");
        testar(numerica, "10");

        // Testa respostas inválidas
        testar(objetiva, "E");
        testar(vf, "X");
        testar(numerica, "abc");
        testar(objetiva, "");
    }

    public static void testar(Questao questao, String resposta) {

        try {
            ValidadorResposta.validar(questao, resposta);

            System.out.println("VALIDA: " + resposta);

        } catch (RespostaInvalidaException e) {

            System.out.println("INVALIDA: " + e.getMessage());
        }
    }
}
