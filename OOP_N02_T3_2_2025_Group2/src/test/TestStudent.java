package test;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import review.Student;

public class TestStudent {
    public static void testStudent() {
        System.out.println("Testing Student class with Comparable interface\n");

        // Create some Student objects
        Student s1 = new Student("Alice", 3.5f);
        Student s2 = new Student("Bob", 3.8f);
        Student s3 = new Student("Charlie", 3.5f);

        if (s3.compareTo((Student) s2) < 0)
            System.out.println(s3.getName() + " has a lower GPA than " + s2.getName());
        Set studentSet = new TreeSet<>();
        studentSet.add(s1);
        studentSet.add(s2);
        studentSet.add(s3);
        Iterator i = studentSet.iterator();
        while (i.hasNext())
            System.out.println(((Student) i.next()).getName() + " has a GPA of " + ((Student) i.next()).getGpa());
    }
}
