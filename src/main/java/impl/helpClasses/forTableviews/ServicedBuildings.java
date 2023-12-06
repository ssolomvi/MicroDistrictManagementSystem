package impl.helpClasses.forTableviews;

public class ServicedBuildings {
    private final Integer BuildingPhysicalNumber;
    private final Integer FloorsCount;
    private final String StreetName;

    public ServicedBuildings(Integer buildingPhysicalNumber, Integer floorsCount, String streetName) {
        this.BuildingPhysicalNumber = buildingPhysicalNumber;
        this.FloorsCount = floorsCount;
        this.StreetName = streetName;
    }

    public final Integer getBuildingPhysicalNumber() {
        return BuildingPhysicalNumber;
    }

    public final Integer getFloorsCount() {
        return FloorsCount;
    }

    public final String getStreetName() {
        return StreetName;
    }

}
