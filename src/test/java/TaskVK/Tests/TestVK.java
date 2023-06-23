package TaskVK.Tests;

import TaskVK.BaseTest;
import TaskVK.Models.PostCommentModel;
import TaskVK.Models.WallRecordModel;
import TaskVK.Utils.JsonUtils;
import TaskVK.Utils.QAUtils;
import TaskVK.Utils.RandomUtils;
import TaskVK.Utils.VkApiUtils;
import TaskVK.VK.*;
import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import io.restassured.response.ResponseBody;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static TaskVK.Constants.ApiDataConstants.VK_ID;
import static TaskVK.Constants.Constants.*;
import static TaskVK.Constants.DataFileConstants.API_DATA_FILE;
import static TaskVK.Constants.DataFileConstants.SIGN_IN_DATA;
import static TaskVK.Constants.SingInDataConstants.*;

public class TestVK extends BaseTest {
    
    @Test
    public void testVkApi() {
        
        Logger.getInstance().info("STEP 2: Authorization");
        LoginPage loginPage = new LoginPage();
        Assert.assertTrue(loginPage.state().isDisplayed(), "This is not Login Page");
        
        loginPage.enterEmail(JsonUtils.getJsonData(SIGN_IN_DATA, VK_LOGIN));
        loginPage.clickSignInButton();
        
        PasswordPage passwordPage = new PasswordPage();
        Assert.assertTrue(AqualityServices.getConditionalWait().waitFor(() -> passwordPage.state().isDisplayed()), "This is not Password Page");
        
        passwordPage.enterPassword(JsonUtils.getJsonData(SIGN_IN_DATA, VK_PASSWORD));
        passwordPage.clickContinueButton();
        
        Logger.getInstance().info("STEP 3: Navigate to My Profile Page");
        NewsPage newsPage = new NewsPage();
        Assert.assertTrue(AqualityServices.getConditionalWait().waitFor(() -> newsPage.state().isDisplayed()), "This is not News Page");
        newsPage.goToMyProfile();
        MyProfilePage myProfilePage = new MyProfilePage();
        Assert.assertTrue(AqualityServices.getConditionalWait().waitFor(() -> myProfilePage.state().isDisplayed()), "This is not My Profile Page");
        
        Logger.getInstance().info("STEP 4: Using API Create a Wall Post with Random Text, and get id of post");
        String text = RandomUtils.randomizeContent(CONTENT_LENGHT);
        int postId = VkApiUtils.createWallRecord(text);
        
        Logger.getInstance().info("STEP 5: Verify that Post displayed on a page with correct Author");
        browser.refresh();
        WallRecordModel createdWallPost = myProfilePage.isWallRecordCreated(postId);
        Assert.assertEquals(createdWallPost.getAuthor(), JsonUtils.getJsonData(API_DATA_FILE, VK_ID), "Author not equal");
        Assert.assertEquals(createdWallPost.getText(), text, "Texts not the same");
        
        Logger.getInstance().info("STEP 6: Edit Wall Post (text and photo)");
        ResponseBody responseBody = VkApiUtils.uploadPictureToWall(VkApiUtils.getUploadUrl().toString(),QAUtils.getAbsolutePath(IMAGE_PATH));
        String newEditText = RandomUtils.randomizeContent(CONTENT_LENGHT);
        String photo = QAUtils.postPhoto(VkApiUtils.savePhoto(responseBody));
        VkApiUtils.editWallRecord(postId, newEditText, photo);
        
        Logger.getInstance().info("STEP 7: Verify that text of Post updated and Image appeared");
        AqualityServices.getConditionalWait().waitFor(() -> myProfilePage.isPostDisplayed(postId));
        browser.refresh();
        WallRecordModel updatedWallPost = myProfilePage.isWallRecordUpdated(postId);
        Assert.assertEquals(updatedWallPost.getPhoto(), photo, "Picture not the same");
        Assert.assertEquals(updatedWallPost.getText(), newEditText, "Text didn't changed");
        
        Logger.getInstance().info("STEP 8: Using API add comment to the Post");
        String commentText = RandomUtils.randomizeContent(CONTENT_LENGHT);
        
        Logger.getInstance().info("STEP 9: Verify That Comment added to the right Post and from correct Author");
        PostCommentModel postCommentModel = myProfilePage.isCommentAddedToPost(postId, VkApiUtils.addCommnentToPost(postId, commentText));
        Assert.assertEquals(postCommentModel.getAuthor(), JsonUtils.getJsonData(API_DATA_FILE, VK_ID), "Incorrect Author");
        Assert.assertEquals(postCommentModel.getComment(), commentText, "Comments not equal");
        
        Logger.getInstance().info("STEP 10: Click Like Button ");
        myProfilePage.likePost(postId);
        
        Logger.getInstance().info("STEP 11: Using API Verify Liked User");
        List<String> uids = VkApiUtils.getListLiked(postId);
        Assert.assertTrue(uids.contains(JsonUtils.getJsonData(API_DATA_FILE, VK_ID)), "User didn't liked post");
        
        Logger.getInstance().info("STEP 12: Using API Delete Post from Wall");
        int deletedPost = VkApiUtils.deletePost(postId);
        Assert.assertEquals(deletedPost, 1, "Post not deleted");
        
        Logger.getInstance().info("STEP 13: Verify that Post deleted");
        Assert.assertTrue(myProfilePage.isPostDeleted(postId), "Post not deleted in a page");
        
    }
    
}