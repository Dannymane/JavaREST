package rest.aw.endpoint;

import rest.aw.connection.Base;
import rest.aw.model.Course;
import rest.aw.model.GroupRecord;
import rest.aw.model.Person;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


@Path("/rest/schedule")
public class ScheduleController {
    Base base = new Base();


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllGroupRecords() {
        base.establishConnection();

        List<GroupRecord> groupRecords = base.getAllGroupRecords();

        base.closeConnection();
        return Response.ok(groupRecords).build();
    }
    @Path("/courses")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCourses() {
        base.establishConnection();

        List<Course> courses = base.getAllCourses();

        base.closeConnection();
        return Response.ok(courses).build();
    }

    @Path("/courses/{courseName}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourse(@PathParam("courseName") String courseName_) {
        base.establishConnection();

        List<Course> c = base.getCourse(courseName_);

        base.closeConnection();
        return Response.ok(c).build();
    }

    @Path("/courses")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCourse(Course course) {
        base.establishConnection();

        Course c = base.addCourse(course);

//        Person person = new Person();
//        person.setId(newPerson.getId());
//        person.setName(newPerson.getName());
        base.closeConnection();

        return Response.ok(c).build();

    }

    @Path("/records")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCoursesToGroup(GroupRecord groupRecord_) {
        base.establishConnection();

        GroupRecord c = base.addCourseToGroup(groupRecord_);

//        Person person = new Person();
//        person.setId(newPerson.getId());
//        person.setName(newPerson.getName());
        base.closeConnection();
        if (c != null){
            return Response.ok(c).build();
        }
        return Response.serverError().build();

    }

    @Path("/courses/{courseName}")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteCourse(@PathParam("courseName") String courseName_) {
        base.establishConnection();

        String response = base.deleteCourse(courseName_);

        base.closeConnection();
        return response;
    }

    @Path("/groups/{groupId}/{courseName}")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public String removeCourseFromGroup(@PathParam("groupId") String groupId_,
                                @PathParam("courseName") String courseName_) {
        base.establishConnection();

        String response = base.removeCourseFromGroup(groupId_, courseName_);

        base.closeConnection();
        return response;
    }
    @Path("/faculties/{faculty}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecordsOneWhereClause(@PathParam("faculty") String faculty_) {
        base.establishConnection();

        List<GroupRecord> groupRecords = base.getRecordsOneWhereClause("faculty",
                faculty_);

        base.closeConnection();
        return Response.ok(groupRecords).build();
    }
    @Path("/faculties/{faculty}/degrees/{degree}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecordsOneWhereClause(@PathParam("faculty") String faculty_,
                                             @PathParam("degree") String degree_) {
        base.establishConnection();

        List<GroupRecord> groupRecords = base.getRecordsTwoWhereClause("faculty",
                faculty_, "degree", degree_);

        base.closeConnection();
        return Response.ok(groupRecords).build();
    }
    @Path("/courses/{courseName}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editCourse(@PathParam("courseName") String courseName_, Course newCourse_) {
        base.establishConnection();

        Course c = base.editCourse(courseName_, newCourse_);

        base.closeConnection();
        if (c != null) return Response.ok(c).build();
        return Response.serverError().build();
    }

}
