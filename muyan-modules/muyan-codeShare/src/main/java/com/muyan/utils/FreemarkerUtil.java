package com.muyan.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.UUID;

@Log4j2
public class FreemarkerUtil {
    private static final Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);

    static {
        cfg.setDefaultEncoding("UTF-8");
        // 禁用模板文件查找
        cfg.setTemplateLoader(new StringTemplateLoader());
        // 允许缺失变量为空
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
    }

    public static String process(String templateStr, Map<String, Object> data) throws IOException, TemplateException {
        StringTemplateLoader loader = (StringTemplateLoader) cfg.getTemplateLoader();
        String templateName = "tpl_" + UUID.randomUUID() + "_" + System.nanoTime();
        loader.putTemplate(templateName, templateStr);

        Template template = cfg.getTemplate(templateName);
        try (StringWriter writer = new StringWriter()) {
            template.process(data, writer);
            return writer.toString();
        }
    }

    // 自定义字符串模板加载器
    private static class StringTemplateLoader implements freemarker.cache.TemplateLoader {
        // 使用ThreadLocal存储每个线程的模板内容
        private static final ThreadLocal<String> templateContentHolder = new ThreadLocal<>();

        public void putTemplate(String name, String content) {
            templateContentHolder.set(content);
        }

        @Override
        public Object findTemplateSource(String name) {
            return new StringTemplateSource(name, templateContentHolder.get());
        }

        @Override
        public long getLastModified(Object templateSource) {
            return 0;
        }

        @Override
        public Reader getReader(Object templateSource, String encoding) {
            return new StringReader(templateContentHolder.get());
        }

        @Override
        public void closeTemplateSource(Object templateSource) {
            templateContentHolder.remove();
        }

        private static class StringTemplateSource {
            private final String name;
            private final String content;

            StringTemplateSource(String name, String content) {
                this.name = name;
                this.content = content;
            }
        }
    }
}
