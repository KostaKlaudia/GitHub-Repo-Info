package GitHubRepoInfo.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import GitHubRepoInfo.model.Owner;
import GitHubRepoInfo.model.Repository;
import GitHubRepoInfo.service.GitHubRepoInfoServiceImp;

@WebMvcTest
class GitHubRepoInfoControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GitHubRepoInfoServiceImp gitHubRepoInfoService;

	@Test
	public void testGetUserRepositories() throws Exception {
		// arrange
		String username = "testuser";
		String acceptHeader = "application/json";

		List<Repository> repositories = new ArrayList<>();

		Repository repo1 = new Repository();
		repo1.setName("Repo1");
		Owner owner = new Owner();
		owner.setLogin("user1");
		repo1.setOwner(owner);

		Repository repo2 = new Repository();
		repo2.setName("Repo2");
		Owner owner2 = new Owner();
		owner.setLogin("user2");
		repo2.setOwner(owner2);

		repositories.add(repo1);
		repositories.add(repo2);

		ResponseEntity<Object> responseEntity = new ResponseEntity<>(repositories, HttpStatus.OK);

		Mockito.when(gitHubRepoInfoService.getUserRepositories(username, acceptHeader)).thenReturn(responseEntity);

		// act
		ResultActions response = mockMvc
				.perform(get("/api/github/repositories/{username}", username).header("Accept", acceptHeader));

		// assert
		response.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(repositories.size())))
				.andExpect(jsonPath("$[0].name", is(repositories.get(0).getName())))
				.andExpect(jsonPath("$[1].name", is(repositories.get(1).getName())));

	}
}
