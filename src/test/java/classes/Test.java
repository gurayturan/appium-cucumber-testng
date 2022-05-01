package classes;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import static classes.Edition098_Visual_Testing_1.CHECK_HOME;


public class Test {
    public static void main(String[] args) throws Exception {

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("udid", "264a74fe");
        desiredCapabilities.setCapability("platformVersion", "11");
        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("automationName", "uiautomator2");

       AndroidDriver<WebElement> driver= new AndroidDriver<>(new URL("http://0.0.0.0:4723/wd/hub"), desiredCapabilities);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Edition098_Visual_Testing_1 ccc=new Edition098_Visual_Testing_1(driver);
        // wait for an element that's on the home screen
       // WebElement loginScreen = ccc.waitForElement(wait, By.id(""));
        String nativeButtonImage = getRefImage ("playstore");
        driver.findElement(MobileBy.image(nativeButtonImage)).click();
        //https://www.headspin.io/blog/visual-testing-with-appium-part-1
        //https://bitbar.com/blog/how-to-use-appium-image-locator-for-finding-elements-and-image-recognition/
        // now we know the home screen is loaded, so do a visual check
//        Edition098_Visual_Testing_1.doVisualCheck("playstore");
       // Edition098_Visual_Testing_1.doVisualCheck("security");

        // nav to the login screen, and wait for an element that's on the login screen
      //  loginScreen.click();
    }
    protected static String getRefImage(String refImgName) throws Exception {

        File file = new File("/home/guray/IdeaProjects/appium-cucumber-testng/src/images/"+ refImgName + ".png");
        Path path = file.toPath();
        return Base64.getEncoder().encodeToString(Files.readAllBytes(path));
    }
}
