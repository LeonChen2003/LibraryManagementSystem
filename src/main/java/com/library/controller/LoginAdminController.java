package com.library.controller;

import com.library.pojo.Reader;
import com.library.pojo.Result;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/login/admin")
@CrossOrigin(origins = "*", maxAge = 60)
public class LoginAdminController {
//    @Autowired
//    private LoginAdminService loginAdminService;

    @PostMapping
    public Result select(@RequestBody Reader reader) {
        if (reader.getId().equals("yaochen") && reader.getPassword().equals("123456")) {
            return Result.success();
        }
        return Result.error("log failed");
    }
}
