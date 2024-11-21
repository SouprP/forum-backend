package me.souprpk.forumbackend.api.models.forum;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true) // UNCOMMENT IMPORTANT!!!!
//    private List<PostComment> reviews = new ArrayList<>(); // UNCOMMENT
}
