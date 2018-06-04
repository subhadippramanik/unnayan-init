package com.unnayan.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.unnayan.service.FileStorageService;

import io.swagger.annotations.ApiOperation;

@RestController
public class ArtifactController {

	private final ArtifactService artifactService;
	// TODO Move this call from controller to service
	private final FileStorageService fileStorageService;

	@Autowired
	public ArtifactController(ArtifactService artifactService, FileStorageService uploadService) {
		this.artifactService = artifactService;
		this.fileStorageService = uploadService;
	}

	@PostMapping("/artifact")
	@ApiOperation(value = "Create artifact")
	public ResponseEntity<Artifact> createArtifact(@RequestBody Artifact artifact) {
		final Artifact artifactCreated = artifactService.createArtifact(artifact.getName(), artifact.getVersion());
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
		} else if (Objects.isNull(artifactService.findArtifactById(id))) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else {
			final String fileName = StringUtils.isEmpty(optionalFileName) ? file.getOriginalFilename()
					: optionalFileName;
			final UploadModel uploadModel = new UploadModel(file, fileName);
			try {
				fileStorageService.storeFile(id, uploadModel);
				return ResponseEntity.status(HttpStatus.ACCEPTED).build();
			} catch (final Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		}
	}

	@GetMapping("/artifact/{id}/download")
	@ApiOperation(value = "Download artifact")
	public ResponseEntity<Resource> downloadArtifact(@PathVariable("id") Integer artifactId,
			HttpServletRequest request) {
		final String fileName = artifactService.findArtifactById(artifactId).getFileName();
		try {
			final Resource resource = fileStorageService.loadFileAsResource(fileName);
			final String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
			return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);
		} catch (MalformedURLException | FileNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (final IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/artifacts")
	@ApiOperation(value = "Get all artifacts")
	public ResponseEntity<Set<Artifact>> artifacts() {
		return ResponseEntity.status(HttpStatus.OK).body(artifactService.findAllArtifacts());
	}

	@GetMapping("/artifact/{id}")
	@ApiOperation(value = "Get artifact by Id")
	public ResponseEntity<Artifact> artifact(@PathVariable int id) {
		final Artifact artifact = artifactService.findArtifactById(id);
		if (Objects.nonNull(artifact)) {
			return ResponseEntity.status(HttpStatus.OK).body(artifact);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
