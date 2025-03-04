package org.RecruitmentTask.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.RecruitmentTask.repository.Branch;
import org.RecruitmentTask.repository.Owner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositoryResponse {
    @JsonProperty("repositoryName")
    public String repositoryName;

    @JsonProperty("repositoryOwnerLogin")
    public String repositoryOwnerLogin;

    @JsonProperty("branches")
    public List<Branch> branches ;

    public RepositoryResponse(String repositoryName, String repositoryOwner, List<Branch> branches) {
        this.repositoryName = repositoryName;
        this.repositoryOwnerLogin = repositoryOwner;
        this.branches = branches;
    }

    @Override
    public String toString() {
        return String.format(
                "{"
                        + "\"repositoryName\": \"%s\", "
                        + "\"repositoryOwnerLogin\": {\"login\": \"%s\"}, "
                        + "\"branches\": [%s]"
                        + "}",
                repositoryName,
                repositoryOwnerLogin != null ? repositoryOwnerLogin : "null",
                branches != null ? branches.stream()
                        .map(branch -> branch != null && branch.lastCommit != null
                                ? String.format(
                                "{\"name\": \"%s\", \"commit\": {\"sha\": \"%s\"}}",
                                branch.name != null ? branch.name : "",
                                branch.lastCommit.sha != null ? branch.lastCommit.sha : ""
                        )
                                : "{}")
                        .collect(Collectors.joining(", "))
                        : ""
        );
    }
}
