package app;

import utils.Baggage;
import utils.ID;
import utils.Gender;

/**
 * Třída pro cestujícího
 * @author Dominik Jireš
 */
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
    private String numberFrequentFlyerProgram;
    private String Catering;
    public int row;
    public char seat;
    private static int PassengerNumbersCount = 1;
    private final int PassengerNumber;

    /**
     * Konstruktor pro pasažéra
     * @param name String
     * @param surname String
     */
    public Passenger(String name, String surname) {
        this.name = name;
        this.surname = surname;
        PassengerNumber = PassengerNumbersCount;
        PassengerNumbersCount++;
    }

    /**
     * Konstruktor pro pasažéra - kopírování
     * @param passenger Passenger
     */
    Passenger(Passenger passenger) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Zadání roku narození
     * @param birthYear int
     */
    public void setBirthYear(int birthYear) {

        this.birthYear = birthYear;

    }

    /**
     * Zadání měsíce narození
     * @param birthMonth int
     */
    public void setBirthMonth(int birthMonth) {

        this.birthMonth = birthMonth;

    }

    /**
     * Zadání dne narození
     * @param birthDay int
     */
    public void setBirthDay(int birthDay) {

        this.birthDay = birthDay;

    }

    /**
     * Zadání pohlaví
     * @param gender char
     */
    public void setGender(char gender) {
        if (gender == 'f' || gender == 'F' || gender == 'w') {
            this.gender = Gender.F;
        } else {
            this.gender = Gender.M;
        }
    }

    /**
     * Zadání čísla dokumentu
     * @param IDNumber String
     */
    public void setIDNumber(String IDNumber) {
        this.IDNumber = IDNumber;
    }

    /**
     * Zadání typu dokumentu
     * @param id String
     */
    public void setID(String id) {
        if ("ID".equals(id)) {
            this.id = ID.ID;
        } else {
            this.id = ID.Passport;
        }
    }

    /**
     * Zadání počtu zavazadel
     * @param numberOfBaggage int
     */
    public void setNumberOfBaggage(int numberOfBaggage) {
        this.numberOfBaggage = numberOfBaggage;
    }

    /**
     * Zadání typu zavazadla
     * @param typeOfBaggage char
     */
    public void setTypeOfBaggage(char typeOfBaggage) {
        switch (typeOfBaggage) {
            case 's':
            case 'S':
                this.typeOfBaggage = Baggage.Small;
                break;
            case 'l':
            case 'L':
                this.typeOfBaggage = Baggage.Large;
                break;
            default:
                this.typeOfBaggage = Baggage.No;
                break;
        }
    }

    /**
     * Zadání čísla věrnostní karty
     * @param numberFrequentFlyerProgram String
     */
    public void setNumberFrequentFlyerProgram(String numberFrequentFlyerProgram) {
        this.numberFrequentFlyerProgram = numberFrequentFlyerProgram;
    }
    
    /**
     * Výběr občerstvení
     * @param Catering String
     */
    public void setCatering(String Catering) {
        this.Catering = Catering;
    }

    /**
     * Zaplaceno/nezaplaceno
     * @param paid Boolean
     */
    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    /**
     * Získání jména
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Získání příjmení
     * @return String
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Získání dne narození
     * @return int
     */
    public int getBirthDay() {
        return birthDay;
    }

    /**
     * Získání měsíce narození
     * @return int
     */
    public int getBirthMonth() {
        return birthMonth;
    }

    /**
     * Získání roku narození
     * @return int
     */
    public int getBirthYear() {
        return birthYear;
    }

    /**
     * Získání pohlaví
     * @return Gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Získání čísla dokumentu
     * @return String
     */
    public String getIDNumber() {
        return IDNumber;
    }

    /**
     * Získání typu dokumentu
     * @return ID
     */
    public ID getId() {
        return id;
    }

    /**
     * Získání zda je zaplaceno
     * @return boolean
     */
    public boolean isPaid() {
        return paid;
    }

    /**
     * Získání počtu zavazadel
     * @return int
     */
    public int getNumberOfBaggage() {
        return numberOfBaggage;
    }

    /**
     * Získání typu zavazadel
     * @return Baggage
     */
    public Baggage getTypeOfBaggage() {
        return typeOfBaggage;
    }

    /**
     * Získání čísla věrnostního programu
     * @return String
     */
    public String getNumberFrequentFlyerProgram() {
        return numberFrequentFlyerProgram;
    }

    /**
     * Získání zda je občerstvení
     * @return String
     */
    public String getCatering() {
        return Catering;
    }

    /**
     * Získání čísla rezervace
     * @return int
     */
    public int getPassengerNumber() {
        return PassengerNumber;
    }

    /**
     * Získání řady sedadla
     * @return int
     */
    public int getRow() {
        return row;
    }

    /**
     * Získání místa sedadla
     * @return char
     */
    public char getSeat() {
        return seat;
    }

    /**
     * Získání Stringu s údaji o cestujícím
     * @return String
     */
    @Override
    public String toString() {
        return this.name + " " + this.surname + " " + this.gender + " " + this.id + " " + this.IDNumber + " " + this.numberOfBaggage + "x " + this.typeOfBaggage + " baggage " + this.row + this.seat + " catering: " + this.Catering;
    }

    /**
     * Získání palubní vstupenky - části o cestujícímu
     * @return String
     */
    public String passengerBoardingPass() {
        return "Seat: " + this.row + this.seat + "\n" + this.name + " " + this.surname + "\nGender: " + this.gender + "\n" + this.id + " " + this.IDNumber + "\n" + this.numberOfBaggage + "x " + this.typeOfBaggage + " Baggage\nFrequent flyer program: " + this.numberFrequentFlyerProgram;
    }

}
