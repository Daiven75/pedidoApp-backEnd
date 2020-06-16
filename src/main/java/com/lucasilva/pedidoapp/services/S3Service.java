package com.lucasilva.pedidoapp.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.lucasilva.pedidoapp.services.exceptions.FileException;

@Service
public class S3Service {

	private Logger LOG = LoggerFactory.getLogger(S3Service.class); 
	
	@Autowired
	private AmazonS3 s3Client;
	
	@Value("${s3.bucket}")
	private String bucketName;
	
	public URI uploadFile(MultipartFile multipartFile) {
		try {	
			String fileName = multipartFile.getOriginalFilename();
			InputStream is = multipartFile.getInputStream();
			String contentType = multipartFile.getContentType();
			return uploadFile(fileName, is, contentType);
			} catch (IOException e) {
				throw new FileException("Erro de IO: " + e.getMessage());
			}
	}
	
  	public URI uploadFile(String fileName, InputStream is, String contentType) {
  		try {
	  		ObjectMetadata metaData = new ObjectMetadata();
	  		metaData.setContentType(contentType);
			LOG.info("Iniciando Upload");
			s3Client.putObject(new PutObjectRequest(bucketName, fileName, is, metaData));
			LOG.info("Upload finalizado");
			return s3Client.getUrl(bucketName, fileName).toURI();
		} catch (URISyntaxException e) {
			throw new FileException("Erro ao converter URL para URI");
		}
  	}
}
