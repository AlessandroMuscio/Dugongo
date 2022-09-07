package it.unibs.pajc.DGNGserver;

import java.io.Serializable;
import java.util.Arrays;

public class Request implements Serializable {
  private int request;
  private String testo;
  private Object[] attributes;

  public Request(int request, String testo, Object[] attributes) {
    this.request = request;
    this.testo = testo;
    this.attributes = attributes;
  }

  public Request(int request, String testo) {
    this.request = request;
    this.testo = testo;
    attributes = null;
  }
  
  public String getTesto() {
    return testo;
  }
  
  public int getRequest() {
    return request;
  }

  public void setRequest(int request) {
    this.request = request;
  }

  public Object[] getAttributes() {
    return attributes;
  }

  public void setAttributes(Object[] attributes) {
    this.attributes = attributes;
  }

  @Override
  public String toString() {
    return "Request [attributes=" + Arrays.toString(attributes) + ", request=" + request + "]";
  }
}
