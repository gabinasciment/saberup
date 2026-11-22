package saberup.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import saberup.model.Questao;
import saberup.model.QuestaoObjetiva;
import saberup.model.QuestaoVerdadeiroFalso;
import saberup.servico.Quiz;

/**
 * Tela das perguntas.
 * O visual foi feito na aba Design. Aqui no código fica só o comportamento:
 * mostrar a questão, ler a resposta e corrigir usando o Quiz (classe da Érika).
 *
 * Uma tela só serve para os 3 tipos de questão:
 *   objetiva            -> rdoA, rdoB, rdoC, rdoD
 *   verdadeiro ou falso -> rdoA (Verdadeiro) e rdoB (Falso)
 *   numérica            -> txtResposta (campo de texto)
 */
public class TelaQuiz extends javax.swing.JFrame {

    // Cores usadas para mostrar se acertou ou errou
    private static final Color FUNDO = new Color(244, 245, 250);
    private static final Color VERDE = new Color(46, 125, 50);
    private static final Color VERMELHO = new Color(198, 40, 40);
    private static final Color VERDE_CLARO = new Color(227, 244, 230);
    private static final Color VERMELHO_CLARO = new Color(251, 228, 228);

    private static final String[] LETRAS = {"A", "B", "C", "D"};

    /** Quantas perguntas cada rodada tem (sorteadas do banco de questões). */
    private static final int QUESTOES_POR_RODADA = 10;

    private Quiz quiz;                    // as regras do jogo ficam no Quiz
    private int totalQuestoes;
    private int numeroAtual = 1;          // para mostrar "Questão 1 de 3"
    private int acertos = 0;
    private boolean jaRespondeu = false;  // false: o botão corrige | true: o botão avança
    private JRadioButton[] opcoes;        // rdoA..rdoD em um vetor, para usar no for

    public TelaQuiz(List<Questao> todasQuestoes) {
        initComponents();
        opcoes = new JRadioButton[]{rdoA, rdoB, rdoC, rdoD};

        // Sorteia no máximo QUESTOES_POR_RODADA questões do banco para esta rodada
        List<Questao> questoes = new ArrayList<>(todasQuestoes);
        Collections.shuffle(questoes);
        if (questoes.size() > QUESTOES_POR_RODADA) {
            questoes = questoes.subList(0, QUESTOES_POR_RODADA);
        }

        quiz = new Quiz(questoes);
        totalQuestoes = questoes.size();
        getRootPane().setDefaultButton(btnResponder);   // Enter também responde / avança
        mostrarQuestao();
    }

