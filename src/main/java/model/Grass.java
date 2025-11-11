package model;

import supportive.Constant;

public class Grass extends Entity {
    public Grass(Coordinate coordinate) {
        super(Constant.EntityType.GRASS, coordinate, null);
    }
}
