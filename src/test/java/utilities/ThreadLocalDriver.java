package utilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class ThreadLocalDriver {
    private static  ThreadLocal<AppiumDriver<MobileElement>> tlDriver = new ThreadLocal<>();

    public static synchronized void setTLDriver(AppiumDriver<MobileElement> driver) { tlDriver.set(driver);}


    public static synchronized AppiumDriver<MobileElement> getTLDriver() {
        return tlDriver.get();
    }
}
