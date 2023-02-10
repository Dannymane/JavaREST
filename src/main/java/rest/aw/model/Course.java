package rest.aw.model;

import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;


@Getter // This 4 annotations are obligatory for serialization, which is used for sending
@Setter // data to client from server
@NoArgsConstructor //
@AllArgsConstructor //
@ToString(includeFieldNames=false)
public class Course {
    private String courseName;
    private String lessonName;
    private String day;
    private String time;
    private String classroom;
}