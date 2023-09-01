package GitHubRepoInfo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import GitHubRepoInfo.model.Repository;

public interface GitHubRepoInfoService {
	ResponseEntity<Object> getUserRepositories(String username, String acceptHeader);

	List<Repository> filterNonForkRepositories(List<Repository> repositories);

}
