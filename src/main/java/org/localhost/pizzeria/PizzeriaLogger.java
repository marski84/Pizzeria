package org.localhost.pizzeria;

import org.slf4j.LoggerFactory;

public final class PizzeriaLogger {
    private static final PizzeriaLogger INSTANCE = new PizzeriaLogger();
    private final org.slf4j.Logger log;

    private PizzeriaLogger() {
        log = LoggerFactory.getLogger(StackWalker.getInstance().getCallerClass());
    }

    public static PizzeriaLogger getInstance() {
        return INSTANCE;
    }

    public void info(String message) {
        log.info(message);
    }

    public void error(String message) {
        log.error(message);
    }

    public void error(String message, Throwable throwable) {
        log.error(message, throwable);
    }



}