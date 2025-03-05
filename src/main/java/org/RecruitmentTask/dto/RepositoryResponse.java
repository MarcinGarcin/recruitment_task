package org.RecruitmentTask.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.RecruitmentTask.repository.Branch;

import java.util.List;

public class RepositoryResponse {
    @JsonProperty("repositoryName")
    public String repositoryName;

    @JsonProperty("repositoryOwnerLogin")
    public String repositoryOwnerLogin;

    @JsonProperty("branches")
    public List<Branch> branches;

    public RepositoryResponse(String repositoryName, String repositoryOwner, List<Branch> branches) {
        this.repositoryName = repositoryName;
        this.repositoryOwnerLogin = repositoryOwner;
        this.branches = branches;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            return "{\"error\": \"Failed to serialize RepositoryResponse\"}";
        }
    }
}
