package com.tencent.wxcloudrun.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
@TableName("Counters")
@Data
public class Counter implements Serializable {

  @TableId(value = "id",type = IdType.AUTO)
  private Integer id;
  @TableField("count")
  private Integer count;
  @TableField("createdAt")
  private LocalDateTime createdAt;
  @TableField("updatedAt")
  private LocalDateTime updatedAt;
}
