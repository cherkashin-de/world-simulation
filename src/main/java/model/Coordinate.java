package model;

import java.util.Objects;
import java.util.Random;

import static service.Games.PROPERTY_MAX_X;
import static service.Games.PROPERTY_MAX_Y;

public class Coordinate {

    private final String key;
    private Integer x;
    private Integer y;

    private static final Random random = new Random();

    public Coordinate(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        this.key = x + "_" + y;
    }

    public String getKey() {
        return key;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate coordinate = (Coordinate) o;
        return Objects.equals(key, coordinate.key);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key);
    }

    public static int getRandomX() {
        return random.nextInt(1, PROPERTY_MAX_X);
    }

    public static int getRandomY() {
        return random.nextInt(1, PROPERTY_MAX_Y);
    }

    @Override
    public String toString() {
        return key;
    }
}
