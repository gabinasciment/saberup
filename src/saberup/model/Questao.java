/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package saberup.model;

/**
 *
 * @author erika
 */
public abstract class Questao {
    private int id;
    private String enunciado;
    private int pontos;
    
    public Questao(int id, String enunciado, int pontos){
        this.id = id;
        this.enunciado = enunciado;
        this.pontos = pontos;
    }
    public int getId(){
        return id;
    }
    public String getEnunciado() {
        return enunciado;
    }

    public int getPontos() {
        return pontos;
    }

    public abstract boolean corrigir(String resposta);

    public abstract String paraCsv();
}
