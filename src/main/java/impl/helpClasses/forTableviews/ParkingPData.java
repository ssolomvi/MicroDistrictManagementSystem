package impl.helpClasses.forTableviews;

public class ParkingPData {
    private final Integer ownerID;
    private final Integer parkingPlaceID;
    private final Integer parkingID;
    private final Integer parkingPlaceNumber;

    public ParkingPData(Integer ownerID, Integer parkingPlaceID, Integer parkingID, Integer parkingPlaceNumber) {
        this.ownerID = ownerID;
        this.parkingPlaceID = parkingPlaceID;
        this.parkingID = parkingID;
        this.parkingPlaceNumber = parkingPlaceNumber;
    }

    public final Integer getOwnerID() {
        return ownerID;
    }

    public final Integer getParkingPlaceID() {
        return parkingPlaceID;
    }

    public final Integer getParkingID() {
        return parkingID;
    }

    public final Integer getParkingPlaceNumber() {
        return parkingPlaceNumber;
    }
}
