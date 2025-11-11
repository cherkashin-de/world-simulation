package model;

import supportive.Constant;

import java.util.List;

public class Hunter extends Entity {

    public Hunter(Coordinate coordinate, List<Entity> targets) {
        super(Constant.EntityType.HUNTER, coordinate, targets);
    }
}
