package oracle.processingsqlstatements;

import org.mozilla.universalchardet.UniversalDetector;
import java.io.ByteArrayInputStream;
import java.io.IOException;
/**
 * Класс StrEncDetector реализует функционал обнаружения кодировку строки.
 *
 * @see https://code.google.com/archive/p/juniversalchardet/
 * Зависимость в pom.xml https://mvnrepository.com/artifact/com.googlecode.juniversalchardet/juniversalchardet
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-11-28
 */
class StrEncDetector {
    /**
     * Получает строку с кодировкой.
     * @param str строка.
     * @return строка с кодировкой.
     */
    public String getEncoding(String str) {
        String encoding = null;
        try {
            byte[] buf = new byte[4096];
            ByteArrayInputStream bais = new ByteArrayInputStream(str.getBytes());
            UniversalDetector detector = new UniversalDetector(null);
            int nread;
            while (!detector.isDone()) {
                nread = bais.read(buf);
                if (nread <= 0) {
                    break;
                }
                detector.handleData(buf, 0, nread);
            }
            detector.dataEnd();
            encoding = detector.getDetectedCharset();
            detector.reset();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return encoding;
    }
}