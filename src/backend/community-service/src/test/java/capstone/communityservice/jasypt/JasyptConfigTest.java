package capstone.communityservice.jasypt;

import capstone.communityservice.global.config.JasyptConfig;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = JasyptConfig.class)
public class JasyptConfigTest {
    final BeanFactory beanFactory = new AnnotationConfigApplicationContext(JasyptConfig.class);
    final StringEncryptor stringEncryptor = beanFactory.getBean("jasyptStringEncryptor", StringEncryptor.class);

    @Test
    @DisplayName("암복호화 테스트")
    void encryptorTest() {
        String keyword = "jdbc:h2:tcp://localhost:9099/~/community";
        String enc = stringEncryptor.encrypt(keyword);
        String des = stringEncryptor.decrypt(enc);
        System.out.println(des + "-> ENC(" + enc + ")");
        assertThat(keyword).isEqualTo(des);

        // 틀릴 경우 에러
//        Assertions.assertThat(keyword).isEqualTo(enc); // 에러
//        Assertions.assertThat(enc).isEqualTo(des); // 에러
    }
}
