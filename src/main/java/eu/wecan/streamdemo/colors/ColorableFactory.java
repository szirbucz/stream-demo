package eu.wecan.streamdemo.colors;

import java.util.stream.Stream;

public interface ColorableFactory<T extends Colorable> {
  Stream<T> generate();
}
