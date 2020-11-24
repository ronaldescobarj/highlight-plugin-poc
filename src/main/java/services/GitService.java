package services;

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
