package Navigator;

import DataStructure.KeyValue;
import DataStructure.MultiMap;
import DataStructure.MyLinkedList;
import Entity.Route;

import java.util.Comparator;
import java.util.List;

public class NavigatorImpl implements Navigator {
    private MultiMap<String, Route> routeMap;

    public NavigatorImpl() {
        this.routeMap = new MultiMap<>();
    }

    @Override
    public void addRoute(Route route) {
        if (contains(route)) {
            System.out.println("Маршрут уже существует");
            return;
        }
        String key = getKey(route);
        routeMap.add(key, route);
        System.out.println("Маршрут успешно добавлен.");
    }

    @Override
    public List<Route> getRoutes() {
        return routeMap.getValues();
    }

    @Override
    public void removeRoute(String routeId) {
        for (KeyValue<String, Route> keyValue : routeMap) {
            MyLinkedList<Route> routes = keyValue.getValues();
            for (Route route : routes) {
                if (route.getId().equals(routeId)) {
                    routes.remove(route);
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
        MyLinkedList<Route> routes = routeMap.get(key);
        return routes != null;
    }

    @Override
    public int size() {
        int size = 0;
        for (KeyValue<String, Route> keyValue : routeMap) {
            size += keyValue.getValues().size();
        }
        return size;
    }

    @Override
    public Route getRoute(String routeId) {
        for (KeyValue<String, Route> keyValue : routeMap) {
            MyLinkedList<Route> routes = keyValue.getValues();
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
        MyLinkedList<Route> result = new MyLinkedList<>();
        for (KeyValue<String, Route> keyValue : routeMap) {
            MyLinkedList<Route> routes = keyValue.getValues();
            for (Route route : routes) {
                if (route.hasLogicalOrder(startPoint, endPoint)) {
                    result.add(route);
                }
            }
        }

        result.sort(new SearchRouteComparator());
        return result;
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

    @Override
    public Iterable<Route> getFavoriteRoutes(String destinationPoint) {
        MyLinkedList<Route> result = new MyLinkedList<>();
        for (KeyValue<String, Route> keyValue : routeMap) {
            MyLinkedList<Route> routes = keyValue.getValues();
            for (Route route : routes) {
                if (route.isFavorite() && route.getLocationPoints().indexOf(destinationPoint) > 0) {
                    result.add(route);
                }
            }
        }

        result.sort(new FavoriteRouteComparator());
        return result;
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
    public Iterable<Route> getTop3Routes() {
        MyLinkedList<Route> allRoutes = new MyLinkedList<>();
        for (KeyValue<String, Route> keyValue : routeMap) {
            allRoutes.addAll(keyValue.getValues());
        }

        allRoutes.sort(new Top3RouteComparator());
        return allRoutes.subList(0, Math.min(allRoutes.size(), 3));
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

    // Дополнительный метод для формирования ключа из начальной точки, конечной точки и длины
    private String getKey(Route route) {
        String startPoint = route.getLocationPoints().get(0);
        String endPoint = route.getLocationPoints().get(route.getLocationPoints().size() - 1);
        double distance = route.getDistance();
        return startPoint + "-" + endPoint + "-" + distance;
    }
}
