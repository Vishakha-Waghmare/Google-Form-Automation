package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.logging.Level;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;


public class TestCases {
    WebDriver driver;

    @Test
    public void testCase01()throws InterruptedException
    {
        driver.get("https://forms.gle/wjPkzeSEk1CM7KgGA");
        Thread.sleep(3000);

        WebElement question1 = driver.findElement(By.xpath("//div[@id='i1']"));  
            Assert.assertNotNull(question1, "Name field is not found");
            WebElement name = driver.findElement(By.xpath("//div[@id='i1']/ancestor::div[contains(@class,'geS5n')]//input[@type='text']"));
            
            boolean result1 = Wrappers.sendInput(name,"Crio Learner");
            Assert.assertTrue(result1, "Task 1 failed");

            System.out.println("Task 1 completed successfully "+result1);
            Thread.sleep(3000);

        //action 2

        WebElement question2 = driver.findElement(By.xpath("//div[@id='i6']"));
            Assert.assertNotNull(question2, "Why are you practicing Automation? is not found");

            WebElement patticipationAut = driver.findElement(By.xpath("//textarea[@jsname='YPqjbf']"));
            
            boolean result2 = Wrappers.sendInput(patticipationAut, "I want to be the best QA Engineer! " + Wrappers.epochTime());
            Assert.assertTrue(result2, "Task 2 failed");

            System.out.println("Task 2 completed successfully "+result2);
            Thread.sleep(3000);
        //action 3

        WebElement question3 = driver.findElement(By.xpath("//div[@id='i11']"));
            Assert.assertNotNull(question3, "Experience question is not displayed");

            Wrappers wp = new Wrappers(driver);
        
            boolean task3 =wp.radioBtn("0 - 2");
            Assert.assertTrue(task3, "Task 3 failed");
    
            System.out.println("Task 3 completed successfully");
            Thread.sleep(3000);
        
        //action 4
        WebElement question4 = driver.findElement(By.xpath("//div[@id='i28']"));
            Assert.assertNotNull(question4, "Skills question is not displayed");

            wp.checkbox("Java");
            wp.checkbox("Selenium");
            wp.checkbox("TestNG");

            System.out.println("Task 4 completed successfully");
            Thread.sleep(3000);

        //action 5
        WebElement question5 = driver.findElement(By.xpath("//div[@id='i46']"));
            Assert.assertNotNull(question5, "Address questions are not displayed");

            boolean task5= wp.addressed("Ms");
            Assert.assertTrue(task5, "Task 5 failed");

            System.out.println("Task 5 completed successfully");
            Thread.sleep(3000);

        //action 6
        WebElement question6 = driver.findElement(By.xpath("//div[@id='i51']"));
            Assert.assertNotNull(question6, "Date question is not displayed");   

            String task6 = wp.date(7);
            Assert.assertNotNull(task6, "Task 6 failed");
            System.out.println("Task 6 completed successfully");
            Thread.sleep(3000);

        //action 7   
        WebElement question7 = driver.findElement(By.xpath("//div[@id='i57']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", question7);
            Assert.assertNotNull(question7, "Time question is not displayed");
        
            boolean task7 = wp.time("07", "30");
            Assert.assertTrue(task7, "Task 7 failed");
            System.out.println("Task 7 completed successfully");
            Thread.sleep(3000);


        String message = wp.submitForm("Thanks for your response, Automation Wizard!");
        Assert.assertEquals(message,"Thanks for your response, Automation Wizard!","Form submission failed");
        Thread.sleep(2000);
    }

    @BeforeTest
    public void startBrowser()
    {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest()
    {
        driver.close();
        driver.quit();

    }
}