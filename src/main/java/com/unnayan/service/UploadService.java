package com.unnayan.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.unnayan.model.Artifact;
import com.unnayan.model.UploadModel;

@Service
public class UploadService {

	private final String ARTIFACT_STORAGE_PATH = "/Users/subhadip/c-Loud/artifacts/";
	private final Logger LOGGER = LogManager.getLogger(getClass());
	private final ArtifactService artifactService;

	public UploadService(ArtifactService artifactService) {
		this.artifactService = artifactService;
	}

	public void uploadFile(int id, UploadModel uploadModel) throws IOException {
		try {
			byte[] bytes = uploadModel.getFile().getBytes();
			Path path = Paths.get(ARTIFACT_STORAGE_PATH + uploadModel.getFileName());
			Files.write(path, bytes);
			Artifact artifactToUpdate = artifactService.findArtifactById(id);
			artifactToUpdate.setFileName(uploadModel.getFileName());
			artifactToUpdate.setPath(ARTIFACT_STORAGE_PATH);
			artifactService.update(artifactToUpdate);
		} catch (IOException e) {
			LOGGER.error("Failed to upload file: " + e.getMessage());
			throw new IOException("Unable to upload artifact");
		}
	}

}
