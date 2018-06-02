package com.unnayan.service;

import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unnayan.model.Artifact;
import com.unnayan.repository.ArtifactRepository;

@Service
public class ArtifactService {
	
	private final ArtifactRepository artifactRepository;
	private final Logger LOGGER = LogManager.getLogger(getClass());

	@Autowired
	public ArtifactService(ArtifactRepository artifactRepository) {
		this.artifactRepository = artifactRepository;
	}
	
	public List<Artifact> findAllArtifacts() {
		return artifactRepository.findAll();
	}

	public Artifact findArtifactById(int id) {
		return artifactRepository.findOne(id);
	}

	public Artifact createArtifact(String name, String version) {
		Artifact artifact = new Artifact();
		artifact.setName(name);
		artifact.setVersion(version);
		Artifact savedArtifact = artifactRepository.save(artifact);
		artifactRepository.flush();
		return savedArtifact;
	}

	public Artifact addFileInfo(Artifact artifactToUpdate) {
		Artifact artifactFound = findArtifactById(artifactToUpdate.getId());
		LOGGER.info("uploading finished..");
		if(Objects.nonNull(artifactFound)) {
			artifactFound.setFileName(artifactToUpdate.getFileName());
			artifactFound.setPath(artifactToUpdate.getPath());
			artifactFound.setDownloadURI("/artifact/"+artifactFound.getId()+"/download");
			Artifact updatedArtifact = artifactRepository.save(artifactFound);
			artifactRepository.flush();
			return updatedArtifact;
		} else {
			return findArtifactById(artifactToUpdate.getId());
		}
	}
}
