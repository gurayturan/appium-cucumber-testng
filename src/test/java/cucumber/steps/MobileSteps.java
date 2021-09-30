package cucumber.steps;
import cucumber.tests.BaseTest;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import utilities.ThreadLocalDriver;
import classes.Element;
import classes.*;
import java.io.IOException;
import java.net.URL;
import utilities.DesiredCapabilitiesUtil;
public class MobileSteps {

    private final DesiredCapabilitiesUtil desiredCapabilitiesUtil = new DesiredCapabilitiesUtil();
    public AndroidDriver<MobileElement> driver;
    public WebDriverWait wait ;
    Parser parser =new Parser();
    Page mypage;


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

}
