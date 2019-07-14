package com.example.node;

import java.sql.Date;

public class Node {

    private int nodeID;
    private String nodeTitle;
    private String nodeContent;
    private String saveDate;
    private int nodeClass;

    public int getNodeID() {
        return nodeID;
    }

    public void setNodeID(int nodeID) {
        this.nodeID = nodeID;
    }

    public String getNodeTitle() {
        return nodeTitle;
    }

    public void setNodeTitle(String nodeTitle) {
        this.nodeTitle = nodeTitle;
    }

    public String getNodeContent() {
        return nodeContent;
    }

    public void setNodeContent(String nodeContent) {
        this.nodeContent = nodeContent;
    }

    public String getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(String saveDate) {
        this.saveDate = saveDate;
    }

    public int getNodeClass() {
        return nodeClass;
    }

    public void setNodeClass(int nodeClass) {
        this.nodeClass = nodeClass;
    }
}
