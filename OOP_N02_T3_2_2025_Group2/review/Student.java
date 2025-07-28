package review;

public class Student implements Comparable<Student>{
    public Student(String name, float gpa){
        this.name =name;
        this.gpa = gpa;
    }
    public Student(){}
    public int compareTo(Student o){
        if( ((Student)o).gpa <gpa) return 1;
        else if(((Student)o).gpa >gpa)return -1;
        else return 0;
    }
    public boolean equals(Student o){
        if(gpa == ((Student)o).gpa) return true;
        else return false;
    }
    public String getName(){return name;}
    public float getGpa(){return gpa;}
    private String name;
    private float gpa;
}
