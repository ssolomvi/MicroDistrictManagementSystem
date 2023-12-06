package impl.helpClasses.forTableviews;

public class ParkingData {
    private final Integer ParkingID;
    private final Integer BuildingID;
    private final String ParkingType;
    private final Integer ParkingPlacesCount;

    public ParkingData(Integer parkingID, Integer buildingID, String parkingType, Integer parkingPlacesCount) {
        this.ParkingID = parkingID;
        this.BuildingID = buildingID;
        this.ParkingType = parkingType;
        this.ParkingPlacesCount = parkingPlacesCount;
    }

    public final Integer getParkingID() {
        return ParkingID;
    }

    public final Integer getBuildingID() {
        return BuildingID;
    }

    public final String getParkingType() {
        return ParkingType;
    }

    public final Integer getParkingPlacesCount() {
        return ParkingPlacesCount;
    }
}
