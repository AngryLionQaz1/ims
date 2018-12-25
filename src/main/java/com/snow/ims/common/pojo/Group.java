package com.snow.ims.common.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Entity
@Data
@Table(name = "s_group")
@AllArgsConstructor
@NoArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**管理员ID*/
    private Long adminId;
    private String groupName;
    private String groupImg;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
    @ManyToMany(targetEntity = User.class)
    private Set<User> users;

}
