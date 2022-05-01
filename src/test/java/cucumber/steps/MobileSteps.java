package cucumber.steps;
import cucumber.tests.BaseTest;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.Setting;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.imagecomparison.FeatureDetector;
import io.appium.java_client.imagecomparison.FeaturesMatchingOptions;
import io.appium.java_client.imagecomparison.FeaturesMatchingResult;
import io.appium.java_client.imagecomparison.MatchingFunction;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import utilities.ThreadLocalDriver;
import classes.Element;
import classes.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.Base64;

import utilities.DesiredCapabilitiesUtil;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.Reporter.log;

public class MobileSteps {

    private final DesiredCapabilitiesUtil desiredCapabilitiesUtil = new DesiredCapabilitiesUtil();
    public AndroidDriver<MobileElement> driver;
    public WebDriverWait wait ;
    Parser parser =new Parser();
    Page mypage;
    private static By photos = MobileBy.AccessibilityId("Photo Demo");


    @Before
    public void setupLoginSteps() {
        driver= (AndroidDriver<MobileElement>) ThreadLocalDriver.getTLDriver();
        wait=new WebDriverWait(driver, 30);
    }

    public Element getElementFromJson(String elemName)
    {
        return  parser.getElement(mypage.getPageName(),elemName );
    }

    public By getLocator(String elemName)
    {
       By returnLocator=null;
       ElementType elemType;
       Element elem=getElementFromJson(elemName);
       String osType= driver.getCapabilities().getCapability("platformName").toString();

       String elementValue;
       if(osType.equalsIgnoreCase("Android"))
       {
           elementValue=elem.getAndroidValue();
           elemType=elem.getAndroidType();
       }else{
           elementValue=elem.getIOSValue();
           elemType=elem.getIOSType();
       }
        if(elemType!=null)
        {
            switch (elemType.toString()){
                case "id":
                    returnLocator=By.id(elementValue);
                    break;
                case "xpath":
                    returnLocator=By.xpath(elementValue);
                    break;
                case "className":
                    returnLocator=By.className(elementValue);
                    break;
                case "name":
                    returnLocator=By.name(elementValue);
                    break;
                case "partialLinkText":
                    returnLocator=By.partialLinkText(elementValue);
                    break;
                case "cssSelector":
                    returnLocator=By.cssSelector(elementValue);
                    break;
                case "tagName":
                    returnLocator=By.tagName(elementValue);
                    break;
            }
        }
        return  returnLocator;
    }

    public MobileElement findLocator(By by,int index)
    {
        MobileElement elem=null;

        for (int i=0;i<30;i++)
        {
            elem=driver.findElements(by).get(index);
            if(elem!=null)
            {
                break;
            }
        }
        return  elem;
    }

    @Given("I see {string} page")
    public void seePage(String page) throws IOException, ParseException
    {
        if(parser.isPageExist(page))
        {
            mypage=parser.getPageAttributes(page);

            if(mypage.getWaitElement().length()>0)
            {
                waitElement(mypage.getWaitElement());
            }
        }
        else{
            Assert.fail(page+"page not exist in json file");
        }
    }

    @Given("I wait {string} element")
    public void waitElement(String elem)
    {
        wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator(elem)));
    }

    @Given("I wait {string} element with index:{int}")
    public void waitElementWithIndex(String elem,int index)
    {
        MobileElement element=findLocator(getLocator(elem),index);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        if(elem==null)
        {
            Assert.fail(elem+ "bulunamadı index:"+index);
        }
    }

    @Given("I wait {string} element and click")
    public void waitAndClick(String elem)
    {
        wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator(elem))).click();
    }

    @Given("I click {string} element")
    public void click(String elem)
    {
        driver.findElement(getLocator(elem)).click();
    }
    @Given("I sleep {int} seconds")
    public void click(int sec) throws InterruptedException {
        Thread.sleep(sec*1000);
    }

    @Given("I send {string} text to {string} element")
    public void sendKey(String text,String elem)
    {
        waitAndFindElement(getLocator(elem)).sendKeys(text);
    }

    @Given("I send {string} text to {string} element with index:{int}")
    public void sendKeyWithIndex(String text,String elem,int index)
    {
        MobileElement element=findLocator(getLocator(elem),index);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        waitAndFindElement(getLocator(elem)).sendKeys(text);
    }

    @Given("I hide keyboard")
    public void hideKeyboard()
    {
        driver.navigate().back();
    }

    /*public List<WebElement> waitAndFindElements(By by) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }*/

    protected WebElement waitAndFindElement(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected String getText(By by) {
        return waitAndFindElement(by).getText();
    }

    private String getReferenceImageB64() throws URISyntaxException, IOException, URISyntaxException {
        URL refImgUrl = getClass().getClassLoader().getResource("test image.jpg");
        File refImgFile = Paths.get(refImgUrl.toURI()).toFile();
        return Base64.getEncoder().encodeToString(Files.readAllBytes(refImgFile.toPath()));
    }

    public void actualTest(AppiumDriver driver) throws URISyntaxException, IOException {

    }

    @Then("I test image {string}")
    public void iTestImage(String image) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        findImageWithOptimizationNotes(image);


    }
    private File getImageFile(String imageName) {
        return new File("src/test/resources/" + imageName + ".png");
    }

    private String getReferenceImageB64(String imageName) throws IOException {
        Path refImgPath = getImageFile(imageName).toPath();
        return Base64.getEncoder().encodeToString(Files.readAllBytes(refImgPath));
    }

    private void shootBird(AndroidDriver driver, WebElement birdEl, int xOffset, int yOffset) {
        Rectangle rect = birdEl.getRect();
        Point start = new Point(rect.x + rect.width / 2, rect.y + rect.height / 2);
        Point end = start.moveBy(xOffset, yOffset);
        Duration dragDuration = Duration.ofMillis(750);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence shoot = new Sequence(finger, 0);
        shoot.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), start.x, start.y));
        shoot.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        shoot.addAction(finger.createPointerMove(dragDuration, PointerInput.Origin.viewport(), end.x, end.y));
        shoot.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(shoot));
    }

    private WebElement findImageWithOptimizationNotes(String imageName) throws Exception {
        String imageData = getReferenceImageB64(imageName);
        WebElement el = null;
        double max = 1.0;
        double min = 0.0;
        double haltSearchSpread = 0.05;
        double check = 0;
        NotFoundException notFound = null;

        while (Math.abs(max - min) > haltSearchSpread) {
            check = (max + min) / 2;
            driver.setSetting(Setting.IMAGE_MATCH_THRESHOLD, check);
            try {
                el = driver.findElement(MobileBy.image(imageData));
                min = check;
            } catch (NotFoundException err) {
                max = check;
                notFound = err;
            }
        }

        if (el != null) {
            System.out.println("Image '" + imageName + "' was found at the highest threshold of: " + check);
            return el;
        }

        System.out.println("Image '" + imageName + "' could not be found even at a threshold as low as: " + check);
        throw notFound;
    }


    @Then("I wait for {int} seconds")
    public void iWaitForSeconds(int arg0) throws InterruptedException {
        Thread.sleep(1000*arg0);
    }
}
