package com.unnayan.controller;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.unnayan.model.Package;
import com.unnayan.service.PackageService;

import io.swagger.annotations.ApiOperation;

@RestController
public class PackageController {

	private final PackageService packageService;
	
	@Autowired
	public PackageController(PackageService packageService) {
		this.packageService = packageService;
	}
	
	@GetMapping("/packages")
	@ApiOperation(value = "Get all packages")
	public ResponseEntity<List<Package>> getAllPackages() {
		return ResponseEntity.status(HttpStatus.OK).body(packageService.findAllPackages());
	}
	
	@GetMapping("/package/{id}")
	@ApiOperation(value = "Get package") 
	public ResponseEntity<Package> getPackage(@PathVariable int id) {
		Package packageFound = packageService.findPackageById(id);
		if(Objects.nonNull(packageFound)) {
			return ResponseEntity.status(HttpStatus.OK).body(packageFound);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@PostMapping("/package")
	@ApiOperation(value = "Create Package")
	public ResponseEntity<Package> createPackage(@RequestBody Package packageToCreate) {
		Package packageCreated = packageService.createPackage(packageToCreate);
		if(Objects.nonNull(packageCreated)) {
			return ResponseEntity.status(HttpStatus.CREATED).body(packageCreated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@PostMapping("/package/{id}/artipack")
	@ApiOperation(value="Create Artipack")
	public ResponseEntity<Package> createArtipack(@PathVariable Integer id, @RequestBody Set<Integer> artifactIDs) {
		Package packageUpdated = packageService.createArtipack(id, artifactIDs);
		if(Objects.nonNull(packageUpdated)) {
			return ResponseEntity.status(HttpStatus.CREATED).body(packageUpdated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
