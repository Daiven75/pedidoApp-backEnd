package com.lucasilva.pedidoapp.services;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lucasilva.pedidoapp.domain.enums.TipoErro;
import com.lucasilva.pedidoapp.services.exceptions.FileException;

@Service
public class ImageService {

	public BufferedImage getJpgImageFromFile(MultipartFile uploadedFile) {
		String ext = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());
		if(!"png".equalsIgnoreCase(ext) && !"jpg".equalsIgnoreCase(ext)) {
			throw new FileException(TipoErro.APENAS_PNG_E_JPG.toString());
		}
		try {
			BufferedImage img = ImageIO.read(uploadedFile.getInputStream());
			if("png".equalsIgnoreCase(ext)) {
				img = pngToJpg(img);
			}
			return img;
		} catch (IOException e) {
			throw new FileException(TipoErro.ERRO_LER_ARQUIVO.toString());
		}
	}

	private BufferedImage pngToJpg(BufferedImage img) {
		BufferedImage jpgImage = new BufferedImage( img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		jpgImage.createGraphics().drawImage( jpgImage, 0, 0, Color.WHITE, null);
		return jpgImage;
	}
	
	public InputStream getInputStream(BufferedImage img, String extension) {
 
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(img, extension, os);
			return new ByteArrayInputStream(os.toByteArray());			
		} catch (IOException e) {
			throw new FileException(TipoErro.ERRO_LER_ARQUIVO.toString());
		}
	}
	
	public BufferedImage cropSquare(BufferedImage sourceImg) {
		int min = (sourceImg.getHeight() <= sourceImg.getWidth() ? 
				sourceImg.getHeight() : 
					sourceImg.getWidth());
		return Scalr.crop(
				sourceImg, 
				(sourceImg.getWidth()/2) - (min/2), 
				(sourceImg.getHeight()/2) - (min/2), 
				min, 
				min);
	}
	
	public BufferedImage resize(BufferedImage sourceImg, int size) {
		return Scalr.resize(sourceImg, Scalr.Method.ULTRA_QUALITY, size);
	}
}