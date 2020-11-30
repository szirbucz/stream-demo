package eu.wecan.streamdemo.car;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import eu.wecan.streamdemo.colors.Color;
import eu.wecan.streamdemo.colors.Colorable;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = Car.CarBuilder.class)
public class Car implements Colorable {
    private String id;
    private Color color;
    private String name;
    private String manufacturer;
    private Integer maxSpeed;
}
