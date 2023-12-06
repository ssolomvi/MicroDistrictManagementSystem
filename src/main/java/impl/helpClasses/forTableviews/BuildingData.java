package impl.helpClasses.forTableviews;

public class BuildingData {
    private final Integer BuildingID;
    private final Integer BuildingPhysicalNumber;
    private final Integer FloorsCount;
    private final Integer haumcid;
    private final Integer StreetID;

    public BuildingData(Integer buildingID, Integer buildingPhysicalNumber, Integer floorsCount, Integer haumcid, Integer streetID) {
        this.BuildingID = buildingID;
        this.BuildingPhysicalNumber = buildingPhysicalNumber;
        this.FloorsCount = floorsCount;
        this.haumcid = haumcid;
        this.StreetID = streetID;
    }

    public final Integer getBuildingID() {
        return BuildingID;
    }

    public final Integer getBuildingPhysicalNumber() {
        return BuildingPhysicalNumber;
    }

    public final Integer getFloorsCount() {
        return FloorsCount;
    }

    public final Integer getHaumcid() {
        return haumcid;
    }

    public final Integer getStreetID() {
        return StreetID;
    }

}
