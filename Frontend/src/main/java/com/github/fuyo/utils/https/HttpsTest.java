package com.github.fuyo.utils.https;

// Jackson
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

// OkHttp3
import lombok.extern.slf4j.Slf4j;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

// Retrofit2
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

// Java
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

// Lombok
import lombok.Getter;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static com.github.fuyo.utils.https.HttpHeaders.DEFAULT_HEADERS;

/**
 * 高级HTTP客户端工具类
 * 功能：
 * 1. 支持同步/异步请求
 * 2. 完整的GET/POST/PUT/DELETE方法
 * 3. 文件上传下载
 * 4. 请求重试机制
 * 5. 完善的异常处理
 */
@Slf4j
public final class HttpsTest {
    private HttpsTest() {
        throw new AssertionError("工具类不允许实例化");
    }

    // ==================== 配置中心 ====================
    public static final class Config {
        // 默认超时时间（秒）
        private static final int DEFAULT_CONNECT_TIMEOUT = 15;
        private static final int DEFAULT_READ_TIMEOUT = 30;
        private static final int DEFAULT_WRITE_TIMEOUT = 30;
        private static final int DEFAULT_MAX_RETRIES = 3;
        private static final long DEFAULT_CACHE_SIZE = 10 * 1024 * 1024;

        @Getter
        private OkHttpClient okHttpClient;
        @Getter
        private ObjectMapper objectMapper;
        @Getter
        private Retrofit retrofit;
        private boolean enableLogging = true;
        private File cacheDirectory;
        private boolean disableSSL = false;
        private boolean productionEnv = false;

        public Config() {
            refresh();
        }

        public synchronized void refresh() {
            // 初始化JSON处理器
            this.objectMapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

            // 配置OkHttp客户端
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(new RetryInterceptor(DEFAULT_MAX_RETRIES));

            // SSL配置
            if (disableSSL) {
                if (productionEnv) {
                    throw new IllegalStateException("严重错误：生产环境不能禁用SSL验证！");
                }
                applyUnsafeSSLConfig(builder);
            }

            // 日志配置
            if (enableLogging) {
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(loggingInterceptor);
            }

            // 缓存配置
            if (cacheDirectory != null) {
                builder.cache(new Cache(cacheDirectory, DEFAULT_CACHE_SIZE));
            }

            this.okHttpClient = builder.build();

            // 配置Retrofit
            this.retrofit = new Retrofit.Builder()
                    .baseUrl("https://placeholder.com/") // 实际使用时会被覆盖
                    .client(okHttpClient)
                    .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                    .build();
        }

        private void applyUnsafeSSLConfig(OkHttpClient.Builder builder) {
            try {
                log.warn("⚠️ 安全警告：SSL证书验证已被禁用 ⚠️");

                // 创建信任所有证书的TrustManager
                final X509TrustManager trustManager = new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {}

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {}

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                };

                // 配置SSLContext
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());

