
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package saberup.persistencia;

/**
 *
 * @author Laysa
 */
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TesteRankingRepository {

    public static void main(String[] args) {

        RankingRepository repositorio = new RankingRepository();
        String caminho = "dados/ranking_teste.csv";

        try {
            // Apaga o arquivo de teste anterior para evitar duplicações
            Files.deleteIfExists(Path.of(caminho));

            // Salva três pontuações de exemplo
            repositorio.salvar(caminho, "Ana", 50);
            repositorio.salvar(caminho, "Pedro", 80);
            repositorio.salvar(caminho, "Maria", 65);

            // Carrega o ranking ordenado
            List<String> ranking = repositorio.carregar(caminho);

            System.out.println("=== RANKING SABERUP ===");

            int posicao = 1;

            for (String jogador : ranking) {
                System.out.println(posicao + "º - " + jogador);
                posicao++;
            }
            // Testa se o ranking rejeita pontuação negativa
try {
    repositorio.salvar(caminho, "JogadorTeste", -10);
    System.out.println("FALHA: pontuação negativa aceita.");
} catch (IllegalArgumentException e) {
    System.out.println("TESTE OK: " + e.getMessage());
}
// Testa se o ranking rejeita nomes vazios
try {
    repositorio.salvar(caminho, "", 50);
    System.out.println("FALHA: nome vazio aceito.");
} catch (IllegalArgumentException e) {
    System.out.println("TESTE OK: " + e.getMessage());
}

        } catch (IOException e) {
            System.out.println("Erro ao acessar o ranking: " + e.getMessage());
        }
    }
}
