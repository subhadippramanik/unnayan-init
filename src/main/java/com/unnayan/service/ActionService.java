package com.unnayan.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableSet;
import com.unnayan.model.Action;
import com.unnayan.model.constants.ActionExecutionStatus;
import com.unnayan.repository.ActionRepository;

@Service
public class ActionService {

	private final ActionRepository actionRepository;

	@Autowired
	public ActionService(ActionRepository actionRepository) {
		this.actionRepository = actionRepository;
	}

	public Set<Action> findAllActions() {
		return ImmutableSet.copyOf(actionRepository.findAll());
	}

	public Action findActionById(Integer id) {
		return actionRepository.findOne(id);
	}

	public Action registerAction(Action action) {
		return actionRepository.save(action);
	}

	public Action updateExecutionStatus(Action action, ActionExecutionStatus actionExecutionStatus) {
		action.setExecutionStatus(actionExecutionStatus);
		return actionRepository.saveAndFlush(action);
	}

}
