package TaskVK.VK;

import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class NewsPage extends Form {
    
    public NewsPage() {
        super(By.xpath("//div[@id='main_feed']"), "VK News Page");
    }
    
    public void goToMyProfile() {
        LeftPanelForm leftPanelForm = new LeftPanelForm();
        leftPanelForm.clickMyProfile();
    }
}
