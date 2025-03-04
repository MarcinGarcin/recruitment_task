package org.RecruitmentTask.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.RecruitmentTask.repository.Commit;

public class BranchResponse {
    @JsonProperty("name")
    public String name;

    @JsonProperty("commit")
    public Commit commit;

    @Override
    public String toString() {
        return String.format(
                "{\"name\": \"%s\", \"commit\": {\"sha\": \"%s\"}}",
                name,
                commit.sha
        );
    }
}
