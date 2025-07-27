package comy.config;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class AliOssClientConfig {
    private final AliOssConfig aliOssConfig;
    public AliOssClientConfig(AliOssConfig aliOssConfig) {
        this.aliOssConfig = aliOssConfig;
    }
    @Bean
    public OSS ossClient() {
        return new OSSClientBuilder()
                .build(aliOssConfig.getEndpoint(),
                       aliOssConfig.getAccessKeyId(),
                       aliOssConfig.getAccessKeySecret());
    }
}