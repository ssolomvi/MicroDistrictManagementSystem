package impl.helpClasses.forTableviews;

public class HAUMCForHouseholderMainForm {
    private final String name;
    private final String description;
    private final String contact;
    private final String haumcType;
    private final String reType;
    private final Integer building;
    private final String street;

    public HAUMCForHouseholderMainForm(String name, String description, String contact, String haumcType, String reType,
                                       Integer building, String street) {
        this.name = name;
        this.description = description;
        this.contact = contact;
        this.haumcType = haumcType;
        this.reType = reType;
        this.building = building;
        this.street = street;
    }

    public final String getName() {
        return name;
    }

    public final String getDescription() {
        return description;
    }

    public final String getContactPhone() {
        return contact;
    }

    public final String getType() {
        return haumcType;
    }

    public final String getReType() {
        return reType;
    }

    public final Integer getBuildingPhysicalNumber() {
        return building;
    }

    public final String getStreetName() {
        return street;
    }
}
