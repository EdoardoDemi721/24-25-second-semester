package university;

public class Course {
    private int ID;
    private String name;
    private String teacher;

    public Course(String name, String teacher, int ID){
        this.name=name;
        this.teacher=teacher;
        this.ID=ID;
    }

    public String info(){
        return ID+", "+name+" "+teacher;
    }

    public String getName(){
        return this.name;
    }
}
