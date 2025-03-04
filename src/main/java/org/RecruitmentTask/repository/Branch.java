package org.RecruitmentTask.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Branch {
    public String name;
    public Commit lastCommit;
}
