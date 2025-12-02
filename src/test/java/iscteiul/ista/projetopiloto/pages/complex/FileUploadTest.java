package iscteiul.ista.projetopiloto.pages.complex;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class FileUploadTest {

    private WebDriver driver;
    private WebDriverWait wait;


    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    public void testFileUpload() {
        driver.get("https://the-internet.herokuapp.com/upload");
        FileUploadPage uploadPage = new FileUploadPage(driver);

        String filePath = new File("example.txt").getAbsolutePath();

        wait.until(ExpectedConditions.elementToBeClickable(uploadPage.getFileInput()));
        wait.until(ExpectedConditions.elementToBeClickable(uploadPage.getSubmitButton()));

        uploadPage.uploadFile(filePath);
        String uploadedFile = wait.until(ExpectedConditions.visibilityOf(uploadPage.getUploadedFiles())).getText();
        assertEquals("example.txt", uploadedFile);
    }

    @Test
    public void testSubmitButtonEnabledBeforeUpload() {
        driver.get("https://the-internet.herokuapp.com/upload");
        FileUploadPage uploadPage = new FileUploadPage(driver);
        assertTrue(uploadPage.getSubmitButton().isEnabled(), "Submit button should be enabled before upload");
    }

    @Test
    public void testUploadEmptyFile() {
        driver.get("https://the-internet.herokuapp.com/upload");
        FileUploadPage uploadPage = new FileUploadPage(driver);
        File emptyFile = new File(System.getProperty("user.dir") + "/empty.txt");
        try {
            if (!emptyFile.exists()) emptyFile.createNewFile();
        } catch (Exception e) {
            fail("Não foi possível criar o ficheiro vazio");
        }

        uploadPage.uploadFile(emptyFile.getAbsolutePath());

        String uploadedFile = wait.until(ExpectedConditions.visibilityOf(uploadPage.getUploadedFiles())).getText();
        assertEquals("empty.txt", uploadedFile);
    }

    @Test
    public void testUploadInvalidFilePath() {
        driver.get("https://the-internet.herokuapp.com/upload");
        FileUploadPage uploadPage = new FileUploadPage(driver);

        String invalidPath = System.getProperty("user.dir") + "/nao_existe.txt";

        Exception exception = assertThrows(InvalidArgumentException.class, () -> {
            uploadPage.uploadFile(invalidPath);
        });

        assertTrue(exception.getMessage().contains("File not found"), "Deve lançar exceção para ficheiro inexistente");
    }
}

