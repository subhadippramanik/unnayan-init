package com.unnayan.controller;

import java.util.List;
import java.util.Objects;

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

import io.swagger.annotations.ApiOperation;

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
	@ApiOperation(value = "Create artifact")
	public ResponseEntity<Artifact> createArtifact(@RequestBody Artifact artifact) {
		Artifact artifactCreated = artifactService.createArtifact(artifact.getName(), artifact.getVersion());
		if (Objects.nonNull(artifactCreated)) {
			return ResponseEntity.status(HttpStatus.CREATED).body(artifactCreated);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping("/artifact/{id}/upload")
	@ApiOperation(value = "Upload file to artifact")
	public ResponseEntity<Artifact> uploadFileToArtifact(@PathVariable("id") final int id,
			@RequestParam("file") final MultipartFile file,
			@RequestParam(value = "filename", required = false) final String optionalFileName) {
		if (file.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}else if(Objects.isNull(artifactService.findArtifactById(id))) { 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}else {
			String fileName = StringUtils.isEmpty(optionalFileName) ? file.getOriginalFilename() : optionalFileName;
			UploadModel uploadModel = new UploadModel(file, fileName);
			try {
				uploadService.uploadFile(id, uploadModel);
				return ResponseEntity.status(HttpStatus.ACCEPTED).build();
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		}
	}

	@GetMapping("/artifacts")
	@ApiOperation(value = "Get all artifacts")
	public ResponseEntity<List<Artifact>> artifacts() {
		return ResponseEntity.status(HttpStatus.OK).body(artifactService.findAllArtifacts());
	}

	@GetMapping("/artifact/{id}")
	@ApiOperation(value = "Get artifact by Id")
	public ResponseEntity<Artifact> artifact(@PathVariable int id) {
		Artifact artifact = artifactService.findArtifactById(id);
		if (Objects.nonNull(artifact)) {
			return ResponseEntity.status(HttpStatus.OK).body(artifact);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
