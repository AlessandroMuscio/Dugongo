package it.unibs.pajc.protocols;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

import it.unibs.pajc.myComponents.MySocket;

public class HostProtocol implements Runnable {
  private static ArrayList<HostProtocol> hostProtocols;
  private static HashMap<String, Consumer<HostProtocol>> commandsMap;

  static {
    commandsMap = new HashMap<String, Consumer<HostProtocol>>();
    commandsMap.put("@gioca", (hostProtocol) -> hostProtocol.write("Giocando..."));
    commandsMap.put("@scarta", (hostProtocol) -> hostProtocol.write("Scartando..."));
    commandsMap.put("@pesca", (hostProtocol) -> hostProtocol.write("Pescando..."));
    commandsMap.put("@dugongo", (hostProtocol) -> hostProtocol.write("Dugongo..."));
    commandsMap.put("@esci", (hostProtocol) -> hostProtocol.quit());
    commandsMap.put("@default@", (hostProtocol) -> hostProtocol.write("Comando non riconosciuto"));
  }

  private Socket client;
  private BufferedReader reader;
  private PrintWriter writer;

  public HostProtocol(Socket client) {
    this.client = client;
  }

  public void write(String message) {
    writer.println(message);
  }

  public void quit() {
    if (!client.isClosed())
      try {
        client.close();
      } catch (IOException e) {
        write("Impossibile effettuare la disconnessione");
      }
  }

  @Override
  public void run() {
    String request;

    try {
      reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
      writer = new PrintWriter(client.getOutputStream(), true);

      System.out.printf("Client connected at port %d\n", client.getPort());

      if (hostProtocols != null) {
        hostProtocols.add(this);
      } else {
        hostProtocols = new ArrayList<HostProtocol>();
        hostProtocols.add(this);
      }

      while (!client.isClosed() && (request = reader.readLine()) != null) {
        System.out.printf("Processed request: %s\n", request);
        request = request.toLowerCase();

        try {
          commandsMap.get(request).accept(this);
        } catch (NullPointerException e) {
          commandsMap.get("@default@").accept(this);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        reader.close();
        writer.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
