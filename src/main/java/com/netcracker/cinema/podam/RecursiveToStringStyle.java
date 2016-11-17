package com.netcracker.cinema.podam;

import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;

/**
 * Created by gaya on 15.11.2016.
 */
public class RecursiveToStringStyle extends ToStringStyle implements Serializable {
    private int offset;

    public RecursiveToStringStyle() {
        this(0);
    }

    private RecursiveToStringStyle(int offset) {
        setUseShortClassName(true);
        setUseFieldNames(true);
        setUseIdentityHashCode(false);
        this.offset = offset;
        String off = "";
        for (int i = 0; i < offset; i++)
            off += "\t";
        this.setContentStart("[");
        this.setFieldSeparator(SystemUtils.LINE_SEPARATOR + off + "  ");
        this.setFieldSeparatorAtStart(true);
        this.setContentEnd(SystemUtils.LINE_SEPARATOR + off + "]");
    }

    protected void appendDetail(StringBuffer buffer, String fieldName,
                                Collection<?> col) {
        buffer.append('[');
        for (Object obj : col) {
            buffer.append(ReflectionToStringBuilder.toString(obj,
                    new RecursiveToStringStyle(offset + 1)));
            buffer.append(',');
        }
        if (buffer.charAt(buffer.length() - 1) == ',')
            buffer.setCharAt(buffer.length() - 1, ']');
    }

    protected void appendDetail(StringBuffer buffer, String fieldName,
                                Object value) {
        if (value instanceof String) {
            buffer.append("\"").append(value.toString()).append("\"");
        } else if (value instanceof BigDecimal) {
            buffer.append(value.getClass().getSimpleName()).append("[").append(value.toString()).append("]");
        } else if (value instanceof BigInteger) {
            buffer.append(value.getClass().getSimpleName()).append("[").append(value.toString()).append("]");
        } else if (!value.getClass().getName().startsWith("java.lang.")) {
            try {
                buffer.append(ReflectionToStringBuilder.toString(value,
                        new RecursiveToStringStyle(offset + 1)));
            } catch (Throwable ignored) {
            }
        } else {
            super.appendDetail(buffer, fieldName, value);
        }
    }
}
