/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package saberup.model;

/**
 *
 * @author erika
 */
public class QuestaoNumerica extends Questao{
    private double gabarito;
    private double tolerancia;

    public QuestaoNumerica(int id, String enunciado, int pontos, double gabarito, double tolerancia) {
        super(id, enunciado, pontos);

        this.gabarito = gabarito;
        this.tolerancia = tolerancia;
    }
    
@Override
public boolean corrigir(String resposta) {
    if (resposta == null || resposta.trim().isEmpty()) {
        return false;
    }
    try {
        double valor = Double.parseDouble(resposta.trim().replace(",", ".")
        );
        if (!Double.isFinite(valor)) {
            return false;
        }
        return Math.abs(valor - gabarito) <= tolerancia;
    } catch (NumberFormatException e) {
        return false;
    }
}

@Override
public String paraCsv() {
    return "NUM;" + getEnunciado() + ";" + getPontos() + ";" + gabarito + ";" + tolerancia;
}


}
