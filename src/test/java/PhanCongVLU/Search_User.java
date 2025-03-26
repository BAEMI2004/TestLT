package PhanCongVLU;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class Search_User {
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
    public void TC_SU_01() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/Phancong02/User']"))).click();
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='tblUser_filter']/label/input")));
        searchBox.sendKeys("Võ Minh Tân");

        WebElement searchResult = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='tblUser']//td[contains(text(), 'Võ Minh Tân')]")));

        if (searchResult.isDisplayed()) {
            System.out.println("✅ TC_SU_01: Test Passed - Tìm thấy kết quả phù hợp.");
        } else {
            System.out.println("❌ TC_SU_01: Test Failed - Không tìm thấy kết quả.");
        }
        Assert.assertTrue(searchResult.isDisplayed(), "Không tìm thấy kết quả chứa tên Võ Minh Tân");
    }

    @Test(priority=1, alwaysRun = true)
    public void TC_SU_02() throws InterruptedException {
        // Reload trang để đảm bảo trạng thái ban đầu
        driver.navigate().refresh();
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='tblUser_filter']/label/input")));

        // Xóa dữ liệu bằng phím tắt để đảm bảo sạch hoàn toàn
        searchBox.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        searchBox.sendKeys("sdgfjhsdhfsdjv");

        // Chờ bảng cập nhật dữ liệu
        Thread.sleep(2000);

        try {
            WebElement noResultMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Không tìm thấy kết quả')]")));
            Assert.assertTrue(noResultMessage.isDisplayed(), "Thông báo 'Không tìm thấy kết quả' không hiển thị.");
            System.out.println("✅ TC_SU_02: Test Passed - Hiển thị thông báo không tìm thấy kết quả.");
        } catch (TimeoutException e) {
            System.out.println("❌ TC_SU_02: Test Failed - Không tìm thấy kết quả nhưng cũng không có thông báo phù hợp.");
            Assert.fail("Không tìm thấy kết quả nhưng cũng không hiển thị thông báo.");
        }
    }

    @Test(priority=2)
    public void TC_SU_03() throws InterruptedException {
        // Truy cập trang quản lý người dùng
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/Phancong02/User']"))).click();

        // Nhập từ khóa tìm kiếm
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='tblUser_filter']/label/input")));
        searchBox.sendKeys("Võ Minh Tân");

        // Chờ đợi kết quả hiển thị
        Thread.sleep(2000);

        // Xác minh tổng số kết quả hiển thị ở góc dưới bên trái
        WebElement resultTextElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='tblUser_info']")));
        String actualText = resultTextElement.getText();
        System.out.println("📢 Thông báo hệ thống: " + actualText);

        // Kiểm tra xem kết quả có đúng không
        String expectedText = "Hiển thị 1 tới 3 của 3 dữ liệu";
        if (actualText.contains(expectedText)) {
            System.out.println("✅ TC_SU_03: Test Passed - Thông tin tổng số kết quả hiển thị đúng.");
        } else {
            System.out.println("❌ TC_SU_03: Test Failed - Kết quả hiển thị không khớp. Expected: '" + expectedText + "' nhưng nhận được: '" + actualText + "'");
        }

        Assert.assertTrue(actualText.contains(expectedText), "Tổng số kết quả hiển thị không đúng.");

        // 🔥 XÓA DỮ LIỆU TRONG Ô TÌM KIẾM SAU KHI TEST
        searchBox.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        Thread.sleep(1000); // Chờ hệ thống cập nhật lại danh sách
    }

    @Test(priority=3)
    public void TC_SU_04() throws InterruptedException {
        // Truy cập trang quản lý người dùng
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/Phancong02/User']"))).click();

        // Nhập ký tự đặc biệt vào ô tìm kiếm
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='tblUser_filter']/label/input")));
        searchBox.sendKeys("\""); // Nhập dấu "

        // Chờ đợi kết quả hiển thị
        Thread.sleep(2000);

        // Xác minh tổng số kết quả hiển thị ở góc dưới bên trái
        WebElement resultTextElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='tblUser_info']")));
        String actualText = resultTextElement.getText();
        System.out.println("📢 Thông báo hệ thống: " + actualText);

        // Kiểm tra xem kết quả có đúng không
        String expectedText = "Hiển thị 1 tới 10 của 504 dữ liệu";
        if (actualText.contains(expectedText)) {
            System.out.println("✅ TC_SU_04: Test Passed - Hệ thống không lọc kết quả khi nhập ký tự đặc biệt.");
        } else {
            System.out.println("❌ TC_SU_04: Test Failed - Kết quả hiển thị không khớp. Expected: '" + expectedText + "' nhưng nhận được: '" + actualText + "'");
        }

        Assert.assertTrue(actualText.contains(expectedText), "Tổng số kết quả hiển thị không đúng khi nhập ký tự đặc biệt.");

        // 🔥 XÓA DỮ LIỆU TRONG Ô TÌM KIẾM SAU KHI TEST
        searchBox.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        Thread.sleep(1000); // Chờ hệ thống cập nhật lại danh sách
    }

    @Test(priority=4)
    public void TC_SU_05() throws InterruptedException {
        // Truy cập trang quản lý người dùng
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/Phancong02/User']"))).click();

        // Nhập từ khóa tìm kiếm
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='tblUser_filter']/label/input")));
        searchBox.sendKeys("Võ Minh Tân");

        // Chờ xem kết quả có cập nhật ngay không
        Thread.sleep(2000);

        // Xác minh kết quả hiển thị ngay lập tức
        List<WebElement> searchResults = driver.findElements(By.xpath("//table[@id='tblUser']//td[contains(text(), 'Võ Minh Tân')]"));
        if (!searchResults.isEmpty()) {
            System.out.println("✅ TC_SU_05: Test Passed - Kết quả cập nhật ngay khi nhập mà không cần nhấn nút tìm kiếm.");
        } else {
            System.out.println("❌ TC_SU_05: Test Failed - Kết quả không cập nhật tức thời.");
        }

        Assert.assertFalse(searchResults.isEmpty(), "Kết quả tìm kiếm không được cập nhật ngay lập tức.");

        // 🔥 XÓA DỮ LIỆU TRONG Ô TÌM KIẾM SAU KHI TEST
        searchBox.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        Thread.sleep(1000);
    }
    @Test(priority=5)
    public void TC_SU_06() throws InterruptedException {
        // Truy cập trang quản lý người dùng
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/Phancong02/User']"))).click();

        // Nhập khoảng trắng vào ô tìm kiếm
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='tblUser_filter']/label/input")));
        searchBox.sendKeys("   "); // Nhập ba khoảng trắng

        // Chờ xem kết quả có cập nhật không
        Thread.sleep(2000);

        try {
            // Kiểm tra xem có thông báo "Không tìm thấy kết quả" không
            WebElement noResultMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Không tìm thấy kết quả')]")));
            Assert.assertTrue(noResultMessage.isDisplayed(), "Thông báo 'Không tìm thấy kết quả' không hiển thị.");
            System.out.println("✅ TC_SU_06: Test Passed - Hệ thống hiển thị 'Không tìm thấy kết quả' khi nhập khoảng trắng.");
        } catch (TimeoutException e) {
            System.out.println("❌ TC_SU_06: Test Failed - Kết quả không hợp lệ khi nhập khoảng trắng.");
            Assert.fail("Không hiển thị thông báo 'Không tìm thấy kết quả' khi nhập khoảng trắng.");
        }

        // 🔥 XÓA DỮ LIỆU TRONG Ô TÌM KIẾM SAU KHI TEST
        searchBox.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        Thread.sleep(1000);
    }
    @Test(priority=6)
    public void TC_SU_07() throws InterruptedException {
        // Truy cập trang quản lý người dùng
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/Phancong02/User']"))).click();

        // Nhập 1 ký tự vào ô tìm kiếm
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='tblUser_filter']/label/input")));
        searchBox.sendKeys("a");

        // Chờ xem kết quả có cập nhật không
        Thread.sleep(2000);

        // Kiểm tra xem có kết quả hiển thị không
        List<WebElement> searchResults = driver.findElements(By.xpath("//table[@id='tblUser']//td[contains(text(), 'a')]"));
        if (!searchResults.isEmpty()) {
            System.out.println("✅ TC_SU_07: Test Passed - Hệ thống hiển thị danh sách chứa ký tự tìm kiếm.");
        } else {
            System.out.println("❌ TC_SU_07: Test Failed - Không có kết quả phù hợp khi nhập 1 ký tự.");
        }

        Assert.assertFalse(searchResults.isEmpty(), "Hệ thống không hiển thị kết quả chứa ký tự tìm kiếm.");

        // 🔥 XÓA DỮ LIỆU TRONG Ô TÌM KIẾM SAU KHI TEST
        searchBox.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        Thread.sleep(1000);
    }
    @Test(priority=7)
    public void TC_SU_08() throws InterruptedException {
        // Truy cập trang quản lý người dùng
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/Phancong02/User']"))).click();

        // Nhập số vào ô tìm kiếm
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='tblUser_filter']/label/input")));
        searchBox.sendKeys("12345");

        // Chờ xem kết quả có cập nhật không
        Thread.sleep(2000);

        // Kiểm tra xem có kết quả hiển thị không
        List<WebElement> searchResults = driver.findElements(By.xpath("//table[@id='tblUser']//td[contains(text(), '12345')]"));
        if (!searchResults.isEmpty()) {
            System.out.println("✅ TC_SU_08: Test Passed - Hệ thống hiển thị danh sách chứa số tìm kiếm.");
        } else {
            System.out.println("❌ TC_SU_08: Test Failed - Không có kết quả phù hợp khi nhập số.");
        }

        Assert.assertFalse(searchResults.isEmpty(), "Hệ thống không hiển thị kết quả chứa số tìm kiếm.");

        // 🔥 XÓA DỮ LIỆU TRONG Ô TÌM KIẾM SAU KHI TEST
        searchBox.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        Thread.sleep(1000);
    }


//    @AfterClass
//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
}
