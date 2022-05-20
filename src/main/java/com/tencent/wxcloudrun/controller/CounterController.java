package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.CounterRequest;
import com.tencent.wxcloudrun.dto.TextMessageRequest;
import com.tencent.wxcloudrun.model.Counter;
import com.tencent.wxcloudrun.service.CounterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

/**
 * counter控制器
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class CounterController {

    final CounterService counterService;


    @RequestMapping("valdation")
    public String wxServerValdation(String signature, String timestamp, String nonce, String echostr) {
        if (ObjectUtils.isEmpty(signature) || ObjectUtils.isEmpty(timestamp) || ObjectUtils.isEmpty(nonce) || ObjectUtils.isEmpty(echostr)) {
            return "";
        }
        ArrayList<String> list = new ArrayList<>();
        list.add(nonce);
        list.add(timestamp);
        list.add("NvJEBK12");//这是第5步中你设置的Token
        Collections.sort(list);
        String sha1Singnature = DigestUtils.sha1Hex(list.get(0) + list.get(1) + list.get(2));
        if (sha1Singnature.equals(signature)) {
            return echostr;
        }
        return "";
    }

    @PostMapping("text")
    public ApiResponse text(@RequestBody TextMessageRequest text) {
        log.info(text.toString());
        return ApiResponse.ok(text);
    }

}