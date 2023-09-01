package GitHubRepoInfo.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import GitHubRepoInfo.service.GitHubRepoInfoServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/github")
public class GitHubRepoInfoController {

	private final GitHubRepoInfoServiceImp gitHubRepoInfoService;

	public GitHubRepoInfoController(GitHubRepoInfoServiceImp gitHubRepoInfoService) {
		this.gitHubRepoInfoService = gitHubRepoInfoService;
	}

	@Operation(summary = "It will find all repositories that belongs to a given user, that are not forks.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Finds all repositories which are not forks and belongs to given user.", headers = {
					@Header(name = "location", description = "http://localhost:8080/api/v1/books") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@GetMapping("/repositories/{username}")
	public ResponseEntity<Object> getUserRepositories(@PathVariable String username,
			@RequestHeader("Accept") String acceptHeader) {
		return gitHubRepoInfoService.getUserRepositories(username, acceptHeader);
	}
}
