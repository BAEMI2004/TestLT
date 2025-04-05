package PhanCongVLU;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.AssertJUnit.assertEquals;

public class Create_Term {
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
    @Test(priority=0)
    public void TC_THK_01() {
        // Nhấp vào menu "Học kỳ và Ngành"
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"main-menu-navigation\"]/li[2]/a/span"))).click();

        //Click vào thêm học kỳ
        wait.until(ExpectedConditions.elementToBeClickable (By.xpath ("//*[@id=\"tblTerm_wrapper\"]/div[1]/div[2]/div/div[2]/button"))).click ();

        //Nhập vào trường học kỳ
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id"))).sendKeys("981");

        // Chọn năm bắt đầu
        WebElement startYearDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='select2-start_year-container']")));
        startYearDropdown.click();
        WebElement startYearOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(text(),'2025')]")));
        startYearOption.click();

        // Chọn năm kết thúc
        WebElement endYearDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='select2-end_year-container']")));
        endYearDropdown.click();
        WebElement endYearOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(text(),'2026')]")));
        endYearOption.click();

        // Chọn tuần bắt đầu
        WebElement startWeekOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"start_week\"]")));
        startWeekOption.click();
        startWeekOption.clear(); // Xóa nội dung hiện tại
        startWeekOption.sendKeys("1"); // Nhập giá trị mới

        JavascriptExecutor js = (JavascriptExecutor) driver;

        //chọn ngày bắt đầu
        wait.until(ExpectedConditions.elementToBeClickable (By.xpath ("//*[@id=\"term-form\"]/div[5]/input[2]"))).click ();
        WebElement selectedDate = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/div[2]/div/span[3]"));
        selectedDate.click(); // Nhấp vào ngày đã chọn


        // Chọn Tiết tối đa
        WebElement tietToiDa = wait.until (ExpectedConditions.elementToBeClickable (By.id ("max_lesson")));
        tietToiDa.click ();
        tietToiDa.clear (); // Xóa nội dung hiện tại
        tietToiDa.sendKeys ("15"); // Nhập giá trị mới

        // Chọn Lớp tối đa
        WebElement lopToiDa = wait.until (ExpectedConditions.elementToBeClickable (By.id ("max_class")));
        lopToiDa.click ();
        lopToiDa.clear (); // Xóa nội dung hiện tại
        lopToiDa.sendKeys ("30"); // Nhập giá trị mới

        // Nhấn nút "Lưu"
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"term-form\"]/div[7]/button[2]")));
        saveButton.click();


        // Chờ và lấy phần tử popup
        WebElement popupElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("toast-message")));


        // Lấy và in ra thông báo
        String popupText = popupElement.getText();
        System.out.println( popupText);
        assertEquals(popupText.trim(), "Lưu thành công!",popupText);

    }
    @Test(priority=1)
    public void TC_THK_02(){
        // Nhấp vào menu "Học kỳ và Ngành"
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"main-menu-navigation\"]/li[2]/a/span"))).click();

        //Click vào thêm học kỳ
        wait.until(ExpectedConditions.elementToBeClickable (By.xpath ("//*[@id=\"tblTerm_wrapper\"]/div[1]/div[2]/div/div[2]/button"))).click ();


        // Chọn tuần bắt đầu
        WebElement startWeekOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"start_week\"]")));
        startWeekOption.clear(); // Xóa nội dung hiện tại




        // Chọn Tiết tối đa
        WebElement tietToiDa = wait.until (ExpectedConditions.elementToBeClickable (By.id ("max_lesson")));
        tietToiDa.clear (); // Xóa nội dung hiện tại

        // Chọn Lớp tối đa
        WebElement lopToiDa = wait.until (ExpectedConditions.elementToBeClickable (By.id ("max_class")));
        lopToiDa.clear (); // Xóa nội dung hiện tại


        // Nhấn nút "Lưu"
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"term-form\"]/div[7]/button[2]")));
        saveButton.click();


        // Lấy các thông báo lỗi từ các ID cụ thể
        WebElement idErrorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id-error")));
        WebElement startWeekErrorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("start_week-error")));
        WebElement startDateErrorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("start_date-error")));
        WebElement maxLessonErrorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("max_lesson-error")));
        WebElement maxClassErrorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("max_class-error")));

        assertEquals(idErrorElement.getText().trim(), "Bạn chưa nhập học kỳ", idErrorElement.getText());
        assertEquals(startWeekErrorElement.getText().trim(), "Bạn chưa nhập tuần bắt đầu", startWeekErrorElement.getText());
        assertEquals(startDateErrorElement.getText().trim(), "Bạn chưa chọn ngày bắt đầu", startDateErrorElement.getText());
        assertEquals(maxLessonErrorElement.getText().trim(), "Bạn chưa nhập số tiết tối đa", maxLessonErrorElement.getText());
        assertEquals(maxClassErrorElement.getText().trim(), "Bạn chưa nhập số lớp tối đa", maxClassErrorElement.getText());

        // In ra các thông báo lỗi
        System.out.println(idErrorElement.getText());
        System.out.println(startWeekErrorElement.getText());
        System.out.println(startDateErrorElement.getText());
        System.out.println(maxLessonErrorElement.getText());
        System.out.println(maxClassErrorElement.getText());




    }

    @Test(priority=2)
    public void TC_THK_03(){
        // Nhấp vào menu "Học kỳ và Ngành"
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"main-menu-navigation\"]/li[2]/a/span"))).click();

        //Click vào thêm học kỳ
        wait.until(ExpectedConditions.elementToBeClickable (By.xpath ("//*[@id=\"tblTerm_wrapper\"]/div[1]/div[2]/div/div[2]/button"))).click ();

        //Nhập vào trường học kỳ
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id"))).sendKeys("999");

        // Chọn năm bắt đầu
        WebElement startYearDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='select2-start_year-container']")));
        startYearDropdown.click();
        WebElement startYearOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(text(),'2025')]")));
        startYearOption.click();

        // Chọn năm kết thúc
        WebElement endYearDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='select2-end_year-container']")));
        endYearDropdown.click();
        WebElement endYearOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(text(),'2026')]")));
        endYearOption.click();

        // Chọn tuần bắt đầu
        WebElement startWeekOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"start_week\"]")));
        startWeekOption.click();
        startWeekOption.clear(); // Xóa nội dung hiện tại
        startWeekOption.sendKeys("1"); // Nhập giá trị mới

        JavascriptExecutor js = (JavascriptExecutor) driver;

        //chọn ngày bắt đầu
        wait.until(ExpectedConditions.elementToBeClickable (By.xpath ("//*[@id=\"term-form\"]/div[5]/input[2]"))).click ();
        WebElement selectedDate = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/div[2]/div/span[3]"));
        selectedDate.click(); // Nhấp vào ngày đã chọn


        // Chọn Tiết tối đa
        WebElement tietToiDa = wait.until (ExpectedConditions.elementToBeClickable (By.id ("max_lesson")));
        tietToiDa.click ();
        tietToiDa.clear (); // Xóa nội dung hiện tại
        tietToiDa.sendKeys ("15"); // Nhập giá trị mới

        // Chọn Lớp tối đa
        WebElement lopToiDa = wait.until (ExpectedConditions.elementToBeClickable (By.id ("max_class")));
        lopToiDa.click ();
        lopToiDa.clear (); // Xóa nội dung hiện tại
        lopToiDa.sendKeys ("30"); // Nhập giá trị mới

        // Nhấn nút "Lưu"
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"term-form\"]/div[7]/button[2]")));
        saveButton.click();


        // Chờ và lấy phần tử popup
        WebElement trungHK = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("swal2-html-container")));

        // Lấy và in ra thông báo
        String popupText = trungHK.getText();
        System.out.println( popupText);
        // Kiểm tra thông báo popup
        assertEquals(popupText.trim(), "Học kỳ này đã được tạo trong hệ thống!", popupText);
    }
    @Test(priority=3)
    public void TC_THK_04() {
        // Nhấp vào menu "Học kỳ và Ngành"
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"main-menu-navigation\"]/li[2]/a/span"))).click();

        // Click vào thêm học kỳ
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"tblTerm_wrapper\"]/div[1]/div[2]/div/div[2]/button"))).click();

        // Nhập mã học kỳ
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id"))).sendKeys("1000");

        // Tuần bắt đầu vượt quá giới hạn
        WebElement startWeek = wait.until(ExpectedConditions.elementToBeClickable(By.id("start_week")));
        startWeek.click();
        startWeek.clear();
        startWeek.sendKeys("53");
        startWeek.sendKeys(Keys.TAB); // Kích hoạt validate

        // Nhập tiết tối đa
        driver.findElement(By.id("max_lesson")).sendKeys("16");

        // Nhập lớp tối đa
        driver.findElement(By.id("max_class")).sendKeys("31");

        // Lấy các thông báo lỗi và kiểm tra
        WebElement startWeekErrorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("start_week-error")));
        WebElement idErrorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id-error")));
        WebElement startDateErrorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("start_date-error")));
        WebElement maxLessonErrorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("max_lesson-error")));
        WebElement maxClassErrorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("max_class-error")));

        // In ra các thông báo lỗi
        System.out.println(startWeekErrorElement.getText());
        System.out.println(idErrorElement.getText());
        System.out.println(startDateErrorElement.getText());
        System.out.println(maxLessonErrorElement.getText());
        System.out.println(maxClassErrorElement.getText());

        // Kiểm tra thông báo lỗi cho start_week vượt quá giới hạn
        assertEquals(startWeekErrorElement.getText().trim(), "Tuần bắt đầu không hợp lệ", "Thông báo lỗi tuần bắt đầu không khớp");
        assertEquals(idErrorElement.getText().trim(), "Mã học kỳ không hợp lệ", "Thông báo lỗi mã học kỳ không khớp");
        assertEquals(startDateErrorElement.getText().trim(), "Ngày bắt đầu không hợp lệ", "Thông báo lỗi ngày bắt đầu không khớp");
        assertEquals(maxLessonErrorElement.getText().trim(), "Số tiết tối đa không hợp lệ", "Thông báo lỗi số tiết tối đa không khớp");
        assertEquals(maxClassErrorElement.getText().trim(), "Số lớp tối đa không hợp lệ", "Thông báo lỗi số lớp tối đa không khớp");
    }
}

