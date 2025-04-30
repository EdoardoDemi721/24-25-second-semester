package university;

public class Student {
    private String name;
    private String surname;
    private int ID;

    public Student (String name, String surname, int ID) {
        this.name=name;
        this.surname=surname;
        this.ID=ID;
    }

    public String info(){
        return ID+" "+name+" "+surname;
    }

    public String getFullName(){
        return this.name+" "+this.surname;
    }

}


