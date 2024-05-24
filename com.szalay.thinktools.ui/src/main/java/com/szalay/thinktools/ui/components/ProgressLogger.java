package com.szalay.thinktools.ui.components;

@SuppressWarnings("unused")
public interface ProgressLogger {

    void logWarning(String message);

    void logError(String error);

    void logInfo(String message);

    void setProgress(long progress);
}
