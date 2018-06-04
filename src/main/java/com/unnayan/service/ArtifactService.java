package com.unnayan.service;

import java.util.Objects;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableSet;
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

	public Set<Artifact> findAllArtifacts() {
		return ImmutableSet.copyOf(artifactRepository.findAll());
	}

	public Artifact findArtifactById(Integer id) {
		return artifactRepository.findOne(id);
	}

	public Artifact createArtifact(String name, String version) {
		final Artifact artifact = new Artifact();
		artifact.setName(name);
		artifact.setVersion(version);
		final Artifact savedArtifact = artifactRepository.saveAndFlush(artifact);
		return savedArtifact;
	}

	public Artifact addFileInfo(Artifact artifactToUpdate) {
		final Artifact artifactFound = findArtifactById(artifactToUpdate.getId());
		LOGGER.info("uploading finished..");
		if (Objects.nonNull(artifactFound)) {
			artifactFound.setFileName(artifactToUpdate.getFileName());
			artifactFound.setPath(artifactToUpdate.getPath());
			artifactFound.setDownloadURI("/artifact/" + artifactFound.getId() + "/download");
			final Artifact updatedArtifact = artifactRepository.saveAndFlush(artifactFound);
			return updatedArtifact;
		} else {
			return null;
		}
	}
}
