package com.github.backend.utils;

import jakarta.annotation.PreDestroy;

import java.io.*;
import java.nio.charset.StandardCharsets;

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
            pythonProcess = Runtime.getRuntime().exec(new String[]{pythonInterpreter, scriptPath});
            // 获取 Python 脚本的输出流（用于向脚本传递数据）
            writer = new BufferedWriter(new OutputStreamWriter(pythonProcess.getOutputStream(), StandardCharsets.UTF_8));
            // 获取 Python 脚本的输入流（用于读取脚本的输出）
            reader = new BufferedReader(new InputStreamReader(pythonProcess.getInputStream(), StandardCharsets.UTF_8));

            isPythonProcessRunning = true;
        } catch (IOException e) {
            e.printStackTrace();
            isPythonProcessRunning = false;
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

    // 停止 Python 进程
    @PreDestroy
    public static void stopPythonProcess() {
        try {
            if (writer != null) {
                writer.close();
            }
            if (reader != null) {
                reader.close();
            }
            if (pythonProcess != null) {
                pythonProcess.destroy();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
