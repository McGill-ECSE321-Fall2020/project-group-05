package com.ecse321.http;

import java.io.IOException;
import java.util.Arrays;

import com.sendgrid.Client;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;

public class Main {
  public static void main(String[] args) {
    System.out.println(Arrays.toString(args));

    Method method = getMethodType(args[0]);

    Request request = new Request();
    request.setBaseUri(args[1]);
    request.setMethod(method);
    request.setEndpoint(args[2]);

    if (method == Method.POST) {
      request.setBody(args[3]);
    }

    Client client = new Client();
    try {
      Response response = client.api(request);
      System.out.print("<" + response.getStatusCode() + "> ");
      System.out.print(response.getHeaders() + ":");
      System.out.println(response.getBody());
      System.exit(0);
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
      System.exit(1);
    } finally {
      try {
        client.close();
      } catch (IOException e) {
      }
    }
  }

  public static Method getMethodType(String methodName) {
    try {
      return Enum.valueOf(Method.class, methodName);
    } catch (IllegalArgumentException | NullPointerException e) {
      return null;
    }
  }
}
