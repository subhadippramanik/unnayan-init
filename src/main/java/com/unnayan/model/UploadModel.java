package com.unnayan.model;

import org.springframework.web.multipart.MultipartFile;

public class UploadModel {

	private final MultipartFile file;
	private final String fileName;

	
	public UploadModel(MultipartFile file, String fileName) {
		this.file = file;
		this.fileName = fileName;
	}

	public MultipartFile getFile() {
		return file;
	}

	public String getFileName() {
		return fileName;
	}


}
