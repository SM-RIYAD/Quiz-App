package com.example.quizapp.classes;

public class QuizDS {
    private String que,_1,_2,_3,_4;


    public QuizDS(String que, String s, String s1, String s2, String s3) {
        this.que = que;
        _1 = s;
        _2 = s1;
        _3 = s2;
        _4 = s3;
    }

    public String getQue() {
        return que;
    }

    public String get_1() {
        return _1;
    }

    public String get_2() {
        return _2;
    }

    public String get_3() {
        return _3;
    }

    public String get_4() {
        return _4;
    }
}
