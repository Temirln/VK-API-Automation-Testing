package TaskVK.Utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;

public class JsonUtils {
    public static String getJsonData(String filename, String property) {
        ISettingsFile environment = new JsonSettingsFile(filename);
        return environment.getValue("/" + property).toString();
    }
}