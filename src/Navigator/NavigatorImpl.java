package Navigator;

import DataStructure.KeyValue;
import DataStructure.MultiMap;
import DataStructure.TwoLinkedList;
import Entity.Route;
import java.util.Comparator;

public class NavigatorImpl implements Navigator {
    private MultiMap<String, Route> routeMap;

    public int size = 0;

    public NavigatorImpl() {
        this.routeMap = new MultiMap<>();
    }

    public void addRoute(Route route) {
        String key = getKey(route);
        System.out.println("Key for route " + route.getId() + ": " + key); // Добавьте эту строку
        if (contains(route)) {
            System.out.println("Маршрут уже существует");
            return;
        }
        routeMap.add(key, route);
        size++;
        System.out.println("Маршрут успешно добавлен.");
    }


    @Override
    public void removeRoute(String routeId) {
        for (KeyValue<String, Route> keyValue : routeMap) {
            TwoLinkedList<Route> routes = keyValue.getValues();
            for (Route route : routes) {
                if (route.getId().equals(routeId)) {
                    routes.remove(route);
                    size--;
                    System.out.println("Маршрут успешно удален.");
                    return;
                }
            }
        }
        System.out.println("Маршрут не найден.");
    }

    @Override
    public boolean contains(Route route) {
        String key = getKey(route);
        TwoLinkedList<Route> routes = routeMap.getValues(key);
        if (routes != null) {
            for (Route storedRoute : routes) {
                if (storedRoute.equals(route)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Route getRoute(String routeId) {
        for (KeyValue<String, Route> keyValue : routeMap) {
            TwoLinkedList<Route> routes = keyValue.getValues();
            for (Route route : routes) {
                if (route.getId().equals(routeId)) {
                    return route;
                }
            }
        }
        return null;
    }

    @Override
    public void chooseRoute(String routeId) {
        Route chosenRoute = getRoute(routeId);
        if (chosenRoute != null) {
            chosenRoute.increasePopularity();
            System.out.println("Маршрут успешно выбран.");
        }
    }

    @Override
    public Iterable<Route> searchRoutes(String startPoint, String endPoint) {
        TwoLinkedList<Route> result = routeMap.getValues(startPoint + "-" + endPoint);
        result.sort(new SearchRouteComparator());
        return result;
    }

    @Override
    public Iterable<Route> getFavoriteRoutes(String destinationPoint) {
        TwoLinkedList<Route> result = new TwoLinkedList<>();
        for (KeyValue<String, Route> keyValue : routeMap) {
            TwoLinkedList<Route> routes = keyValue.getValues();
            for (Route route : routes) {
                if (route.isFavorite() && route.getLocationPoints().contains(destinationPoint) && !route.getLocationPoints().get(0).contains(destinationPoint)) {
                    result.addLast(route);
                }
            }
        }

        result.sort(new FavoriteRouteComparator());
        return result;
    }


    @Override
    public Iterable<Route> getTop3Routes() {
        TwoLinkedList<Route> allRoutes = routeMap.getAllValues();
        allRoutes.sort(new Top3RouteComparator());
        return allRoutes.subList(0, Math.min(allRoutes.size(), 3));
    }

    private static class FavoriteRouteComparator implements Comparator<Route> {
        @Override
        public int compare(Route route1, Route route2) {
            int distanceComparison = Double.compare(route1.getDistance(), route2.getDistance());
            if (distanceComparison != 0) {
                return distanceComparison;
            }

            return Integer.compare(route2.getPopularity(), route1.getPopularity());
        }
    }
    @Override
    public Iterable<Route> getAllRoutes() {
        TwoLinkedList<Route> allRoutes = routeMap.getAllValues();
        return allRoutes;
    }

    private static class Top3RouteComparator implements Comparator<Route> {
        @Override
        public int compare(Route route1, Route route2) {
            int popularityDiff = route2.getPopularity() - route1.getPopularity();
            if (popularityDiff != 0) {
                return popularityDiff;
            }

            double distanceDiff = route1.getDistance() - route2.getDistance();
            if (distanceDiff != 0) {
                return Double.compare(distanceDiff, 0.0);
            }

            return Integer.compare(route1.getLocationPoints().size(), route2.getLocationPoints().size());
        }
    }

    private static class SearchRouteComparator implements Comparator<Route> {
        @Override
        public int compare(Route route1, Route route2) {
            boolean isFavorite1 = route1.isFavorite();
            boolean isFavorite2 = route2.isFavorite();

            if (isFavorite1 && !isFavorite2) {
                return -1;
            } else if (!isFavorite1 && isFavorite2) {
                return 1;
            }

            int distanceComparison = Double.compare(route1.getDistance(), route2.getDistance());
            if (distanceComparison != 0) {
                return distanceComparison;
            }

            return Integer.compare(route2.getPopularity(), route1.getPopularity());
        }
    }

    private String getKey(Route route) {
        String startPoint = route.getLocationPoints().get(0);
        String endPoint = route.getLocationPoints().get(route.getLocationPoints().size() - 1);
        return startPoint + "-" + endPoint;
    }

    @Override
    public String toString() {
        return "NavigatorImpl{" +
                "routeMap=" + routeMap +
                '}';
    }
}
