package ua.tartemchuk.usermanagement.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.tartemchuk.usermanagement.services.ImageService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImageServiceImpl implements ImageService {

    @Override
    public String transferToLocalStorage(String uploadDir, String fileName, MultipartFile image) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath;
        try (InputStream inputStream = image.getInputStream()) {
            filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
        return filePath.toString();
    }

    @Override
    public byte[] downloadImage(String filePath) throws IOException {
        return Files.readAllBytes(new File(filePath).toPath());
    }

}
