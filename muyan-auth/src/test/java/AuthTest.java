import cn.dev33.satoken.secure.BCrypt;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-04-27 13:46
 */
@SpringBootTest(classes = AuthTest.class)
@Slf4j
public class AuthTest {


    @Test
    public void authTest() {
        String pwd = "123456";
        String encodePwd = BCrypt.hashpw(pwd, BCrypt.gensalt());
        log.info("pwd:{}, encodePwd:{}", pwd, encodePwd);
    }

    @Test
    public void pwdTest() {
        String pwd = "lh878188";
        String encodePwd = "$2a$10$H21JSrY1Sxm8P1hgMk8oYOg3xxRAbJihfMdRIzESFQxFLZX6OlkZC";
        log.info("match:{}", BCrypt.checkpw(pwd, encodePwd));
    }
}
