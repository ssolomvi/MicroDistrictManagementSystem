package impl.helpClasses.forTableviews;

public class ResidentialPropertyData {
    private final Integer ID;
    private final Integer building;
    private final String type;
    private final Integer size;
    private final Integer roomCount;

    public ResidentialPropertyData(Integer ID, Integer building, String type, Integer size, Integer roomCount) {
        this.ID = ID;
        this.building = building;
        this.type = type;
        this.size = size;
        this.roomCount = roomCount;
    }

    public final Integer getResidentialPropertyID() {
        return ID;
    }

    public final Integer getBuildingID() {
        return building;
    }

    public final String getType() {
        return type;
    }

    public final Integer getSize() {
        return size;
    }

    public final Integer getRoomCount() {
        return roomCount;
    }
}
