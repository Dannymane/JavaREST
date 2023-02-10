package rest.aw;

import rest.aw.model.Course;
import rest.aw.model.Faculty;

public class test {
    public static void main(String[] args) {
    Faculty f = new Faculty("Wesoly",null);
    System.out.println(f.getName());

        Course c = new Course("SNP204","Info","Mon","12.15","12B");
        System.out.println(c.toString().substring(6));
//        GroupRecord g = new GroupRecord();
//        System.out.println(g.toString().substring(5));

    }
}
