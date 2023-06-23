package TaskVK.VK;

import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class LeftPanelForm extends Form {
    
    private final ILink myProfile = getElementFactory().getLink(By.xpath("//li[@id='l_pr']"), "My Profile Link");
    
    public LeftPanelForm() {
        super(By.xpath("//div[@id='side_bar']"), "VK Left Panel Form");
    }
    
    public void clickMyProfile() {
        myProfile.clickAndWait();
    }
    
}
