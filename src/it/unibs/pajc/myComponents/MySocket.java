package it.unibs.pajc.myComponents;

import java.io.IOException;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MySocket extends Socket {
  private String name;

  public MySocket() {
    super();
  }

  public MySocket(String host, int port, String name) throws UnknownHostException, IOException {
    super(host, port);
    this.name = name;
  }

  public MySocket(InetAddress address, int port, String name) throws IOException {
    super(address, port);
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
