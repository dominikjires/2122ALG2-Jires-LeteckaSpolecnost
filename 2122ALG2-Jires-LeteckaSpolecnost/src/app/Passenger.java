package app;

import utils.Baggage;
import utils.ID;
import utils.Gender;

public final class Passenger {

    private final String name;
    private final String surname;
    private int birthDay;
    private int birthMonth;
    private int birthYear;
    private Gender gender;
    private String IDNumber;
    private ID id;
    private boolean paid;
    private int numberOfBaggage;
    private Baggage typeOfBaggage;
    private int numberFrequentFlyerProgram;
    private String Catering;
    public int row;
    public char seat;
    private static int PassengerNumbersCount = 1;
    private final int PassengerNumber;

    public Passenger(String name, String surname) {
        this.name = name;
        this.surname = surname;
        PassengerNumber = PassengerNumbersCount;
        PassengerNumbersCount++;
    }

    Passenger(Passenger passenger) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setBirthYear(int birthYear) {

        this.birthYear = birthYear;

    }

    public void setBirthMonth(int birthMonth) {

        this.birthMonth = birthMonth;

    }

    public void setBirthDay(int birthDay) {

        this.birthDay = birthDay;

    }

    public void setGender(char gender) {
        if (gender == 'f' || gender == 'F' || gender == 'w') {
            this.gender = Gender.F;
        } else {
            this.gender = Gender.M;
        }
    }

    public void setIDNumber(String IDNumber) {
        this.IDNumber = IDNumber;
    }

    public void setID(String id) {
        if (id == "ID") {
            this.id = ID.ID;
        } else {
            this.id = ID.Passport;
        }
    }

    public void setNumberOfBaggage(int numberOfBaggage) {
        this.numberOfBaggage = numberOfBaggage;
    }

    public void setTypeOfBaggage(char typeOfBaggage) {
        if (typeOfBaggage == 's' || typeOfBaggage == 'S') {
            this.typeOfBaggage = Baggage.Small;
        } else {
            this.typeOfBaggage = Baggage.Large;
        }
    }

    public void setNumberFrequentFlyerProgram(int numberFrequentFlyerProgram) {
        this.numberFrequentFlyerProgram = numberFrequentFlyerProgram;
    }

    public void setCatering(String Catering) {
        this.Catering = Catering;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getBirthDay() {
        return birthDay;
    }

    public int getBirthMonth() {
        return birthMonth;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public Gender getGender() {
        return gender;
    }

    public String getIDNumber() {
        return IDNumber;
    }

    public ID getId() {
        return id;
    }

    public boolean isPaid() {
        return paid;
    }

    public int getNumberOfBaggage() {
        return numberOfBaggage;
    }

    public Baggage getTypeOfBaggage() {
        return typeOfBaggage;
    }

    public int getNumberFrequentFlyerProgram() {
        return numberFrequentFlyerProgram;
    }

    public String getCatering() {
        return Catering;
    }

    public int getPassengerNumber() {
        return PassengerNumber;
    }

    public int getRow() {
        return row;
    }

    public char getSeat() {
        return seat;
    }
    
    @Override
    public String toString() {
        return this.name + " " + this.surname + " " + this.gender + " " + this.id + " " + this.IDNumber + " " + this.numberOfBaggage + " " + this.typeOfBaggage + " " + this.row + this.seat + " " + this.Catering;
    }
    
    public String passengerBoardingPass() {
        return this.name + " " + this.surname + "\nGender: " + this.gender + "\n" + this.id + " " + this.IDNumber + "\n" + this.numberOfBaggage + "x " + this.typeOfBaggage + " Baggage\nSeat: " + this.row + this.seat + "\nFrequent flyer program: " + this.numberFrequentFlyerProgram;
    }

}
