package com.bolsadeideas.springboot.app.models.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFileServiceImpl implements IUploadFileService{
    private final static String UPLOAD_FOLDER = "uploads";
    private final Logger log= LoggerFactory.getLogger(getClass());

    @Override
    public Resource load(String filename) throws MalformedURLException{
        Path pathFoto = getPath(filename);
        log.info("pathFoto : "+pathFoto);
        Resource recurso=null;
        try {
            recurso=new UrlResource(pathFoto.toUri());
            if(!recurso.exists()|| !recurso.isReadable()) {
                throw new RuntimeException("Error no se puede cargar la imagen"+pathFoto.toString());

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return recurso;
    }

    @Override
    public String copy(MultipartFile file) throws IOException {
        String uniqueFilename= UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path rootPath = getPath(uniqueFilename);

        Files.copy(file.getInputStream(), rootPath);

        return uniqueFilename;
    }

    @Override
    public boolean delete(String filename) {
        Path rootPath = getPath(filename);
        File archivo = rootPath.toFile();

        if (archivo.exists() && archivo.canRead()){
            return archivo.delete();
        }
        return false;
    }

    public Path getPath(String fileName){
        return Paths.get(UPLOAD_FOLDER).resolve(fileName).toAbsolutePath();
    }
}
