package model;

import supportive.Constant;

import java.util.List;

public abstract class Entity {

    private final Constant.EntityType entityType;   // тип сущности (трава, животное, охотник)
    private Coordinate coordinate;                  // текущие координаты на поле
    private final List<? extends Entity> targets;   // список возможных целей для взаимодействия
    private Entity target;                          // активная цель
    private int count;                              // счётчик "поеданий"

    public int getCount() {
        return count;
    }

    public void incrementCount() {
        this.count = this.count + 1;
    }

    public Entity(Constant.EntityType entityType, Coordinate coordinate, List<? extends Entity> targets) {
        this.entityType = entityType;
        this.coordinate = coordinate;
        this.targets = targets;
    }

    public List<? extends Entity> getTargets() {
        return targets;
    }

    public Constant.EntityType getEntityType() {
        return entityType;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Entity getTarget() {
        return target;
    }

    /**
     * Возвращает символ (иконку) для отображения сущности на поле.
     */
    public String printLogo() {
        return switch (entityType) {
            case GRASS -> "\uD83C\uDF3F"; // 🌿
            case ANIMAL -> "\uD83D\uDC07"; // 🐇
            case HUNTER -> "\uD83C\uDFF9"; // 🏹
            default -> null;
        };
    }

    /**
     * Проверяет, совпадают ли заданные координаты с координатами цели.
     * Используется для определения момента "поедания".
     */
    public boolean equalsCoordinateWithTarget(Integer x, Integer y) {
        return x.equals(target.getCoordinate().getX()) && y.equals(target.getCoordinate().getY());
    }

    /**
     * Находит ближайшую цель из списка доступных по минимальному расстоянию (по оси X и Y).
     * Результат сохраняется в поле target.
     */
    public void calculateTarget() {
        if(targets.isEmpty())
            return;

        Integer minX = targets.getFirst().getCoordinate().getX();
        Integer minY = targets.getFirst().getCoordinate().getY();

        Integer x = coordinate.getX();
        Integer y = coordinate.getY();

        for (Entity target_ : targets) {
            int findY = Math.abs(y - target_.getCoordinate().getY());
            int lastY = Math.abs(y - minY);

            int findX = Math.abs(x - target_.getCoordinate().getX());
            int lastX = Math.abs(x - minX);

            if(findY + findX <= lastY + lastX){
                minX = target_.getCoordinate().getX();
                minY = target_.getCoordinate().getY();
                this.target = target_;
            }
        }
    }

    /**
     * Рассчитывает координату следующего шага сущности по направлению к цели.
     * Возвращает новую координату, не изменяя текущую.
     */
    public Coordinate calculateCoordinateToTarget() {
        Integer x = getCoordinate().getX();
        Integer y = getCoordinate().getY();

        if (x < getTarget().getCoordinate().getX()) ++x;
        else if (x > getTarget().getCoordinate().getX()) --x;

        if (y < getTarget().getCoordinate().getY()) ++y;
        else if (y > getTarget().getCoordinate().getY()) --y;

        return new Coordinate(x, y);
    }

    public void removeTarget() {
        incrementCount();

        this.targets.remove(target);
        this.target = null;
    }

    @Override
    public String toString() {
        return "%s %s (%s)".formatted(entityType,
                String.valueOf(hashCode()).substring(0, 3),
                coordinate);
    }
}
