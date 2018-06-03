package com.unnayan.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;
import com.unnayan.model.Action;
import com.unnayan.model.Device;
import com.unnayan.repository.DeviceRepository;

@Service
public class DeviceService {

	private final DeviceRepository deviceRepository;
	private final ActionService actionService;
	private final PackageService packageService;

	@Autowired
	public DeviceService(DeviceRepository deviceRepository, ActionService actionService,
			PackageService packageService) {
		this.deviceRepository = deviceRepository;
		this.actionService = actionService;
		this.packageService = packageService;
	}

	public List<Device> findAllDevices() {
		return deviceRepository.findAll();
	}

	public Device findDeviceById(Integer id) {
		return deviceRepository.findOne(id);
	}

	public Device findDeviceByIdentity(String identity) {
		final Optional<Device> optionalDevice = getDeviceByIdentityIfPresent(identity);
		return optionalDevice.isPresent() ? optionalDevice.get() : null;
	}

	private Optional<Device> getDeviceByIdentityIfPresent(String identity) {
		final Optional<Device> optionalDevice = deviceRepository.findAll().stream()//
				.filter(device -> device.getIdentity().equals(identity))//
				.findAny();
		return optionalDevice;
	}

	public Device registerDevice(Device device) {
		if (Objects.isNull(device.getName())) {
			device.setName(device.getIdentity());
		}
		return deviceRepository.save(device);
	}

	public Device assignPackages(Device device, Set<Integer> packageIDs) {
		final Set<Action> actionsCreated = Sets.newHashSet();
		packageIDs.stream()//
				.map(packageId -> packageService.findPackageById(packageId))//
				.forEach(packageToAssign -> {
					final Action action = new Action();
					action.setAssignedPackage(packageToAssign);
					actionsCreated.add(actionService.registerAction(action));
				});
		device.setActions(actionsCreated);
		deviceRepository.saveAndFlush(device);
		return findDeviceById(device.getId());
	}

	public Device assignActions(Device device, Set<Integer> actionIDs) {
		actionIDs.stream()//
				.map(actionId -> actionService.findActionById(actionId))//
				.forEach(action -> device.setActions(Sets.newHashSet(action)));
		return deviceRepository.saveAndFlush(device);
	}

}
