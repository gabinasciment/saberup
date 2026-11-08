/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package saberup.model;

/**
 *
 * @author erika
 */
public class QuestaoVerdadeiroFalso extends Questao {
    private boolean gabarito;
    
    
public QuestaoVerdadeiroFalso(int id, String enunciado, int pontos, boolean gabarito) {
    super(id, enunciado, pontos);
    this.gabarito = gabarito;
    
}  

@Override
public boolean corrigir(String resposta) {
    if (resposta == null) {
        return false;
    }
    String respostaTratada = resposta.trim().toUpperCase();
    
    if (respostaTratada.equals("V")) {
        return gabarito == true;
    }
    if (respostaTratada.equals("F")) {
        return gabarito == false;
    }
    return false;
}

@Override
public String paraCsv() {
    String respostaCorreta;
    if (gabarito == true) {
        respostaCorreta = "V";
    } else {
        respostaCorreta = "F";
    }
    return "VF;" + getEnunciado() + ";" + getPontos() + ";" + respostaCorreta;
}

}
