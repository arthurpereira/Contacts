package br.com.arthurpereira.contacts.entity;

import br.com.arthurpereira.contacts.entity.listener.TelefoneListener;
import br.com.arthurpereira.contacts.enums.TelefoneEnum;
import br.com.arthurpereira.contacts.util.StringUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;;
;
;
import java.io.Serializable;

@Entity
@Table(name = "telefone")
@EntityListeners(TelefoneListener.class)
public class Telefone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero")
    private String numero;

    @Column(name = "numero_formatado")
    private String numeroFormatado;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TelefoneEnum tipo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contato_id")
    private Contato contato;

    public Telefone() {

    }

    public Telefone(Contato contato) {
        this.contato = contato;
    }

    // GETTERS AND SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNumeroFormatado() {
        return numeroFormatado;
    }

    public void setNumeroFormatado() {
        this.numeroFormatado = StringUtil.formatPhoneNumber(numero);
    }

    public TelefoneEnum getTipo() {
        return tipo;
    }

    public void setTipo(TelefoneEnum tipo) {
        this.tipo = tipo;
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }
}
