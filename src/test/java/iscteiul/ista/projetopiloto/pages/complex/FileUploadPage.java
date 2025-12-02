package iscteiul.ista.projetopiloto.pages.complex;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class FileUploadPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // input de ficheiro
    @FindBy(id = "file-upload")
    private WebElement fileInput;

    // botão de submit
    @FindBy(id = "file-submit")
    private WebElement submitButton;

    // elemento que mostra o ficheiro enviado
    @FindBy(id = "uploaded-files")
    private WebElement uploadedFiles;

    public FileUploadPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void uploadFile(String filePath) {
        fileInput.sendKeys(filePath);
        submitButton.click();
    }

    public String getUploadedFileName() {
        return wait.until(ExpectedConditions.visibilityOf(uploadedFiles)).getText();
    }

    public WebElement getFileInput() {
        return fileInput;
    }

    public WebElement getSubmitButton() {
        return submitButton;
    }

    public WebElement getUploadedFiles() {
        return uploadedFiles;
    }
}
