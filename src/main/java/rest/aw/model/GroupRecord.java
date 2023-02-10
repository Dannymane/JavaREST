package rest.aw.model;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(includeFieldNames=false)
public class GroupRecord {
    private String faculty;
    private String degree;
    private int session;
    private String groupId;
    private String courseName;
}