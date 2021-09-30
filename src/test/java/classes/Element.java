package classes;

public class Element {
    private ElementType AndroidType;
    private ElementType IOSType;
    private String AndroidValue;
    private String IOSValue;

    public Element() {

    }

    public ElementType getAndroidType() {
        return AndroidType;
    }

    public void setAndroidType(String type) {
        switch (type){
            case "id":
                this.AndroidType=ElementType.id;
                break;
            case "name":
                this.AndroidType=ElementType.name;
                break;
            case "className":
                this.AndroidType=ElementType.className;
                break;
            case "xpath":
                this.AndroidType=ElementType.xpath;
                break;
            case "cssSelector":
                this.AndroidType=ElementType.cssSelector;
                break;
            case "linkText":
                this.AndroidType=ElementType.linkText;
                break;
            case "partialLinkText":
                this.AndroidType=ElementType.partialLinkText;
                break;
            case "tagName":
                this.AndroidType=ElementType.tagName;
                break;
        }
    }
    public ElementType getIOSType() {
        return IOSType;
    }

    public void setIOSType(String type) {
        switch (type){
            case "id":
                this.IOSType=ElementType.id;
                break;
            case "name":
                this.IOSType=ElementType.name;
                break;
            case "className":
                this.IOSType=ElementType.className;
                break;
            case "xpath":
                this.IOSType=ElementType.xpath;
                break;
            case "cssSelector":
                this.IOSType=ElementType.cssSelector;
                break;
            case "linkText":
                this.IOSType=ElementType.linkText;
                break;
            case "partialLinkText":
                this.IOSType=ElementType.partialLinkText;
                break;
            case "tagName":
                this.IOSType=ElementType.tagName;
                break;
        }
    }


    public String getAndroidValue() {
        return AndroidValue;
    }

    public void setAndroidValue(String androidValue) {
        AndroidValue = androidValue;
    }

    public String getIOSValue() {
        return IOSValue;
    }

    public void setIOSValue(String IOSValue) {
        this.IOSValue = IOSValue;
    }
}
