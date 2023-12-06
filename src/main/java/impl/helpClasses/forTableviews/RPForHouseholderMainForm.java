package impl.helpClasses.forTableviews;

public class RPForHouseholderMainForm {
    private final Integer building;
    private final String street;
    private final String type;
    private final Integer size;
    private final Integer roomCount;

    public RPForHouseholderMainForm(Integer building, String street, String type, Integer size, Integer roomCount) {
        this.building = building;
        this.size = size;
        this.street = street;
        this.type = type;
        this.roomCount = roomCount;
    }

    public final Integer getBuildingPhysicalNumber() {
        return building;
    }

    public final String getStreetName() {
        return street;
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
