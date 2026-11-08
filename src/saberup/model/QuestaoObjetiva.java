/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package saberup.model;

import java.util.List;

/**
 *
 * @author erika
 */
public class QuestaoObjetiva extends Questao{
    private List<String> alternativas;
    private char gabarito;
    
    public QuestaoObjetiva (int id, String enunciado, int pontos, List<String> alternativas, char gabarito){
        super(id, enunciado, pontos);
        
        this.alternativas = alternativas;
        this.gabarito = gabarito;
        
    }      

@Override
public boolean corrigir(String resposta) {
    if (resposta == null) {
        return false;
    }

    return resposta.trim().equalsIgnoreCase(
            String.valueOf(gabarito)
    );
}



@Override
public String paraCsv() {
    return "OBJ;" + getEnunciado()
            + ";" + getPontos()
            + ";" + String.join("|", alternativas)
            + ";" + gabarito;
}
    }
    

