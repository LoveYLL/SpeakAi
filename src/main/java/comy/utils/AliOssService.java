package comy.utils;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import comy.config.AliOssConfig;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;
@Service
public class AliOssService {
    private final OSS ossClient;
    private final AliOssConfig config;
    public AliOssService(OSS ossClient, AliOssConfig config) {
        this.ossClient = ossClient;
        this.config = config;
    }
    public String upload(byte[] content, String suffix) {
        String fileName = System.currentTimeMillis() + "_" + UUID.randomUUID() + suffix;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(content);
        ossClient.putObject(config.getBucketName(), fileName, inputStream);
        Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000L);
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(config.getBucketName(), fileName, HttpMethod.GET);
        req.setExpiration(expiration);
        URL url = ossClient.generatePresignedUrl(req);
        return url.toString();
    }
}