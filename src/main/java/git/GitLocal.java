package git;

import at.aau.softwaredynamics.runner.util.GitHelper;
import com.intellij.openapi.editor.Editor;
import editor.EditorUtils;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.filter.PathSuffixFilter;
import org.eclipse.jgit.util.io.NullOutputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GitLocal {

    String repoPath;
    Repository repository;

    public GitLocal(String repoPath) {
        this.repoPath = repoPath;
    }

    public void openRepository() {
        try {
            repository = GitHelper.openRepository(repoPath);
        } catch (IOException exception) {
            System.out.println("IOException");
        }
    }

    public void closeRepository() {
        repository.close();
    }

    public List<RevCommit> getSelectedLatestCommits(int numberOfCommits) {
        try {
            Collection<RevCommit> commits = GitHelper.getCommits(repository, "HEAD");
            List<RevCommit> commitsList = new ArrayList<>(commits);
            List<RevCommit> slicedCommits = commitsList.subList(0, numberOfCommits);
            Collections.reverse(slicedCommits);
            return slicedCommits;
        } catch (IOException e) {
            return null;
        }
    }

    public DiffEntry getFileDiffWithPreviousCommit(RevCommit commit, String fileName) {
        DiffFormatter diffFormatter = createDiffFormatter(fileName);
        RevCommit[] parents = commit.getParents().length > 0 ? commit.getParents() :new RevCommit[]{ null };
        try {
            List<DiffEntry> diffs = diffFormatter.scan(parents[0], commit.getTree());
            return diffs.get(0);
        } catch (IOException exception) {
            return null;
        }
    }

    public String getPreviousCommitFileContent(DiffEntry diffEntry) {
        try {
            return GitHelper.getFileContent(diffEntry.getOldId(), repository);
        } catch(IOException exception) {
            return "";
        }
    }

    public String getCurrentCommitFileContent(DiffEntry diffEntry) {
        try {
            return GitHelper.getFileContent(diffEntry.getNewId(), repository);
        } catch(IOException exception) {
            return "";
        }
    }

    private DiffFormatter createDiffFormatter(String fileName) {
        DiffFormatter diffFormatter = new DiffFormatter(NullOutputStream.INSTANCE);
        diffFormatter.setRepository(repository);
        diffFormatter.setPathFilter(PathSuffixFilter.create(fileName));
        diffFormatter.setDetectRenames(true);
        return diffFormatter;
    }

    public String getPreviousCommitFileContent(Editor editor) {
        String fileName = new EditorUtils().getFileName(editor);
        try {
            Collection<RevCommit> commits = GitHelper.getCommits(repository, "HEAD");
            RevCommit commit = commits.iterator().next();
            DiffFormatter diffFormatter = createDiffFormatter(fileName);
            return getPreviousCommitContent(diffFormatter, commit);
        } catch(Exception e) {
            return "";
        }
    }

    private String getPreviousCommitContent(DiffFormatter diffFormatter, RevCommit commit) {
        RevCommit[] parents = commit.getParents();
        try {
            if (parents.length != 0) {
                List<DiffEntry> diffs = diffFormatter.scan(parents[0], commit.getTree());
                DiffEntry diff = diffs.get(0);
                return GitHelper.getFileContent(diff.getOldId(), repository);
            }
            return "";
        } catch (IOException e) {
            return "";
        }
    }
}
