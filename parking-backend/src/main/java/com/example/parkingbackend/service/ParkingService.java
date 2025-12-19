package com.example.parkingbackend.service;


import com.example.parkingbackend.entity.VehicleRecord;
import com.example.parkingbackend.mapper.VehicleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ParkingService {

    @Autowired
    private VehicleMapper vehicleMapper;

    // --- 车辆入场 ---
    // 在 ParkingService.java 中修改 entry 方法
    public String entry(String plate) {
        // --- 后端正则表达式校验 ---
        // 注意Java中 \ 需要转义成 \\
        String plateRegex = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z][A-Z0-9]{5,6}$";
        if (!plate.matches(plateRegex)) {
            return "错误：服务器端校验失败，车牌号格式不正确！";
        }

        // 1. 检查是否已经在场
        if (vehicleMapper.findActiveByPlate(plate) != null) {
            return "错误：该车辆已经在场，未检测到离场记录！";
        }

        // ... 后续代码不变 ...
        int count = vehicleMapper.countActiveVehicles();
        if (count >= 20) {
            return "错误：车位已满 (20/20)，禁止入场！";
        }

        VehicleRecord record = new VehicleRecord();
        record.setPlateNumber(plate);
        record.setEntryTime(LocalDateTime.now());
        record.setDateId(1);

        vehicleMapper.insert(record);
        return "入场成功！车牌：" + plate;
    }
// 在 ParkingService 类中添加/修改

    // 新增：只计算费用，不执行数据库更新（给手机端展示用）
    public BigDecimal calculateFee(String plate) {
        VehicleRecord record = vehicleMapper.findActiveByPlate(plate);
        if (record == null) return BigDecimal.ZERO;

        LocalDateTime now = LocalDateTime.now();
        long minutes = Duration.between(record.getEntryTime(), now).toMinutes();

        // 算法：(分钟数 / 30.0) 向上取整 * 2
        long periods = (long) Math.ceil(minutes / 30.0);
        if (periods == 0) periods = 1;

        return BigDecimal.valueOf(periods * 2);
    }

    // 修改原 exit 方法（保持原逻辑，但为了防止重复扣费，可以加个校验，不过这里为了演示简单，保持原样即可）
// 原有的 exit 方法不用动，支付成功后我们再调用它。
    // --- 车辆出场 ---
    public String exit(String plate) {
        // 1. 找这辆车
        VehicleRecord record = vehicleMapper.findActiveByPlate(plate);
        if (record == null) {
            return "错误：未找到该车牌的在场记录！";
        }

        // 2. 计算时间
        LocalDateTime now = LocalDateTime.now();
        record.setExitTime(now);

        // 计算停车分钟数
        long minutes = Duration.between(record.getEntryTime(), now).toMinutes();

        // 3. 计算费用 (每半小时2元，不满半小时按半小时算)
        // 算法：(分钟数 / 30.0) 向上取整，再乘以 2
        long periods = (long) Math.ceil(minutes / 30.0);
        // 如果不足1分钟（刚进就出），也算一个周期，或者你可以设为免费
        if (periods == 0) periods = 1;

        BigDecimal fee = BigDecimal.valueOf(periods * 2);
        record.setParkingFee(fee);

        // 4. 更新数据库
        vehicleMapper.updateExit(record);

        return "出场成功！停车时长：" + minutes + "分钟，费用：" + fee + "元";
    }

    // --- 获取列表 ---
    public List<VehicleRecord> getAllRecords() {
        return vehicleMapper.findAll();
    }
}
