package com.thumbing.shared.controller;

import com.thumbing.shared.utils.datatype.DataTypeUtils;
import com.thumbing.shared.utils.datatype.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.WebDataBinder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author Stan Sai
 * @date 2020-06-23
 */
public abstract class BaseController {
    /**
     * 初始化绑定
     *
     * @param binder 数据绑定
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new PropertiesEditor() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(DataTypeUtils.parseString(text));
            }

            @Override
            public String getAsText() {
                return getValue().toString();
            }
        });
        binder.registerCustomEditor(Boolean.class, new PropertiesEditor() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(DataTypeUtils.parseBoolean(text));
            }

            @Override
            public String getAsText() {
                return getValue().toString();
            }
        });
        binder.registerCustomEditor(boolean.class, new PropertiesEditor() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(DataTypeUtils.parseBool(text));
            }

            @Override
            public String getAsText() {
                return getValue().toString();
            }
        });
        binder.registerCustomEditor(long.class, new PropertiesEditor() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(StringUtils.isBlank(text) || "null".equals(text.trim()) ? 0L : Long.parseLong(text));
            }

            @Override
            public String getAsText() {
                return getValue().toString();
            }
        });
        binder.registerCustomEditor(double.class, new PropertiesEditor() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(StringUtils.isBlank(text) || "null".equals(text.trim()) ? 0.0 : Double.parseDouble(text));
            }

            @Override
            public String getAsText() {
                return getValue().toString();
            }
        });


        binder.registerCustomEditor(int.class, new PropertiesEditor() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(DataTypeUtils.parseInt(text));
            }

            @Override
            public String getAsText() {
                return getValue().toString();
            }
        });

        binder.registerCustomEditor(LocalDate.class, new PropertiesEditor() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(DateUtils.parseDate(text));
            }

            @Override
            public String getAsText() {
                return getValue().toString();
            }
        });
        binder.registerCustomEditor(LocalDateTime.class, new PropertiesEditor() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(DateUtils.parseDateTime(text));
            }

            @Override
            public String getAsText() {
                return getValue().toString();
            }
        });
        binder.registerCustomEditor(LocalTime.class, new PropertiesEditor() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(DateUtils.parseTime(text));
            }

            @Override
            public String getAsText() {
                return getValue().toString();
            }
        });
    }
}
