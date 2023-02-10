package rest.aw.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Faculty {
    private String name;
    private List<Degree> Degrees;
}
