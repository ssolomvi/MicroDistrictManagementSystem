package impl.helpClasses.forTableviews;

public class PPForHouseholderMainForm {
    private final Integer number;
    private final Integer building;
    private final String street;

    public PPForHouseholderMainForm(Integer number, Integer building, String street) {
        this.number = number;
        this.building = building;
        this.street = street;
    }

    public final Integer getParkingPlaceNumber() {
        return number;
    }

    public final Integer getBuildingPhysicalNumber() {
        return building;
    }

    public final String getStreetName() {
        return street;
    }
}
