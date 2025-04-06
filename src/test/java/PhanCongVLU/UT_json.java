package PhanCongVLU;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class UT_json {
    private WebDriver driver;
    private WebDriverWait wait;
    private ChromeOptions options;
    private static final String baseURL = "https://cntttest.vanlanguni.edu.vn:18081/Phancong02/";
    private static final String LOGIN_EMAIL = "phi.2274802010649@vanlanguni.vn";
    private static final String LOGIN_PASSWORD = "PhichoiBongro2004";

    @BeforeClass
    public void setUp() {
        options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        driver.get(baseURL);
        driver.manage().window().maximize();

        // Đăng nhập chỉ một lần trước khi chạy các test case
        login();
    }

    public void login() {
        WebElement loginButton = new WebDriverWait(driver, Duration.ofSeconds(45))
                .until(ExpectedConditions.elementToBeClickable(By.id("OpenIdConnect")));
        loginButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("i0116"))).sendKeys(LOGIN_EMAIL);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("i0118"))).sendKeys(LOGIN_PASSWORD);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9"))).click();
    }

    @DataProvider(name = "termData")
    public Object[][] loadTestData() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonData = (JSONObject) parser.parse(new FileReader("/Users/nguyenlapis/Downloads/TestLT/src/test/java/PhanCongVLU/Data.json"));

        // Giả sử "testCases" là một mảng
        JSONArray testCasesArray = (JSONArray) jsonData.get("testCases");
        Object[][] data = new Object[testCasesArray.size()][];

        // Duyệt qua mảng testCases và lấy các giá trị
        for (int i = 0; i < testCasesArray.size(); i++) {
            JSONObject testCase = (JSONObject) testCasesArray.get(i);
            data[i] = new Object[] {
                    testCase.get("startYear"),
                    testCase.get("endYear"),
                    testCase.get("startWeek"),
                    testCase.get("startDate"),
                    testCase.get("maxLesson"),
                    testCase.get("maxClass"),
                    testCase.get("expectedMessage")
            };
        }

        return data;
    }

    @Test(dataProvider = "termData", priority = 0)
    public void TC_UT_01(String startYear, String endYear, String startWeek, String startDate, String maxLesson, String maxClass, String expectedMessage) {
        // Nhấp vào menu "Học kỳ và Ngành"
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"main-menu-navigation\"]/li[2]/a/span"))).click();

        // Nhấp vào nút chỉnh sửa học kỳ
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"tblTerm\"]/tbody/tr[1]/td[9]/a[1]"))).click();

        // Chọn năm bắt đầu
        WebElement startYearDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='select2-start_year-container']")));
        startYearDropdown.click();
        WebElement startYearOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(text(),'" + startYear + "')]")));
        startYearOption.click();

        // Chọn năm kết thúc
        WebElement endYearDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='select2-end_year-container']")));
        endYearDropdown.click();
        WebElement endYearOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(text(),'" + endYear + "')]")));
        endYearOption.click();

        // Chọn tuần bắt đầu
        WebElement startWeekOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"start_week\"]")));
        startWeekOption.click();
        startWeekOption.clear(); // Xóa nội dung hiện tại
        startWeekOption.sendKeys(startWeek);

        // Chọn ngày bắt đầu
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement startDateElement = driver.findElement(By.xpath("//*[@id=\"term-form\"]/div[5]/input[2]"));
        js.executeScript("arguments[0].value = '" + startDate + "';", startDateElement);

        WebElement tietToiDa = wait.until(ExpectedConditions.elementToBeClickable(By.id("max_lesson")));
        tietToiDa.click();
        tietToiDa.clear();
        tietToiDa.sendKeys(maxLesson);

        WebElement lopToiDa = wait.until(ExpectedConditions.elementToBeClickable(By.id("max_class")));
        lopToiDa.click();
        lopToiDa.clear();
        lopToiDa.sendKeys(maxClass);

        // Nhấn nút "Lưu"
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"term-form\"]/div[7]/button[2]")));
        saveButton.click();

        // Kiểm tra thông báo
        FluentWait<WebDriver> fluentWait = new FluentWait<> (driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);

        WebElement popupElement = fluentWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("toast-message")));
        String popupText = popupElement.getText();
        System.out.println(popupText);
        Assert.assertEquals(popupText.trim(), expectedMessage, popupText);
    }
}
