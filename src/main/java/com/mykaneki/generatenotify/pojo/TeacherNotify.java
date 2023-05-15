package com.mykaneki.generatenotify.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherNotify {
    // private String role;
    // private String administrativeposition;
    // private List<String> grade;
    // private String teacherposition;
    private List<String> facepeople;
    private String ortherpeople;
    private String articletheme;
    private String articlelen;
    private String articletype;
    private String ortherarticletype;
    private String articlestyle;
    private String otherdemand;
}
