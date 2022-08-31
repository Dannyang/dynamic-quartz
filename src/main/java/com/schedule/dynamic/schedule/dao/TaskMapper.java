package com.schedule.dynamic.schedule.dao;

import com.schedule.dynamic.schedule.entity.SysTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskMapper {
    List<SysTask> getTasks();
}
