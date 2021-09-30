package classes;

import org.json.simple.parser.ParseException;
import org.junit.Before;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException, ParseException {
        Parser parser =new Parser();
        parser.getElement("common","input box").getAndroidValue();
        System.out.println(parser.getElement("common","input box").getAndroidValue());
        System.out.println(parser.getElement("common","input box").getAndroidType().toString());
        System.out.println(parser.getElement("common","input box").getIOSValue());
        System.out.println(parser.getElement("common","input box").getIOSType().toString());
        System.out.println(parser.getPageAttributes("common").getPageName());
        System.out.println(parser.getPageAttributes("common").getParentName());
        System.out.println(parser.getPageAttributes("home").getWaitElement());
        System.out.println(parser.getElement("home","textbox").getIOSType().toString());
    }
}
