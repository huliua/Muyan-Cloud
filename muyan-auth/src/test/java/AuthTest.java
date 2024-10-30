import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.AES;
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
        String pwd = "lh878188";
        String encodePwd = BCrypt.hashpw(pwd, BCrypt.gensalt());
        log.info("pwd:{}, encodePwd:{}", pwd, encodePwd);
    }

    @Test
    public void pwdTest() {
        String pwd = "lh878188";
        String encodePwd = "$2a$10$H21JSrY1Sxm8P1hgMk8oYOg3xxRAbJihfMdRIzESFQxFLZX6OlkZC";
        log.info("match:{}", BCrypt.checkpw(pwd, encodePwd));
    }

    @Test
    public void testSha256() {
        String encryptedData = "LQbiuUxc+NmG8fMjjHQ1Lg==";

        // 密钥和向量需要和前端保持一致
        byte[] key = "codeShare-huliua".getBytes();
        byte[] iv = "codeShare-huliua".getBytes();

        // 使用Hutool进行AES解密
        AES aes = new AES("CBC", "PKCS7Padding", key, iv);
        String decryptedData = aes.decryptStr(encryptedData);

        System.out.println(decryptedData); // 打印解密后的数据
    }

    @Test
    public void testRsa() {
        String data = "Ysruhuue2EnebMbwdx3RBmJhucNLMC34+CGs6oNeTNu+9XqT3jQ2J1Rc69Iqh2fKbqMCktbTzkXFKxGhXLG26AndEaQmxdX53z3xKuyRdBK9t0EEARPkEVCw1VDl9JUjYylmnuE6TTEy4JmPmMJeUlOTkIX4iIM8cCYfbJMBMmI=";
        RSA rsa = new RSA("", null);

        byte[] decrypt = rsa.decrypt(data, KeyType.PrivateKey);
        System.out.println(new String(decrypt, CharsetUtil.CHARSET_UTF_8));
    }


}
