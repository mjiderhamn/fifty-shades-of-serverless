package se.jiderhamn.serverless;

import java.util.function.Function;

@SuppressWarnings("unused")
public class TransformStringFunction implements Function<String, String> {
  @Override
  public String apply(String input) {
    return new String(TransformationService.transform(input.getBytes()));
  }
}