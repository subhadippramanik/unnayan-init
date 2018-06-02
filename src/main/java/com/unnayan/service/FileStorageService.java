package com.unnayan.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.unnayan.model.Artifact;
import com.unnayan.model.UploadModel;

@Service
public class FileStorageService {

	private final String ARTIFACT_STORAGE_PATH = "/Users/subhadip/c-Loud/artifacts/";
	private final Logger LOGGER = LogManager.getLogger(getClass());
	private final ArtifactService artifactService;

	public FileStorageService(ArtifactService artifactService) {
		this.artifactService = artifactService;
	}

	public void storeFile(int id, UploadModel uploadModel) throws IOException {
		try {
			byte[] bytes = uploadModel.getFile().getBytes();
			Path path = Paths.get(ARTIFACT_STORAGE_PATH + uploadModel.getFileName());
			Files.write(path, bytes);
			Artifact artifactToUpdate = artifactService.findArtifactById(id);
			artifactToUpdate.setFileName(uploadModel.getFileName());
			artifactToUpdate.setPath(ARTIFACT_STORAGE_PATH);
			artifactService.addFileInfo(artifactToUpdate);
		} catch (IOException e) {
			LOGGER.error("Failed to upload file: " + e.getMessage());
			throw new IOException("Unable to upload artifact");
		}
	}
	
	public Resource loadFileAsResource(String fileName) throws MalformedURLException, FileNotFoundException {
        try {
            Path filePath = Paths.get(ARTIFACT_STORAGE_PATH).toAbsolutePath().normalize().resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MalformedURLException();
        }
    }

}
