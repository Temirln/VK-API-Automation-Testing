package TaskVK.Methods;

import TaskVK.Models.LikedUsersModel;
import io.restassured.response.Response;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static TaskVK.Constants.JsonPathConstants.JSON_PATH_RESPONSE;

public class ResponseReturns {
    
    public static List<String> getLikesResponse(Response response) {
        
        List<LikedUsersModel> users = response.body().jsonPath().getList("response.users", LikedUsersModel.class);
        
        List<String> uids = new ArrayList<>();
        for (LikedUsersModel user : users) {
            uids.add(String.valueOf(user.getUid()));
        }
        return uids;
    }
    
    public static Object getResponse(Response response) {
        return response.body().jsonPath().get(JSON_PATH_RESPONSE);
    }
    
    public static Object getNestedResponse(Response response, String childElement) {
        return new JSONObject(response.body().jsonPath().get(JSON_PATH_RESPONSE)).get(childElement);
    }
    
}
