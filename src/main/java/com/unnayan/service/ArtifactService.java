package com.unnayan.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unnayan.model.Artifact;
import com.unnayan.repository.ArtifactRepository;

@Service
public class ArtifactService {

	private final ArtifactRepository artifactRepository;

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
		return artifactRepository.save(artifact);
	}

	public Artifact update(Artifact artifactToUpdate) {
		Artifact artifactFound = findArtifactById(artifactToUpdate.getId());
		if(Objects.nonNull(artifactFound)) {
			artifactFound.setFileName(artifactToUpdate.getFileName());
			artifactFound.setPath(artifactToUpdate.getPath());
			return artifactRepository.save(artifactFound);
		} else {
			return findArtifactById(artifactToUpdate.getId());
		}
	}
}