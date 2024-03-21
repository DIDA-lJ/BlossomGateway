package blossom.project.client.api;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author linqi
 * @version 1.0.0
 * @description
 */


@Data
@ConfigurationProperties(prefix = "api")
public class ApiProperties {
    /**
     * 注册中心地址
     */
    private String registerAddress;
    /**
     * 环境
     */
    private String env = "dev";
    /**
     * 是否灰度发布
     */
    private boolean gray;
}
