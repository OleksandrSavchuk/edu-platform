package com.example.eduplatform.dto.lesson;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class LessonVideo {

    MultipartFile file;

}
