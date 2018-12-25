package com.snow.ims.common.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Data
@Table(name = "s_friends")
@AllArgsConstructor
@NoArgsConstructor
public class Friends {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**好友id*/
    private Long friendId;
    /**用户id*/
    private Long uId;
    /**添加时间*/
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JSONField(serialize = false)
    private User user;




}
