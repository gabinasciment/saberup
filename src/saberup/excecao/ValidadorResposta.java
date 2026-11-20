
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package saberup.excecao;

import saberup.model.Questao;
import saberup.model.QuestaoObjetiva;
import saberup.model.QuestaoVerdadeiroFalso;
import saberup.model.QuestaoNumerica;

/**
 * Responsável por validar as respostas dos jogadores
 * antes de realizar a correção das questões.
 *
 * @author Laysa
 */
public class ValidadorResposta {

    public static void validar(Questao questao, String resposta)
            throws RespostaInvalidaException {

        // Verifica se a questão foi informada
        if (questao == null) {
            throw new RespostaInvalidaException(
                    "Questão inválida.");
        }

        // Verifica se a resposta está vazia
        if (resposta == null || resposta.trim().isEmpty()) {
            throw new RespostaInvalidaException(
                    "A resposta não pode estar vazia.");
        }

        String respostaTratada = resposta.trim();

        // Valida respostas de questões objetivas
        if (questao instanceof QuestaoObjetiva) {

            if (!respostaTratada.matches("(?i)[ABCD]")) {
                throw new RespostaInvalidaException(
                        "Digite uma alternativa válida: A, B, C ou D.");
            }

        // Valida respostas de verdadeiro ou falso
        } else if (questao instanceof QuestaoVerdadeiroFalso) {

            if (!respostaTratada.matches("(?i)[VF]")) {
                throw new RespostaInvalidaException(
                        "Digite V para verdadeiro ou F para falso.");
            }

        // Valida respostas de questões numéricas
        } else if (questao instanceof QuestaoNumerica) {

            try {
                double numero = Double.parseDouble(
                        respostaTratada.replace(",", ".")
                );

                if (!Double.isFinite(numero)) {
                    throw new NumberFormatException();
                }

            } catch (NumberFormatException e) {
                throw new RespostaInvalidaException(
                        "Digite uma resposta numérica válida.");
            }

        } else {
            throw new RespostaInvalidaException(
                    "Tipo de questão não reconhecido.");
        }
    }
}
