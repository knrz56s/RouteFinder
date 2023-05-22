package routefinder.gui;

public class LanguageDatabase{
    private static final int UPLOAD = 0;
    private static final int RESET = 1;
    private static final int SAVE = 2;
    private static final int CLOSE = 3;

    private static final int unreadableFile = 0;
    private static final int outOfBound = 1;
    private static final int routeNotFound = 2;

    private String[] buttonText;
    private String[] availableLanguages = {"繁體中文", "English"};
    private String[] informationText;
    private String[] errorMessage;

    public LanguageDatabase(){
        setLanguage("繁體中文"); //set language to default = 繁體中文
    }

    public void setLanguage(String language){ //create language database based on selection
        switch(language){
            case "繁體中文":
                buttonText = new String[]{"上傳", "重新", "存檔", "離開"};
                informationText = new String[] {"請上傳圖片", "請按起點", "請按終點", "結果"};
                errorMessage = new String[] {"讀不到檔案", "節點不合理" , "找不到路徑"};
                break;
            case "English":
                buttonText = new String[]{"Upload", "Reset", "Save", "Exit"};
                informationText = new String[] {"Please upload image", "Please choose start point", "Please choose end point", "Result"};
                errorMessage = new String[] {"Unable to read file", "Unacceptable Selection", "Route Not Found"};
                break;
        }
    }

    public String getUploadButtonText(){
        return buttonText[UPLOAD];
    }

    public String getResetButtonText(){
        return buttonText[RESET];
    }

    public String getSaveButtonText(){
        return buttonText[SAVE];
    }

    public String getCloseButtonText(){
        return buttonText[CLOSE];
    }

    public String[] getAvailableLanguage(){
        return availableLanguages;
    }

    public String getInformationText(int informationIndex){
        return informationText[informationIndex];
    }

    public String getErrorMessage(String errorType){
        if(errorType == "unreadableFile") return errorMessage[unreadableFile];
        else if(errorType == "outOfBound") return errorMessage[outOfBound];
        else if(errorType == "noRoute") return errorMessage[routeNotFound];
        return null;
    }
}