package com.jc.cz_index.common.utils;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.xml.sax.SAXException;

public class WSUtils {
    public static JAXBContext context = null;



    // 对象转xml
    public static <T> String BeanConvertToXML(T t, Class<T> requiredType) throws JAXBException {
        context = JAXBContext.newInstance(requiredType);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setListener(new MarshallerListener());
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "GBK");
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
        StringWriter writer = new StringWriter();
        marshaller.marshal(t, writer);
        return writer.toString();
    }



    // String转document
    public static Document strConvertToXML(String xml) throws ParserConfigurationException, SAXException, IOException, DocumentException {
        return DocumentHelper.parseText(xml);
    }



    // xml转对象
    @SuppressWarnings("unchecked")
    public static <T> T xmlConvertToBean(String xml, Class<T> requiredType) throws JAXBException {
        context = JAXBContext.newInstance(requiredType);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(xml);
        return (T) unmarshaller.unmarshal(reader);
    }
}

/**
 * 
 * 描述：将对象空字符串设置为null（object to xml时将不转化）
 * 
 * @author yangyongchuan 2018年1月16日 下午1:46:11
 * @version 1.0
 */
class MarshallerListener extends Marshaller.Listener {

    @Override
    public void beforeMarshal(Object source) {
        super.beforeMarshal(source);
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                if (f.getType() == String.class) {
                    if ("".equals(f.get(source))) {
                        f.set(source, null);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}