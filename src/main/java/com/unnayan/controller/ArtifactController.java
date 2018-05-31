package com.unnayan.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.unnayan.model.Artifact;
import com.unnayan.model.UploadModel;
import com.unnayan.service.ArtifactService;
import com.unnayan.service.UploadService;

@RestController
public class ArtifactController {

	private final ArtifactService artifactService;
	private final UploadService uploadService;

	@Autowired
	public ArtifactController(ArtifactService artifactService, UploadService uploadService) {
		this.artifactService = artifactService;
		this.uploadService = uploadService;
	}

	@PostMapping("/artifact")
	public Artifact createArtifact(@RequestBody Artifact artifact) {
		return artifactService.createArtifact(artifact.getName(), artifact.getVersion());
	}

	@PostMapping("/artifact/{id}/upload")
	public ResponseEntity<Artifact> uploadFileToArtifact(@PathVariable("id") final int id, @RequestParam("file") final MultipartFile file,
			@RequestParam(value = "filename", required = false) final String optionalFileName) {
		if(file.isEmpty()) {
			return ResponseEntity.badRequest().build();
		} else {
			String fileName = StringUtils.isEmpty(optionalFileName) ? file.getOriginalFilename() : optionalFileName;
			UploadModel uploadModel = new UploadModel(file, fileName);
			try {
				uploadService.uploadFile(id, uploadModel);
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
			
		}
		return null;

	}

	@GetMapping("/artifacts")
	public List<Artifact> artifacts() {
		return artifactService.findAllArtifacts();
	}

	@GetMapping("/artifact/{id}")
	public Artifact artifact(@PathVariable int id) {
		return artifactService.findArtifactById(id);
	}
}
