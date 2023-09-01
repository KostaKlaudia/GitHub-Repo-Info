package GitHubRepoInfo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import GitHubRepoInfo.model.Repository;

@ExtendWith(MockitoExtension.class)
class GitHubRepoInfoServiceImpTest {

	@InjectMocks
	private GitHubRepoInfoServiceImp gitHubRepoInfoService;

	@Mock
	private RestTemplate restTemplate;

	private final String githubApiBaseUrl = "https://api.github.com";

	@Test
	public void testGetUserRepositories_Success() {
		// Arrange
		String username = "testuser";
		String acceptHeader = "application/json";
		Repository[] mockRepositories = new Repository[] { new Repository(), new Repository() };
		ResponseEntity<Repository[]> mockResponse = new ResponseEntity<>(mockRepositories, HttpStatus.OK);
		Mockito.when(restTemplate.exchange(eq(githubApiBaseUrl + "/users/" + username + "/repos"), eq(HttpMethod.GET),
				any(HttpEntity.class), eq(Repository[].class))).thenReturn(mockResponse);

		// Act
		ResponseEntity<Object> responseEntity = gitHubRepoInfoService.getUserRepositories(username, acceptHeader);
		List<Repository> nonForkRepositories = (List<Repository>) responseEntity.getBody();

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(2, nonForkRepositories.size());
	}

	@Test
	public void testGetUserRepositories_UserNotFound() {
		// Arrange
		String username = "nonexistentuser";
		String acceptHeader = "application/json";
		ResponseEntity<Repository[]> mockResponse = new ResponseEntity<>(HttpStatus.NOT_FOUND);

		Mockito.when(restTemplate.exchange(eq(githubApiBaseUrl + "/users/" + username + "/repos"), eq(HttpMethod.GET),
				any(HttpEntity.class), eq(Repository[].class))).thenReturn(mockResponse);

		// Act
		ResponseEntity<Object> responseEntity = gitHubRepoInfoService.getUserRepositories(username, acceptHeader);

		// Assert
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

	@Test
	public void testFilterNonForkRepositories() {
		// Arrange
		Repository repo1 = new Repository();
		repo1.setFork(true);

		Repository repo2 = new Repository();
		repo2.setFork(false);

		Repository repo3 = new Repository();
		repo3.setFork(false);

		List<Repository> repositories = Arrays.asList(repo1, repo2, repo3);

		// Act
		List<Repository> nonForkRepositories = gitHubRepoInfoService.filterNonForkRepositories(repositories);

		// Assert
		assertEquals(2, nonForkRepositories.size());
		assertEquals(repo2, nonForkRepositories.get(0));
		assertEquals(repo3, nonForkRepositories.get(1));
	}
}
