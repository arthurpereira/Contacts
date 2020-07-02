package br.com.arthurpereira.contacts.exception;

public class DiretorioNaoEncontradoException extends Exception {

    public static final long serialVersionUID = 1L;

    public DiretorioNaoEncontradoException() {
        super("O diretório de arquivos não foi encontrado ou não existe.");
    }
}
