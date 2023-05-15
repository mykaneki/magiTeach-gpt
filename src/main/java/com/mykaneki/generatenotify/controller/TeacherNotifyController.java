package com.mykaneki.generatenotify.controller;

import com.mykaneki.generatenotify.pojo.Result;
import com.mykaneki.generatenotify.pojo.TeacherNotify;
import com.mykaneki.generatenotify.service.TeacherNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


// @RestController
@Controller
@RequestMapping(value = {"/api"})
public class TeacherNotifyController {

    @Autowired
    private TeacherNotifyService teacherNotifyService;

    @PostMapping(value = "/submit", consumes = "application/json")
    @ResponseBody
    public Result notify(@RequestBody TeacherNotify teacherNotify) {
        System.out.println(teacherNotify);
        teacherNotifyService.setTeacherNotify(teacherNotify);
        StringBuilder requestString = teacherNotifyService.getRequestString(teacherNotify);
        try {
            String str = teacherNotifyService.getResponseString(requestString);
            System.out.println(str);
            return Result.success(str);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("服务器错误");
        }
    }
}
