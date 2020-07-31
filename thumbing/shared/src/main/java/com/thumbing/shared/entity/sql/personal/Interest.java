package com.thumbing.shared.entity.sql.personal;

import com.thumbing.shared.entity.sql.BaseSqlEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/16 15:04
 */
@Entity
@Table(name = "interest")
@Getter
@Setter
@FieldNameConstants
public class Interest extends BaseSqlEntity {
    private String name;
    private int sort;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "personal_interest",
            joinColumns = { @JoinColumn(name = "interest_id") },
            inverseJoinColumns = {@JoinColumn(name = "personal_id")})
    private Set<Personal> personals;
    @Override
    public boolean equals(Object object){
        if(object == null) return false;
        if(!(object instanceof Interest)) return false;
        Interest a = (Interest) object;
        return  a.getId() == getId();
    }
}
