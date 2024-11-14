package br.unitins.tp1.creatina.service.fileservice;

import java.io.File;
import java.io.IOException;

public interface FileService {

    String save(String nomeArquivo, byte[] aquivo) throws IOException;
    
    File find(String nomearquivo);
    
}
