package com.unnayan.service;

import static org.junit.Assert.assertThat;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.unnayan.model.Artifact;
import com.unnayan.repository.ArtifactRepository;

@RunWith(MockitoJUnitRunner.class)
public class ArtifactServiceTest {

	@Mock
	private ArtifactRepository artifactRepository;

	@Mock
	private Artifact testArtifact;

	@InjectMocks
	private ArtifactService testee;

	@Test
	public void findAllArtifacts_ExecutesFindAllOnRepository_Success() {
		// act
		testee.findAllArtifacts();

		// assert
		Mockito.verify(artifactRepository, Mockito.times(1)).findAll();
	}

	@Test
	public void findArtifactById_ExecutesFindOneOnRepository_Success() {
		// act
		testee.findArtifactById(1);

		// aseert
		Mockito.verify(artifactRepository, Mockito.times(1)).findOne(1);
	}

	@Test
	public void createArtifact_ExecutesSaveOnRepository_Success() {
		// act
		testee.createArtifact("TestArtifact", "0.0.1-SNAPSHOT");

		// assert
		Mockito.verify(artifactRepository, Mockito.times(1)).saveAndFlush(Mockito.<Artifact>any());
	}

	@Test
	public void addFileInfo_SetsFileInfoOnArtifactAndExecutesSaveAndFlushOnRepository_Success() {
		// assert
		Mockito.when(testArtifact.getId()).thenReturn(1);
		Mockito.when(artifactRepository.findOne(1)).thenReturn(testArtifact);

		// act
		testee.addFileInfo(testArtifact);

		// assert
		Mockito.verify(testArtifact, Mockito.times(1)).setFileName(Mockito.anyString());
		Mockito.verify(testArtifact, Mockito.times(1)).setPath(Mockito.anyString());
		Mockito.verify(testArtifact, Mockito.times(1)).setDownloadURI(Mockito.anyString());
		Mockito.verify(artifactRepository, Mockito.times(1)).saveAndFlush(Mockito.<Artifact>any());
	}

	@Test
	public void addFileInfo_InvalidArtifactId_NoSaveOnRepositoryAndReturnNull_Success() {
		// assert
		Mockito.when(testArtifact.getId()).thenReturn(1);
		Mockito.when(artifactRepository.findOne(1)).thenReturn(null);

		// act
		final Artifact updatedArtifact = testee.addFileInfo(testArtifact);

		// assert
		Mockito.verify(testArtifact, Mockito.times(0)).setFileName(Mockito.anyString());
		Mockito.verify(testArtifact, Mockito.times(0)).setPath(Mockito.anyString());
		Mockito.verify(testArtifact, Mockito.times(0)).setDownloadURI(Mockito.anyString());
		Mockito.verify(artifactRepository, Mockito.times(0)).saveAndFlush(Mockito.<Artifact>any());
		assertThat(updatedArtifact, CoreMatchers.nullValue());
	}

}
