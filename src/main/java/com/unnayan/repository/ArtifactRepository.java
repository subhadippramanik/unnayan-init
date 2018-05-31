package com.unnayan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unnayan.model.Artifact;

@Repository
public interface ArtifactRepository  extends JpaRepository<Artifact, Integer>{

}
