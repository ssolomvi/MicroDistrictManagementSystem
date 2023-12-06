/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impl.helpClasses;

//import java.sql.Date;

import java.time.LocalDate;
import java.sql.Date;

public class ListData {

    public static String admin_username;

    public static String teacher_username;

    public static String student_username;

    public static String[] role = {"Admin", "Householder", "HAUMC"};

    public static String[] gender = {"Male", "Female"};
    public static Integer tempHouseholderID;
    public static String tempHouseholderFirstName;
    public static String tempHouseholderLastName;
    public static Date tempHouseholderDateOfBirth;
    public static String tempHouseholderGender;
    public static String tempHouseholderContact;
    public static String tempHouseholderEmail;

    public static String[] realEstateType = {"Apartment", "House"};
    public static Integer tempRealEstateID;
    public static Integer tempRealEstateBuilding;
    public static String tempRealEstateType;
    public static Integer tempRealEstateSize;
    public static Integer tempRealEstateRoomCount;

    public static String[] parkingType = {"Open", "Close"};
    public static Integer tempParkingID;
    public static Integer tempParkingBuildingID;
    public static String tempParkingParkingType;
    public static Integer tempParkingParkingPlacesCount;

    public static String[] haumcType = {"Homeowners Association", "Housing Company"};
    public static Integer tempHaumcID;
    public static String tempHaumcName;
    public static String tempHaumcContactPhone;
    public static String tempHaumcDescription;
    public static String tempHaumcType;

    public static Integer tempBuildingsID;
    public static Integer tempBuildingsPhysicalNumber;
    public static Integer tempBuildingsFloorsCount;
    public static Integer tempBuildingsHaumcid;
    public static Integer tempBuildingsStreetID;

    public static Integer tempStreetsID;
    public static String tempStreetsName;

    public static Integer tempRpmOwnerID;
    public static Integer tempRpmRealEstateID;

    public static Integer tempParkingPOwnerID;
    public static Integer tempParkingPID;
    public static Integer tempParkingPParking;
    public static Integer tempParkingPNumber;

}