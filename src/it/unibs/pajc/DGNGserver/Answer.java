package it.unibs.pajc.DGNGserver;

import java.io.Serializable;

public class Answer implements Serializable {
  private int answer;
  private String title;
  private String message;

  public Answer(int answer, String title, String message) {
    this.answer = answer;
    this.title = title;
    this.message = message;
  }

  public Answer(int answer) {
    this.answer = answer;
  }

  public int getAnswer() {
    return answer;
  }

  public void setAnswer(int answer) {
    this.answer = answer;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
