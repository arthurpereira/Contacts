package br.com.arthurpereira.contacts.entity;

import br.com.arthurpereira.contacts.exception.DiretorioNaoEncontradoException;
import br.com.arthurpereira.contacts.exception.TipoDeArquivoInvalidoException;
import br.com.arthurpereira.contacts.util.FileUtil;
import br.com.arthurpereira.contacts.resource.PropertiesResource;
import br.com.arthurpereira.contacts.util.StringUtil;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.imageio.ImageIO;
import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "foto")
public class Foto implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String TIPOS_DE_IMAGEM_PERMITIDOS = PropertiesResource.getResource()
            .getString("contacts.upload.tipos_permitidos.foto_de_perfil");
    private static final String DIRETORIO = PropertiesResource.getResource()
            .getString("contacts.diretorio.foto_de_perfil");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "width")
    private String width;

    @Column(name = "height")
    private String height;

    @Column(name = "extensao")
    private String extensao;

    @Transient
    private Pattern pattern = Pattern.compile(TIPOS_DE_IMAGEM_PERMITIDOS);

    @Transient
    private Matcher matcher;

    public void persistirArquivo(InputStream arquivo, String tipo) throws DiretorioNaoEncontradoException, TipoDeArquivoInvalidoException {
        if (arquivo != null) {
            validaDiretorio(DIRETORIO);
            validaTipoDeArquivo(tipo);

            String nome = StringUtil.generateToken(20) + "." + FileUtil.getType(tipo);

            salvarArquivoNoDiretorio(arquivo, nome, DIRETORIO);
            getDimensoes(DIRETORIO);
        }
    }

    public void removerArquivo() {
        try {
            String arquivo = DIRETORIO.concat(this.nome);
            File file = new File(arquivo);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean validaDiretorio(String diretorio) throws DiretorioNaoEncontradoException {
        boolean oCaminhoExiste = new File(diretorio).canRead();
        if (!oCaminhoExiste) {
            throw new DiretorioNaoEncontradoException();
        }
        return oCaminhoExiste;
    }

    public boolean validaTipoDeArquivo(String nome) throws TipoDeArquivoInvalidoException {
        matcher = pattern.matcher(nome);
        boolean match = matcher.matches();

        if (!match)
            throw new TipoDeArquivoInvalidoException();

        return match;
    }

    private String salvarArquivoNoDiretorio(InputStream inputStream, String nomeDoArquivo, String diretorio) {
        String arquivo = nomeDoArquivo;
        this.nome = nomeDoArquivo;
        this.extensao = FileUtil.getExtension(nomeDoArquivo);
        nomeDoArquivo = diretorio.concat(nomeDoArquivo);

        try {
            OutputStream out = new FileOutputStream(new File(nomeDoArquivo));
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            out.flush();
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return arquivo;
    }

    public void getDimensoes(String diretorio) {
        BufferedImage image;

        try {
            String arquivo = diretorio.concat(this.nome);
            File file = new File(arquivo);
            FileInputStream inputStream = new FileInputStream(file);

            image = ImageIO.read(inputStream);
            this.width = new Integer(image.getWidth()).toString();
            this.height = new Integer(image.getHeight()).toString();

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public StreamedContent getBinary() throws IOException {
        return new DefaultStreamedContent(new FileInputStream(new File(DIRETORIO, this.nome)));
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

    public String getWidth() {
        return width;
    }

    public String getHeight() {
        return height;
    }

    public String getExtensao() {
        return extensao;
    }
}
