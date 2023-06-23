package TaskVK.Methods;

import TaskVK.Utils.JsonUtils;

import java.util.HashMap;

import static TaskVK.Constants.ApiDataConstants.*;
import static TaskVK.Constants.DataFileConstants.API_DATA_FILE;
import static TaskVK.Constants.DataFileConstants.SIGN_IN_DATA;
import static TaskVK.Constants.RequestParametersConstants.*;

public class RequestsParameters {
    
    public static HashMap<String, String> createWallPostParameters(String text) {
        HashMap<String, String> map = new HashMap<>();
        map.put(OWNER_ID, JsonUtils.getJsonData(API_DATA_FILE, VK_ID));
        map.put(MESSAGE, text);
        map.put(ACCESS_TOKEN, JsonUtils.getJsonData(SIGN_IN_DATA, VK_TOKEN));
        map.put(VERSION, JsonUtils.getJsonData(API_DATA_FILE, API_VERSION));
        
        return map;
    }
    
    public static HashMap<String, String> editWallPostParameters(int id, String text, String photo) {
        HashMap<String, String> map = new HashMap<>();
        map.put(OWNER_ID, JsonUtils.getJsonData(API_DATA_FILE, VK_ID));
        map.put(POST_ID, String.valueOf(id));
        map.put(MESSAGE, text);
        map.put(ATTACHMENTS, photo);
        map.put(ACCESS_TOKEN, JsonUtils.getJsonData(SIGN_IN_DATA, VK_TOKEN));
        map.put(VERSION, JsonUtils.getJsonData(API_DATA_FILE, API_VERSION));
        
        return map;
    }
    
    public static HashMap<String, String> addCommentToPostParameters(int id, String text) {
        HashMap<String, String> map = new HashMap<>();
        map.put(OWNER_ID, JsonUtils.getJsonData(API_DATA_FILE, VK_ID));
        map.put(POST_ID, String.valueOf(id));
        map.put(MESSAGE, text);
        map.put(ACCESS_TOKEN, JsonUtils.getJsonData(SIGN_IN_DATA, VK_TOKEN));
        map.put(VERSION, JsonUtils.getJsonData(API_DATA_FILE, API_VERSION));
        
        return map;
    }
    
    public static HashMap<String, String> getLikesParameters(int id) {
        HashMap<String, String> map = new HashMap<>();
        map.put(OWNER_ID, JsonUtils.getJsonData(API_DATA_FILE, VK_ID));
        map.put(POST_ID, String.valueOf(id));
        map.put(ACCESS_TOKEN, JsonUtils.getJsonData(SIGN_IN_DATA, VK_TOKEN));
        map.put(VERSION, JsonUtils.getJsonData(API_DATA_FILE, API_VERSION));
        
        return map;
    }
    
    public static HashMap<String, String> getUploadUrl() {
        HashMap<String, String> map = new HashMap<>();
        map.put(ACCESS_TOKEN, JsonUtils.getJsonData(SIGN_IN_DATA, VK_TOKEN));
        map.put(VERSION, JsonUtils.getJsonData(API_DATA_FILE, API_VERSION));
        return map;
    }
    
    public static HashMap<String, String> uploadPicture(String user_id, String server, String photolist, String hash) {
        HashMap<String, String> map = new HashMap<>();
        map.put(USER_ID, user_id);
        map.put(SERVER, server);
        map.put(HASH, hash);
        map.put(PHOTO, photolist);
        
        map.put(ACCESS_TOKEN, JsonUtils.getJsonData(SIGN_IN_DATA, VK_TOKEN));
        map.put(VERSION, JsonUtils.getJsonData(API_DATA_FILE, API_VERSION));
        
        return map;
    }
    
    
    public static HashMap<String, String> deleteWallPostParameters(int id) {
        HashMap<String, String> map = new HashMap<>();
        map.put(OWNER_ID, JsonUtils.getJsonData(API_DATA_FILE, VK_ID));
        map.put(POST_ID, String.valueOf(id));
        map.put(ACCESS_TOKEN, JsonUtils.getJsonData(SIGN_IN_DATA, VK_TOKEN));
        map.put(VERSION, JsonUtils.getJsonData(API_DATA_FILE, API_VERSION));
        
        return map;
    }
}
