package gitremote;

import com.google.gson.Gson;
import models.GitCommit;
import models.GitFile;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class GitRemote {
    HttpClient client;
    String gitApiUrl;

    public GitRemote(String gitApiUrl) {
        client = HttpClient.newHttpClient();
        this.gitApiUrl = gitApiUrl;
    }

    public GitCommit[] getCommits(String gitApiUrl) {
        HttpRequest request = HttpRequest.newBuilder(
                URI.create(gitApiUrl + "/commits"))
                .header("accept", "application/json")
                .build();
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        Gson gson = new Gson();
        GitCommit[] commits;
        try {
            HttpResponse<String> response = client.send(request, bodyHandler);
            commits = gson.fromJson(response.body(), GitCommit[].class);
            return commits;
        } catch(InterruptedException | IOException exception) {
            return null;
        }
    }

    public String getPreviousCommitFileContent(String commitSha, String filePath) {
        GitFile gitFile = getPreviousCommit(commitSha, filePath);
        String encodedContent = gitFile.getContent();
        String encodedWithoutLineBreaks = encodedContent.replaceAll("\n", "");
        byte[] decodedBytes = Base64.getDecoder().decode(encodedWithoutLineBreaks);
        String decodedContent = new String(decodedBytes);
        return decodedContent;
    }

    private GitFile getPreviousCommit(String commitSha, String filePath) {
        HttpRequest request = HttpRequest.newBuilder(
                URI.create(gitApiUrl + "/contents" + filePath + "?ref=" + commitSha))
                .header("accept", "application/json")
                .build();
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        Gson gson = new Gson();
        GitFile gitFile;
        try {
            HttpResponse<String> response = client.send(request, bodyHandler);
            gitFile = gson.fromJson(response.body(), GitFile.class);
            return gitFile;
        } catch(InterruptedException | IOException exception) {
            return null;
        }
    }

}
