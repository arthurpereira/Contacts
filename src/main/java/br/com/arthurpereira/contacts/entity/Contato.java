package br.com.arthurpereira.contacts.entity;

import br.com.arthurpereira.contacts.entity.listener.ContatoListener;
import br.com.arthurpereira.contacts.exception.DiretorioNaoEncontradoException;
import br.com.arthurpereira.contacts.exception.TipoDeArquivoInvalidoException;
import br.com.arthurpereira.contacts.util.StringUtil;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "contato")
@EntityListeners(ContatoListener.class)
public class Contato implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "sobrenome")
    private String sobrenome;

    @Column(name = "nome_completo")
    private String nomeCompleto;

    @Temporal(TemporalType.DATE)
    @Column(name = "aniversario")
    private Date aniversario;

    @Column(name = "nota")
    private String nota;

    @OneToMany(mappedBy = "contato", cascade = {CascadeType.ALL}, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Telefone> telefones;

    @OneToMany(mappedBy = "contato", cascade = {CascadeType.ALL}, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Email> emails;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Foto foto;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public void salvarFoto(InputStream arquivo, String tipo) throws DiretorioNaoEncontradoException, TipoDeArquivoInvalidoException {
        if (foto != null) {
            foto.removerArquivo();
        }
        foto = new Foto();
        foto.persistirArquivo(arquivo, tipo);
    }

    public void removerFoto() {
        if (foto != null) {
            foto.removerArquivo();
            foto = null;
        }
    }

    // GETTERS AND SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = StringUtil.formatName(nome);
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto() {
        this.nomeCompleto = nome + " " + sobrenome;
    }

    public Date getAniversario() {
        return aniversario;
    }

    public void setAniversario(Date aniversario) {
        this.aniversario = aniversario;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public List<Telefone> getTelefones() {
        if (telefones == null)
            telefones = new ArrayList<>();
        return telefones;
    }

    public void setTelefones(List<Telefone> numerosDeTelefone) {
        this.telefones = numerosDeTelefone;
    }

    public List<Email> getEmails() {
        if (emails == null)
            emails = new ArrayList<>();
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }

    public Foto getFoto() {
        return foto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
