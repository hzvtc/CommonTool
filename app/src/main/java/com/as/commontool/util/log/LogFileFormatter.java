package com.as.commontool.util.log;

import java.text.MessageFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import cn.jesse.nativelogger.formatter.TagFormatter;

/**
 * Created by FJQ on 2017/5/27.
 */

public class LogFileFormatter extends Formatter {
    private static final String LINE_SEPARATOR = "\n";

    public LogFileFormatter() {
    }

    public String format(LogRecord r) {
        StringBuilder sb = new StringBuilder();
        sb.append(MessageFormat.format("{0,date,yyyy-MM-dd HH:mm:ss} ", new Object[]{new Date(r.getMillis())}));
        sb.append(r.getLoggerName()).append(": ");
        sb.append(r.getLevel().getName());
        sb.append(this.formatMessage(r)).append("\n");
        if (r.getThrown() != null) {
            sb.append("Throwable occurred: ");
            sb.append(TagFormatter.format(r.getThrown()));
        }

        return sb.toString();
    }
}
