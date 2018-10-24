package com.jc.cz_index.common.utils;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.List;

/**
 * 
 * 描述：
 * 
 * @author liujicheng 2016年7月11日 上午10:28:36
 * @version 1.0.1
 * @since 1.0.1
 */
public class MultiDateParseEditor extends PropertyEditorSupport {
    private List<DateFormat> dateFormats;

    private boolean          allowEmpty;

    private final int        exactDateLength;



    public MultiDateParseEditor(List<DateFormat> dateFormats, boolean allowEmpty) {
        if (dateFormats == null || dateFormats.size() == 0) {
            throw new IllegalArgumentException("Param dateFormats could not be empty");
        }
        this.dateFormats = dateFormats;
        this.allowEmpty = allowEmpty;
        this.exactDateLength = -1;
    }



    public MultiDateParseEditor(List<DateFormat> dateFormats, boolean allowEmpty, int exactDateLength) {
        if (dateFormats == null || dateFormats.size() == 0) {
            throw new IllegalArgumentException("Param dateFormats could not be empty");
        }
        this.dateFormats = dateFormats;
        this.allowEmpty = allowEmpty;
        this.exactDateLength = exactDateLength;
    }



    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (this.allowEmpty && StringUtils.isEmpty(text)) {
            // Treat empty String as null value.
            setValue(null);
        } else if (text != null && this.exactDateLength >= 0 && text.length() != this.exactDateLength) {
            throw new IllegalArgumentException("Could not parse date: it is not exactly" + this.exactDateLength + "characters long");
        } else {
            ParseException lastException = null;
            for (DateFormat dateFormat : dateFormats) {
                try {
                    setValue(dateFormat.parse(text));
                    return;
                } catch (ParseException e) {
                    lastException = e;
                }
            }
            throw new IllegalArgumentException("Could not parse date: " + lastException.getMessage(), lastException);
        }
    }

}
