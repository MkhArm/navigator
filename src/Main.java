public class Main {

    public static void main(String[] args) {
        MultiMap<Route, Route> routeMap = new MultiMap<>();

        // Создание нескольких объектов Route
        TwoLinkedList<String> locations1 = new TwoLinkedList<>();
        locations1.addLast("A");
        locations1.addLast("B");
        locations1.addLast("C");
        Route route1 = new Route("1", 100.0, 10, true, locations1);

        TwoLinkedList<String> locations2 = new TwoLinkedList<>();
        locations2.addLast("X");
        locations2.addLast("Y");
        Route route2 = new Route("2", 150.0, 8, false, locations2);

        // Добавление маршрутов в MultiMap
        routeMap.add(route1, route1);
        routeMap.add(route2, route2);

        // Создание нового маршрута с таким же хэш-кодом как у маршрута с id "1"
        TwoLinkedList<String> locations3 = new TwoLinkedList<>();
        locations3.addLast("A");
        locations3.addLast("B");
        locations3.addLast("F");
        locations3.addLast("E");
        locations3.addLast("C");
        Route route3 = new Route("3", 100.0, 15, false, locations3);

        // Добавление маршрута с тем же хэш-кодом, что и с id "1"
        routeMap.add(route3, route3);

        // Вывод хэш-кодов ключей
        System.out.println("Hash code for id 1: " + route1.hashCode());
        System.out.println("Hash code for id 2: " + route2.hashCode());
        System.out.println("Hash code for id 3: " + route3.hashCode());

        // Вывод информации о MultiMap
        System.out.println("Size of MultiMap: " + routeMap.size());
        System.out.println("Capacity of MultiMap: " + routeMap.capacity());
        System.out.println("Collisions in MultiMap: " + routeMap.getCollisions());

        // Получение маршрутов по ключам
        TwoLinkedList<Route> routesForKey1 = routeMap.get(route1);
        TwoLinkedList<Route> routesForKey2 = routeMap.get(route2);
        TwoLinkedList<Route> routesForKey3 = routeMap.get(route3);

        // Вывод маршрутов по ключам
        System.out.println("Routes for id 1: " + routesForKey1);
        System.out.println("Routes for id 2: " + routesForKey2);
        System.out.println("Routes for id 3: " + routesForKey3);

        // Итерация по MultiMap
        System.out.println("Iterating through MultiMap:");
        for (KeyValue<Route, Route> keyValue : routeMap) {
            System.out.println(keyValue.getValues());
        }

        System.out.println(routeMap);

    }
}
