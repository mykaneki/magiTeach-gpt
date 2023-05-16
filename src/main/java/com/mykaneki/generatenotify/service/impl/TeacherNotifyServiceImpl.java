package com.mykaneki.generatenotify.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mykaneki.generatenotify.pojo.ChatGptApi;
import com.mykaneki.generatenotify.pojo.TeacherNotify;
import com.mykaneki.generatenotify.service.TeacherNotifyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class TeacherNotifyServiceImpl implements TeacherNotifyService {
    private final ChatGptApi chatGptApi;

    public TeacherNotifyServiceImpl(ChatGptApi chatGptApi) {
        this.chatGptApi = chatGptApi;
    }

    @Override
    public void setTeacherNotify(TeacherNotify teacherNotify) {
        // if (Objects.equals(teacherNotify.getRole(), "1")) {
        //     teacherNotify.setRole("行政");
        // } else if (Objects.equals(teacherNotify.getRole(), "2")) {
        //     teacherNotify.setRole("教师");
        // }
        List<String> facepeople = teacherNotify.getFacepeople();
        for (int i = 0; i < facepeople.size(); i++) {
            String value = facepeople.get(i);
            if (Objects.equals(value, "1")) {
                facepeople.set(i, "学生");
            } else if (Objects.equals(value, "2")) {
                facepeople.set(i, "家长");
            } else if (Objects.equals(value, "3")) {
                facepeople.set(i, "教师");
            } else if (Objects.equals(value, "4")) {
                facepeople.set(i, "同事");
            } else if (Objects.equals(value, "5")) {
                facepeople.set(i, "其他");
            }
        }
        if (Objects.equals(teacherNotify.getArticlelen(), "1")) {
            teacherNotify.setArticlelen("短");
        } else if (Objects.equals(teacherNotify.getArticlelen(), "2")) {
            teacherNotify.setArticlelen("中");
        } else if (Objects.equals(teacherNotify.getArticlelen(), "3")) {
            teacherNotify.setArticlelen("长");
        }
        if (Objects.equals(teacherNotify.getArticletype(), "1")) {
            teacherNotify.setArticletype("通知");
        } else if (Objects.equals(teacherNotify.getArticletype(), "2")) {
            teacherNotify.setArticletype("邮件");
        } else if (Objects.equals(teacherNotify.getArticletype(), "3")) {
            teacherNotify.setArticletype("其他");
        }
        if (Objects.equals(teacherNotify.getArticlestyle(), "1")) {
            teacherNotify.setArticlestyle("严谨");
        } else if (Objects.equals(teacherNotify.getArticlestyle(), "2")) {
            teacherNotify.setArticlestyle("活泼");
        }
    }

    @Override
    public StringBuilder getRequestString(TeacherNotify teacherNotify) {
        StringBuilder stringBuilder = new StringBuilder();
        // stringBuilder.append("请你以一名");
        // if (Objects.equals(teacherNotify.getRole(), "行政")) {
        //     stringBuilder.append(teacherNotify.getAdministrativeposition());
        // } else if (Objects.equals(teacherNotify.getRole(), "教师")) {
        //     stringBuilder.append(teacherNotify.getTeacherposition());
        // }
        // stringBuilder.append("的身份，");
        stringBuilder.append("面向");
        for (String facepeople : teacherNotify.getFacepeople()) {
            stringBuilder.append(facepeople);
            stringBuilder.append("，");
        }
        stringBuilder.append("写一个以");
        stringBuilder.append(teacherNotify.getArticletheme());
        stringBuilder.append("为主题的");
        if (Objects.equals(teacherNotify.getArticletype(), "其他")) {
            stringBuilder.append("其他");
        } else {
            stringBuilder.append(teacherNotify.getArticletype());
        }
        stringBuilder.append("，");
        if (teacherNotify.getArticlelen().equals("中")) {
            stringBuilder.append("篇幅适中");
        } else {
            stringBuilder.append("篇幅较");
            stringBuilder.append(teacherNotify.getArticlelen());
        }
        stringBuilder.append("，");
        stringBuilder.append("风格较");
        stringBuilder.append(teacherNotify.getArticlestyle());
        stringBuilder.append(",");
        stringBuilder.append("具体要求如下：");
        if (teacherNotify.getOtherdemand() != null) {
            stringBuilder.append(teacherNotify.getOtherdemand());
        }
        // System.out.println("stringBuilder = " + stringBuilder);
        return stringBuilder;

    }

    @Override
    public String getResponseString(StringBuilder requestText) throws Exception {
        StringBuilder stringBuilder = new StringBuilder(requestText);
        // TODO: 2023/3/22 生成响应文本
        return getResponseFromChatGPT(stringBuilder);
    }

    public String getResponseFromChatGPT(StringBuilder requestText) throws Exception {
        // 从配置文件中读取 API URL 和 API 密钥
        String url = chatGptApi.getUrl();
        String apiKey = chatGptApi.getApiKey();
        JSONObject jsonObject = JSONUtil.createObj()
                .set("model", "gpt-3.5-turbo")
                .set("temperature", 0.7)
                .set("messages", JSONUtil.createArray().put(JSONUtil.createObj()
                        .set("role", "user")
                        .set("content", requestText)));
        String content = null;
        String jsonString = jsonObject.toString();
        System.out.println("url = " + url);
        System.out.println("jsonString = " + jsonString);
        String response = HttpRequest
                .post(url)
                .header("Authorization", " Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .body(jsonString)
                .execute()
                .body();
        Map map = JSONUtil.toBean(response, Map.class);
        try{
            JSONArray choices = (JSONArray) map.get("choices");
            for (Object choice : choices) {
                JSONObject tempChoice = (JSONObject) choice;
                JSONObject message = (JSONObject) tempChoice.get("message");
                content = (String) message.get("content");
            }
        }catch (Exception e){
            content = map.toString();
        }

        return content;
    }

}

