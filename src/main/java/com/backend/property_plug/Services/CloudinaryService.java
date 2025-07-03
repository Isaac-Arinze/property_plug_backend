package com.backend.property_plug.Services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public CloudinaryService(
            @Value("${cloudinary.cloud-name}") String cloudName,
            @Value("${cloudinary.api-key}") String apiKey,
            @Value("${cloudinary.api-secret}") String apiSecret) {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String contentType = file.getContentType();
        String originalFilename = file.getOriginalFilename();
        // If content type is application/octet-stream, try to infer from filename
        if (contentType == null || contentType.equals("application/octet-stream")) {
            if (originalFilename != null) {
                String ext = originalFilename.substring(originalFilename.lastIndexOf('.') + 1).toLowerCase();
                switch (ext) {
                    case "jpg":
                    case "jpeg":
                        contentType = "image/jpeg";
                        break;
                    case "png":
                        contentType = "image/png";
                        break;
                    case "svg":
                        contentType = "image/svg+xml";
                        break;
                    case "gif":
                        contentType = "image/gif";
                        break;
                    case "webp":
                        contentType = "image/webp";
                        break;
                    case "bmp":
                        contentType = "image/bmp";
                        break;
                    case "tiff":
                        contentType = "image/tiff";
                        break;
                    case "ico":
                        contentType = "image/x-icon";
                        break;
                }
            }
        }
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto", "folder", "property_images", "public_id", originalFilename, "overwrite", true, "type", "upload", "use_filename", true, "unique_filename", true, "content_type", contentType));
        return uploadResult.get("secure_url").toString();
    }
} 