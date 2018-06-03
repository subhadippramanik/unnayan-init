package com.unnayan.service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unnayan.model.Artifact;
import com.unnayan.model.Package;
import com.unnayan.repository.PackageRepository;

@Service
public class PackageService {

	private final PackageRepository packageRepository;
	private final ArtifactService artifactService;
	private final Logger LOGGER = LogManager.getLogger(getClass());

	@Autowired
	public PackageService(PackageRepository packageRepository, ArtifactService artifactService) {
		this.packageRepository = packageRepository;
		this.artifactService = artifactService;
	}

	public List<Package> findAllPackages() {
		return packageRepository.findAll();
	}

	public Package findPackageById(Integer id) {
		return packageRepository.findOne(id);
	}

	public Package createPackage(Package packageToCreate) {
		Package savedPackage = packageRepository.save(packageToCreate);
		packageRepository.flush();
		LOGGER.info("createPackage: " + packageRepository);
		return savedPackage;
	}

	public Package createArtipack(Integer packageId, Set<Integer> artifactIDs) {
		final Package packageById = findPackageById(packageId);
		Set<Artifact> artifacts = artifactIDs.stream()//
				.map(artifactId -> artifactService.findArtifactById(artifactId))//
				.filter(artifact -> Objects.nonNull(artifact))//
				.collect(Collectors.toSet());
		packageById.setArtifacts(artifacts);
		packageRepository.saveAndFlush(packageById);
		return findPackageById(packageId);
	}

}
