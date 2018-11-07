package se.jiderhamn.serverless.spring;

import se.jiderhamn.serverless.TransformationService;

import java.util.function.Function;

@SuppressWarnings("unused")
public class TransformFunction implements Function<String, String> {
  @Override
  public String apply(String input) {
    return new String(TransformationService.transform(input.getBytes()));
  }
}