package main.dto;

public class DtoMessage {

  private String text;
  private String datetime;
  private String userName;


  public DtoMessage() {
  }

  public DtoMessage(String text, String datetime, String userName) {
    this.text = text;
    this.datetime = datetime;
    this.userName = userName;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getDatetime() {
    return datetime;
  }

  public void setDatetime(String datetime) {
    this.datetime = datetime;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}
