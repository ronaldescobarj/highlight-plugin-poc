package visualelements;

import models.Data;

public class PopupUtils {
    public static String createContent(Data data) {
        return "<html>" + data.renderData() +
                "</html>";
    }
}
