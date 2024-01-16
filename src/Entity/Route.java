package Entity;

import DataStructure.TwoLinkedList;
import java.util.Objects;

public class Route {
    private String id;
    private double distance;
    private int popularity;
    private boolean isFavorite;
    private TwoLinkedList<String> locationPoints;

    public Route(String id, double distance, int popularity, boolean isFavorite, TwoLinkedList<String> locationPoints) {
        this.id = id;
        this.distance = distance;
        this.popularity = popularity;
        this.isFavorite = isFavorite;
        this.locationPoints = locationPoints;
    }

    public String getId() {
        return id;
    }

    public double getDistance() {
        return distance;
    }

    public int getPopularity() {
        return popularity;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public TwoLinkedList<String> getLocationPoints() {
        return locationPoints;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id='" + id + '\'' +
                ", distance=" + distance +
                ", popularity=" + popularity +
                ", isFavorite=" + isFavorite +
                ", locationPoints=" + locationPoints.toString() +
                '}';
    }

    public void increasePopularity() {
        this.popularity++;
    }

    public boolean hasLogicalOrder(String startPoint, String endPoint) {
        int startIndex = locationPoints.indexOf(startPoint);
        int endIndex = locationPoints.indexOf(endPoint);

        return startIndex == 0 && endIndex == locationPoints.size() - 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Route otherRoute = (Route) obj;
        return Objects.equals(distance, otherRoute.distance) &&
                Objects.equals(locationPoints.get(0), otherRoute.locationPoints.get(0)) &&
                Objects.equals(locationPoints.get(locationPoints.size() - 1), otherRoute.locationPoints.get(otherRoute.locationPoints.size() - 1));
    }

    @Override
    public int hashCode() {
        return Objects.hash(distance, locationPoints.get(0), locationPoints.get(locationPoints.size() - 1));
    }

}
