package com.justkidding.www.service;

import com.justkidding.www.model.Post;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {
    @Value("${upload.dir}")
    private String UPLOADPATH;

    public String uploadFile (MultipartFile file) throws IOException {
        Path upload_Path = Paths.get(UPLOADPATH);
        if (!Files.exists(upload_Path)) {
            Files.createDirectory(upload_Path);
        }
        Path filePath = upload_Path.resolve(file.getOriginalFilename());
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return filePath.toString();
    }

    public Resource downloadFilePost (Post post) throws MalformedURLException {
        Path filePath = Paths.get(post.getFile_path());
        return new UrlResource(filePath.toUri());
    }

}
