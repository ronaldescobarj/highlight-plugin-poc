package models;

public abstract class Data {
    protected long startOffset;
    protected long endOffset;

    public abstract String renderData();
    public abstract String getType();

    public long getStartOffset() {
        return startOffset;
    }

    public void setStartOffset(int startOffset) {
        this.startOffset = startOffset;
    }

    public long getEndOffset() {
        return endOffset;
    }

    public void setEndOffset(int endOffset) {
        this.endOffset = endOffset;
    }
}
