package com.thumbing.shared.service.impl;


import com.thumbing.shared.entity.mongo.BaseMongoEntity;
import com.thumbing.shared.repository.mongo.IBaseMongoRepository;
import com.thumbing.shared.service.mongo.IBaseMongoService;
import com.thumbing.shared.utils.property.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Stan Sai
 * @date 2020-07-05
 */
public class BaseMongoService<T extends BaseMongoEntity, K extends IBaseMongoRepository<T>> implements IBaseMongoService<T, K> {
    @Autowired
    protected K repository;

    @Override
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public List<T> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public Iterable<T> findAllById(Iterable<Long> iterable) {
        return repository.findAllById(iterable);
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> iterable) {
        return repository.saveAll(iterable);
    }


    @Override
    public <S extends T> List<S> findAll(Example<S> example) {
        return repository.findAll(example);
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        return repository.findAll(example, sort);
    }


    @Override
    public <S extends T> Optional<S> findOne(Example<S> example) {
        return repository.findOne(example);
    }

    @Override
    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        return repository.findAll(example, pageable);
    }

    @Override
    public <S extends T> long count(Example<S> example) {
        return repository.count(example);
    }

    @Override
    public <S extends T> boolean exists(Example<S> example) {
        return repository.exists(example);
    }

    @Override
    public <S extends T> S save(S s) {
        return repository.save(s);
    }

    @Override
    public Optional<T> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public boolean existsById(long id) {
        return repository.existsById(id);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(T t) {
        repository.delete(t);
    }

    @Override
    public void deleteAll(Iterable<? extends T> iterable) {
        repository.deleteAll(iterable);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }


    /**
     * 比较两个列表的项目，删除不存在的项目
     *
     * @param sourceList 修改前的列表
     * @param targetList 修改后的列表
     */
    @Override
    public void deleteListItem(List<T> sourceList, List<T> targetList) {
        if (!sourceList.isEmpty()) {
            if (targetList.stream().filter(t -> t.getId() != null).collect(Collectors.toList()).isEmpty()) {
                repository.deleteAll(sourceList);
            } else {
                sourceList.forEach(s -> {
                    if (targetList.stream().filter(t -> t.getId() != null).anyMatch(t -> !t.getId().equals(s.getId()))) {
                        repository.deleteById(s.getId());
                    }
                });
            }
        }
    }

    /**
     * 更新列表项
     *
     * @param sourceList 数据库读取出的列表结合
     * @param targetList 接口传入的列表集合
     */
    @Override
    public void updateListItem(List<T> sourceList, List<T> targetList, Consumer<? super T> action) {
        if (sourceList.isEmpty()) {
            if (!targetList.isEmpty()) {
                //全部新增
                targetList.forEach(t -> {
                    if (action != null) {
                        action.accept(t);
                    }
                });
                repository.saveAll(targetList);
            }
        } else {
            if (!targetList.isEmpty()) {
                //遍历已经存在的数据，如果不在提交的列表中，删除
                List<T> sourcesListForDelete = new ArrayList<>();
                sourceList.forEach(s -> {
                    if (targetList.stream().filter(t -> t.getId() != null).allMatch(t -> !t.getId().equals(s.getId()))) {
                        sourcesListForDelete.add(s);
                    }
                });
                if (!sourcesListForDelete.isEmpty()) {
                    sourcesListForDelete.forEach(d -> {
                        if (sourceList.stream().anyMatch(s -> s.getId().equals(d.getId()))) {
                            T itemForDelete = sourceList.stream().filter(s -> s.getId().equals(d.getId())).findFirst().get();
                            sourceList.remove(itemForDelete);
                        }
                    });
                    repository.deleteAll(sourcesListForDelete);
                }

                //部分新增
                targetList.forEach(t -> {
                    sourceList.forEach(s -> {
                        if (t.getId() != null && t.getId().equals(s.getId())) {
                            PropertyUtils.copyNotNullProperties(t, s);
                        }
                    });
                    if (t.getId() == null) {
                        if (action != null) {
                            action.accept(t);
                            sourceList.add(t);
                        }
                    }
                });
                repository.saveAll(sourceList);
            } else {
                //删除所有已经存在的数据
                repository.deleteAll(sourceList);
            }
        }
    }
}
