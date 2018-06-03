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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.unnayan.model.Device;
import com.unnayan.service.DeviceService;

import io.swagger.annotations.ApiOperation;

@RestController
public class DeviceController {

	private final DeviceService deviceService;

	@Autowired
	public DeviceController(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	@GetMapping("/devices")
	@ApiOperation(value = "Get all devices")
	public ResponseEntity<List<Device>> getAllDevices() {
		return ResponseEntity.status(HttpStatus.OK).body(deviceService.findAllDevices());
	}

	@GetMapping("/device/{id}")
	@ApiOperation(value = "Get device by id")
	public ResponseEntity<Device> getDevice(@PathVariable("id") Integer id) {
		final Device device = deviceService.findDeviceById(id);
		if (Objects.nonNull(device)) {
			return ResponseEntity.status(HttpStatus.OK).body(device);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping("/device/{identity}")
	@ApiOperation(value = "Get device by identity")
	public ResponseEntity<Device> getDevice(@PathVariable("identity") String identity) {
		final Device device = deviceService.findDeviceByIdentity(identity);
		if (Objects.nonNull(device)) {
			return ResponseEntity.status(HttpStatus.OK).body(device);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PostMapping("/device")
	@ApiOperation(value = "Register device")
	public ResponseEntity<Device> registerDevice(@RequestBody Device device) {
		final Device deviceFound = deviceService.findDeviceByIdentity(device.getIdentity());
		if (Objects.nonNull(deviceFound)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		final Device deviceRegistered = deviceService.registerDevice(device);
		if (Objects.nonNull(deviceRegistered)) {
			return ResponseEntity.status(HttpStatus.CREATED).body(device);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping("/device/{identity}/action")
	@ApiOperation(value = "Assign actions to device")
	public ResponseEntity<Device> assignActions(@PathVariable String identity, @RequestBody Set<Integer> actionIDs) {
		final Device device = deviceService.findDeviceByIdentity(identity);
		if (Objects.isNull(device)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		final Device updatedDevice = deviceService.assignActions(device, actionIDs);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedDevice);
	}

	@PutMapping("/device/{identity}/package")
	@ApiOperation(value = "Assign actions to device")
	public ResponseEntity<Device> assignPackages(@PathVariable String identity, @RequestBody Set<Integer> packageIDs) {
		final Device device = deviceService.findDeviceByIdentity(identity);
		if (Objects.isNull(device)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		final Device updatedDevice = deviceService.assignPackages(device, packageIDs);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedDevice);
	}
}
