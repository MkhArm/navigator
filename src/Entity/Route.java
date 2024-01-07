package Entity;

import java.util.List;
import java.util.Objects;

public class Route {
    private String id;
    private double distance;
    private int popularity;
    private boolean isFavorite;
    private List<String> locationPoints;

    public Route(String id, double distance, int popularity, boolean isFavorite, List<String> locationPoints) {
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

    public List<String> getLocationPoints() {
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

        return startIndex >= 0 && endIndex >= 0 && startIndex <= endIndex;
    }

    public boolean equals(Route otherRoute) {
        return this.hashCode() == otherRoute.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(distance, locationPoints.get(0), locationPoints.get(locationPoints.size() - 1));
    }

}
