package it.unibs.pajc.DGNGserver;

import java.io.Serializable;

public class Answer implements Serializable {
  private int answer;
  private Object[] body;
  
  public Answer(int answer) {
    this.answer = answer;
    this.body = new Object[]{};
  }

  public Answer(int answer, Object[] body) {
    this.answer = answer;
    this.body = body;
  }
  public int getAnswer() {
    return answer;
  }

  public void setAnswer(int answer) {
    this.answer = answer;
  }
  
  public Object[] getBody() {
    return body;
  }
  
  public void setBody(Object[] body) {
    this.body = body;
  }
}
