package me.souprpk.forumbackend.api.controllers;

import me.souprpk.forumbackend.api.dto.responses.ImageResponse;
import me.souprpk.forumbackend.api.models.imageboard.Image;
import me.souprpk.forumbackend.api.service.UserService;
import me.souprpk.forumbackend.api.service.imageboard.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/images")
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ImageController {

    private final ImageService imageService;
    private final UserService userService;

    @Autowired
    public ImageController(ImageService imageService, UserService userService) {
        this.imageService = imageService;
        this.userService = userService;
    }

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file,
                                              @RequestParam("tags") List<String> tags) throws IOException {
        Image image = new Image();
        image.setFilename(file.getOriginalFilename());
        image.setFileType(file.getContentType());
        image.setContent(file.getBytes());

        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //User currentUser = (User) authentication.getPrincipal();
        //image.setUserId(userService.getUserByUsername(currentUser.getUsername()));
        //image.setUsername(currentUser.getUsername());

        LocalDateTime date = LocalDateTime.now().withNano(0);
        image.setDate(date);
        image.setTags(tags);

        imageService.saveFile(image);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ImageResponse> getImage(@PathVariable int id){
        // get the image from the repository
        Image image = imageService.getImage(id);

        // not found
        if(image == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        ImageResponse dto = new ImageResponse();
        dto.setId(image.getId());
        dto.setFilename(image.getFilename());
        dto.setFileType(image.getFileType());
        dto.setDate(image.getDate().toString());
        dto.setContent(Base64.getEncoder().encodeToString(image.getContent()));
        dto.setTags(image.getTags());

        return ResponseEntity.ok(dto);
    }

//    @GetMapping("/get")
//    public ResponseEntity<ImageListResponseDto> getAllImages(
//            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
//            @RequestParam(value = "pageSize", defaultValue = "20", required = false) int pageSize,
//            @RequestParam(value = "tags", defaultValue = "", required = false) String tags
//    ){
//        //return new ResponseEntity<>(imageService.getAllImages(pageNo, pageSize), HttpStatus.OK);
//        if(tags.isEmpty())
//            return new ResponseEntity<>(imageService.getAllImages(pageNo, pageSize), HttpStatus.OK);
//
//        List<String> tagList = new ArrayList<>(Arrays.asList(tags.split(",")));
//        return new ResponseEntity<>(imageService.getImageListByTags(tagList, pageNo, pageSize), HttpStatus.OK);
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable("id") int id){
        imageService.deleteImage(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get-tags/{id}")
    public ResponseEntity<List<String>> getImageTags(@PathVariable("id") int id){
        Image image = imageService.getImage(id);
        return new ResponseEntity<>(image.getTags(), HttpStatus.OK);
    }

//    @PostMapping("/get-by-tags")
//    public ResponseEntity<ImageListResponseDto> getImagesByTags(@PathVariable("tags") String tags,
//                                                                @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
//                                                                @RequestParam(value = "pageSize", defaultValue = "20", required = false) int pageSize){
//
//        List<String> tagList = new ArrayList<>(Arrays.asList(tags.split(",")));
//        return new ResponseEntity<>(imageService.getImageListByTags(tagList, pageNo, pageSize), HttpStatus.OK);
//    }

    @GetMapping("/get-tags-map/{id}")
    public ResponseEntity<Map<String, Integer>> getTagsMap(@PathVariable("id") int id){
        return new ResponseEntity<>(imageService.getTagsMap(0, 25), HttpStatus.OK);
    }
}
