package com.unnayan.service;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;

import java.util.Set;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;
import com.unnayan.model.Action;
import com.unnayan.model.constants.ActionExecutionStatus;
import com.unnayan.repository.ActionRepository;

@RunWith(MockitoJUnitRunner.class)
public class ActionServiceTest {

	@Mock
	private ActionRepository actionRepository;

	@Mock
	private Action actionMocked;

	@InjectMocks
	private ActionService testee;

	@Test
	public void findAllActions_ListOfActions() {
		// arrange
		Mockito.when(actionRepository.findAll()).thenReturn(Lists.newArrayList(actionMocked));

		// act
		final Set<Action> actions = testee.findAllActions();

		// assert
		Mockito.verify(actionRepository, Mockito.times(1)).findAll();
		assertThat("List of action not returned", actions, hasItem(actionMocked));
	}

	@Test
	public void findActionById_Success() {
		// arrange
		Mockito.when(actionRepository.findOne(1)).thenReturn(actionMocked);
		Mockito.when(actionMocked.getId()).thenReturn(1);

		// act
		final Action actual = testee.findActionById(1);

		// assert
		Mockito.verify(actionRepository, Mockito.times(1)).findOne(1);
		assertThat(actual, CoreMatchers.notNullValue());
		assertThat(actual.getId(), CoreMatchers.equalTo(1));
	}

	@Test
	public void registerAction_ExecutesSaveOnRepository_Success() {
		// act
		testee.registerAction(actionMocked);

		// assert
		Mockito.verify(actionRepository, Mockito.times(1)).save(actionMocked);
	}

	@Test
	public void updateExecutionStatus_UpdatesStatusOnObjectAndSaveOnRepository_Success() {
		// act
		testee.updateExecutionStatus(actionMocked, ActionExecutionStatus.CANCELLED);

		// assert
		Mockito.verify(actionMocked, Mockito.times(1)).setExecutionStatus(ActionExecutionStatus.CANCELLED);
		Mockito.verify(actionRepository, Mockito.times(1)).saveAndFlush(actionMocked);
	}

}
