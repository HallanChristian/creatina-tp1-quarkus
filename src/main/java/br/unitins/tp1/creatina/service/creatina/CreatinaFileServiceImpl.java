package br.unitins.tp1.creatina.service.creatina;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jboss.logging.Logger;

import br.unitins.tp1.creatina.resource.creatina.CreatinaResource;
import br.unitins.tp1.creatina.service.fileservice.FileService;
import br.unitins.tp1.creatina.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreatinaFileServiceImpl implements FileService {

    private static final Logger LOG = Logger.getLogger(CreatinaResource.class);

    private final String PATH_CLIENTE = "C:\\Users\\halla\\Downloads\\images";

    private static final List<String> SUPPORTED_MIME_TYPES = Arrays.asList("image/jpeg", "image/jpg", "image/png",
            "image/gif");

    private static final int MAX_FILE_SIZE = 1024 * 1024 * 10; // 10 MB

    @Override
    public String save(String nomeArquivo, byte[] arquivo) throws IOException {
        LOG.info("Iniciando o salvamento do arquivo: " + nomeArquivo);
        verificarTipoArquivo(nomeArquivo);
        verificarTamanhoArquivo(arquivo);

        Path diretorio = Paths.get(PATH_CLIENTE);
        if (!Files.exists(diretorio)) {
            LOG.info("Diretório não encontrado. Criando diretório: " + diretorio.toString());
            Files.createDirectory(diretorio);
        }

        String mimeType = Files.probeContentType(Paths.get(nomeArquivo));
        if (mimeType == null) {
            LOG.error("Não foi possível determinar o MIME type do arquivo: " + nomeArquivo);
            throw new IOException("Não foi possível determinar o MIME type do arquivo.");
        }
        LOG.info("MIME type identificado: " + mimeType);

        String extensao = mimeType.substring(mimeType.lastIndexOf("/") + 1);
        String novoNomeArquivo = UUID.randomUUID() + "." + extensao;

        // Evitar duplicação de nome de arquivo
        Path filePath = diretorio.resolve(novoNomeArquivo);
        while (filePath.toFile().exists()) {
            LOG.warn("Conflito de nome detectado. Gerando novo nome.");
            novoNomeArquivo = UUID.randomUUID() + "." + extensao;
            filePath = diretorio.resolve(novoNomeArquivo);
        }

        try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
            fos.write(arquivo);
            LOG.info("Arquivo salvo com sucesso em: " + filePath.toString());
        } catch (IOException e) {
            LOG.error("Erro ao salvar o arquivo: " + e.getMessage(), e);
            throw e;
        }

        return novoNomeArquivo;
    }

    private void verificarTipoArquivo(String nomeArquivo) throws IOException {
        String mimeType = Files.probeContentType(Paths.get(nomeArquivo));
        if (!SUPPORTED_MIME_TYPES.contains(mimeType)) {
            throw new IOException("Formato de arquivo nao suportado pelo sistema.");
        }
    }

    private void verificarTamanhoArquivo(byte[] arquivo) throws IOException {
        if (arquivo.length > MAX_FILE_SIZE) {
            throw new IOException("O arquivo excede o limite maximo de 10 MB.");
        }
    }

    @Override
    public File find(String nomeArquivo) {
        File arquivo = new File(PATH_CLIENTE, nomeArquivo);
        LOG.info("Tentando localizar arquivo: " + arquivo.getAbsolutePath());
        if (!arquivo.exists()) {
            LOG.error("Arquivo não encontrado: " + arquivo.getAbsolutePath());
            throw new ValidationException("nomeArquivo", "Arquivo não encontrado");
        }
        return arquivo;
    }
}