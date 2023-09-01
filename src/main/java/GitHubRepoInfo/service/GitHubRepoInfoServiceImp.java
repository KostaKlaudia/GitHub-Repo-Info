package GitHubRepoInfo.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import GitHubRepoInfo.exception.GitHubErrorResponse;
import GitHubRepoInfo.model.Repository;

@Service
public class GitHubRepoInfoServiceImp implements GitHubRepoInfoService {
	private final RestTemplate restTemplate;
	private final String githubApiBaseUrl = "https://api.github.com";

	public GitHubRepoInfoServiceImp(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}

	@Override
	public ResponseEntity<Object> getUserRepositories(String username, String acceptHeader) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", acceptHeader);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		ResponseEntity<Repository[]> response = restTemplate.exchange(
				githubApiBaseUrl + "/users/" + username + "/repos", HttpMethod.GET, entity, Repository[].class);

		if (response.getStatusCode() == HttpStatus.OK) {
			Repository[] repositories = response.getBody();
			List<Repository> nonForkRepositories = filterNonForkRepositories(Arrays.asList(repositories));

			if (acceptHeader.equals("application/json")) {
				return new ResponseEntity<>(nonForkRepositories, HttpStatus.OK);
			}
		} else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
			GitHubErrorResponse errorResponse = new GitHubErrorResponse("Not Found", "User not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
		return new ResponseEntity<>("Niewłaściwy nagłówek 'Accept'.", HttpStatus.NOT_ACCEPTABLE);
	}

	@Override
	public List<Repository> filterNonForkRepositories(List<Repository> repositories) {
		return repositories.stream().filter(repo -> !repo.isFork()).collect(Collectors.toList());
	}
}
