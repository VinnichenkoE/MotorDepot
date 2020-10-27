package com.vinnichenko.motorDepot.controller.jsp;

import com.vinnichenko.motorDepot.util.DateConverter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class DateTag extends TagSupport {

    private long date;

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public int doStartTag() throws JspException {
        String date = DateConverter.toDate(this.date);
        try {
            pageContext.getOut().write(date);
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }
}
