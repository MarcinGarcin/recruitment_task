package org.RecruitmentTask;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.RecruitmentTask.dto.RepositoryResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

@QuarkusTest
public class GithubControllerTest {

    @Test
    public void testGetUserRepositories() {

        String username = "MarcinGarcin"; //My username


        Response response = given()
                .pathParam("username", username)
                .when().get("/github/repos/{username}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().response();


        List<RepositoryResponse> repositories = response.jsonPath().getList(".", RepositoryResponse.class);


        assertEquals(6, repositories.size(), "Expected one repository");


        RepositoryResponse repo = repositories.get(0);
        assertEquals("AiFromScratch", repo.repositoryName, "Repository name should be 'Hello-World'");
        assertEquals("MarcinGarcin", repo.repositoryOwnerLogin, "Repository owner should be 'octocat'");
    }
}
