package org.RecruitmentTask.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.RecruitmentTask.dto.ErrorResponse;
import org.RecruitmentTask.dto.RepositoryResponse;
import org.RecruitmentTask.repository.Branch;
import org.RecruitmentTask.repository.Repository;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class GithubService {

    @Inject
    @RestClient
    private GithubApi githubApi;

    private ObjectMapper objectMapper = new ObjectMapper();

    public Uni<List<RepositoryResponse>> getUserRepositories(String username) {
        return Uni.createFrom().item(() -> fetchRepositories(username))
                .onFailure(WebApplicationException.class).recoverWithUni(e -> Uni.createFrom().failure(e))  // Propagate WebApplicationException
                .onFailure().recoverWithUni(e -> Uni.createFrom().failure(handleGenericException(e))); // Handle other exceptions
    }

    private List<RepositoryResponse> fetchRepositories(String username) {
        List<Repository> repositories = githubApi.getRepositories(username)
                .stream()
                .filter(repo -> !repo.fork)
                .collect(Collectors.toList());

        if (repositories.isEmpty()) {
            throw new WebApplicationException("No non-forked repositories found for user", Response.Status.NOT_FOUND);
        }

        return repositories.stream()
                .map(repo -> new RepositoryResponse(
                        repo.name,
                        repo.owner.login,
                        fetchBranches(username, repo.name)
                ))
                .collect(Collectors.toList());
    }

    private List<Branch> fetchBranches(String username, String repoName) {
        try {
            return githubApi.getBranches(username, repoName);
        } catch (Exception e) {
            return List.of();
        }
    }

    private WebApplicationException handleGenericException(Throwable e) {

        ErrorResponse errorResponse = new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage());


        try {
            String jsonResponse = objectMapper.writeValueAsString(errorResponse);
            // Create a WebApplicationException with the JSON response
            return new WebApplicationException(
                    Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity(jsonResponse) // Return the JSON string
                            .type("application/json") // Specify JSON type
                            .build()
            );
        } catch (IOException ioException) {
            return new WebApplicationException(
                    "Internal Server Error",
                    Response.Status.INTERNAL_SERVER_ERROR
            );
        }
    }
}
