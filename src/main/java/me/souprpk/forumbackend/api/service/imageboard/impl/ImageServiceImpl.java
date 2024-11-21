package me.souprpk.forumbackend.api.service.imageboard.impl;

import me.souprpk.forumbackend.api.models.imageboard.Image;
import me.souprpk.forumbackend.api.repository.imageboard.ImageRepository;
import me.souprpk.forumbackend.api.service.imageboard.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {

    private ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public void saveFile(Image image) {
        imageRepository.save(image);
    }

//    @Override
//    public ImageListResponseDto getAllImages(int pageNo, int pageSize) {
//        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
//        Page<Image> images = imageRepository.findAll(pageable);
//        return getImageListResponseDto(images);
//    }

    @Override
    public Image getImage(int id) {
        return imageRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteImage(int id) {
        imageRepository.deleteById(id);
    }

//    public ImageResponseDto mapToDto(Image image){
//        ImageResponseDto responseDto = new ImageResponseDto();
//
//        responseDto.setId(image.getId());
//        responseDto.setFileType(image.getFileType());
//        responseDto.setFilename(image.getFilename());
//        responseDto.setContent(Base64.getEncoder().encodeToString(image.getContent()));
//        responseDto.setDate(image.getDate().toString());
//        responseDto.setTags(image.getTags());
//
//        return responseDto;
//    }

    @Override
    public Map<String, Integer> getTagsMap(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Image> images = imageRepository.findAll(pageable);

        Map<String, Integer> tagsMap = new HashMap<>();
        for(Image image : images.getContent())
            if(image.getTags() != null)
                for(String tag : image.getTags()){
                    tagsMap.put(tag, tagsMap.getOrDefault(tag, 0) + 1);
                }

        return tagsMap;
    }

//    @Override
//    public ImageListResponseDto getImageListByTags(List<String> tags, int pageNo, int pageSize) {
//        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
//        Page<Image> images = findByTagsContaining(tags, pageable);
//        return getImageListResponseDto(images);
//    }
//
//    public Page<Image> findByTagsContaining(List<String> tags, Pageable pageable) {
//        String tagQuery = tags.stream()
//                .map(tag -> "%" + tag + "%")
//                .collect(Collectors.joining(","));
//        return imageRepository.findByTagsContaining(tagQuery, pageable);
//    }
//
//    private ImageListResponseDto getImageListResponseDto(Page<Image> images) {
//        List<Image> imageList = images.getContent();
//        List<ImageResponseDto> imageResponseDtoList = imageList.stream().map(this::mapToDto).toList();
//
//        ImageListResponseDto responseDto = new ImageListResponseDto();
//        responseDto.setContent(imageResponseDtoList);
//        responseDto.setPageNo(images.getNumber());
//        responseDto.setPageSize(images.getSize());
//        responseDto.setTotalElements((int) images.getTotalElements());
//        responseDto.setTotalPages(images.getTotalPages());
//        responseDto.setLast(images.isLast());
//
//        return responseDto;
//    }
}
