package br.com.arthurpereira.contacts.enums;

public enum TelefoneEnum {

    CELULAR("Celular"),
    RESIDENCIAL("Residencial"),
    TRABALHO("Trabalho"),
    PRINCIPAL("Principal"),
    FAX_RESIDENCIAL("Fax (residencial)"),
    FAX_TRABALHO("Fax (trabalho)"),
    FAX_OUTRO("Fax (outro)"),
    PAGER("Pager"),
    OUTRO("Outro");

    private String descricao;

    private TelefoneEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
