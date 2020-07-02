package br.com.arthurpereira.contacts.exception;

public class TipoDeArquivoInvalidoException extends Exception {

    public static final long serialVersionUID = 1L;

    public TipoDeArquivoInvalidoException() {
        super("O formato de arquivo enviado é inválido ou não é permitido.");
    }
}
