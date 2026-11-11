/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package saberup.persistencia;

import java.io.IOException;
import java.util.List;

import saberup.excecao.FormatoInvalidoException;
import saberup.model.Questao;

/**
 * Testa a leitura das questões do arquivo CSV.
 *
 * @author Laysa
 */
public class TesteQuestaoRepository {

    public static void main(String[] args) {

        QuestaoRepository repositorio = new QuestaoRepository();

        try {
            List<Questao> questoes =
                    repositorio.carregar("dados/questoes.csv");

            System.out.println("Questões carregadas: "
                    + questoes.size());

            for (Questao questao : questoes) {
                System.out.println(questao.paraCsv());
            }

        } catch (IOException | FormatoInvalidoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}

