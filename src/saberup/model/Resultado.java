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

public class Resultado implements Comparable<Resultado> {
    private String nome;
    private int pontos;
    private int total;
    private LocalDate data;
    
    public Resultado (String nome, int pontos, int total, LocalDate data) {
        this.nome = nome;
        this.pontos = pontos;
        this.total = total;
        this.data = data;
        
        
    } 
    public String getNome() {
        return nome;
    }

    public int getPontos() {
        return pontos;
    }

    public int getTotal() {
        return total;
    }

    public LocalDate getData() {
        return data;
    }
    
    @Override
    public int compareTo(Resultado outro) {
        return Integer.compare(outro.getPontos(), this.pontos);
}
    @Override
    public String toString() {
        return nome + " - " + pontos + " pontos";
}

    public String paraCsv() {
        return nome + ";" + pontos + ";" + total + ";" + data;
}
}


