package edu.uga.cs4060.projectfourquiz;

/**
 *  This class represents a single quiz question/information
 */
public class QuizQuestions {

    private long id;
    private String state;
    private String capitalCity;
    private String secondCity;
    private String thirdCity;
    private int statehood;
    private int capitalSince;
    private int sizeRank;

    public QuizQuestions() {
        this.id = -1;
        this.state = null;
        this.capitalCity = null;
        this.secondCity = null;
        this.thirdCity = null;
        this.statehood = -1;
        this.capitalSince = -1;
        this.sizeRank = -1;
    }

    public QuizQuestions(String state, String capitalCity, String secondCity, String thirdCity, int statehood, int capitalSince, int sizeRank) {
        this.id = -1;
        this.state = state;
        this.capitalCity = capitalCity;
        this.secondCity = secondCity;
        this.thirdCity = thirdCity;
        this.statehood = statehood;
        this.capitalSince = capitalSince;
        this.sizeRank = sizeRank;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCapitalCity() {
        return capitalCity;
    }

    public void setCapitalCity(String capitalCity) {
        this.capitalCity = capitalCity;
    }

    public String getSecondCity() {
        return secondCity;
    }

    public void setSecondCity(String secondCity) {
        this.secondCity = secondCity;
    }

    public String getThirdCity() {
        return thirdCity;
    }

    public void setThirdCity(String thirdCity) {
        this.thirdCity = thirdCity;
    }

    public int getStatehood() {
        return statehood;
    }

    public void setStatehood(int statehood) {
        this.statehood = statehood;
    }

    public int getCapitalSince() {
        return capitalSince;
    }

    public void setCapitalSince(int capitalSince) {
        this.capitalSince = capitalSince;
    }

    public int getSizeRank() {
        return sizeRank;
    }

    public void setSizeRank(int sizeRank) {
        this.sizeRank = sizeRank;
    }

    @Override
    public String toString() {
        return "QuizQuestions{" +
                "id=" + id +
                ", state='" + state + '\'' +
                ", capitalCity='" + capitalCity + '\'' +
                ", secondCity='" + secondCity + '\'' +
                ", thirdCity='" + thirdCity + '\'' +
                ", statehood=" + statehood +
                ", capitalSince=" + capitalSince +
                ", sizeRank=" + sizeRank +
                '}';
    }
}
