package services;

public class VisualElementsToggleService {

    boolean areVisualElementsShown;

    public void initialize() {
        this.areVisualElementsShown = true;
    }

    public boolean areVisualElementsShown() {
        return areVisualElementsShown;
    }

    public void setAreVisualElementsShown(boolean areVisualElementsShown) {
        this.areVisualElementsShown = areVisualElementsShown;
    }

}
