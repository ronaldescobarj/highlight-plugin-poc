package difflogic;

public class DiffRow {
    String commit;
    String label;
    String change;
    String nodeType;
    int srcStart;
    int srcEnd;
    int dstStart;
    int dstEnd;
    String dstFile;

    public DiffRow(String commit, String label, String change, String nodeType, int srcStart, int srcEnd, int dstStart, int dstEnd, String dstFile) {
        this.commit = commit;
        this.label = label;
        this.change = change;
        this.nodeType = nodeType;
        this.srcStart = srcStart;
        this.srcEnd = srcEnd;
        this.dstStart = dstStart;
        this.dstEnd = dstEnd;
        this.dstFile = dstFile;
    }

    public String toString() {
        return this.commit + ", " + this.label + ", " + this.change + ", " + this.nodeType + ", " + this.srcStart + ", " + this.srcEnd + ", " + this.dstStart + ", " + this.dstEnd + ", " + this.dstFile;
    }
}
