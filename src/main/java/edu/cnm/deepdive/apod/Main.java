package edu.cnm.deepdive.apod;

import edu.cnm.deepdive.apod.controller.ApodRetriever;
import edu.cnm.deepdive.apod.model.Apod;
import edu.cnm.deepdive.apod.service.ApodService;
import java.io.IOException;
import java.time.LocalDate;
import picocli.CommandLine;

public class Main {

  public static void main(String[] args) throws IOException {
    System.exit(new CommandLine(new ApodRetriever()).execute(args));
  }

}
