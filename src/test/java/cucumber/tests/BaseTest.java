package cucumber.tests;

import classes.Parser;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import java.io.IOException;
import java.net.URL;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import utilities.DesiredCapabilitiesUtil;
import utilities.ThreadLocalDriver;

public class BaseTest {
    private final DesiredCapabilitiesUtil desiredCapabilitiesUtil = new DesiredCapabilitiesUtil();

    @BeforeMethod
    @Parameters({ "udid", "platformVersion","ostype" })
    public void setup(String udid, String platformVersion,String OSType) throws IOException {
        if(OSType.equals("Android"))
        {
            DesiredCapabilities caps=  desiredCapabilitiesUtil.getDesiredAndroidCapabilities(udid, platformVersion);
            ThreadLocalDriver.setTLDriver(new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), caps));
        }
        else{
            DesiredCapabilities caps=  desiredCapabilitiesUtil.getDesiredIOSCapabilities(udid, platformVersion);
            ThreadLocalDriver.setTLDriver(new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), caps));
        }
    }

    @AfterMethod
    public synchronized void teardown() {
        ThreadLocalDriver.getTLDriver().quit();
    }
}
