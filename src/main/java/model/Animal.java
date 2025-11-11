package model;

import supportive.Constant;

import java.util.List;

public class Animal extends Entity {

    public Animal(Coordinate coordinate, List<? extends Entity> targets) {
        super(Constant.EntityType.ANIMAL, coordinate, targets);
    }
}
