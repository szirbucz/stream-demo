package eu.wecan.streamdemo.colors;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = Bag.BagBuilder.class)
public class Bag implements Colorable {
    private String brand;
    private Color color;
}
