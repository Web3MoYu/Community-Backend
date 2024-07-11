package com.shixi3.communitybackend.wxapp.controller;

import com.alibaba.fastjson.JSON;
import com.shixi3.communitybackend.common.model.CommonResult;
import com.shixi3.communitybackend.wxapp.DTO.FaceRecDto;
import okhttp3.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/wx/imageRec")
public class ImageRecognitionController {
    public static final String API_KEY = "Gc8IiLT2vQrJS2HpnVg44bDh";
    public static final String SECRET_KEY = "Y4oIYc63ce0AEmyZ7ZYSw4yen06UaJ9L";

    OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();


    @PostMapping("/faceRec")
    public CommonResult<String> faceRec(@org.springframework.web.bind.annotation.RequestBody FaceRecDto faceRecDto) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "[{\"image\":\"" + faceRecDto.getImage1() + "\",\"image_type\":\"BASE64\"},{\"image\":\"" + faceRecDto.getImage2() + "\",\"image_type\":\"BASE64\"}]");
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rest/2.0/face/v3/match?access_token=" + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        return CommonResult.success(response.body().string());
    }

    private String getAccessToken() throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + API_KEY
                + "&client_secret=" + SECRET_KEY);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        return JSON.parseObject(response.body().string()).getString("access_token");
    }
}
