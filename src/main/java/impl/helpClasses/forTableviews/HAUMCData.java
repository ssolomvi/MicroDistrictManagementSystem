package impl.helpClasses.forTableviews;

public class HAUMCData {
    private final Integer ID;
    private final String Name;
    private final String ContactPhone;
    private final String Description;
    private final String Type;

    public HAUMCData(Integer ID, String Name, String ContactPhone, String Description, String Type) {
        this.ID = ID;
        this.Name = Name;
        this.ContactPhone = ContactPhone;
        this.Description = Description;
        this.Type = Type;
    }

    public final Integer getHaumcid() {
        return ID;
    }
    public final String getName() {
        return Name;
    }
    public final String getContactPhone() {
        return ContactPhone;
    }
    public final String getDescription() {
        return Description;
    }
    public final String getType() {
        return Type;
    }
}
