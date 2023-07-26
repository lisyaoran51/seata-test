package org.example.orderService.cloud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(url = "127.0.0.1:8091")
public interface accountService {

    @PostMapping("/user/updateUser")
    Result updateUser(@RequestBody UserVo userVo);
}