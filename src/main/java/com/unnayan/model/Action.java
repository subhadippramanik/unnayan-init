package com.unnayan.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.unnayan.model.constants.ActionExecutionStatus;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Action {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne
	private Package assignedPackage;

	@ApiModelProperty(allowableValues = "ASSIGNED,REQUEST_CANCEL,FORCE_CANCEL,CANCELLED,FINISHED_WITH_SUCCESS,FINISHED_WITH_ERROR")
	private ActionExecutionStatus executionStatus = ActionExecutionStatus.ASSIGNED;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Package getAssignedPackage() {
		return assignedPackage;
	}

	public void setAssignedPackage(Package assignedPackage) {
		this.assignedPackage = assignedPackage;
	}

	public ActionExecutionStatus getExecutionStatus() {
		return executionStatus;
	}

	public void setExecutionStatus(ActionExecutionStatus executionStatus) {
		this.executionStatus = executionStatus;
	}

}
