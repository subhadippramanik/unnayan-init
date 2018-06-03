package com.unnayan.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.google.common.collect.ImmutableSet;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Embeddable
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "version" }) })
public class Package {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(hidden = true)
	private Integer id;

	@ApiModelProperty(required = true)
	private String name;

	@ApiModelProperty(required = true)
	private String version;

	@OneToMany
	@ApiModelProperty(hidden = true)
	private Set<Artifact> artifacts;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Set<Artifact> getArtifacts() {
		return Objects.isNull(artifacts) ? Collections.emptySet() : ImmutableSet.copyOf(artifacts);
	}

	public void setArtifacts(Set<Artifact> artipacks) {
		if (Objects.isNull(artifacts)) {
			artifacts = new HashSet<>();
		}
		artifacts.addAll(artipacks);
	}

}
