package app;

public final class Passenger {

    private final String name;
    private final String surname;
    private int birthDay;
    private int birthMonth;
    private int birthYear;
    private Gender gender;
    private int IDNumber;
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

    public void setIDNumber(int IDNumber) {
        this.IDNumber = IDNumber;
    }

    public void setID(char id) {
        if (id == 'i' || id == 'I') {
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
            this.typeOfBaggage = Baggage.S;
        } else {
            this.typeOfBaggage = Baggage.L;
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

    public int getIDNumber() {
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

}
