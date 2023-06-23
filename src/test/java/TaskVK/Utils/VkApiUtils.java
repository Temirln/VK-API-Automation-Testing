package TaskVK.Utils;

import TaskVK.Methods.RequestsParameters;
import TaskVK.Methods.ResponseReturns;
import aquality.selenium.core.logging.Logger;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import static TaskVK.Constants.ApiDataConstants.*;
import static TaskVK.Constants.DataFileConstants.API_DATA_FILE;
import static TaskVK.Constants.DataFileConstants.ENDPOINT_DATA_FILE;
import static TaskVK.Constants.JsonPathConstants.*;
import static TaskVK.Constants.RequestParametersConstants.FILE1;
import static TaskVK.Constants.VkApiConstants.*;

public class VkApiUtils {
    
    private static Response requestTemplate(String method, HashMap<String, String> map) {
        return RestAssured.given().
                contentType(ContentType.JSON).
                when().
                basePath("{method}").
                pathParam("method", method).
                queryParams(map).
                post().
                then().
                extract().
                response();
    }
    
    
    public static int createWallRecord(String text) {
        Response res = requestTemplate(JsonUtils.getJsonData(ENDPOINT_DATA_FILE, CREATE_WALL_POST), RequestsParameters.createWallPostParameters(text));
        Logger.getInstance().info("New Wall Post created");
        return (int) ResponseReturns.getNestedResponse(res, JSON_PATH_POST_ID);
    }
    
    public static void editWallRecord(int id, String text, String photo) {
        requestTemplate(JsonUtils.getJsonData(ENDPOINT_DATA_FILE, EDIT_WALL_POST), RequestsParameters.editWallPostParameters(id, text, photo));
        Logger.getInstance().info(String.format("Wall Post with id=%d updated", id));
    }
    
    
    public static int addCommnentToPost(int id, String text) {
        Response res = requestTemplate(JsonUtils.getJsonData(ENDPOINT_DATA_FILE, ADD_COMMENT_WALL_POST), RequestsParameters.addCommentToPostParameters(id, text));
        Logger.getInstance().info(String.format("Comment added to Wall Post with id=%d", id));
        return (int) ResponseReturns.getNestedResponse(res, JSON_PATH_COMMENT_ID);
    }
    
    public static List<String> getListLiked(int id) {
        Response res = requestTemplate(JsonUtils.getJsonData(ENDPOINT_DATA_FILE, GET_USERS_LIKES_WALL_POST), RequestsParameters.getLikesParameters(id));
        return ResponseReturns.getLikesResponse(res);
    }
    
    public static int deletePost(int id) {
        Response res = requestTemplate(JsonUtils.getJsonData(ENDPOINT_DATA_FILE, DELETE_WALL_POST), RequestsParameters.deleteWallPostParameters(id));
        Logger.getInstance().info(String.format("Wall Post with id=%d deleted", id));
        return (int) ResponseReturns.getResponse(res);
    }
    
    public static ResponseBody uploadPictureToWall(String uploadUrl, String path) {
        File file = new File(path);
        
        Response res = RestAssured.given().log().all()
                .multiPart(FILE1, file, DATA_TYPE)
                .post(uploadUrl)
                .then().extract().response();
        
        Logger.getInstance().info("Picture Uploaded to the server");
        return res.body();
    }
    
    public static String savePhoto(ResponseBody responseBody) {
        
        String server = responseBody.jsonPath().get(JSON_PATH_SERVER).toString();
        String photo = responseBody.jsonPath().get(JSON_PATH_PHOTO).toString();
        String hash = responseBody.jsonPath().get(JSON_PATH_HASH).toString();
        String userId = JsonUtils.getJsonData(API_DATA_FILE, VK_ID);
        
        Response response = requestTemplate(JsonUtils.getJsonData(ENDPOINT_DATA_FILE, SAVE_PHOTO), RequestsParameters.uploadPicture(userId, server, photo, hash));
        
        List<JSONObject> resp = (List<JSONObject>) ResponseReturns.getResponse(response);
        String pictureId = new JSONObject(resp.get(0)).get(JSON_PATH_ID).toString();
        Logger.getInstance().info(String.format("Picture with id=%s Saved to Wall Album",pictureId));
        return pictureId;
        
    }
    
    public static Object getUploadUrl() {
        Response response = requestTemplate(JsonUtils.getJsonData(ENDPOINT_DATA_FILE, UPLOAD_SERVER_URL), RequestsParameters.getUploadUrl());
        return ResponseReturns.getNestedResponse(response, JSON_PATH_UPLOAD_URL);
    }
    
}
