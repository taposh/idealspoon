
package com.kingombo.slf5j;

import java.util.Formatter;

import org.slf4j.Marker;

public class Logger {

    private static ThreadLocal<Formatter> formatterCache = new FormatterCache();

    final private org.slf4j.Logger logger;

    public Logger(org.slf4j.Logger logger) {
        this.logger = logger;
    }

    public void debug(Marker marker, String format, Object... args) {
        logger.debug(marker, sprintf(format, args));
    }

    public void debug(Marker marker, String msg, Throwable throwable) {
        logger.debug(marker, msg, throwable);
    }

    public void debug(Marker marker, String msg) {
        logger.debug(marker, msg);
    }


    public void debug(String msg, Throwable throwable) {
        logger.debug(msg, throwable);
    }

    public void debug(String msg) {
        logger.debug(msg);
    }


    public void error(Marker marker, String format, Object... args) {
        logger.error(marker, sprintf(format, args));
    }

    public void error(Marker marker, String msg, Throwable throwable) {
        logger.error(marker, msg, throwable);
    }

    public void error(Marker marker, String msg) {
        logger.error(marker, msg);
    }

    public void error(String msg, Throwable throwable) {
        logger.error(msg, throwable);
    }

    public void error(String msg) {
        logger.error(msg);
    }

    public String getName() {
        return logger.getName();
    }

    public void info(Marker marker, String format, Object... args) {
        logger.info(marker, sprintf(format, args));
    }

    public void info(Marker marker, String msg, Throwable throwable) {
        logger.info(marker, msg, throwable);
    }

    public void info(Marker marker, String msg) {
        logger.info(marker, msg);
    }

    public void info(String s, Throwable throwable) {
        logger.info(s, throwable);
    }

    public void info(String msg) {
        logger.info(msg);
    }

    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    public boolean isDebugEnabled(Marker marker) {
        return logger.isDebugEnabled(marker);
    }

    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    public boolean isErrorEnabled(Marker marker) {
        return logger.isErrorEnabled(marker);
    }

    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    public boolean isInfoEnabled(Marker marker) {
        return logger.isInfoEnabled(marker);
    }

    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    public boolean isTraceEnabled(Marker marker) {
        return logger.isTraceEnabled(marker);
    }

    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    public boolean isWarnEnabled(Marker marker) {
        return logger.isWarnEnabled(marker);
    }

    public void trace(Marker marker, String format, Object... args) {
        logger.trace(marker, sprintf(format, args));
    }

    public void trace(Marker marker, String msg, Throwable throwable) {
        logger.trace(marker, msg, throwable);
    }

    public void trace(Marker marker, String msg) {
        logger.trace(marker, msg);
    }


    public void trace(String msg, Throwable throwable) {
        logger.trace(msg, throwable);
    }

    public void trace(String msg) {
        logger.trace(msg);
    }


    public void warn(Marker marker, String format, Object... args) {
        logger.warn(marker, sprintf(format, args));
    }

    public void warn(Marker marker, String msg, Throwable throwable) {
        logger.warn(marker, msg, throwable);
    }

    public void warn(Marker marker, String msg) {
        logger.warn(marker, msg);
    }

    public void warn(String msg, Throwable throwable) {
        logger.warn(msg, throwable);
    }

    public void warn(String msg) {
        logger.warn(msg);
    }

    public void trace(String format, Object... args) {

        if (!logger.isTraceEnabled()) {
            return;
        }

        logger.trace(sprintf(format, args));
    }

    public void trace(String format, Throwable t, Object... args) {

        if (!logger.isTraceEnabled())
            return;

        logger.trace(sprintf(format, args), t);
    }

    public void debug(String format, Object... args) {

        if (!logger.isDebugEnabled()) {
            return;
        }

        logger.debug(sprintf(format, args));
    }

    public void debug(String format, Throwable t, Object... args) {

        if (!logger.isDebugEnabled())
            return;

        logger.debug(sprintf(format, args), t);
    }

    public void info(String format, Object... args) {

        if (!logger.isInfoEnabled()) {
            return;
        }

        logger.info(sprintf(format, args));
    }

    public void info(String format, Throwable t, Object... args) {

        if (!logger.isInfoEnabled())
            return;

        logger.info(sprintf(format, args), t);
    }

    public void warn(String format, Object... args) {

        if (!logger.isWarnEnabled()) {
            return;
        }

        logger.warn(sprintf(format, args));
    }

    public void warn(String format, Throwable t, Object... args) {

        if (!logger.isWarnEnabled())
            return;

        logger.warn(sprintf(format, args), t);
    }

    public void error(String format, Object... args) {

        if (!logger.isErrorEnabled()) {
            return;
        }

        logger.error(sprintf(format, args));
    }

    public void error(String format, Throwable t, Object... args) {

        if (!logger.isErrorEnabled())
            return;

        logger.error(sprintf(format, args), t);
    }

    private static String sprintf(String format, Object[] args) {

        Formatter formatter = getFormatter();
        formatter.format(format, args);

        StringBuilder sb = (StringBuilder) formatter.out();
        String message = sb.toString();
        sb.setLength(0);

        return message;
    }

    private static Formatter getFormatter() {
        return formatterCache.get();
    }

    public void traceMethodStart(Object... args) {
        if (!logger.isTraceEnabled())
            return;
        String method = Thread.currentThread().getStackTrace()[2].getMethodName();
        StringBuilder sb = new StringBuilder();
        sb.append(method);
        sb.append("(");
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]);
            if(i != args.length-1)
            sb.append(",");
        }
        sb.append(") is called");
        logger.trace(sb.toString());
    }

    public void traceMethodEnd() {
        if (!logger.isTraceEnabled())
            return;
        String method = Thread.currentThread().getStackTrace()[2].getMethodName();
        StringBuilder sb = new StringBuilder();
        sb.append(method);
        sb.append(" is returned");
        logger.trace(sb.toString());
    }
}

class FormatterCache extends ThreadLocal<Formatter> {

    @Override
    protected synchronized Formatter initialValue() {
        return new Formatter();
    }

}


