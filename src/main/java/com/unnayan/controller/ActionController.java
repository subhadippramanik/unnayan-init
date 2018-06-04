package com.unnayan.controller;

import java.util.Objects;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.unnayan.model.Action;
import com.unnayan.model.constants.ActionExecutionStatus;
import com.unnayan.service.ActionService;

import io.swagger.annotations.ApiOperation;

@RestController
public class ActionController {

	private final ActionService actionService;
	private final Logger LOGGER = LogManager.getLogger(getClass());

	@Autowired
	public ActionController(ActionService actionService) {
		this.actionService = actionService;
	}

	@GetMapping("/actions")
	@ApiOperation(value = "Get all actions")
	public Set<Action> getAllActions() {
		return actionService.findAllActions();
	}

	@GetMapping("/action/{id}")
	@ApiOperation(value = "Get action by action id")
	public ResponseEntity<Action> getActionById(@PathVariable("id") Integer id) {
		final Action action = actionService.findActionById(id);
		if (Objects.nonNull(action)) {
			return ResponseEntity.status(HttpStatus.OK).body(action);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PutMapping("/action/{id}")
	@ApiOperation(value = "Update action execution status")
	public ResponseEntity<Action> updateActionExecutionStatus(@PathVariable("id") Integer actionId,
			@RequestBody ActionExecutionStatus actionExecutionStatus) {
		final Action action = actionService.findActionById(actionId);
		if (Objects.isNull(action)) {
			LOGGER.error("No action found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		final Action actionUpdated = actionService.updateExecutionStatus(action, actionExecutionStatus);
		if (Objects.nonNull(actionUpdated)) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(actionUpdated);
		} else
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

}
