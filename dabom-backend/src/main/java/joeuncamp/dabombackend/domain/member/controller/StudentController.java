package joeuncamp.dabombackend.domain.member.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.course.service.MyCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "[2-3.Student]", description = "수강생과 관련된 API입니다.")
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudentController {
    private final MyCourseService myCourseService;


}
