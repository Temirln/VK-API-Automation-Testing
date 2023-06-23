package TaskVK.Utils;

import java.io.File;
import java.io.IOException;

import static TaskVK.Constants.ApiDataConstants.VK_ID;
import static TaskVK.Constants.DataFileConstants.API_DATA_FILE;

public class QAUtils {
    
    public static String getAbsolutePath(String relativePath) {
        
        File a = new File(relativePath);
        String absolutePath;
        try {
            absolutePath = a.getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        return absolutePath;
    }
    
    public static String postPhoto(String photoId){
        return String.format("photo%s_%s",JsonUtils.getJsonData(API_DATA_FILE, VK_ID),photoId);
    }
    
    
}
