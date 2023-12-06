package impl.helpClasses.forTableviews;

public class StreetsData {
    private final Integer streetID;
    private final String streetName;

    public StreetsData(Integer streetID, String streetName) {
        this.streetID = streetID;
        this.streetName = streetName;
    }

    public final Integer getStreetID() {
        return streetID;
    }

    public final String getStreetName() {
        return streetName;
    }
}
