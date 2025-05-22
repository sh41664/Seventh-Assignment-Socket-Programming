package Shared;

import com.fasterxml.jackson.annotation.JsonProperty;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Message implements Serializable {

    @JsonProperty
    private int type = -1;
    @JsonProperty
    private String sender = "";
    @JsonProperty
    private String content = "";
    @JsonProperty
    private String username = "";
    @JsonProperty
    private String password = "";
    @JsonProperty
    private boolean loginResult = false;
    @JsonProperty
    private String fileName = "";
    @JsonProperty
    private int fileSize = 0;
    @JsonProperty
    private List<String> listOfFiles = new ArrayList<>();

    public Message(@JsonProperty("type") int type,
                   @JsonProperty("sender") String sender,
                   @JsonProperty("content") String content) {
        this.type = type;
        this.sender = sender;
        this.content = content;
        switch(type){
            case 0:
                loginRequest();
                break;
            case 1:
                break;
            case 2:
                loginResponse();
                break;
            case 3:
                fileMetaData();
                break;
            case 4:
                break;
            case 5:
                serverFileList();
                break;
            case 6:
                sendFileName();
                break;

        }
    }

    private void sendFileName() {
        fileName = content;
    }

    private void serverFileList() {
        int numberOfFiles = content.split(",").length;
        for(int i = 0; i < numberOfFiles; i++){
            listOfFiles.add(content.split(",")[i]);
        }
    }

    private void fileMetaData() {
        fileName = content.split("/")[0];
        fileSize = Integer.parseInt(content.split("/")[1]);
    }

    private void loginResponse() {
        if(content.toLowerCase().contains("approved")){
            loginResult = true;
        }else{
            loginResult = false;
        }
    }

    private void loginRequest() {
        this.password = content.split("/")[1];
        this.username = content.split("/")[0];
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public int getType() {
        return type;
    }
    public String getSender() {
        return sender;
    }
    public boolean loginResult() {
        return loginResult;
    }
    public String getContent() {
        return content;
    }
    public int getFileSize() {
        return fileSize;
    }
    public String getFileName() {
        return fileName;
    }
    public List<String> getListOfFiles() {
        return listOfFiles;
    }
}