package br.com.arthurpereira.contacts.bean;

import br.com.arthurpereira.contacts.datamodel.ContatoDataModel;
import br.com.arthurpereira.contacts.entity.Contato;
import br.com.arthurpereira.contacts.entity.Email;
import br.com.arthurpereira.contacts.entity.Foto;
import br.com.arthurpereira.contacts.entity.Telefone;
import br.com.arthurpereira.contacts.enums.EmailEnum;
import br.com.arthurpereira.contacts.enums.StatusEnum;
import br.com.arthurpereira.contacts.enums.TelefoneEnum;
import br.com.arthurpereira.contacts.exception.DiretorioNaoEncontradoException;
import br.com.arthurpereira.contacts.exception.TipoDeArquivoInvalidoException;
import br.com.arthurpereira.contacts.resource.MessageResource;
import br.com.arthurpereira.contacts.service.ContatoService;
import br.com.arthurpereira.contacts.service.EmailService;
import br.com.arthurpereira.contacts.service.TelefoneService;
import br.com.arthurpereira.contacts.util.MessageUtil;
import br.com.arthurpereira.contacts.util.SessionUtil;
import br.com.arthurpereira.contacts.util.StringUtil;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Named
@SessionScoped
public class ContatoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private ContatoService contatoService;

    @EJB
    private TelefoneService telefoneService;

    @EJB
    private EmailService emailService;

    private Contato contato;
    private String busca;
    private List<Contato> contatos;
    private ContatoDataModel listaDeContatos;

    private Foto foto;
    private Part arquivoFoto;

    private StatusEnum status;

    public ContatoBean() {
        limparContato();
    }

    @PostConstruct
    private void listarContatos() {
        contatos = contatoService.listAllContatosDoUsuario(SessionUtil.getUsuario());
        atualizarListaDeContatosDataModel();
    }

    public void cadastrar() {
        try {
            contato.setNomeCompleto();
            contato.setUsuario(SessionUtil.getUsuario());

            if (arquivoFoto != null)
                enviarArquivo();

            contatoService.save(contato);
            MessageUtil.showMessageInfo(MessageResource.getResource().getString("contato.cadastro.sucesso"));

            listarContatos();
            status = StatusEnum.VISUALIZACAO;

        } catch (IOException ex) {
            MessageUtil.showMessageWarn(MessageResource.getResource().getString("contato.cadastro.erro"));
        } catch (DiretorioNaoEncontradoException ex) {
            MessageUtil.showMessageWarn(MessageResource.getResource().getString("contato.cadastro.erro"));
        } catch (TipoDeArquivoInvalidoException ex) {
            MessageUtil.showMessageWarn(MessageResource.getResource().getString("contato.upload.erro.foto"));
        } catch (Exception ex) {
            MessageUtil.showMessageWarn(MessageResource.getResource().getString("contato.cadastro.erro"));
        }
    }

    public void atualizar() {
        try {
            if (arquivoFoto != null)
                enviarArquivo();

            contato.setNomeCompleto();
            contatoService.update(contato);
            MessageUtil.showMessageInfo(MessageResource.getResource().getString("contato.edicao.sucesso"));

            listarContatos();
            status = StatusEnum.VISUALIZACAO;

        } catch (IOException ex) {
            MessageUtil.showMessageWarn(MessageResource.getResource().getString("contato.edicao.erro"));
        } catch (DiretorioNaoEncontradoException ex) {
            MessageUtil.showMessageWarn(MessageResource.getResource().getString("contato.edicao.erro"));
        } catch (TipoDeArquivoInvalidoException ex) {
            MessageUtil.showMessageWarn(MessageResource.getResource().getString("contato.upload.erro.foto"));
        } catch (Exception ex) {
            MessageUtil.showMessageWarn(MessageResource.getResource().getString("contato.edicao.erro"));
        }
    }

    private void enviarArquivo() throws IOException, DiretorioNaoEncontradoException, TipoDeArquivoInvalidoException {
        InputStream inputStream = arquivoFoto.getInputStream();
        String tipo = arquivoFoto.getContentType();

        contato.salvarFoto(inputStream, tipo);

        inputStream.close();
    }

    public void buscar() {
        busca = StringUtil.removeUnnecessarySpaces(busca);
        if (busca.length() != 0) {
            try {
                contatos = contatoService.buscarContatosDoUsuarioPeloNome(busca, SessionUtil.getUsuario());
                atualizarListaDeContatosDataModel();

                if (contatos.isEmpty()) {
                    status = StatusEnum.SEM_SELECAO;
                } else {
                    contato = contatos.get(0);
                    status = StatusEnum.VISUALIZACAO;
                }
            } catch (Exception ex) {
                MessageUtil.showMessageWarn(MessageResource.getResource().getString("contato.busca.erro"));
            }
        } else {
            if (status != StatusEnum.SEM_SELECAO) {
                listarContatos();
                limparContato();
                status = StatusEnum.SEM_SELECAO;
            }
        }
    }

    public void deletarContato() {
        try {
            contatoService.delete(contato);
            listarContatos();
            limparContato();
            MessageUtil.showMessageInfo(MessageResource.getResource().getString("contato.exclusao.sucesso"));
        } catch (Exception ex) {
            MessageUtil.showMessageWarn(MessageResource.getResource().getString("contato.exclusao.erro"));
        }
    }

    private void atualizarListaDeContatosDataModel() {
        listaDeContatos = new ContatoDataModel(contatos);
    }

    public void limparContato() {
        contato = new Contato();
        status = StatusEnum.SEM_SELECAO;
    }

    public void preparaCadastro() {
        limparContato();
        status = StatusEnum.CADASTRO;
    }

    public void preparaVisualizacao(Contato contatoParaVisualizar) {
        contato = contatoParaVisualizar;
        contato.setTelefones(telefoneService.listAllTelefonesDoContato(contato));
        contato.setEmails(emailService.listAllEmailsDoContato(contato));
        status = StatusEnum.VISUALIZACAO;
    }

    public void preparaEdicao() {
        status = StatusEnum.EDICAO;
    }

    public void retomaVisualizacao() {
        status = StatusEnum.VISUALIZACAO;
    }

    public void adicionarTelefone() {
        contato.getTelefones().add(new Telefone(contato));
    }

    public void removerTelefone(Telefone telefone) {
        contato.getTelefones().remove(telefone);
    }

    public void adicionarEmail() {
        contato.getEmails().add(new Email(contato));
    }

    public void removerEmail(Email email) {
        contato.getEmails().remove(email);
    }

    public List<TelefoneEnum> getTiposTelefone() {
        return Arrays.asList(TelefoneEnum.values());
    }

    public List<EmailEnum> getTiposEmail() {
        return Arrays.asList(EmailEnum.values());
    }

    // GETTERS AND SETTERS

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    public String getBusca() {
        return busca;
    }

    public void setBusca(String busca) {
        this.busca = busca;
    }

    public List<Contato> getContatos() {
        return contatos;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    public Part getArquivoFoto() {
        return arquivoFoto;
    }

    public void setArquivoFoto(Part arquivoFoto) {
        this.arquivoFoto = arquivoFoto;
    }

    public ContatoDataModel getListaDeContatos() {
        return listaDeContatos;
    }

    public boolean isSemSelecao() {
        return status == StatusEnum.SEM_SELECAO;
    }

    public boolean isVisualizando() {
        return status == StatusEnum.VISUALIZACAO;
    }

    public boolean isCadastrando() {
        return status == StatusEnum.CADASTRO;
    }

    public boolean isEditando() {
        return status == StatusEnum.EDICAO;
    }

}
