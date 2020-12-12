package visualelements;

import models.ModificationData;
import org.eclipse.jgit.lib.PersonIdent;

public class PopupUtils {
    public static String createContent(ModificationData modificationData) {
        return "<html>" + modificationData.getAuthorName() + "<br>" + modificationData.getAuthorEmail() + "</html>";
    }
}
