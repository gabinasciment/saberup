/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package saberup.model;

/**
 *
 * @author erika
 */
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TesteResultado {
     public static void main(String[] args) {

        // Criando os resultados dos jogadores
        Resultado ana = new Resultado(
                "Ana", 80, 100, LocalDate.now());

        Resultado pedro = new Resultado(
                "Pedro", 70, 100, LocalDate.now());

        Resultado maria = new Resultado(
                "Maria", 100, 100, LocalDate.now());

        // Criando uma lista de resultados
        List<Resultado> ranking = new ArrayList<>();

        ranking.add(ana);
        ranking.add(pedro);
        ranking.add(maria);

        // Exibindo a ordem original
        System.out.println("=== ANTES DA ORDENAÇÃO ===");

        for (Resultado resultado : ranking) {
            System.out.println(resultado);
        }

        // Ordenando pela pontuação
        Collections.sort(ranking);

        // Exibindo o ranking ordenado
        System.out.println();
        System.out.println("=== DEPOIS DA ORDENAÇÃO ===");

        for (Resultado resultado : ranking) {
            System.out.println(resultado);
        }
        // TESTE 2 - CONVERSÃO PARA CSV

        System.out.println();
        System.out.println("=== TESTE CSV ===");

        for (Resultado resultado : ranking) {
            System.out.println(resultado.paraCsv());
        }
    }
}
