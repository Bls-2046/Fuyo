package com.github.fuyo.utils.https;

/**
 * HTTP请求相关自定义异常体系
 */
public class HttpsException extends Exception {
  public HttpsException(String message) {
    super(message);
  }

  public HttpsException(String message, Throwable cause) {
    super(message, cause);
  }

  /** 网络连接异常（超时、无法连接等） */
  public static final class NetworkException extends HttpsException {
    public NetworkException(String message) {
      super(message);
    }
    public NetworkException(String message, Throwable cause) {
      super(message, cause);
    }
  }

  /** HTTP状态码异常（4xx/5xx等） */
  public static final class HttpStatusException extends HttpsException {
    private final int statusCode;

    public HttpStatusException(String message, int statusCode) {
      super(message);
      this.statusCode = statusCode;
    }
    public int getStatusCode() {
      return statusCode;
    }
  }

  /** 响应数据解析异常 */
  public static final class ParseException extends HttpsException {
    public ParseException(String message) {
      super(message);
    }
    public ParseException(String message, Throwable cause) {
      super(message, cause);
    }
  }

  /** 空响应异常 */
  public static final class EmptyResponseException extends HttpsException {
    public EmptyResponseException() {
      super("Received empty response body");
    }
  }

  /** 请求超时异常 */
  public static final class TimeoutException extends HttpsException {
    public TimeoutException(String message) {
      super(message);
    }
  }

  /** SSL安全异常 */
  public static final class SslException extends HttpsException {
    public SslException(String message) {
      super(message);
    }
    public SslException(String message, Throwable cause) {
      super(message, cause);
    }
  }

  /** 请求取消异常 */
  public static final class CanceledException extends HttpsException {
    public CanceledException() {
      super("Request was canceled");
    }
  }
}
