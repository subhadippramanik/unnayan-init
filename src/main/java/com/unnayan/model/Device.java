package com.unnayan.model;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "identity" }) })
public class Device {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(hidden = true)
	private Integer id;

	@ApiModelProperty(required = false, allowEmptyValue = false)
	private String name;

	@ApiModelProperty(required = true, allowEmptyValue = false)
	private String identity;

	@OneToMany
	@ApiModelProperty(hidden = true)
	private Set<Action> actions;

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

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public Set<Action> getActions() {
		return actions;
	}

	public void setActions(Set<Action> actions) {
		this.actions = Objects.isNull(this.actions) ? actions : this.actions;
		this.actions.addAll(actions);
	}

}
