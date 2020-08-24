package com.example.fitness_tracker;

public class Person {
    // we have our test name
    String PId;
    String pname;
    String page;
    String calCon;
    String calB;

    //defining constructor this constructor is used while reading the values
    public Person(){

    }
//this next constructor is used to initialize our variables
public Person(String PId, String pname, String Page, String calCon, String calB){
        this.PId = PId;
        this.pname = pname;
        this.page = Page;
        this.calCon = calCon;
        this.calB = calB;
    }

    //we use getters to read the value. or getters are used while reading the value
    // we can use geters by right click<generator<getters
    public String getPId(){
        return PId;
    }
    public String getpage(){
        return page;
    }
    public String getcalCon(){
        return calCon;
    }
    public String getcalB(){
        return calB;
    }
    public String getpname(){

        return pname;
    }


}
