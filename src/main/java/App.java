import service.Games;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class App {

    public static void main(String[] args) throws IOException {
        printWelcome();
        Reader reader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(reader);

        String input = bufferedReader.readLine();

        if (!"1".equals(input)) {
            System.out.println("Выход из симуляции. До встречи!");
            return;
        }

        Games games = new Games();
        games.startGames();

        System.out.println("✨ Симуляция началась! ✨");
        games.printEntities();

        while (true) {
            printDoing();
            input = bufferedReader.readLine();

            if (!"1".equals(input)) {
                System.out.println("Симуляция завершена. Спасибо за участие!");
                break;
            }

            games.moveEntities();
            games.printEntities();
            games.printCount();
        }
    }

    private static void printWelcome() {
        System.out.println("🌍  ЭМУЛЯЦИЯ МИРА  🌍");
        System.out.println("=======================================");
        System.out.println("Добро пожаловать в симуляцию живого мира!");
        System.out.println("Наблюдайте за существами и их взаимодействиями.");
        System.out.println("---------------------------------------");
        System.out.println("Для старта введите: 1");
        System.out.println("Для выхода — любую другую клавишу");
        System.out.println("---------------------------------------");
    }

    private static void printDoing() {
        System.out.println("---------------------------------------");
        System.out.println("Введите 1, чтобы сделать шаг симуляции,");
        System.out.println("или любую другую клавишу для завершения:");
    }

}
