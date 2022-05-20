package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.CounterRequest;
import com.tencent.wxcloudrun.model.Counter;
import com.tencent.wxcloudrun.service.CounterService;
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
@RestController
@Slf4j
public class CounterController {

    final CounterService counterService;

    public CounterController(@Autowired CounterService counterService) {
        this.counterService = counterService;
    }


    /**
     * 获取当前计数
     *
     * @return API response json
     */
    @GetMapping(value = "/api/count")
    ApiResponse get() {
        log.info("/api/count get request");
        Optional<Counter> counter = counterService.getCounter(1);
        Integer count = 0;
        if (counter.isPresent()) {
            count = counter.get().getCount();
        }

        return ApiResponse.ok(count);
    }


    /**
     * 更新计数，自增或者清零
     *
     * @param request {@link CounterRequest}
     * @return API response json
     */
    @PostMapping(value = "/api/count")
    ApiResponse create(@RequestBody CounterRequest request) {
        log.info("/api/count post request, action: {}", request.getAction());

        Optional<Counter> curCounter = counterService.getCounter(1);
        if (request.getAction().equals("inc")) {
            Integer count = 99999999;
            if (curCounter.isPresent()) {
                count += curCounter.get().getCount();
            }
            Counter counter = new Counter();
            counter.setId(1);
            counter.setCount(count);
            counterService.upsertCount(counter);
            return ApiResponse.ok(count);
        } else if (request.getAction().equals("clear")) {
            if (!curCounter.isPresent()) {
                return ApiResponse.ok(0);
            }
            counterService.clearCount(1);
            return ApiResponse.ok(0);
        } else {
            return ApiResponse.error("参数action错误");
        }
    }


    @ResponseBody
    @RequestMapping("valdation")
    public String wxServerValdation(String signature, String timestamp, String nonce, String echostr) {
        if (ObjectUtils.isEmpty(signature) || ObjectUtils.isEmpty(timestamp) || ObjectUtils.isEmpty(nonce) || ObjectUtils.isEmpty(echostr)) {
            return "";
        }
        ArrayList<String> list = new ArrayList<String>();
        list.add(nonce);
        list.add(timestamp);
        list.add("NvJEBK12");//这是第5步中你设置的Token
        Collections.sort(list);
        String sha1Singnature = DigestUtils.sha1Hex(list.get(0) + list.get(1) + list.get(2));
        if (sha1Singnature.equals(signature)) {
            return echostr;
        } else {
            return "";
        }

    }
}