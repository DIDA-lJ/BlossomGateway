package enums;

import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.Getter;

/**
 * @author linqi
 * @version 1.0.0
 * @description ResponseCode类,基础响应状态码定义
 */

@Getter
public enum ResponseCode {

    SUCCESS(HttpResponseStatus.OK, 200, "成功"),
    INTERNAL_ERROR(HttpResponseStatus.INTERNAL_SERVER_ERROR, 500, "网关内部异常");;


    private HttpResponseStatus status;
    private int code;
    private String message;

    ResponseCode(HttpResponseStatus status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpResponseStatus getStatus() {
        return status;
    }

    public void setStatus(HttpResponseStatus status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
