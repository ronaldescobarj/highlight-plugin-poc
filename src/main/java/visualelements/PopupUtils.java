package visualelements;

import models.ModificationData;

public class PopupUtils {
    public static String createContent(ModificationData modificationData) {
        return "<html>" + modificationData.getAuthorName() + "<br>" +
                modificationData.getAuthorEmail() + "<br>" +
                modificationData.getDateTimeString() + "<br>" +
                "</html>";
    }
}
