package blossom.gateway.common.response;

import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author linqi
 * @version 1.0.0
 * @description  Response类
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisplayResponse {
    private HttpResponseStatus status;
    private int code;
    private String message;
    private String data;
}