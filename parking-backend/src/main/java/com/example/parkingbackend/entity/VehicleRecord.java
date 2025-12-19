package com.example.parkingbackend.entity;


import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data // 自动生成 Getter/Setter
public class VehicleRecord {
    private Long id;
    private String plateNumber;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private BigDecimal parkingFee;
    private Integer status; // 0-在场 1-已离场
    private Integer dateId; // 关联周期表
}
