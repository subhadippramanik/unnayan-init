package com.unnayan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unnayan.model.Device;

public interface DeviceRepository extends JpaRepository<Device, Integer> {

}
