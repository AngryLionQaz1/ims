package com.snow.ims.common.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Builder
@Entity
@Data
@Table(name = "s_authority")
@AllArgsConstructor
@NoArgsConstructor
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**权限名*/
    private String name;
    /**权限uri*/
    private String uri;
    /**详细描述*/
    private String details;
    @ManyToMany(cascade=CascadeType.REFRESH,mappedBy="authorities",fetch = FetchType.EAGER)
    @JSONField(serialize = false)
    private Set<Role> roles;











}

