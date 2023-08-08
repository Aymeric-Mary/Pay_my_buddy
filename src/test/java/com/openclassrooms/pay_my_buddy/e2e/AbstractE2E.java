package com.openclassrooms.pay_my_buddy.e2e;

import com.openclassrooms.pay_my_buddy.model.User;
import com.openclassrooms.pay_my_buddy.repository.UserRepository;
import com.openclassrooms.pay_my_buddy.service.transaction.TransactionService;
import com.openclassrooms.pay_my_buddy.service.user.UserService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractE2E {
    @LocalServerPort
    protected int port;
    protected WebDriver driver;
    protected String baseUrl;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    UserService userServiceMock;

    @MockBean
    TransactionService transactionService;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        baseUrl = "http://localhost:" + port;
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void login(String email, String password) {
        login(email, password, false);
    }

    protected void login(String email, String password, Boolean rememberMe) {
        driver.get(baseUrl + "/login");
        WebElement inputEmail = driver.findElement(By.id("email"));
        WebElement inputPassword = driver.findElement(By.id("password"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type=submit]"));
        WebElement rememberMeCheckbox = driver.findElement(By.id("remember-me"));

        inputEmail.sendKeys(email);
        inputPassword.sendKeys(password);
        if (rememberMe) rememberMeCheckbox.click();
        submitButton.click();
    }

    protected User mockUserRepository(String email, String password) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = User.builder()
                .email(email)
                .password(encoder.encode(password))
                .build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        return user;
    }

    protected void mockConnectableUsers(List<User> connectableUsers) {
        when(userServiceMock.getConnectableUsers()).thenReturn(connectableUsers);
    }

}
