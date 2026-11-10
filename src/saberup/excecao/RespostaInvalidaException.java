
package saberup.excecao;

/**
 * Exceção utilizada quando o jogador informa
 * uma resposta inválida para uma questão.
 *
 * @author Laysa
 */
public class RespostaInvalidaException extends Exception {

    public RespostaInvalidaException(String mensagem) {
        super(mensagem);
    }
}
