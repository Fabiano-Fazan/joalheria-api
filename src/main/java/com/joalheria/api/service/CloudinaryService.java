package com.joalheria.api.service;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public UploadResult uploadProdutoImagem(MultipartFile file, UUID produtoId){
        validaImagem(file);
        try {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    Map.of(
                        "folder", "products/" + produtoId,
                        "resource_type", "image"
            ));
            return new UploadResult(
                    uploadResult.get("secure_url").toString(),
                    uploadResult.get("public_id").toString()
            );
        }catch (IOException e){
            throw new RuntimeException("Erro ao fazer upload da imagem: " + e.getMessage());
        }
    }

    public void deletarImagem(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, Map.of());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao remover imagem enviada: " + e.getMessage());
        }
    }

    private void validaImagem(MultipartFile file){
        if (file.isEmpty()) {
            throw new IllegalArgumentException("O arquivo de imagem não pode ser vazio.");
        }
        String contentType = file.getContentType();

        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("O arquivo deve ser uma imagem válida.");
        }
    }

    public record UploadResult(String secureUrl, String publicId) {
    }
}
