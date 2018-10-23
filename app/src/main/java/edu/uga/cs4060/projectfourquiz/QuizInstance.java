package edu.uga.cs4060.projectfourquiz;

/**
 *  This class represents a single quiz instance
 */
public class QuizInstance {

    private long id;
    private String date;
    private long question1;
    private long question2;
    private long question3;
    private long question4;
    private long question5;
    private long question6;
    private int numCorrect;
    private int numAnswered;

    public QuizInstance(){
        this.id = -1;
        this.date = null;
        this.question1 = -1;
        this.question2 = -1;
        this.question3 = -1;
        this.question4 = -1;
        this.question5 = -1;
        this.question6 = -1;
        this.numCorrect = -1;
        this.numAnswered = -1;
    }

    public QuizInstance(String date, long q1, long q2, long q3, long q4, long q5, long q6, int numCorrect, int numAnswered){
        this.id = -1;
        this.date = date;
        this.question1 = q1;
        this.question2 = q2;
        this.question3 = q3;
        this.question4 = q4;
        this.question5 = q5;
        this.question6 = q6;
        this.numCorrect = numCorrect;
        this.numAnswered = numAnswered;
    }

    public long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public long getQuestion1() {
        return question1;
    }

    public long getQuestion2() {
        return question2;
    }

    public long getQuestion3() {
        return question3;
    }

    public long getQuestion4() {
        return question4;
    }

    public long getQuestion5() {
        return question5;
    }

    public long getQuestion6() {
        return question6;
    }

    public int getNumCorrect() {
        return numCorrect;
    }

    public int getNumAnswered(){ return numAnswered; }

    public void setId(long id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setQuestion1(long question1) {
        this.question1 = question1;
    }

    public void setQuestion2(long question2) {
        this.question2 = question2;
    }

    public void setQuestion3(long question3) {
        this.question3 = question3;
    }

    public void setQuestion4(long question4) {
        this.question4 = question4;
    }

    public void setQuestion5(long question5) {
        this.question5 = question5;
    }

    public void setQuestion6(long question6) {
        this.question6 = question6;
    }

    public void setNumCorrect(int numCorrect) {
        this.numCorrect = numCorrect;
    }

    public void setNumAnswered(int numAnswered) {this.numAnswered = numAnswered; }

    @Override
    public String toString() {
        return "QuizInstance{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", question1=" + question1 +
                ", question2=" + question2 +
                ", question3=" + question3 +
                ", question4=" + question4 +
                ", question5=" + question5 +
                ", question6=" + question6 +
                ", numCorrect=" + numCorrect +
                ", numAnswered=" + numAnswered +
                '}';
    }
}