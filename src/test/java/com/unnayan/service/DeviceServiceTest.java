package com.unnayan.service;

import static org.junit.Assert.assertThat;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.unnayan.model.Action;
import com.unnayan.model.Device;
import com.unnayan.model.Package;
import com.unnayan.repository.DeviceRepository;

@RunWith(MockitoJUnitRunner.class)
public class DeviceServiceTest {

	private final String identity = "TestDeviceIdentity";

	@Mock
	private DeviceRepository deviceRepository;

	@Mock
	private ActionService actionService;

	@Mock
	private PackageService packageService;

	@Mock
	private Device myTestDevice;

	@Mock
	private Device otherDevice;

	@Mock
	private Package myTestPackage;

	@InjectMocks
	private DeviceService testee;

	@Test
	public void findAllDevices_ExecutesFindAllOnRepository_Success() {
		// act
		testee.findAllDevices();

		// assert
		Mockito.verify(deviceRepository, Mockito.times(1)).findAll();
	}

	@Test
	public void findDeviceById_ExecutesFindOneOnRepository_Success() {
		// act
		testee.findDeviceById(1);

		// assert
		Mockito.verify(deviceRepository, Mockito.times(1)).findOne(1);
	}

	@Test
	public void findDeviceByIdentity_ExecutesFindAllOnRepositoryDeviceFound_Success() {
		// arrange
		Mockito.when(deviceRepository.findAll()).thenReturn(ImmutableList.of(myTestDevice, otherDevice));
		Mockito.when(myTestDevice.getIdentity()).thenReturn(identity);

		// act
		final Device actualDeviceFound = testee.findDeviceByIdentity(identity);

		// assert
		Mockito.verify(deviceRepository, Mockito.times(1)).findAll();
		assertThat(actualDeviceFound, CoreMatchers.equalTo(myTestDevice));
	}

	@Test
	public void findDeviceByIdentity_ExecutesFindAllOnRepositoryDeviceNotFound_Success() {
		// arrange
		Mockito.when(deviceRepository.findAll()).thenReturn(ImmutableList.of());
		Mockito.when(myTestDevice.getIdentity()).thenReturn(identity);

		// act
		final Device actualDeviceFound = testee.findDeviceByIdentity(identity);

		// assert
		Mockito.verify(deviceRepository, Mockito.times(1)).findAll();
		assertThat(actualDeviceFound, CoreMatchers.nullValue());
	}

	@Test
	public void registerDevice_NameSetIfNullAndExecutesSaveOnRepository_Success() {
		// arrange
		Mockito.when(myTestDevice.getIdentity()).thenReturn(identity);
		// act
		testee.registerDevice(myTestDevice);

		// assert
		Mockito.verify(myTestDevice, Mockito.times(1)).setName(identity);
		Mockito.verify(deviceRepository, Mockito.times(1)).save(myTestDevice);
	}

	@Test
	public void registerDevice_NameNotSetIfNotNullAndExecutesSaveOnRepository_Success() {
		// arrange
		Mockito.when(myTestDevice.getName()).thenReturn("SomeName");
		// act
		testee.registerDevice(myTestDevice);

		// assert
		Mockito.verify(myTestDevice, Mockito.times(0)).setName(identity);
		Mockito.verify(deviceRepository, Mockito.times(1)).save(myTestDevice);
	}

	@Test
	public void assignPackages_ValidPackageId_CreateActionAndExecutesSaveOnRepository_Success() {
		// arrange
		final Integer validPackageId = 1;
		Mockito.when(packageService.findPackageById(validPackageId)).thenReturn(myTestPackage);

		// act
		testee.assignPackages(myTestDevice, ImmutableSet.of(validPackageId));

		// assert
		Mockito.verify(packageService, Mockito.times(1)).findPackageById(validPackageId);
		Mockito.verify(actionService, Mockito.times(1)).registerAction(Mockito.any(Action.class));
		Mockito.verify(deviceRepository, Mockito.times(1)).saveAndFlush(myTestDevice);
	}

	@Test
	public void assignPackages_InvalidPackageId_NoActionAndExecutionOfSaveOnRepository_Success() {
		// arrange
		final Integer invalidPackageId = 1;
		Mockito.when(packageService.findPackageById(invalidPackageId)).thenReturn(null);

		// act
		testee.assignPackages(myTestDevice, ImmutableSet.of(invalidPackageId));

		// assert
		Mockito.verify(packageService, Mockito.times(1)).findPackageById(invalidPackageId);
		Mockito.verify(actionService, Mockito.times(0)).registerAction(Mockito.any(Action.class));
		Mockito.verify(deviceRepository, Mockito.times(1)).saveAndFlush(myTestDevice);
	}

}
