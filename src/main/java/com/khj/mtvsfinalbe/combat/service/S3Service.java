package com.khj.mtvsfinalbe.combat.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.UUID;

@Service
public class S3Service {

    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket.name}")
    private String bucketName;

    public S3Service(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadImage(String RadarChart) {
        byte[] decodedImage = Base64.getDecoder().decode(RadarChart);
        String fileName = "images/" + UUID.randomUUID() + ".png";

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(decodedImage.length);
        metadata.setContentType("image/png");

        s3Client.putObject(new PutObjectRequest(bucketName, fileName, new ByteArrayInputStream(decodedImage), metadata));

        return s3Client.getUrl(bucketName, fileName).toString();
    }
}
