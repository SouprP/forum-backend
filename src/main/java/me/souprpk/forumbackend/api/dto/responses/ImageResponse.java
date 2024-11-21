package me.souprpk.forumbackend.api.dto.responses;

import lombok.Data;

import java.util.List;

@Data
public class ImageResponse {
    private int id;
    private String filename;
    private String fileType;
    private String date;
    private List<String> tags;

    private String content;
}
