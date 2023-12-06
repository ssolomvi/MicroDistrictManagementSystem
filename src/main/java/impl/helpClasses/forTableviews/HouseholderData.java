package impl.helpClasses.forTableviews;

import java.sql.Date;

public class HouseholderData {
    private final Integer ID;
    private final String firstName;
    private final String lastName;
    private final Date dateOfBirth;
    private final String gender;
    private final String contactNumber;
    private final String email;

    public HouseholderData(Integer ID, String firstName, String lastName, Date dateOfBirth,
                           String gender, String contactNumber, String email) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    public Integer getOwnerID() {
        return ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }
}
