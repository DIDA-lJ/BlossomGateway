package blossom.project.common.exception;


import blossom.project.common.enums.ResponseCode;

/**
 * @author: linqi
 * @description: 限流异常
 */
public class LimitedException  extends BaseException {

 private static final long serialVersionUID = -5534700534739261461L;

 public LimitedException(ResponseCode code) {
  super(code.getMessage(), code);
 }

 public LimitedException(Throwable cause, ResponseCode code) {
  super(code.getMessage(), cause, code);
 }


}
