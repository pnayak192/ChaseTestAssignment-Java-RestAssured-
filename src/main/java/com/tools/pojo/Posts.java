package com.tools.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Posts {
    @JsonProperty("userId")
    public int getUserId() {
        return this.userId; }
    public void setUserId(int userId) {
        this.userId = userId; }
    int userId;
    @JsonProperty("id")
    public int getId() {
        return this.id; }
    public void setId(int id) {
        this.id = id; }
    int id;
    @JsonProperty("title")
    public String getTitle() {
        return this.title; }
    public void setTitle(String title) {
        this.title = title; }
    String title;
    @JsonProperty("body")
    public String getBody() {
        return this.body; }
    public void setBody(String body) {
        this.body = body; }
    String body;
}
