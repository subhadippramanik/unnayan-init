package com.unnayan.model.constants;

public enum ActionExecutionStatus {

	ASSIGNED("ASSIGNED"), //
	REQUEST_CANCEL("REQUEST_CANCEL"), //
	FORCE_CANCEL("FORCE_CANCEL"), //
	CANCELLED("CANCELLED"), //
	FINISHED_WITH_SUCCESS("FINISHED_WITH_SUCCESS"), //
	FINISHED_WITH_ERROR("FINISHED_WITH_ERROR");

	private final String executionStatus;

	private ActionExecutionStatus(String status) {
		executionStatus = status;
	}

	@Override
	public String toString() {
		return this.executionStatus;
	}
}
