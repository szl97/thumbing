package com.thumbing.shared.entity.sql.personal;

import com.thumbing.shared.entity.BaseEntity;
import com.thumbing.shared.entity.sql.SqlFullAuditedEntity;
import com.thumbing.shared.entity.sql.system.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/16 14:41
 */
@Entity
@Table(name = "personal")
@Getter
@Setter
@FieldNameConstants
//phone_num, email, gender, birth_date, birth_year,
// birth_month, birth_day, constellation, interest_id(fk),
// is_student, occupation_id(fk), job_id(fk), currentCountry, nativeCountry
public class Personal extends SqlFullAuditedEntity {
    /**
     * 标识身份信息所属的唯一用户
     */
    @OneToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", referencedColumnName = BaseEntity.Fields.id)
    private User user;
    /**
     * 民字
     */
    private String name;
    /**
     * 电话号码
     */
    private String phone_num;
    /**
     * 邮件
     */
    private String email;
    /**
     * 性别
     */
    private String gender;
    /**
     * 出生日期
     */
    private LocalDateTime birth_date;
    /**
     * 出生年
     */
    private short birth_year;
    /**
     * 出生月
     */
    private short birth_month;
    /**
     * 出生日
     */
    private short birth_day;
    /**
     * 星座
     */
    private String constellation;
    /**
     * 是否是学生
     */
    private boolean is_student;
    /**
     * 目前所在国家
     */
    private String currentCountry;
    /**
     * 故乡国家
     */
    private String nativeCountry;
    /**
     * 兴趣爱好
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "personal_interest",
            joinColumns = { @JoinColumn(name = "personal_id") },
            inverseJoinColumns = {@JoinColumn(name = "interest_id")})
    private Set<Interest> interests;
    /**
     * 专业（所学或所擅长）
     */
    @ManyToOne(targetEntity = Occupation.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "occupation_id", referencedColumnName = BaseEntity.Fields.id)
    private Occupation occupation;
    /**
     * 职业
     */
    @ManyToOne(targetEntity = Job.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "job_id", referencedColumnName = BaseEntity.Fields.id)
    private Job job;

    @Override
    public boolean equals(Object object){
        if(object == null) return false;
        if(!(object instanceof Personal)) return false;
        Personal a = (Personal)object;
        return  a.getId() == getId();
    }
}
