package ua.tartemchuk.usermanagement.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    String transferToLocalStorage(String uploadDir, String fileName, MultipartFile image) throws IOException;

    byte[] downloadImage(String filePath) throws IOException;

}
