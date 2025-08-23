package com.womantech.mowo.global.ai;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ai")
public class PythonProperties {
    private String pythonExec;
    private String scriptPath;
    private long timeoutMs = 10000;
    private int expectedFeatures = 21;

    public String getPythonExec() { return pythonExec; }
    public void setPythonExec(String pythonExec) { this.pythonExec = pythonExec; }

    public String getScriptPath() { return scriptPath; }
    public void setScriptPath(String scriptPath) { this.scriptPath = scriptPath; }

    public long getTimeoutMs() { return timeoutMs; }
    public void setTimeoutMs(long timeoutMs) { this.timeoutMs = timeoutMs; }

    public int getExpectedFeatures() { return expectedFeatures; }
    public void setExpectedFeatures(int expectedFeatures) { this.expectedFeatures = expectedFeatures; }
}
