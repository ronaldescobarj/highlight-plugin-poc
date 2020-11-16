package simplelanguage.psi.impl;

import com.intellij.lang.ASTNode;
import simplelanguage.psi.SimpleDiffline;
import simplelanguage.psi.SimpleTypes;

public class SimplePsiImplUtil {
    public static String getNotModified(SimpleDiffline element) {
        ASTNode notModifiedNode = element.getNode().findChildByType(SimpleTypes.NOTMODIFIED);
        if (notModifiedNode != null) {
            return notModifiedNode.getText();
        } else {
            return null;
        }
    }

    public static String getInserted(SimpleDiffline element) {
        ASTNode insertedNode = element.getNode().findChildByType(SimpleTypes.INSERTED);
        if (insertedNode != null) {
            return insertedNode.getText();
        } else {
            return null;
        }
    }

    public static String getUpdated(SimpleDiffline element) {
        ASTNode updatedNode = element.getNode().findChildByType(SimpleTypes.UPDATED);
        if (updatedNode != null) {
            return updatedNode.getText();
        } else {
            return null;
        }
    }

    public static String getUpdatedMulitipleTimes(SimpleDiffline element) {
        ASTNode updatedMultipleTimesNode = element.getNode().findChildByType(SimpleTypes.UPDATEDMULTIPLETIMES);
        if (updatedMultipleTimesNode != null) {
            return updatedMultipleTimesNode.getText();
        } else {
            return null;
        }
    }

    public static String getMoved(SimpleDiffline element) {
        ASTNode movedNode = element.getNode().findChildByType(SimpleTypes.MOVED);
        if (movedNode != null) {
            return movedNode.getText();
        } else {
            return null;
        }
    }
}