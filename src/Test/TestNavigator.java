package Test;

import DataStructure.TwoLinkedList;
import Entity.Route;
import Navigator.Navigator;
import Navigator.NavigatorImpl;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TestNavigator {
    public static void main(String[] args) {
        Navigator navigator = new NavigatorImpl();
        Scanner scanner = new Scanner(System.in);
        addDefaultRoutes(navigator);
        System.out.println("Contains route 1: " + navigator.contains(navigator.getRoute("1")));
        System.out.println("Route with ID 1: " + navigator.getRoute("1"));
        navigator.chooseRoute("1");
        System.out.println("Routes from A to D:");
        navigator.searchRoutes("A", "D").forEach(System.out::println);
        System.out.println("Favorite routes with D:");
        navigator.getFavoriteRoutes("D").forEach(System.out::println);
        System.out.println("Top 3 routes:");
        navigator.getTop3Routes().forEach(System.out::println);
        System.out.println("Size after adding routes: " + navigator.size());
        navigator.removeRoute("2");
        System.out.println("Size after removing route 2: " + navigator.size());
        while (true) {
            System.out.println("1. Добавить маршрут");
            System.out.println("2. Удалить маршрут");
            System.out.println("3. Выбрать маршрут");
            System.out.println("4. Поиск маршрутов");
            System.out.println("5. Вывести все маршруты");
            System.out.println("6. Вывести топ 3 маршрута");
            System.out.println("7. Вывести избранные маршруты по точке назначения");
            System.out.println("8. Вывести количество маршрутов");
            System.out.println("9. Получить маршрут по ID");
            System.out.println("0. Выйти");

            int choice = getUserChoice(scanner);

            switch (choice) {
                case 1:
                    addRoute(navigator, scanner);
                    break;
                case 2:
                    removeRoute(navigator, scanner);
                    break;
                case 3:
                    chooseRoute(navigator, scanner);
                    break;
                case 4:
                    searchRoutes(navigator, scanner);
                    break;
                case 5:
                    printAllRoutes(navigator);
                    break;
                case 6:
                    printTop3Routes(navigator);
                    break;
                case 7:
                    printFavoriteRoutes(navigator, scanner);
                    break;
                case 8:
                    printNavigatorSize(navigator);
                    break;
                case 9:
                    printRouteById(navigator, scanner);
                    break;
                case 0:
                    System.out.println("Завершение программы.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, повторите попытку.");
            }
        }
    }

    private static void addRoute(Navigator navigator, Scanner scanner) {
        System.out.print("Введите ID маршрута: ");
        String id = scanner.next();
        System.out.print("Введите расстояние: ");
        double distance = scanner.nextDouble();
        System.out.print("Введите популярность: ");
        int popularity = scanner.nextInt();
        System.out.print("Избранный маршрут? (true/false): ");
        boolean isFavorite = scanner.nextBoolean();
        System.out.print("Введите количество точек маршрута: ");
        int numPoints = scanner.nextInt();

        scanner.nextLine();

        System.out.println("Введите точки маршрута (по одной на строке):");
        TwoLinkedList<String> locationPoints = new TwoLinkedList<>();
        for (int i = 0; i < numPoints; i++) {
            String point = scanner.nextLine();
            locationPoints.addLast(point);
        }

        Route route = new Route(id, distance, popularity, isFavorite, locationPoints);
        navigator.addRoute(route);
    }

    private static void removeRoute(Navigator navigator, Scanner scanner) {
        System.out.print("Введите ID маршрута для удаления: ");
        String id = scanner.next();
        navigator.removeRoute(id);
    }

    private static void chooseRoute(Navigator navigator, Scanner scanner) {
        System.out.print("Введите ID маршрута для выбора: ");
        String id = scanner.next();
        navigator.chooseRoute(id);
    }

    private static void searchRoutes(Navigator navigator, Scanner scanner) {
        System.out.print("Введите начальную точку: ");
        String startPoint = scanner.next();
        System.out.print("Введите конечную точку: ");
        String endPoint = scanner.next();

        Iterable<Route> routes = navigator.searchRoutes(startPoint, endPoint);
        System.out.println("Совпадающие маршруты:");
        for (Route route : routes) {
            System.out.println(route);
        }
    }

    private static void printAllRoutes(Navigator navigator) {
        System.out.println("Все маршруты:");
        for (Route route : navigator.getAllRoutes()) {
            System.out.println(route);
        }
    }

    private static void printTop3Routes(Navigator navigator) {
        System.out.println("Все топ 3 маршрута:");
        for (Route route : navigator.getTop3Routes()) {
            System.out.println(route);
        }
    }

    private static int getUserChoice(Scanner scanner) {
        try {
            System.out.print("Введите ваш выбор: ");
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Считать некорректный ввод
            return -1;
        }
    }


    private static void printFavoriteRoutes(Navigator navigator, Scanner scanner) {
        System.out.print("Введите точку назначения: ");
        String destinationPoint = scanner.next();

        Iterable<Route> favoriteRoutes = navigator.getFavoriteRoutes(destinationPoint);
        System.out.println("Избранные маршруты:");
        for (Route route : favoriteRoutes) {
            System.out.println(route);
        }
    }

    private static void printNavigatorSize(Navigator navigator) {
        System.out.println("Количество маршрутов: " + navigator.size());
    }

    private static void printRouteById(Navigator navigator, Scanner scanner) {
        System.out.print("Введите ID маршрута: ");
        String routeId = scanner.next();

        Route route = navigator.getRoute(routeId);
        if (route != null) {
            System.out.println("Маршрут по ID " + routeId + ":");
            System.out.println(route);
        } else {
            System.out.println("Маршрут с ID " + routeId + " не найден.");
        }
    }

    private static void addDefaultRoutes(Navigator navigator) {
        TwoLinkedList<String> points1 = new TwoLinkedList<>();
        points1.addAll("A", "B", "C", "F", "G", "D");
        Route route1 = new Route("1", 50.0, 100, false, points1);

        TwoLinkedList<String> points2 = new TwoLinkedList<>();
        points2.addAll("A", "Y", "D");
        Route route2 = new Route("2", 30.0, 75, true, points2);

        TwoLinkedList<String> points3 = new TwoLinkedList<>();
        points3.addAll("A", "Q", "R", "S", "D");
        Route route3 = new Route("3", 80.0, 115, true, points3);

        TwoLinkedList<String> points4 = new TwoLinkedList<>();
        points4.addAll("A", "N", "D");
        Route route4 = new Route("4", 20.0, 90, true, points4);

        TwoLinkedList<String> points5 = new TwoLinkedList<>();
        points5.addAll("E", "F", "G", "D", "I");
        Route route5 = new Route("5", 80.0, 110, true, points5);

        TwoLinkedList<String> points6 = new TwoLinkedList<>();
        points6.addAll("A", "S", "D");
        Route route6 = new Route("6", 20.0, 95, false, points6);

        TwoLinkedList<String> points7 = new TwoLinkedList<>();
        points7.addAll("J", "K", "L");
        Route route7 = new Route("7", 45.0, 95, true, points7);

        TwoLinkedList<String> points8 = new TwoLinkedList<>();
        points8.addAll("AA", "BB", "CC");
        Route route8 = new Route("8", 25.0, 70, false, points8);

        TwoLinkedList<String> points9 = new TwoLinkedList<>();
        points9.addAll("DD", "EE", "FF", "GG");
        Route route9 = new Route("9", 70.0, 130, true, points9);

        TwoLinkedList<String> points10 = new TwoLinkedList<>();
        points10.addAll("HH", "II", "JJ", "KK");
        Route route10 = new Route("10", 55.0, 105, false, points10);

        TwoLinkedList<String> points11 = new TwoLinkedList<>();
        points11.addAll("A", "B", "C", "F", "G", "D");
        Route route11 = new Route("11", 50.0, 100, true, points11);

        TwoLinkedList<String> points12 = new TwoLinkedList<>();
        points12.addAll("A", "D", "F");
        Route route12 = new Route("12", 45.0, 45, true, points12);


        navigator.addRoute(route1);
        navigator.addRoute(route2);
        navigator.addRoute(route3);
        navigator.addRoute(route4);
        navigator.addRoute(route5);
        navigator.addRoute(route6);
        navigator.addRoute(route7);
        navigator.addRoute(route8);
        navigator.addRoute(route9);
        navigator.addRoute(route10);
        navigator.addRoute(route11);
        navigator.addRoute(route12);
        System.out.println(navigator);

    }
}
