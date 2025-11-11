package service;

import model.*;
import supportive.Constant;

import java.util.*;

import static model.Coordinate.getRandomX;
import static model.Coordinate.getRandomY;

public class Games {

    // Максимальные размеры игрового поля
    public static final Integer PROPERTY_MAX_X = 8;
    public static final Integer PROPERTY_MAX_Y = 8;

    // Ограничения на количество сущностей
    private static final Integer PROPERTY_MAX_ANIMAL = 4;
    private static final Integer PROPERTY_MAX_GRASS = 10;
    private static final Integer PROPERTY_MAX_HUNTER = 1;

    // Основное хранилище всех сущностей игры
    private Map<Coordinate, Entity> entities;

    /**
     * Инициализирует игровое поле и создаёт все сущности.
     */
    public void startGames() {
        generatedEntityList();
    }


    /**
     * Перемещает все активные сущности (животных и охотников).
     * Каждая сущность определяет цель, направление и делает шаг.
     * Если на новом месте находится цель, происходит "поедание".
     */
    public void moveEntities() {
        Set<Coordinate> check = new HashSet<>();

        for (Map.Entry<Coordinate, Entity> entry : entities.entrySet()) {
            if (entry.getValue() != null && !check.contains(entry.getKey())) {
                Entity entity = entry.getValue();

                // Трава не двигается
                if (entity.getEntityType().equals(Constant.EntityType.GRASS))
                    continue;

                // Рассчитываем цель для движения (жертву)
                entity.calculateTarget();
                if (entity.getTarget() == null)
                    continue;

                // Определяем новую координату
                entity.calculateCoordinateToTarget();
                Coordinate newCoordinate = entity.calculateCoordinateToTarget();

                // Проверяем наличие другого объекта в целевой клетке
                Entity findObject = entities.get(newCoordinate);
                if (findObject != null && !entity.getTargets().contains(findObject))
                    continue;

                // Проверяем, достиг ли объект своей цели
                if (entity.equalsCoordinateWithTarget(newCoordinate.getX(), newCoordinate.getY())) {
                    System.out.printf("%s eating... %s%n", entity, entity.getTarget());
                    entity.removeTarget();
                }

                // Обновляем координату сущности
                entity.setCoordinate(newCoordinate);
                entities.put(entity.getCoordinate(), entity);

                // Помечаем клетку как обработанную
                check.add(entity.getCoordinate());

                // Освобождаем старое место
                entry.setValue(null);
            }
        }
    }

    /**
     * Выводит текущее состояние поля в консоль.
     * Каждая клетка отображается символом сущности или пустым квадратом 🟫.
     */
    public void printEntities() {
        for (int y = 1; y < PROPERTY_MAX_Y; y++) {
            for (int x = 1; x < PROPERTY_MAX_X; x++) {
                Entity obj = entities.get(new Coordinate(x, y));
                System.out.print(obj == null ? " \uD83D\uDFEB " : " " +obj.printLogo() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Выводит количество и координаты активных сущностей (без травы).
     */
    public void printCount(){
        System.out.println();

        entities.values().stream().filter(Objects::nonNull)
                .filter(entity -> !entity.getEntityType().equals(Constant.EntityType.GRASS))
                .map(entity -> "%s %s count: %s".formatted(entity.printLogo(), entity.getCoordinate(), entity.getCount()))
                .forEach(System.out::println);

        System.out.println();
    }

    /**
     * Создаёт и размещает все объекты на поле:
     * - траву (GRASS)
     * - животных (ANIMAL)
     * - охотников (HUNTER)
     */
    private void generatedEntityList() {
        entities = new HashMap<>();
        List<Grass> targetAnimals = new ArrayList<>();
        List<Entity> targetHunter = new ArrayList<>();

        //Заполняем мапу пустыми значениями, для дальнейшей работы
        fillMap(entities);

        for (int i = 0; i < PROPERTY_MAX_GRASS; i++) {
            Coordinate coordinate = generatedCoordinate();
            Grass grass = new Grass(coordinate);
            entities.put(coordinate, grass);
            targetAnimals.add(grass);
        }

        for (int i = 0; i < PROPERTY_MAX_ANIMAL; i++) {
            Coordinate coordinate = generatedCoordinate();
            Animal animal = new Animal(coordinate, targetAnimals);
            entities.put(coordinate, animal);
            targetHunter.add(animal);
        }

        for (int i = 0; i < PROPERTY_MAX_HUNTER; i++) {
            Coordinate coordinate = generatedCoordinate();
            Hunter hunter = new Hunter(coordinate, targetHunter);
            entities.put(coordinate, hunter);
        }
    }

    /**
     * Инициализирует игровое поле пустыми клетками.
     */
    private void fillMap(Map<Coordinate, Entity> entities) {
        for (int x = 1; x < PROPERTY_MAX_X; x++)
            for (int y = 1; y < PROPERTY_MAX_Y; y++)
                entities.put(new Coordinate(x, y), null);
    }

    /**
     * Генерирует случайную свободную координату на поле.
     * Если свободных координат нет — возвращает null.
     */
    private Coordinate generatedCoordinate() {
        for (int i = 0; i < PROPERTY_MAX_X * PROPERTY_MAX_Y; i++) {
            Coordinate coordinate = new Coordinate(getRandomX(), getRandomY());
            if (entities.get(coordinate) != null)
                continue;

            return coordinate;
        }
        return null;
    }
}
