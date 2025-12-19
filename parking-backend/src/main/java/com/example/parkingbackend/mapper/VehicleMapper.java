package com.example.parkingbackend.mapper;


import com.example.parkingbackend.entity.VehicleRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VehicleMapper {

    // 1. 查当前在这个停车场的车有多少辆 (status=0)
    @Select("SELECT COUNT(*) FROM vehicle_record WHERE status = 0")
    int countActiveVehicles();

    // 2. 车辆入场：插入一条记录
    @Insert("INSERT INTO vehicle_record(plate_number, entry_time, status, date_id) " +
            "VALUES(#{plateNumber}, #{entryTime}, 0, #{dateId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(VehicleRecord record);

    // 3. 查某辆车是否还在场 (防止重复入场，或者用于出场结算)
    @Select("SELECT * FROM vehicle_record WHERE plate_number = #{plate} AND status = 0 LIMIT 1")
    VehicleRecord findActiveByPlate(String plate);

    // 4. 车辆离场：更新时间和费用
    @Update("UPDATE vehicle_record SET exit_time=#{exitTime}, parking_fee=#{parkingFee}, status=1 " +
            "WHERE id=#{id}")
    void updateExit(VehicleRecord record);

    // 5. 获取所有记录（展示在前端表格里）
    @Select("SELECT * FROM vehicle_record ORDER BY entry_time DESC")
    List<VehicleRecord> findAll();
}
