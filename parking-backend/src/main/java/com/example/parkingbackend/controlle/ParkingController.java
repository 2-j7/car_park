package com.example.parkingbackend.controlle;


import com.example.parkingbackend.entity.VehicleRecord;
import com.example.parkingbackend.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // 🌟关键：允许VS Code前端跨域访问
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    // 入场接口
    // 调用方式：POST http://localhost:8080/api/entry?plate=京A88888
    @PostMapping("/entry")
    public String entry(@RequestParam String plate) {
        return parkingService.entry(plate);
    }

    // 出场接口
    // 调用方式：POST http://localhost:8080/api/exit?plate=京A88888
    @PostMapping("/exit")
    public String exit(@RequestParam String plate) {
        return parkingService.exit(plate);
    }

    // 获取所有记录列表
    // 调用方式：GET http://localhost:8080/api/list

    // 在 Controller 里添加
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        // 这里为了演示简单，硬编码账号密码
        // 实际项目中应该去查 admin 表
        if ("admin".equals(username) && "123456".equals(password)) {
            return "success";
        }
        return "fail";
    }
    @GetMapping("/list")
    public List<VehicleRecord> list() {
        return parkingService.getAllRecords();
    }
}
