package TaskVK.VK;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class PasswordPage extends Form {
    
    private final ITextBox passwordTextBox = getElementFactory().getTextBox(By.xpath("//input[@type='password']"), "Password Text Box");
    private final IButton continueButton = getElementFactory().getButton(By.xpath("//button[@type='submit']"), "Continue Button");
    
    public PasswordPage() {
        super(By.xpath("//form[@class='vkc__EnterPasswordNoUserInfo__content']"), "Password Enter ");
    }
    
    public void enterPassword(String password) {
        passwordTextBox.clearAndType(password);
    }
    
    public void clickContinueButton() {
        continueButton.clickAndWait();
    }
}
