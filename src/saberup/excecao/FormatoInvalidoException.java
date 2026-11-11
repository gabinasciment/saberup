
package saberup.excecao;

/**
 * Exceção utilizada quando os dados de uma questão
 * apresentam um formato inválido.
 *
 * @author Laysa
 */
public class FormatoInvalidoException extends Exception {

    public FormatoInvalidoException(String mensagem) {
        super(mensagem);
    }
}

