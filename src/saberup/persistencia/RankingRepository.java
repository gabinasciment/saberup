
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
import java.nio.file.StandardOpenOption;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RankingRepository {

    // Salva o nome e a pontuação de um jogador no arquivo CSV
    public void salvar(String caminho, String nome, int pontos) throws IOException {

        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do jogador não pode estar vazio.");
        }

        if (nome.contains(";") || nome.contains("\n") || nome.contains("\r")) {
            throw new IllegalArgumentException("O nome do jogador contém caracteres inválidos.");
        }

        if (pontos < 0) {
            throw new IllegalArgumentException("A pontuação não pode ser negativa.");
        }

        Path arquivo = Path.of(caminho);

        // Cria a pasta, caso ela ainda não exista
        Path pasta = arquivo.getParent();

        if (pasta != null) {
            Files.createDirectories(pasta);
        }

        // Monta a linha no formato: nome;pontos
        String linha = nome.trim() + ";" + pontos + System.lineSeparator();

        // Cria o arquivo se necessário e adiciona a nova pontuação
        Files.writeString(
                arquivo,
                linha,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );
    }

    // Lê as pontuações e organiza o ranking do maior para o menor
    public List<String> carregar(String caminho) throws IOException {

        Path arquivo = Path.of(caminho);
        List<String> ranking = new ArrayList<>();

        // Se o arquivo ainda não existir, retorna um ranking vazio
        if (!Files.exists(arquivo)) {
            return ranking;
        }

        List<String> linhas = Files.readAllLines(arquivo, StandardCharsets.UTF_8);

        for (String linha : linhas) {

            if (linha.isBlank()) {
                continue;
            }

            String[] partes = linha.split(";", -1);

            if (partes.length != 2) {
                throw new IOException("Linha inválida no arquivo de ranking: " + linha);
            }

            String nome = partes[0].trim();
            int pontos;

            if (nome.isEmpty()) {
                throw new IOException("Nome vazio no arquivo de ranking.");
            }

            try {
                pontos = Integer.parseInt(partes[1].trim());
            } catch (NumberFormatException e) {
                throw new IOException("Pontuação inválida no ranking: " + linha, e);
            }

            if (pontos < 0) {
                throw new IOException("Pontuação negativa no ranking: " + linha);
            }

            ranking.add(nome + ";" + pontos);
        }

        // Ordena as pontuações em ordem decrescente
        ranking.sort(
                Comparator.comparingInt(
                        (String registro) -> Integer.parseInt(registro.split(";")[1])
                ).reversed()
        );

        return ranking;
    }
}
