package com.unnayan.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"name" , "version"})})
public class Artifact {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(hidden = true)
	private Integer id;
	
	@ApiModelProperty(required = true)
	private String name;
	
	@ApiModelProperty(required = true)
	private String version;
	
	@ApiModelProperty(hidden = true)
	private String fileName;
	
	@ApiModelProperty(hidden = true)
	private String fileSha;
	
	@ApiModelProperty(hidden = true)
	private String path;
	
	@ApiModelProperty(hidden = true)
	private String downloadURI;
	
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSha() {
		return fileSha;
	}

	public void setFileSha(String fileSha) {
		this.fileSha = fileSha;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDownloadURI() {
		return downloadURI;
	}

	public void setDownloadURI(String downloadURI) {
		this.downloadURI = downloadURI;
	}
}
