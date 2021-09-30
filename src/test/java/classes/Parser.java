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
    static String json =  Paths.get("").toAbsolutePath().toString()+"\\src\\test\\java\\cucumber\\elements\\page.json";
    static String config =  Paths.get("").toAbsolutePath().toString()+"\\src\\test\\config.json";
    public Page getPageAttributes(String mypage) throws  IOException, ParseException
    {
        Page returnPage=new Page();
        JSONObject object = null;
        try {
            object = (JSONObject) parser.parse(new FileReader(json));
        }
        catch (IOException | ParseException e)
        {
            e.printStackTrace();
        }

        JSONArray array = (JSONArray) object.get("pages");
        String value=null;
        String parentName="";
        for (Object o : array) {
            JSONObject page = (JSONObject) o;

            JSONObject pageInfo = (JSONObject) page.get("pageInfo");
            String pagename = (String) pageInfo.get("pageName");
            if (pagename.equalsIgnoreCase(mypage)) {
                returnPage.setPageName( (String) pageInfo.get("pageName"));
                returnPage.setParentName( (String) pageInfo.get("parentName"));
                returnPage.setWaitElement( (String) pageInfo.get("waitElement"));
                break;
            }
        }
        return returnPage;
    }

    public Boolean isPageExist(String mypage)
    {
        JSONObject object = null;
        try
        {
            object = (JSONObject) parser.parse(new FileReader(json));
        }
        catch (IOException | ParseException e)
        {
            e.printStackTrace();
        }
        JSONArray  array= (JSONArray) object.get("pages");

    for (Object o : array)
    {
        JSONObject page = (JSONObject) o;

        JSONObject pageInfo = (JSONObject) page.get("pageInfo");
        String pagename=(String)pageInfo.get("pageName");

        if(pagename.equalsIgnoreCase(mypage))
        {
            System.out.println(pagename+" sayfası bulundu");
            return true;
        }
    }
    return false;
    }

    public Element getElement(String mypage,String myelement)
    {
        Element returnElement =new Element();
        JSONObject object = null;
        try {
            object = (JSONObject) parser.parse(new FileReader(json));
        }
        catch (IOException | ParseException e)
        {
            e.printStackTrace();
        }

        JSONArray array = (JSONArray) object.get("pages");
        String value=null;
        String parentName="";
        for (Object o : array) {
            JSONObject page = (JSONObject) o;

            JSONObject pageInfo = (JSONObject) page.get("pageInfo");
            String pagename = (String) pageInfo.get("pageName");
            parentName=(String) pageInfo.get("parent");
            boolean elemFind=false;
            if (pagename.equalsIgnoreCase(mypage))
            {
                JSONArray elements = (JSONArray) page.get("elements");
                for (Object element : elements)
                {
                    JSONObject elem = (JSONObject) element;
                    value= (String) elem.get("elementName");

                    if(value.equals(myelement)){
                        returnElement.setAndroidValue((String) elem.get("AndroidValue"));
                        returnElement.setIOSValue((String) elem.get("IOSValue"));
                        returnElement.setAndroidType((String) elem.get("AndroidType"));
                        returnElement.setIOSType((String) elem.get("IOSType"));
                        elemFind=true;
                        break;
                    }
                }

                //control parent
                if(elemFind==false)
                {
                    for (Object obj : array)
                    {
                        JSONObject parentPage = (JSONObject) obj;
                         pageInfo = (JSONObject) parentPage.get("pageInfo");
                         pagename = (String) pageInfo.get("pageName");

                        if (pagename.equalsIgnoreCase(parentName))
                        {
                            JSONArray parenEelements = (JSONArray) parentPage.get("elements");
                            for (Object element : parenEelements)
                            {
                                JSONObject elem = (JSONObject) element;
                                value= (String) elem.get("elementName");

                                if(value.equals(myelement)){
                                    returnElement.setAndroidValue((String) elem.get("AndroidValue"));
                                    returnElement.setIOSValue((String) elem.get("IOSValue"));
                                    returnElement.setAndroidType((String) elem.get("AndroidType"));
                                    returnElement.setIOSType((String) elem.get("IOSType"));
                                    elemFind=true;
                                    break;
                                }
                            }
                            if(elemFind==true)
                            {
                                break;
                            }
                        }
                    }
                }
            }
            if(elemFind==true)
            {
                break;
            }
        }

        return returnElement;
    }

    public String readConfigInfo(Boolean isLocal)
    {
        JSONObject object = null;
        try {
            object = (JSONObject) parser.parse(new FileReader(config));
        }
        catch (IOException | ParseException e)
        {
            e.printStackTrace();
        }
        JSONObject remoteConfig = (JSONObject) object.get("remote");

        return  (String) remoteConfig.get("nodeUrl");
    }
}
