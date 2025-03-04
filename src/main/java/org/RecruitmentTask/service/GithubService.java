package org.RecruitmentTask.service;


import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.RecruitmentTask.dto.BranchResponse;
import org.RecruitmentTask.dto.RepositoryResponse;
import org.RecruitmentTask.repository.Branch;
import org.RecruitmentTask.repository.Repository;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class GithubService {

    @Inject
    @RestClient
    private GithubApi githubApi;

    public Uni<List<RepositoryResponse>> getUserRepositories(String username) {
        return Uni.createFrom().deferred(() -> {
            try {
                List<Repository> repositories = githubApi.getRepositories(username)
                        .stream()
                        .filter(repo -> !repo.fork)
                        .collect(Collectors.toList());

                if (repositories.isEmpty()) {
                    throw new WebApplicationException(
                            "No non-forked repositories found for user",
                            Response.Status.NOT_FOUND
                    );
                }


                return Uni.createFrom().item(
                        repositories.stream()
                                .map(repo -> {
                                    try {
                                        List<Branch> branches = githubApi.getBranches(
                                                repo.owner.login,
                                                repo.name
                                        );
                                        branches = branches != null ? branches : Collections.emptyList();

                                        return new RepositoryResponse(
                                                repo.name,
                                                repo.owner.login,
                                                branches
                                        );
                                    } catch (Exception e) {

                                        return new RepositoryResponse(
                                                repo.name,
                                                repo.owner.login,
                                                Collections.emptyList()
                                        );
                                    }
                                })
                                .collect(Collectors.toList())
                );
            } catch (WebApplicationException e) {
                return Uni.createFrom().failure(e);
            }
        });
    }
}
