package crawler.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//读取配置信息
public class ConfigUtil {
    private final static String XMLPATH = "/home/PIC/conf.xml";

    public static String getConfig(String node) {
        Document document = null;
        try {
            document = Jsoup.parse(getXmlStringFromFile(XMLPATH), "config", Parser.xmlParser());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements elements = document.select(node);
        return elements.text();
    }

    private static String getXmlStringFromFile(String xmlFilePath) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        FileReader fileReader = new FileReader(xmlFilePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }
}
