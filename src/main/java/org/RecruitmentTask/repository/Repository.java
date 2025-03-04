package org.RecruitmentTask.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Repository {
    public String name;
    public Owner owner;
    public boolean fork;
}
