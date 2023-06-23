package TaskVK.VK;

import TaskVK.Models.PostCommentModel;
import TaskVK.Models.WallRecordModel;
import TaskVK.Utils.JsonUtils;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

import static TaskVK.Constants.ApiDataConstants.VK_ID;
import static TaskVK.Constants.AttriburtesConstants.*;
import static TaskVK.Constants.DataFileConstants.API_DATA_FILE;

public class MyProfilePage extends Form {
    
    private final By authorBy = By.xpath("//a[@class='PostHeaderTitle__authorLink']");
    private final By textBy = By.xpath("//div[@class='wall_post_text']");
    private final By photoBy = By.xpath("//a[@class='MediaGrid__interactive']");
    private final By commentTextBy = By.xpath("//div[@class='reply_text']");
    private final By likeBy = By.xpath("//div[contains(@class,'PostButtonReactions__icon')]");
    private final By commentAuthor = By.xpath("//a[@class='author']");
    private final String postLocator = ".//div[@id='post%s']";
    
    
    public MyProfilePage() {
        super(By.xpath("//div[@class='ProfileWrapper']"), "My Profile Page");
    }
    
    public WallRecordModel isWallRecordCreated(int id) {
        ILabel post = getWallPost(id);
        
        WallRecordModel wallRecordModel = new WallRecordModel();
        wallRecordModel.setAuthor(getPostAuthor(post));
        wallRecordModel.setText(getPostText(post));
        return wallRecordModel;
    }
    
    public String getPostAuthor(ILabel post) {
        return post.findChildElement(authorBy, ElementType.LABEL).getAttribute(DATA_ID);
    }
    
    public String getPostText(ILabel post) {
        return post.findChildElement(textBy, ElementType.LABEL).getText();
    }
    
    public String getPostPhoto(ILabel post) {
        String photoId = post.findChildElement(photoBy, "Post Photo", ElementType.LINK).getAttribute(DATA_PHOTO_ID);
        return String.format("photo%s",photoId);
    }
    
    
    public boolean isPostDisplayed(int id) {
        return getWallPost(id).state().waitForDisplayed();
    }
    
    public boolean isPostDeleted(int id) {
        return getWallPost(id).state().waitForNotDisplayed();
    }
    
    public WallRecordModel isWallRecordUpdated(int id) {
        
        ILabel post = getWallPost(id);
        
        WallRecordModel wallRecordModel = new WallRecordModel();
        wallRecordModel.setPhoto(getPostPhoto(post));
        wallRecordModel.setText(getPostText(post));
        
        return wallRecordModel;
    }
    
    public PostCommentModel isCommentAddedToPost(int post_id, int comment_id) {
        ILabel comment = getWallPost(post_id).findChildElement(getPostComment(comment_id), "Comment", ElementType.LABEL);
        
        PostCommentModel postCommentModel = new PostCommentModel();
        postCommentModel.setAuthor(getPostCommentAuthor(comment));
        postCommentModel.setComment(getPostCommentText(comment));
        
        return postCommentModel;
    }
    
    private String getPostCommentAuthor(ILabel comment) {
        return comment.findChildElement(commentAuthor, "Author Text", ElementType.LABEL).getAttribute(DATA_ID);
    }
    
    public String getPostCommentText(ILabel comment) {
        return comment.findChildElement(commentTextBy, "Comments Text", ElementType.LABEL).getText();
    }
    
    
    public void likePost(int id) {
        IButton likeButton = getWallPost(id).findChildElement(likeBy, "Like Button", ElementType.BUTTON);
        likeButton.click();
    }
    
    private ILabel getWallPost(int id) {
        String postId = JsonUtils.getJsonData(API_DATA_FILE, VK_ID) + "_" + id;
        return getElementFactory().getLabel(By.xpath(String.format(postLocator, postId)), "Wall Post");
    }
    
    private By getPostComment(int comment_id) {
        String commentId = JsonUtils.getJsonData(API_DATA_FILE, VK_ID) + "_" + comment_id;
        return By.xpath(String.format(postLocator, commentId));
    }
    
}
