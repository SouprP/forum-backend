package me.souprpk.forumbackend.api.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String filename;
    private String fileType;
    private String username;

    @Lob
    private byte[] content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime date;

    private String tags;

    @OneToMany(mappedBy = "image", cascade = CascadeType.ALL, orphanRemoval = true) // UNCOMMENT IMPORTANT!!!!
    private List<ImageComment> reviews = new ArrayList<>(); // UNCOMMENT

    //    @ManyToOne(fetch = FetchType.LAZY)
    //    @JoinColumn(name = "user_id")
    //    private UserEntity userId;

    public List<String> getTags() {
        if(tags == null)
            return new ArrayList<>();

        return Arrays.asList(tags.split(","));
    }

    public void setTags(List<String> tags) {
        this.tags = String.join(",", tags);
    }

}
