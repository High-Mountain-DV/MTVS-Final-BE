package com.khj.mtvsfinalbe.combat.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.UUID;

@Service
public class S3Service {
//    private final AmazonS3 s3Client;
//    private final String bucketName = "your-s3-bucket-name";
//
//    public S3Service(AmazonS3 s3Client) {
//        this.s3Client = s3Client;
//    }
//
//    public String uploadImage(String base64Image) {
//        byte[] decodedImage = Base64.getDecoder().decode(base64Image);
//        String fileName = "images/" + UUID.randomUUID() + ".png";
//
//        s3Client.putObject(new PutObjectRequest(bucketName, fileName, new ByteArrayInputStream(decodedImage), null));
//        return s3Client.getUrl(bucketName, fileName).toString();
//    }
}
