package classes;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

public class Parser {
    JSONParser parser = new JSONParser();
    static String json = "";
    private static final String OS = System.getProperty("os.name").toLowerCase();

    public Page getPageAttributes(String mypage) throws IOException, ParseException {
        setPath();
        Page returnPage = new Page();
        JSONObject object = null;
        try {
            object = (JSONObject) parser.parse(new FileReader(json));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        JSONArray array = (JSONArray) object.get("pages");
        String value = null;
        String parentName = "";
        for (Object o : array) {
            JSONObject page = (JSONObject) o;

            JSONObject pageInfo = (JSONObject) page.get("pageInfo");
            String pagename = (String) pageInfo.get("pageName");
            if (pagename.equalsIgnoreCase(mypage)) {
                returnPage.setPageName((String) pageInfo.get("pageName"));
                returnPage.setParentName((String) pageInfo.get("parentName"));
                returnPage.setWaitElement((String) pageInfo.get("waitElement"));
                break;
            }
        }
        return returnPage;
    }

    public Boolean isPageExist(String mypage) {
        setPath();
        JSONObject object = null;
        try {
            object = (JSONObject) parser.parse(new FileReader(json));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        JSONArray array = (JSONArray) object.get("pages");

        for (Object o : array) {
            JSONObject page = (JSONObject) o;

            JSONObject pageInfo = (JSONObject) page.get("pageInfo");
            String pagename = (String) pageInfo.get("pageName");

            if (pagename.equalsIgnoreCase(mypage)) {
                System.out.println(pagename + " sayfası bulundu");
                return true;
            }
        }
        return false;
    }

    public Element getElement(String mypage, String myelement) {
        setPath();
        Element returnElement = new Element();
        JSONObject object = null;
        try {
            object = (JSONObject) parser.parse(new FileReader(json));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        JSONArray array = (JSONArray) object.get("pages");
        String value = null;
        String parentName = "";
        for (Object o : array) {
            JSONObject page = (JSONObject) o;

            JSONObject pageInfo = (JSONObject) page.get("pageInfo");
            String pagename = (String) pageInfo.get("pageName");
            parentName = (String) pageInfo.get("parent");

            boolean elemFind = false;

            if (pagename.equalsIgnoreCase(mypage)) {
                JSONArray elements = (JSONArray) page.get("elements");
                for (Object element : elements) {
                    JSONObject elem = (JSONObject) element;
                    value = (String) elem.get("elementName");

                    if (value.equals(myelement)) {
                        returnElement.setAndroidValue((String) elem.get("AndroidValue"));
                        returnElement.setIOSValue((String) elem.get("IOSValue"));
                        returnElement.setAndroidType((String) elem.get("AndroidType"));
                        returnElement.setIOSType((String) elem.get("IOSType"));
                        elemFind = true;
                        break;
                    }
                }

                //control parent
                if (!elemFind) {
                    for (Object obj : array) {
                        JSONObject parentPage = (JSONObject) obj;
                        pageInfo = (JSONObject) parentPage.get("pageInfo");
                        pagename = (String) pageInfo.get("pageName");

                        if (pagename.equalsIgnoreCase(parentName)) {
                            JSONArray parenEelements = (JSONArray) parentPage.get("elements");
                            for (Object element : parenEelements) {
                                JSONObject elem = (JSONObject) element;
                                value = (String) elem.get("elementName");

                                if (value.equals(myelement)) {
                                    returnElement.setAndroidValue((String) elem.get("AndroidValue"));
                                    returnElement.setIOSValue((String) elem.get("IOSValue"));
                                    returnElement.setAndroidType((String) elem.get("AndroidType"));
                                    returnElement.setIOSType((String) elem.get("IOSType"));
                                    elemFind = true;
                                    break;
                                }
                            }
                            if (elemFind) {
                                break;
                            }
                        }
                    }
                }
            }
            if (elemFind) {
                break;
            }
        }

        return returnElement;
    }

    public void setPath() {
        System.out.println("os.name: " + OS);

        if (isWindows()) {
            //System.out.println("This is Windows");
            json = Paths.get("").toAbsolutePath().toString() + "\\src\\test\\java\\cucumber\\elements\\page.json";
        } else if (isMac()) {
            //System.out.println("This is Mac");
            json = Paths.get("").toAbsolutePath().toString() + "/src/test/java/cucumber/elements/page.json";
        } else if (isUnix()) {
            //System.out.println("This is Unix or Linux");
            json = Paths.get("").toAbsolutePath().toString() + "/src/test/java/cucumber/elements/page.json";
        } else if (isSolaris()) {
            //System.out.println("This is Solaris");
            json = Paths.get("").toAbsolutePath().toString() + "/src/test/java/cucumber/elements/page.json";
        } else {
            //System.out.println("Your OS is not support!!");
        }
    }

    public static boolean isWindows() {
        return (OS.contains("win"));
    }

    public static boolean isMac() {
        return (OS.contains("mac"));
    }

    public static boolean isUnix() {
        return (OS.contains("nix") || OS.contains("nux") || OS.indexOf("aix") > 0);
    }

    public static boolean isSolaris() {
        return (OS.contains("sunos"));
    }

}
