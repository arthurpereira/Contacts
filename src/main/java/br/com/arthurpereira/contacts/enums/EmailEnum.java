package br.com.arthurpereira.contacts.enums;

public enum EmailEnum {

    PESSOAL("Pessoal"),
    TRABALHO("Trabalho"),
    OUTRO("Outro");

    private String descricao;

    private EmailEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
