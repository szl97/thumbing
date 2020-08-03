//package com.thumbing.shared.repository.sql;
//
//import com.thumbing.shared.entity.sql.SqlFullAuditedEntity;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.List;
//
///**
// * @Author: Stan Sai
// * @Date: 2020/8/3 13:00
// */
//public interface ISqlLogicDeleteRepository<T extends SqlFullAuditedEntity> extends IBaseSqlRepository<T>{
//    @Query(
//            value =
//                    "update #{#entityName} set is_delete=1 where id = ?1 "
//                            + "and is_delete=0")
//    @Modifying
//    void delete(Long id);
//
//    @Override
//    default void delete(T entity) {
//        delete(entity.getId());
//    }
//
//    default void delete(Iterable<? extends T> entities) {
//        entities.forEach(entitiy -> delete(entitiy.getId()));
//    }
//
//    @Override
//    @Query(value = "update #{#entityName} set is_delete=1 where is_delete=0")
//    @Modifying
//    void deleteAll();
//
//    @Query(
//            value =
//                    "update #{#entityName} set is_delete=1 where id in ?1 "
//                            + "and is_delete=0")
//    @Modifying
//    void deleteInBatch(List<Long> ids);
//}
