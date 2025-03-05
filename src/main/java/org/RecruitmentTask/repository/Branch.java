package org.RecruitmentTask.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Branch {
    public String name;

    @JsonProperty("commit")
    private Commit commit;

}

