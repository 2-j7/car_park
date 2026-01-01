package com.example.parkingbackend.controlle; // 你的包名可能写错成了 controlle，保持一致即可

import com.example.parkingbackend.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/pay")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private ParkingService parkingService;

    // 内存中模拟存储支付状态：Key=车牌号, Value=是否已支付
    // 生产环境应该用 Redis 或 数据库
    private static final ConcurrentHashMap<String, Boolean> paymentStatusMap = new ConcurrentHashMap<>();

    // 1. 获取待支付金额 (手机端调用)
    @GetMapping("/info")
    public BigDecimal getOrderInfo(@RequestParam String plate) {
        // 初始化支付状态为 false
        paymentStatusMap.put(plate, false);
        return parkingService.calculateFee(plate);
    }

    // 2. 确认支付 (手机端点击“确认付款”后调用)
    @PostMapping("/confirm")
    public String confirmPay(@RequestParam String plate) {
        // 标记为已支付
        paymentStatusMap.put(plate, true);
        return "success";
    }

    // 3. 查询支付状态 (电脑端前端轮询调用)
    @GetMapping("/check")
    public boolean checkStatus(@RequestParam String plate) {
        // 如果map里有且为true，则返回true
        return paymentStatusMap.getOrDefault(plate, false);
    }
}