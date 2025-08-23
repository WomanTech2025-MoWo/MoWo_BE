package com.womantech.mowo.global.ai;

import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Component
public class PythonRunner {
    private final PythonProperties props;

    public PythonRunner(PythonProperties props) {
        this.props = props;
    }

    public String runWithJsonInput(String jsonInput) {
        // 명령어 리스트(공백으로 합치지 말고 리스트로 넘기기! Windows에서도 안전)
        List<String> command = List.of(props.getPythonExec(), props.getScriptPath());

        ProcessBuilder pb = new ProcessBuilder(command);
        // 작업 디렉토리: 프로젝트 루트(기본). predict.py가 절대경로면 상관 없음.
        pb.redirectErrorStream(false);

        Process process;
        try {
            process = pb.start();
        } catch (IOException e) {
            throw new IllegalStateException("파이썬 프로세스 시작 실패: " + e.getMessage(), e);
        }

        // 입력 쓰기 (stdin)
        try (BufferedWriter w = new BufferedWriter(
                new OutputStreamWriter(process.getOutputStream(), StandardCharsets.UTF_8))) {
            w.write(jsonInput);
            w.flush();
        } catch (IOException e) {
            process.destroyForcibly();
            throw new IllegalStateException("파이썬 stdin 전송 실패: " + e.getMessage(), e);
        }

        // stdout/stderr 비동기 수집
        ExecutorService es = Executors.newFixedThreadPool(2);
        Future<String> outFuture = es.submit(() -> readAll(process.getInputStream()));
        Future<String> errFuture = es.submit(() -> readAll(process.getErrorStream()));

        boolean finished;
        try {
            finished = process.waitFor(props.getTimeoutMs(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            finished = false;
        }

        if (!finished) {
            process.destroyForcibly();
            es.shutdownNow();
            throw new IllegalStateException("파이썬 실행 타임아웃(" + props.getTimeoutMs() + "ms)");
        }

        int code = process.exitValue();
        String stdout, stderr;
        try {
            stdout = outFuture.get(1, TimeUnit.SECONDS);
            stderr = errFuture.get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            es.shutdownNow();
            throw new IllegalStateException("파이썬 출력 수집 실패: " + e.getMessage(), e);
        } finally {
            es.shutdown();
        }

        if (code != 0) {
            throw new IllegalStateException("파이썬 종료 코드 " + code + ", stderr: " + stderr);
        }

        return stdout.trim();
    }

    private static String readAll(InputStream is) throws IOException {
        try (BufferedReader r = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) sb.append(line);
            return sb.toString();
        }
    }
}
