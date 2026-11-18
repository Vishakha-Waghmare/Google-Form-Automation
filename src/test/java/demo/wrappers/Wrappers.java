package demo.wrappers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Wrappers {

    WebDriver driver;

    public Wrappers(WebDriver driver)
    {
        this.driver=driver;
    }

    public static boolean formField(WebElement element) throws InterruptedException
    {
        try
        {
            if(element.isDisplayed())
            {
                return true;
            }
            return false;
        }
        catch(Exception e)
        {
            System.out.println("Exception occurred while sending input: " + e.getMessage());
            throw e;
        }
    }

    public static boolean sendInput(WebElement element , String input) throws InterruptedException
    {
        try
        {
            //element.click(); //this line causing issue for date selection. 
            element.clear();
            element.sendKeys(input);

            return true;
        }
        catch (Exception e) {
            System.out.println("Exception occurred while sending input: " + e.getMessage());
            return false;
        }
        
    }
    
    public  static long epochTime() throws InterruptedException // unable to read by humans 
    {
        try
        {
            Instant time= Instant.now(); // this is the method provided by java gives current Unix timestamp.
            long epoch = time.getEpochSecond();
            return epoch;
        }
        catch(Exception e)
        {
            System.out.println("Exception occurred while getting epoch time: " + e.getMessage());
            throw e;
        }
    }

    public static void click(List<WebElement> elements)
    {
       for (WebElement element : elements) {
        element.click();
        }
    }

    public static boolean dropdown(List<WebElement> options, String ddText)
    {
        for (WebElement option : options)
        {
            if (option.getText().trim().equals(ddText))
            {
                click(Arrays.asList(option));
                return true;
            }
        }
        return  false;
    }

    public boolean radioBtn(String input)throws InterruptedException
    {
        try 
        {
            //we can create webelement but common click not wroking so we changed DT.
            List<WebElement> experience = driver.findElements(By.xpath("//div[contains(@class,'nWQGrd') and normalize-space(.)='" + input + "']")); 

            if (!experience.isEmpty())
            {
                click(experience);
                return true;
            }
            return  false;
        }
        catch (Exception e) 
        {
            System.out.println("Exception occurred while getting epoch time: " + e.getMessage());
            throw e;
        }
    }
    

    public boolean checkbox(String input)
    {
        List<WebElement> checkList = driver.findElements(By.xpath("//div[contains(@class,'eBFwI') and normalize-space(.)='" +input+ "']"));
        //we can use for loop if we dont know what dynamically option come or lot of options there here we will use parameterize xpath 
        if (!checkList.isEmpty())
        {
            click(checkList);
            return true;
        }
        return  false;
    }  

    public boolean addressed(String input) throws InterruptedException
    {
        try
        {
            WebElement addressDD = driver.findElement(By.xpath("//div[@id='i46']/ancestor::div[contains(@class,'geS5n')]//div[contains(@class,'ry3kXd')]"));
            addressDD.click();

            List<WebElement> addressList = driver.findElements(By.xpath("//div[contains(@class,'OA0qNb')]//div[contains(@class,'MocG8c')]"));

            dropdown(addressList, input);
            return true;
        
        }
        
        catch(Exception e)
        {
            System.out.println("Exception occurred while sending input: " + e.getMessage());
            throw e;
        }    
    }

    public String date(int days) throws InterruptedException
    {
        try
        {
            WebElement dateField = driver.findElement(By.xpath("//div[@id='i51']/ancestor::div[contains(@class,'geS5n')]//input"));

            LocalDate currentDate = LocalDate.now(); 
            LocalDate requiredDate = currentDate.minusDays(days);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            String displaydate = requiredDate.format(formatter);

            sendInput(dateField, displaydate);
            
            return displaydate;
        }
        catch(Exception e)
        {
        System.out.println("Exception occurred while sending input: " + e.getMessage());
        throw e;
        }
    }


public boolean time(String hours, String minutes) throws InterruptedException
{
    try
    {
        WebElement timeQuestion = driver.findElement(By.xpath("//div[@id='i57']/ancestor::div[contains(@class,'geS5n')]"));

        WebElement hourField = timeQuestion.findElement(By.xpath(".//input[@aria-label='Hour']"));

        WebElement minuteField = timeQuestion.findElement(By.xpath(".//input[@aria-label='Minute']"));

        boolean hourEntered = sendInput(hourField, hours);
        boolean minuteEntered = sendInput(minuteField, minutes);

        return hourEntered && minuteEntered;
    }
    catch(Exception e)
    {
        System.out.println("Exception occurred while selecting time: " + e.getMessage());
        throw e;
    }
}

public String submitForm(String message) throws InterruptedException
{
    try {
        WebElement submit = driver.findElement(By.xpath("//span[text()='Submit']/ancestor::div[@role='button']"));
        submit.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement successful = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='vHW8K' and text()='Thanks for your response, Automation Wizard!']")));

        message = successful.getText();
        System.out.println("Submission message: " + message);

        return message;  
    } catch (Exception e) 
    {
         System.out.println("Exception occurred while selecting time: " + e.getMessage());
        throw e;
    }
}

}


