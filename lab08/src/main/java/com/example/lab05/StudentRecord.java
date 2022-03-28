package com.example.lab05;

public class StudentRecord {

    private String StudentID;
    private float Midterm;
    private float Assignments;
    private float FinalExam;
    private float FinalMark;
    private char LetterGrade;

    public StudentRecord(String id, float assignments, float midterm, float finalExam) {
        this.StudentID = id;
        this.Assignments = assignments;
        this.Midterm = midterm;
        this.FinalExam = finalExam;
        this.FinalMark = (float) (assignments * .20 + midterm * .30 + finalExam * .50);

        if (FinalMark >= 80 && FinalMark <= 100) {
            LetterGrade = 'A';
        }
        else if (FinalMark >= 70 && FinalMark <= 79) {
            LetterGrade = 'B';
        }
        else if (FinalMark >= 60 && FinalMark <= 69) {
            LetterGrade = 'C';
        }
        else if (FinalMark >= 50 && FinalMark <= 59) {
            LetterGrade = 'D';
        }
        else if (FinalMark >= 0 && FinalMark <= 49) {
            LetterGrade = 'F';
        }
    }

    public String getStudentID() {
        return StudentID;
    }

    public void setStudentID(String studentID) {
        StudentID = studentID;
    }

    public float getMidterm() {
        return Midterm;
    }

    public void setMidterm(float midterm) {
        Midterm = midterm;
    }

    public float getAssignments() {
        return Assignments;
    }

    public void setAssignments(float assignments) {
        Assignments = assignments;
    }

    public float getFinalExam() {
        return FinalExam;
    }

    public void setFinalExam(float finalExam) {
        FinalExam = finalExam;
    }

    public float getFinalMark() {
        return FinalMark;
    }

    public void setFinalMark(float finalMark) {
        FinalMark = finalMark;
    }

    public char getLetterGrade() {
        return LetterGrade;
    }

    public void setLetterGrade(char letterGrade) {
        LetterGrade = letterGrade;
    }
}
