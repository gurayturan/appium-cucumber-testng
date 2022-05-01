package cucumber.tests;

import io.appium.java_client.AppiumDriver;
import io.cucumber.testng.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;
import utilities.DesiredCapabilitiesUtil;
import utilities.ThreadLocalDriver;

import java.io.IOException;
import java.net.URL;

@CucumberOptions(
        monochrome = true,
        tags = "@Candidate",
        plugin = {"pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json"
        },
        features = "src/test/java/cucumber/features",
        glue = "cucumber.steps",
        publish = true
)
public class TestRunner extends BaseTest {
    private TestNGCucumberRunner testNGCucumberRunner;
    private final DesiredCapabilitiesUtil desiredCapabilitiesUtil = new DesiredCapabilitiesUtil();


    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @Test(groups = "cucumber", description = "Run Cucumber Features.", dataProvider = "scenarios" ,threadPoolSize = 2, invocationCount = 1)
    public void scenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
    }

    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return testNGCucumberRunner.provideScenarios();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        testNGCucumberRunner.finish();
    }
}