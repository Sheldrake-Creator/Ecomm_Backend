package com.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {

    private static final Logger logger = LoggerFactory.getLogger(LogUtils.class);

    public static void entry() {
        if (logger.isInfoEnabled()) {
            String methodName = getCurrentMethodName();
            logger.debug("Entering method: " + methodName);
        }
    }

    public static void exit() {
        if (logger.isInfoEnabled()) {
            String methodName = getCurrentMethodName();
            logger.debug("Exiting method: " + methodName);
        }
    }

    private static String getCurrentMethodName() {
        return StackWalker.getInstance().walk(stream -> stream.skip(2).findFirst())
                .map(StackWalker.StackFrame::getMethodName).orElse("Unknown method");
    }
}