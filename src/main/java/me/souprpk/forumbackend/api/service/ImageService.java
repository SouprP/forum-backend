package me.souprpk.forumbackend.api.service;

import me.souprpk.forumbackend.api.models.Image;

import java.util.Map;

public interface ImageService {
    public void saveFile(Image image);
    //public ImageListResponseDto getAllImages(int pageNo, int pageSize);
    public Image getImage(int id);
    public void deleteImage(int id);
    //public ImageResponseDto mapToDto(Image image);
    public Map<String, Integer> getTagsMap(int pageNo, int pageSize);
    //public ImageListResponseDto getImageListByTags(List<String> tags, int pageNo, int pageSize);
}
