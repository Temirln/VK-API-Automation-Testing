package TaskVK;

import TaskVK.Utils.JsonUtils;
import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import aquality.selenium.core.logging.Logger;
import io.restassured.RestAssured;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static TaskVK.Constants.ApiDataConstants.API_URI;
import static TaskVK.Constants.Constants.*;
import static TaskVK.Constants.DataFileConstants.API_DATA_FILE;
import static TaskVK.Constants.DataFileConstants.CONFIG_DATA;

public abstract class BaseTest {
    
    public Browser browser;
    
    @BeforeMethod(alwaysRun = true)
    public void startUp() {
        RestAssured.baseURI = JsonUtils.getJsonData(API_DATA_FILE, API_URI);
        browser = AqualityServices.getBrowser();
        browser.maximize();
        Logger.getInstance().info("STEP 1: Navigate to page");
        browser.goTo(JsonUtils.getJsonData(CONFIG_DATA, TEST_URL));
        browser.waitForPageToLoad();
        
    }
    
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        browser.quit();
    }
    
}
