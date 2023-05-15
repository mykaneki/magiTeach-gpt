package com.mykaneki.generatenotify.service;

import com.mykaneki.generatenotify.pojo.TeacherNotify;
import org.springframework.stereotype.Component;

@Component
public interface TeacherNotifyService {
    void setTeacherNotify(TeacherNotify teacherNotify);
    StringBuilder getRequestString(TeacherNotify teacherNotify);
    String getResponseString(StringBuilder requestText) throws Exception;
}

