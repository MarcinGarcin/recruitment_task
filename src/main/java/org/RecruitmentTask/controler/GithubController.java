package org.RecruitmentTask.controler;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.RecruitmentTask.dto.RepositoryResponse;
import org.RecruitmentTask.service.GithubService;

import java.util.List;

@Path("/github")
public class GithubController {


    @Inject
    private GithubService githubService;

    @GET
    @Path("/repos/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @Blocking
    public Uni<List<RepositoryResponse>> getUserRepositories(@PathParam("username") String username) {
        return githubService.getUserRepositories(username);
    }
}
