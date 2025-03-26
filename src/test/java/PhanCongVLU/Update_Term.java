package PhanCongVLU;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import java.time.Duration;

public class Update_Term {
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
        driver = new ChromeDriver (options);
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
    @Test(priority=0)
    public void TC_UT_01(){
        // Nhấp vào menu "Học kỳ và Ngành"
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"main-menu-navigation\"]/li[2]/a/span"))).click();

        // Nhấp vào nút chỉnh sửa học kỳ
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"tblTerm\"]/tbody/tr[1]/td[9]/a[1]"))).click();

        // Chọn năm bắt đầu
        WebElement startYearDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='select2-start_year-container']")));
        startYearDropdown.click();
        WebElement startYearOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(text(),'2019')]")));
        startYearOption.click();

        // Chọn năm kết thúc
        WebElement endYearDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='select2-end_year-container']")));
        endYearDropdown.click();
        WebElement endYearOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(text(),'2023')]")));
        endYearOption.click();

        // Chọn tuần bắt đầu
        WebElement startWeekOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"start_week\"]")));
        startWeekOption.click();
        startWeekOption.clear(); // Xóa nội dung hiện tại
        startWeekOption.sendKeys("1"); // Nhập giá trị mới

        // Chọn ngày bắt đầu
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement startDate = driver.findElement(By.xpath("//*[@id=\"term-form\"]/div[5]/input[2]"));
        js.executeScript("arguments[0].value = '24/03/2020';", startDate);

        //
        WebElement tietToiDa= wait.until(ExpectedConditions.elementToBeClickable(By.id("max_lesson")));
        tietToiDa.click();
        tietToiDa.clear(); // Xóa nội dung hiện tại
        tietToiDa.sendKeys("15"); // Nhập giá trị mới

        // Chọn Lớp tối đa
        WebElement lopToiDa= wait.until(ExpectedConditions.elementToBeClickable(By.id("max_class")));
        lopToiDa.click();
        lopToiDa.clear(); // Xóa nội dung hiện tại
        lopToiDa.sendKeys("30"); // Nhập giá trị mới

        // Nhấn nút "Lưu"
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"term-form\"]/div[7]/button[2]")));
        saveButton.click();

        // Tìm phần tử popup
        WebElement popupElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("toast-message")));


        // Lấy nội dung của popup
        String popupText = popupElement.getText();
        System.out.println("Nội dung popup: " + popupText);

    }
    @Test(priority=1)
    public void TC_UT_02() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"main-menu-navigation\"]/li[2]/a/span"))).click();

        // Nhấp vào nút chỉnh sửa học kỳ
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"tblTerm\"]/tbody/tr[1]/td[9]/a[1]"))).click();

        // Xóa giá trị ngày bắt đầu
        WebElement startDate = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"term-form\"]/div[5]/input[2]")));
        startDate.click();
        startDate.sendKeys(Keys.CONTROL, "a"); // Chọn toàn bộ nội dung
        startDate.sendKeys(Keys.BACK_SPACE);    // Xóa nội dung

        // Chờ và lấy thông báo lỗi
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("start_date-error")));
        String errorText = errorMessage.getText();
        System.out.println( errorText);
    }
    @Test(priority=2)
    public void TC_UT_03() {
        // Nhấp vào menu "Học kỳ và Ngành"
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"main-menu-navigation\"]/li[2]/a/span"))).click();

        // Nhấp vào nút chỉnh sửa học kỳ
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"tblTerm\"]/tbody/tr[1]/td[9]/a[1]"))).click();

        // Chọn năm bắt đầu
        WebElement startYearDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='select2-start_year-container']")));
        startYearDropdown.click();
        WebElement startYearOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(text(),'2029')]")));
        startYearOption.click();

        // Chọn năm kết thúc
        WebElement endYearDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='select2-end_year-container']")));
        endYearDropdown.click();
        WebElement endYearOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(text(),'2024')]")));
        endYearOption.click();

        // Nhấn nút "Lưu"
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"term-form\"]/div[7]/button[2]")));
        saveButton.click();

        // Chờ và lấy thông báo lỗi
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("end_year-error")));
        String errorText = errorMessage.getText();
        System.out.println( errorText);
    }
    @Test(priority=3)
    public void TC_UT_04() {
        // Nhấp vào menu "Học kỳ và Ngành"
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"main-menu-navigation\"]/li[2]/a/span"))).click();

        // Nhấp vào nút chỉnh sửa học kỳ
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"tblTerm\"]/tbody/tr[1]/td[9]/a[1]"))).click();

        // Chọn tuần bắt đầu
        WebElement startWeekOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"start_week\"]")));
        startWeekOption.click();
        startWeekOption.clear(); // Xóa nội dung hiện tại
        startWeekOption.sendKeys("53"); // Nhập giá trị mới

        // Chờ và lấy thông báo lỗi
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("start_week-error")));// lưu ý khi chạy tới phải nhấp ra ra bên cạnh 1 click
        String errorText = errorMessage.getText();
        System.out.println( errorText);
    }

    @Test(priority=4)
    public void TC_UT_05() {
        // Nhấp vào menu "Học kỳ và Ngành"
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"main-menu-navigation\"]/li[2]/a/span"))).click();

        // Nhấp vào nút chỉnh sửa học kỳ
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"tblTerm\"]/tbody/tr[1]/td[9]/a[1]"))).click();

        // Chọn năm bắt đầu
        WebElement startYearDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='select2-start_year-container']")));
        startYearDropdown.click();
        WebElement startYearOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(text(),'2025')]")));
        startYearOption.click();

        // Chọn năm kết thúc
        WebElement endYearDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='select2-end_year-container']")));
        endYearDropdown.click();
        WebElement endYearOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(text(),'2027')]")));
        endYearOption.click();

        // Chọn ngày bắt đầu
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement startDate = driver.findElement(By.xpath("//*[@id=\"term-form\"]/div[5]/input[2]"));
        js.executeScript("arguments[0].value = '01/02/2024';", startDate);




        // Nhấn nút "Lưu"
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"term-form\"]/div[7]/button[2]")));
        saveButton.click();

        // Tìm phần tử popup
        WebElement popupElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("toast-message")));


        // Lấy nội dung của popup
        String popupText = popupElement.getText();
        System.out.println(popupText);

    }
    @Test(priority=5)
    public void TC_UT_06() {
        // Nhấp vào menu "Học kỳ và Ngành"
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"main-menu-navigation\"]/li[2]/a/span"))).click();

        // Nhấp vào nút chỉnh sửa học kỳ
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"tblTerm\"]/tbody/tr[1]/td[9]/a[1]"))).click();

        // Chọn năm bắt đầu
        WebElement startYearDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='select2-start_year-container']")));
        startYearDropdown.click();
        WebElement startYearOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(text(),'2025')]")));
        startYearOption.click();

        // Chọn năm kết thúc
        WebElement endYearDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='select2-end_year-container']")));
        endYearDropdown.click();
        WebElement endYearOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(text(),'2027')]")));
        endYearOption.click();

        // Chọn ngày bắt đầu
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement startDate = driver.findElement(By.xpath("//*[@id=\"term-form\"]/div[5]/input[2]"));
        js.executeScript("arguments[0].value = '01/02/2028';", startDate);




        // Nhấn nút "Lưu"
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"term-form\"]/div[7]/button[2]")));
        saveButton.click();

        // Tìm phần tử popup
        WebElement popupElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("toast-message")));


        // Lấy nội dung của popup
        String popupText = popupElement.getText();
        System.out.println(popupText);

    }
    @Test(priority=6)
    public void TC_UT_07() {
        // Nhấp vào menu "Học kỳ và Ngành"
        wait.until (ExpectedConditions.elementToBeClickable (By.xpath ("//*[@id=\"main-menu-navigation\"]/li[2]/a/span"))).click ();

        // Nhấp vào nút chỉnh sửa học kỳ
        wait.until (ExpectedConditions.elementToBeClickable (By.xpath ("//*[@id=\"tblTerm\"]/tbody/tr[1]/td[9]/a[1]"))).click ();
        // Chọn Lớp tối đa
        WebElement lopToiDa = wait.until (ExpectedConditions.elementToBeClickable (By.id ("max_class")));
        lopToiDa.click ();
        lopToiDa.clear (); // Xóa nội dung hiện tại
        lopToiDa.sendKeys ("31"); // Nhập giá trị mới

        // Chờ và lấy thông báo lỗi
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("max_class-error"))); // lưu ý khi chạy tới phải nhấp ra ra bên cạnh 1 click
        String errorText = errorMessage.getText();
        System.out.println( errorText);
    }


}