    /** Coloca a questão atual na tela. */
    private void mostrarQuestao() {
        Questao questao = quiz.questaoAtual();

        // O <html> faz o enunciado quebrar a linha em vez de ser cortado quando é comprido
        lblEnunciado.setText("<html>" + questao.getEnunciado()
                .replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;") + "</html>");
        lblProgresso.setText("Questão " + numeroAtual + " de " + totalQuestoes + " — Vale " + questao.getPontos() + " ponto(s)");
        lblPontos.setText("Pontos: " + quiz.getPontos());
        barProgresso.setMaximum(totalQuestoes);
        barProgresso.setValue(numeroAtual);
        lblFeedback.setText(" ");

        // Limpa e esconde tudo; depois mostra só o que esta questão precisa
        grpAlternativas.clearSelection();
        for (JRadioButton rdo : opcoes) {
            rdo.setVisible(false);
            rdo.setEnabled(true);
            rdo.setBackground(FUNDO);
        }
        txtResposta.setVisible(false);
        txtResposta.setText("");
        txtResposta.setEditable(true);
        txtResposta.setBackground(Color.WHITE);

        if (questao instanceof QuestaoObjetiva) {
            QuestaoObjetiva objetiva = (QuestaoObjetiva) questao;
            List<String> alternativas = objetiva.getAlternativas();
            for (int i = 0; i < 4; i++) {
                opcoes[i].setText(LETRAS[i] + ") " + alternativas.get(i));
                opcoes[i].setVisible(true);
            }
        } else if (questao instanceof QuestaoVerdadeiroFalso) {
            rdoA.setText("Verdadeiro");
            rdoB.setText("Falso");
            rdoA.setVisible(true);
            rdoB.setVisible(true);
        } else {
            txtResposta.setVisible(true);   // questão numérica
        }
    }

    /**
     * Devolve a resposta no formato que as classes de questão entendem:
     * "A" a "D" (objetiva), "V" ou "F" (verdadeiro ou falso),
     * o número digitado (numérica), ou "" se nada foi respondido.
     */
    private String lerResposta() {
        Questao questao = quiz.questaoAtual();

        if (questao instanceof QuestaoVerdadeiroFalso) {
            if (rdoA.isSelected()) {
                return "V";
            }
            if (rdoB.isSelected()) {
                return "F";
            }
            return "";
        }
        if (questao instanceof QuestaoObjetiva) {
            for (int i = 0; i < 4; i++) {
                if (opcoes[i].isSelected()) {
                    return LETRAS[i];
                }
            }
            return "";
        }
        return txtResposta.getText().trim();   // numérica
    }

    /**
     * Posição do botão com a resposta certa (0 = rdoA, 1 = rdoB...),
     * ou -1 na questão numérica, que não usa botões.
     * As classes de questão não mostram o gabarito, então a tela testa cada
     * opção com corrigir() até achar a que dá certo.
     */
    private int indiceCorreto(Questao questao) {
        if (questao instanceof QuestaoVerdadeiroFalso) {
            return questao.corrigir("V") ? 0 : 1;
        }
        if (questao instanceof QuestaoObjetiva) {
            for (int i = 0; i < LETRAS.length; i++) {
                if (questao.corrigir(LETRAS[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    /** Texto da resposta certa, para mostrar quando a pessoa erra. */
    private String respostaCorreta(Questao questao) {
        int certa = indiceCorreto(questao);
        if (certa >= 0) {
            return opcoes[certa].getText();   // ex.: "B) Sintetizar proteínas" ou "Falso"
        }

        // Questão numérica: o gabarito é o penúltimo campo de paraCsv()
        // (NUM;enunciado;pontos;resposta;tolerância)
        String[] partes = questao.paraCsv().split(";");
        String gabarito = partes[partes.length - 2];
        try {
            double valor = Double.parseDouble(gabarito);
            if (valor == Math.rint(valor)) {
                return String.valueOf((long) valor);          // 46.0 -> "46"
            }
            return String.valueOf(valor).replace('.', ',');   // 42.5 -> "42,5"
        } catch (NumberFormatException e) {
            return gabarito;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grpAlternativas = new javax.swing.ButtonGroup();
        pnlTopo = new javax.swing.JPanel();
        lblProgresso = new javax.swing.JLabel();
        lblPontos = new javax.swing.JLabel();
        barProgresso = new javax.swing.JProgressBar();
        pnlCentro = new javax.swing.JPanel();
        lblEnunciado = new javax.swing.JLabel();
        txtResposta = new javax.swing.JTextField();
        rdoA = new javax.swing.JRadioButton();
        rdoB = new javax.swing.JRadioButton();
        rdoC = new javax.swing.JRadioButton();
        rdoD = new javax.swing.JRadioButton();
        lblFeedback = new javax.swing.JLabel();
        pnlRodape = new javax.swing.JPanel();
        btnResponder = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SaberUp - Quiz");
        setPreferredSize(new java.awt.Dimension(960, 700));
        setResizable(false);

        pnlTopo.setBackground(new java.awt.Color(37, 59, 117));
        pnlTopo.setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 24, 16, 24));
        pnlTopo.setLayout(new java.awt.BorderLayout(0, 10));

        lblProgresso.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblProgresso.setForeground(new java.awt.Color(255, 255, 255));
        lblProgresso.setText("Questão 1 de 10");
        pnlTopo.add(lblProgresso, java.awt.BorderLayout.WEST);

        lblPontos.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblPontos.setForeground(new java.awt.Color(255, 255, 255));
        lblPontos.setText("Pontos: 0");
        pnlTopo.add(lblPontos, java.awt.BorderLayout.EAST);

        barProgresso.setBackground(new java.awt.Color(255, 255, 255));
        barProgresso.setForeground(new java.awt.Color(108, 99, 255));
        barProgresso.setValue(10);
        pnlTopo.add(barProgresso, java.awt.BorderLayout.SOUTH);

        getContentPane().add(pnlTopo, java.awt.BorderLayout.NORTH);

        pnlCentro.setBackground(new java.awt.Color(244, 245, 250));
        pnlCentro.setBorder(javax.swing.BorderFactory.createEmptyBorder(24, 48, 16, 48));
        pnlCentro.setLayout(new java.awt.GridLayout(7, 1, 0, 10));

        lblEnunciado.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        lblEnunciado.setForeground(new java.awt.Color(43, 43, 58));
        lblEnunciado.setText("Aqui aparece o enunciado da pergunta?");
        pnlCentro.add(lblEnunciado);

        txtResposta.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        pnlCentro.add(txtResposta);

        rdoA.setBackground(new java.awt.Color(244, 245, 250));
        grpAlternativas.add(rdoA);
        rdoA.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        rdoA.setForeground(new java.awt.Color(43, 43, 58));
        rdoA.setText("A) Alternativa A");
        rdoA.setOpaque(true);
        pnlCentro.add(rdoA);

        rdoB.setBackground(new java.awt.Color(244, 245, 250));
        grpAlternativas.add(rdoB);
        rdoB.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        rdoB.setForeground(new java.awt.Color(43, 43, 58));
        rdoB.setText("B) Alternativa B");
        rdoB.setOpaque(true);
        pnlCentro.add(rdoB);

        rdoC.setBackground(new java.awt.Color(244, 245, 250));
        grpAlternativas.add(rdoC);
        rdoC.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        rdoC.setForeground(new java.awt.Color(43, 43, 58));
        rdoC.setText("C) Alternativa C");
        rdoC.setOpaque(true);
        pnlCentro.add(rdoC);

        rdoD.setBackground(new java.awt.Color(244, 245, 250));
        grpAlternativas.add(rdoD);
        rdoD.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        rdoD.setForeground(new java.awt.Color(43, 43, 58));
        rdoD.setText("D) Alternativa D");
        rdoD.setOpaque(true);
        pnlCentro.add(rdoD);

        lblFeedback.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblFeedback.setForeground(new java.awt.Color(46, 125, 50));
        lblFeedback.setText("errou ou acertou");
        pnlCentro.add(lblFeedback);

        getContentPane().add(pnlCentro, java.awt.BorderLayout.CENTER);

        pnlRodape.setBackground(new java.awt.Color(244, 245, 250));
        pnlRodape.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 20, 0));

        btnResponder.setBackground(new java.awt.Color(249, 199, 79));
        btnResponder.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnResponder.setForeground(new java.awt.Color(37, 59, 117));
        btnResponder.setText("Responder");
        btnResponder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResponderActionPerformed(evt);
            }
        });
        pnlRodape.add(btnResponder);

        getContentPane().add(pnlRodape, java.awt.BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnResponderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResponderActionPerformed
        // 2º clique: vai para a próxima questão ou para o resultado
        if (jaRespondeu) {
            if (quiz.temProxima()) {
                quiz.avancar();
                numeroAtual++;
                jaRespondeu = false;
                btnResponder.setText("Responder");
                mostrarQuestao();
            } else {
                new TelaResultado(quiz.getPontos(), quiz.getPontosPossiveis(),
                        acertos, totalQuestoes).setVisible(true);
                dispose();
            }
            return;
        }

        // 1º clique: corrige
        String resposta = lerResposta();
        if (resposta.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escolha ou digite uma resposta.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Polimorfismo: o Quiz chama corrigir() e cada tipo de questão corrige do seu jeito
        boolean acertou = quiz.responder(resposta);

        Questao questao = quiz.questaoAtual();
        if (acertou) {
            acertos++;
            lblFeedback.setText("Acertou!");
            lblFeedback.setForeground(VERDE);
        } else {
            // O <html> deixa o texto quebrar a linha se a alternativa for comprida
            lblFeedback.setText("<html>Errou! Resposta correta: " + respostaCorreta(questao) + "</html>");
            lblFeedback.setForeground(VERMELHO);
        }

        // Pinta a resposta escolhida de verde ou vermelho, destaca a certa e trava as opções
        Color cor = acertou ? VERDE_CLARO : VERMELHO_CLARO;
        int certa = indiceCorreto(questao);
        for (int i = 0; i < opcoes.length; i++) {
            if (opcoes[i].isSelected()) {
                opcoes[i].setBackground(cor);
            }
            if (i == certa) {
                opcoes[i].setBackground(VERDE_CLARO);
            }
            opcoes[i].setEnabled(false);
        }
        if (txtResposta.isVisible()) {
            txtResposta.setBackground(cor);
            txtResposta.setEditable(false);
        }

        lblPontos.setText("Pontos: " + quiz.getPontos());
        jaRespondeu = true;
        if (quiz.temProxima()) {
            btnResponder.setText("Próxima");
        } else {
            btnResponder.setText("Ver resultado");
        }
    }//GEN-LAST:event_btnResponderActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barProgresso;
    private javax.swing.JButton btnResponder;
    private javax.swing.ButtonGroup grpAlternativas;
    private javax.swing.JLabel lblEnunciado;
    private javax.swing.JLabel lblFeedback;
    private javax.swing.JLabel lblPontos;
    private javax.swing.JLabel lblProgresso;
    private javax.swing.JPanel pnlCentro;
    private javax.swing.JPanel pnlRodape;
    private javax.swing.JPanel pnlTopo;
    private javax.swing.JRadioButton rdoA;
    private javax.swing.JRadioButton rdoB;
    private javax.swing.JRadioButton rdoC;
    private javax.swing.JRadioButton rdoD;
    private javax.swing.JTextField txtResposta;
    // End of variables declaration//GEN-END:variables
}