                // 应用到Builder
                builder.sslSocketFactory(sslContext.getSocketFactory(), trustManager)
                        .hostnameVerifier((hostname, session) -> true);

            } catch (Exception e) {
                throw new RuntimeException("配置不安全SSL失败", e);
            }
        }

        // 配置方法
        public Config setEnableLogging(boolean enable) {
            this.enableLogging = enable;
            refresh();
            return this;
        }

        public Config setCacheDirectory(File directory) {
            this.cacheDirectory = directory;
            refresh();
            return this;
        }

        public Config setDisableSSL(boolean disable) {
            this.disableSSL = disable;
            refresh();
            return this;
        }

        public Config setProductionMode(boolean isProduction) {
            this.productionEnv = isProduction;
            if (isProduction && disableSSL) {
                throw new IllegalStateException("生产环境配置冲突：不能同时启用生产模式和禁用SSL");
            }
            return this;
        }
    }

    private static final Config CONFIG = new Config();

    // ==================== Retrofit接口定义 ====================
    private interface ApiService {
        @GET
        Call<ResponseBody> get(
                @Url String url,
                @QueryMap Map<String, String> params,
                @HeaderMap Map<String, String> headers
        );

        @POST
        Call<ResponseBody> post(
                @Url String url,
                @Body Object body,
                @HeaderMap Map<String, String> headers
        );

        @PUT
        Call<ResponseBody> put(
                @Url String url,
                @Body Object body,
                @HeaderMap Map<String, String> headers
        );

        @DELETE
        Call<ResponseBody> delete(
                @Url String url,
                @Body Object body,
                @HeaderMap Map<String, String> headers
        );

        @Multipart
        @POST
        Call<ResponseBody> upload(
                @Url String url,
                @PartMap Map<String, RequestBody> parts,
                @HeaderMap Map<String, String> headers
        );

        @Streaming
        @GET
        Call<ResponseBody> download(
                @Url String url,
                @HeaderMap Map<String, String> headers
        );
    }

    private static final ApiService API_SERVICE = CONFIG.getRetrofit().create(ApiService.class);

    // ==================== 同步请求方法 ====================

    /**
     * 同步GET请求
     * @param url 请求地址
     * @param responseType 响应类型Class
     * @return 响应数据对象
     */
    public static <T> T get(String url, Class<T> responseType) throws HttpsException {
        return get(url, null, null, responseType); // 保持原调用方式
    }

    public static <T> T get(String url,
                            Map<String, String> params,
                            Map<String, String> headers,
                            Class<T> responseType) throws HttpsException {
        try {
            // 仅在此处添加headers合并逻辑
            Map<String, String> finalHeaders = mergeHeaders(headers);
            Response<ResponseBody> response = API_SERVICE
                    .get(url, params, finalHeaders)
                    .execute();
            return parseResponse(response, responseType);
        } catch (IOException e) {
            throw new HttpsException.NetworkException("GET请求失败", e);
        }
    }

    /**
     * 同步POST请求
     * @param url 请求地址
     * @param body 请求体对象
     * @param responseType 响应类型Class
     * @return 响应数据对象
     */
    public static <T> T post(String url, Object body, Class<T> responseType) throws HttpsException {
        return post(url, body, null, responseType); // 保持原调用方式
    }

    public static <T> T post(String url,
                             Object body,
                             Map<String, String> headers,
                             Class<T> responseType) throws HttpsException {
        try {
            // 仅在此处添加headers合并逻辑
            Map<String, String> finalHeaders = mergeHeaders(headers);
            Response<ResponseBody> response = API_SERVICE
                    .post(url, body, finalHeaders)
                    .execute();
            return parseResponse(response, responseType);
        } catch (IOException e) {
            throw new HttpsException.NetworkException("POST请求失败", e);
        }
    }

    /**
     * 同步PUT请求
     * @param url 请求地址
     * @param body 请求体对象
     * @param responseType 响应类型Class
     * @return 响应数据对象
     */
    public static <T> T put(String url, Object body, Class<T> responseType) throws HttpsException {
        return put(url, body, null, responseType); // 保持原调用方式
    }

    public static <T> T put(String url,
                            Object body,
                            Map<String, String> headers,
                            Class<T> responseType) throws HttpsException {
        try {
            Map<String, String> finalHeaders = mergeHeaders(headers);
            Response<ResponseBody> response = API_SERVICE
                    .put(url, body, finalHeaders)
                    .execute();
            return parseResponse(response, responseType);
        } catch (IOException e) {
            throw new HttpsException.NetworkException("PUT请求失败", e);
        }
    }

    /**
     * 同步DELETE请求
     * @param url 请求地址
     * @return 是否成功
     */
    public static boolean delete(String url) throws HttpsException {
        return delete(url, null, null); // 保持原调用方式
    }

    public static boolean delete(String url,
                                 Object body,
                                 Map<String, String> headers) throws HttpsException {
        try {
            Map<String, String> finalHeaders = mergeHeaders(headers);
            Response<ResponseBody> response = API_SERVICE
                    .delete(url, body, finalHeaders)
                    .execute();
            return response.isSuccessful();
        } catch (IOException e) {
            throw new HttpsException.NetworkException("DELETE请求失败", e);
        }
    }
    // ==================== 共享方法 ====================
    /**
     * 内部headers合并方法（所有HTTP方法共用）
     * 保持原有null参数行为：
     * - headers为null时使用默认
     * - headers非null时与默认合并
     */
    private static Map<String, String> mergeHeaders(Map<String, String> customHeaders) {
        if (customHeaders == null) {
            return DEFAULT_HEADERS;
        }
        Map<String, String> merged = new HashMap<>(DEFAULT_HEADERS);
        merged.putAll(customHeaders);
        return merged;
    }

    // ==================== 异步请求方法 ====================

    public static <T> CompletableFuture<T> getAsync(String url, Class<T> responseType) {
        CompletableFuture<T> future = new CompletableFuture<>();
        API_SERVICE.get(url, null, null).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    future.complete(parseResponse(response, responseType));
                } catch (HttpsException e) {
                    future.completeExceptionally(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                future.completeExceptionally(
                        new HttpsException.NetworkException("异步GET请求失败", t)
                );
            }
        });
        return future;
    }

    public static <T> CompletableFuture<T> postAsync(String url, Object body, Class<T> responseType) {
        CompletableFuture<T> future = new CompletableFuture<>();
        API_SERVICE.post(url, body, null).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    future.complete(parseResponse(response, responseType));
                } catch (HttpsException e) {
                    future.completeExceptionally(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                future.completeExceptionally(
                        new HttpsException.NetworkException("异步POST请求失败", t)
                );
            }
        });
        return future;
    }

    // ==================== 文件操作 ====================

    public static ResponseBody download(String url) throws HttpsException {
        try {
            Response<ResponseBody> response = API_SERVICE
                    .download(url, null)
                    .execute();
            if (!response.isSuccessful()) {
                throw new HttpsException.HttpStatusException(
                        "下载失败: " + response.code(),
                        response.code()
                );
            }
            return response.body();
        } catch (IOException e) {
            throw new HttpsException.NetworkException("文件下载失败", e);
        }
    }

    public static <T> T upload(String url,
                               Map<String, RequestBody> parts,
                               Class<T> responseType) throws HttpsException {
        try {
            Response<ResponseBody> response = API_SERVICE
                    .upload(url, parts, null)
                    .execute();
            return parseResponse(response, responseType);
        } catch (IOException e) {
            throw new HttpsException.NetworkException("文件上传失败", e);
        }
    }

    // ==================== 核心工具方法 ====================

    private static <T> T parseResponse(Response<ResponseBody> response, Class<T> clazz)
            throws HttpsException {
        try {
            if (!response.isSuccessful()) {
                throw new HttpsException.HttpStatusException(
                        "HTTP " + response.code(),
                        response.code()
                );
            }
            ResponseBody body = response.body();
            if (body == null) {
                throw new HttpsException.EmptyResponseException();
            }
            return CONFIG.getObjectMapper().readValue(body.string(), clazz);
        } catch (JsonProcessingException e) {
            throw new HttpsException.ParseException("JSON解析失败", e);
        } catch (IOException e) {
            throw new HttpsException.ParseException("响应读取失败", e);
        }
    }

    // ==================== 拦截器 ====================

    private static class RetryInterceptor implements Interceptor {
        private final int maxRetries;
        private final AtomicInteger retryCount = new AtomicInteger(0);

        RetryInterceptor(int maxRetries) {
            this.maxRetries = maxRetries;
        }

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            okhttp3.Response response = null;
            IOException lastException = null;

            while (retryCount.getAndIncrement() <= maxRetries) {
                try {
                    response = chain.proceed(request);
                    if (response.isSuccessful()) {
                        return response;
                    }
                } catch (IOException e) {
                    lastException = e;
                }

                if (retryCount.get() <= maxRetries) {
                    try {
                        Thread.sleep(1000 * (long) Math.pow(2, retryCount.get()));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new IOException("重试中断", e);
                    }
                }
            }

            if (response != null) {
                return response;
            }
            throw lastException != null ? lastException : new IOException("未知网络错误");
        }
    }

    // ==================== 工具方法 ====================

    public static RequestBody createTextPart(String text) {
        return RequestBody.create(text, MediaType.parse("text/plain"));
    }

    public static RequestBody createFilePart(File file, MediaType type) {
        return RequestBody.create(file, type);
    }

    public static Config getConfig() {
        return CONFIG;
    }
}