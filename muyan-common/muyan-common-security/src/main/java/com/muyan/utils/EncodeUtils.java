package com.muyan.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.muyan.config.EncryptConfig;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-11-05 22:03
 */
@Component
public class EncodeUtils {

    @Resource
    private EncryptConfig encryptConfig;

    public String decode(String str) {
        RSA rsa = new RSA(encryptConfig.getPrivateKey(), null);
        byte[] decrypt = rsa.decrypt(str, KeyType.PrivateKey);
        return new String(decrypt, CharsetUtil.CHARSET_UTF_8);
    }
}
