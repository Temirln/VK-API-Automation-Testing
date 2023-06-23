package TaskVK.VK;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class LoginPage extends Form {
    
    private final ITextBox emailTextBox = getElementFactory().getTextBox(By.xpath("//input[@id='index_email']"), "Email Text Field");
    private final IButton signInButton = getElementFactory().getButton(By.xpath("//button[@type='submit']"), "Sign in button");
    
    
    public LoginPage() {
        super(By.xpath("//div[@class='VkIdForm']"), "VK Login Page");
    }
    
    public void enterEmail(String email) {
        emailTextBox.clearAndType(email);
    }
    
    public void clickSignInButton() {
        signInButton.clickAndWait();
    }
    
    
}
