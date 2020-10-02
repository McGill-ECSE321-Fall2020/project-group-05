package com.ecse321.visart;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.*;

/**
 * This Demo Application displays some simple text to the root url at
 * localhost:8080. If it can connect to the database at the url specified by the
 * environment variable DATABASE_URL, then it displays a different set of text,
 * an id from the database.
 * 
 * @author Ryan Au
 *
 */
@Controller
@SpringBootApplication
public class DemoApplication {

  @RequestMapping("/")
  @ResponseBody
  String home() {
    try {
      Connection conn = getConnection();
      return "Hey the connection worked! " + conn.getCatalog();
    } catch (URISyntaxException | SQLException e) {
      e.printStackTrace();
    }
    return "Hello World! We are Vis Art!";
  }

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  private static Connection getConnection(String url) throws URISyntaxException, SQLException {
    URI dbUri = new URI(url);

    String username = dbUri.getUserInfo().split(":")[0];
    String password = dbUri.getUserInfo().split(":")[1];
    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

    return DriverManager.getConnection(dbUrl + "?sslmode=require", username, password);
  }

  private static Connection getConnection() throws URISyntaxException, SQLException {
    return getConnection(System.getenv("DATABASE_URL"));
  }

  private static Connection getConnectionJDBC() throws URISyntaxException, SQLException {
    String dbUrl = System.getenv("JDBC_DATABASE_URL");
    return DriverManager.getConnection(dbUrl);
  }

}