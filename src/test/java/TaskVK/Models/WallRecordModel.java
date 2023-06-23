package TaskVK.Models;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class WallRecordModel {
    
    private String author;
    private String text;
    private String photo;
}
