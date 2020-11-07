package models;

public class GitCommit {
    String sha;
    String node_id;
    GitCommitData commit;
    String url;
    String html_url;
    String comments_url;
    GitUser author;
    GitUser commiter;
    GitParentCommit[] parents;
}
