package it.unibs.pajc.DGNGserver;

import java.io.Serializable;

public class Answer implements Serializable {
  private int code;
  private Object[] body;

  public Answer(int code) {
    this.code = code;
    this.body = new Object[] {};
  }

  public Answer(int code, Object[] body) {
    this.code = code;
    this.body = body;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public Object[] getBody() {
    return body;
  }

  public void setBody(Object[] body) {
    this.body = body;
  }
}
