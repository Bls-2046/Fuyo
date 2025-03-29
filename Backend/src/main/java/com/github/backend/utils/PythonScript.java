package com.github.backend.utils;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class PythonScript {
    private PythonScript() {}

    // 脚本预加载
    private static Process pythonProcess;
    private static BufferedWriter writer;
    private static BufferedReader reader;
    private static boolean isPythonProcessRunning = false;

    // 启动 Python 进程（预加载）
    public static void startPythonProcess(String pythonInterpreter, String scriptPath){
        try {
            log.info("准备启动 Python 脚本");
            pythonProcess = Runtime.getRuntime().exec(new String[]{pythonInterpreter, scriptPath});

            // 获取 Python 脚本的输出流（用于向脚本传递数据）
            log.info("准备创建输出流");
            writer = new BufferedWriter(new OutputStreamWriter(pythonProcess.getOutputStream(), StandardCharsets.UTF_8));
            log.info("输出流创建完成");

            // 获取 Python 脚本的输入流（用于读取脚本的输出）
            log.info("准备创建输入流");
            reader = new BufferedReader(new InputStreamReader(pythonProcess.getInputStream(), StandardCharsets.UTF_8));
            log.info("输入流创建完成");

            log.info("Python 脚本启动成功");
            isPythonProcessRunning = true;
        } catch (IOException e) {
            log.error(e.getMessage());
            isPythonProcessRunning = false;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 执行 Python 脚本并返回输出结果
     *
     * @param username 用户名
     * @param password 密码
     * @return Python 脚本的输出结果
     * @throws IOException 如果发生 I/O 错误
     */
    public static String executePythonScript(String username, String password) throws IOException {
        while (true) {
            if (isPythonProcessRunning) {
                break;
            }
        }
        // 构造输入数据
        writer.write(username + "\n");
        writer.write(password + "\n");
        writer.flush();

        // 读取 Python 进程的输出（仅一行）
        return reader.readLine();
    }

    /**
     * 优雅地退出 Python 脚本
     */
    @PreDestroy
    public void stopPythonProcess() {
        try {
            log.info("发送脚本退出命令...");
            writer.write("exit\n");
            writer.flush();

            // 阻塞式读取（避免 ready() 遗漏数据）
            String line;
            long deadline = System.currentTimeMillis() + 30000;
            while ((line = reader.readLine()) != null) {
                log.debug("收到Python输出: {}", line);
                if ("BROWSERS_CLOSED_OK".equals(line)) {
                    log.info("接收脚本关闭确认");
                    break;
                }
                if (System.currentTimeMillis() > deadline) {
                    log.warn("等待超时");
                    break;
                }
            }
        } catch (IOException e) {
            log.error("关闭 Python 进程时出错: {}", e.getMessage());
        } try {
            // 智能终止进程（关键修改）
            if (pythonProcess != null) {
                // 先尝试正常终止
                if (pythonProcess.isAlive()) {
                    pythonProcess.destroy();  // 先发 SIGTERM 信号

                    // 等待5秒让进程自然退出
                    boolean exitedNormally = pythonProcess.waitFor(5, TimeUnit.SECONDS);

                    if (!exitedNormally && pythonProcess.isAlive()) {
                        log.warn("进程未正常退出，准备强制终止...");
                        pythonProcess.destroyForcibly();  // 发送 SIGKILL
                        if (!pythonProcess.waitFor(2, TimeUnit.SECONDS)) {
                            log.error("强制终止进程失败！可能存在僵尸进程");
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("进程终止等待被中断", e);
        } finally {
            // 安全的资源清理
            Throwable primaryException = null;

            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                primaryException = e;
                log.error("关闭writer失败: {}", e.getMessage());
            }

            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                if (primaryException == null) {
                    primaryException = e;
                }
                log.error("关闭reader失败: {}", e.getMessage());
            }

            // 确保资源置空（防止重复关闭）
            pythonProcess = null;
            reader = null;
            writer = null;

            if (primaryException != null) {
                log.error("资源清理失败", primaryException);
            }
        }
    }
}
