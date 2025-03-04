package org.RecruitmentTask.service;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.RecruitmentTask.repository.Branch;
import org.RecruitmentTask.repository.Repository;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(baseUri = "https://api.github.com")
public interface GithubApi {


    @GET
    @Path("/users/{username}/repos")
    @Produces(MediaType.APPLICATION_JSON)
    List<Repository> getRepositories(@PathParam("username") String username);

    @GET
    @Path("/repos/{owner}/{repo}/branches")
    @Produces(MediaType.APPLICATION_JSON)
    List<Branch> getBranches(@PathParam("owner") String owner, @PathParam("repo") String repo);




}
