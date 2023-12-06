package impl.helpClasses.forTableviews;

public class RpmData {
    private final Integer ownerID;
    private final Integer residentialPropertyID;

    public RpmData(Integer ownerID, Integer residentialPropertyID) {
        this.ownerID = ownerID;
        this.residentialPropertyID = residentialPropertyID;
    }

    public final Integer getOwnerID() {
        return ownerID;
    }

    public final Integer getResidentialPropertyID() {
        return residentialPropertyID;
    }
}
