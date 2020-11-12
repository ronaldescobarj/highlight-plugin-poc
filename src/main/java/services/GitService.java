package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GitService {
    private String remoteUrl;
    private String repoOwner;
    private String repoName;

    public void setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

    public void setRepoOwner(String repoOwner) {
        this.repoOwner = repoOwner;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getRemoteUrl() {
        return remoteUrl;
    }

    public String getRepoOwner() {
        return repoOwner;
    }

    public String getRepoName() {
        return repoName;
    }
}
