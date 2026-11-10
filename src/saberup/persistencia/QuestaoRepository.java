/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package saberup.persistencia;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import saberup.excecao.FormatoInvalidoException;
import saberup.model.Questao;
import saberup.model.QuestaoNumerica;
import saberup.model.QuestaoObjetiva;
import saberup.model.QuestaoVerdadeiroFalso;

/**
 * Responsável por carregar as questões do arquivo CSV.
 *
 * @author Laysa
 */
public class QuestaoRepository {

    public List<Questao> carregar(String caminho)
            throws IOException, FormatoInvalidoException {

        List<Questao> questoes = new ArrayList<>();

        List<String> linhas = Files.readAllLines(Path.of(caminho));

        int numeroLinha = 0;

        for (String linha : linhas) {
            numeroLinha++;

            if (linha.trim().isEmpty()) {
                continue;
            }

            String[] partes = linha.split(";", -1);

            try {
                String tipo = partes[0].trim();

                if (partes.length < 3) {
                    throw new IllegalArgumentException(
                            "Campos insuficientes.");
                }

                String enunciado = partes[1].trim();
                int pontos = Integer.parseInt(partes[2].trim());

                if (enunciado.isEmpty() || pontos <= 0) {
                    throw new IllegalArgumentException(
                            "Enunciado ou pontuação inválida.");
                }

                int id = questoes.size() + 1;

                switch (tipo) {

                    case "OBJ":
                        if (partes.length != 5) {
                            throw new IllegalArgumentException(
                                    "Questão objetiva deve ter 5 campos.");
                        }

                        List<String> alternativas = Arrays.asList(
                                partes[3].split("\\|", -1)
                        );

                        if (alternativas.size() != 4) {
                            throw new IllegalArgumentException(
                                    "A questão deve ter 4 alternativas.");
                        }

                        for (String alternativa : alternativas) {
                            if (alternativa.trim().isEmpty()) {
                                throw new IllegalArgumentException(
                                        "Alternativa vazia.");
                            }
                        }

                        String respostaObjetiva = partes[4].trim()
                                .toUpperCase();

                        if (!respostaObjetiva.matches("[ABCD]")) {
                            throw new IllegalArgumentException(
                                    "Gabarito objetivo inválido.");
                        }

                        questoes.add(new QuestaoObjetiva(
                                id,
                                enunciado,
                                pontos,
                                alternativas,
                                respostaObjetiva.charAt(0)
                        ));
                        break;

                    case "VF":
                        if (partes.length != 4) {
                            throw new IllegalArgumentException(
                                    "Questão VF deve ter 4 campos.");
                        }

                        String respostaVF = partes[3].trim()
                                .toUpperCase();

                        if (!respostaVF.equals("V")
                                && !respostaVF.equals("F")) {
                            throw new IllegalArgumentException(
                                    "Gabarito VF inválido.");
                        }

                        boolean gabaritoVF = respostaVF.equals("V");

                        questoes.add(new QuestaoVerdadeiroFalso(
                                id,
                                enunciado,
                                pontos,
                                gabaritoVF
                        ));
                        break;

                    case "NUM":
                        if (partes.length != 5) {
                            throw new IllegalArgumentException(
                                    "Questão numérica deve ter 5 campos.");
                        }

                        double gabaritoNumerico = Double.parseDouble(
                                partes[3].trim()
                        );

                        double tolerancia = Double.parseDouble(
                                partes[4].trim()
                        );

                        if (!Double.isFinite(gabaritoNumerico)
                                || !Double.isFinite(tolerancia)
                                || tolerancia < 0) {
                            throw new IllegalArgumentException(
                                    "Gabarito ou tolerância inválida.");
                        }

                        questoes.add(new QuestaoNumerica(
                                id,
                                enunciado,
                                pontos,
                                gabaritoNumerico,
                                tolerancia
                        ));
                        break;

                    default:
                        throw new IllegalArgumentException(
                                "Tipo de questão desconhecido: " + tipo);
                }

            } catch (IllegalArgumentException
                    | ArrayIndexOutOfBoundsException e) {

                throw new FormatoInvalidoException(
                        "Erro na linha " + numeroLinha
                        + " do CSV: " + e.getMessage()
                );
            }
        }

        return questoes;
    }
}



   
