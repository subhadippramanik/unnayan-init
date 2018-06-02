package com.unnayan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unnayan.model.Package;

@Repository
public interface PackageRepository extends JpaRepository<Package, Integer>{

}
