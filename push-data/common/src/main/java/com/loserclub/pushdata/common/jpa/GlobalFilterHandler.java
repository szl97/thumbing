package com.loserclub.pushdata.common.jpa;

import com.loserclub.pushdata.common.constants.EntityConstants;
import com.loserclub.pushdata.common.utils.entity.EntityUtils;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.hibernate.persister.walking.spi.AttributeDefinition;

import javax.persistence.EntityManagerFactory;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Stan Sai
 * @date 2020-06-28
 */
public class GlobalFilterHandler {

    private final EntityManagerFactory entityManagerFactory;

    //逻辑删除过滤表集合
    private static final List<String> HAS_LOGIC_TABLES=new ArrayList<>();

    public GlobalFilterHandler(EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory=entityManagerFactory;

    }

    public void init() {
        SessionFactoryImpl sessionFactory = (SessionFactoryImpl)entityManagerFactory.unwrap(SessionFactory.class);
        Map<String, EntityPersister> persisterMap = sessionFactory.getMetamodel().entityPersisters();
        for(Map.Entry<String, EntityPersister> entity : persisterMap.entrySet()){
            Class targetClass = entity.getValue().getMappedClass();
            SingleTableEntityPersister persister = (SingleTableEntityPersister)entity.getValue();
            Iterable<AttributeDefinition> attributes = persister.getAttributes();
            String entityName = targetClass.getSimpleName();//Entity的名称
            String tableName = persister.getTableName();//Entity对应的表的英文名
            //属性
            for(AttributeDefinition attr : attributes){
                String propertyName = attr.getName(); //在entity中的属性名称
                String[] columnName = persister.getPropertyColumnNames(propertyName);
                if(checkHasAnnotation(propertyName,targetClass, LogicDelete.class)){
                    //如果字段存在逻辑删除 不考虑支持自定义逻辑删除字段 必须使用框架生命得字段名
                    if(!HAS_LOGIC_TABLES.contains(tableName)){
                        HAS_LOGIC_TABLES.add(tableName);
                    }
                }
            }

        }
    }

    private boolean checkHasAnnotation(String fieldName,Class clazz,Class annotation){
        List<Field> allFields = EntityUtils.getAllFields(clazz);
        for(Field field :allFields){
            if(field.getName().equals(fieldName)){
                if(field.getAnnotation(annotation)!=null){
                    return true;
                }
            }
        }
        return false;
    }


    public  List<String> getHasLogicTables() {
        return HAS_LOGIC_TABLES;
    }

    public String getLogicDeleteColumn(){
        return EntityConstants.DR;
    }



    public Expression getLogicDeleteExpression(){
        return new LongValue(EntityConstants.LOGIC_UN_DELETE_VALUE);
    }

    public List<Expression> getGlobalExpressions(Table table){

        List<Expression> expressions=new ArrayList<>();
        if(HAS_LOGIC_TABLES.contains(table.getName())){
            EqualsTo equalsTo=new EqualsTo();
            equalsTo.setLeftExpression(getAliasColumn(table,getLogicDeleteColumn()));
            equalsTo.setRightExpression(getLogicDeleteExpression());
            expressions.add(equalsTo);
        }
        return expressions;
    }

    private Column getAliasColumn(Table table, String columnName) {
        StringBuilder column = new StringBuilder();
        if (null == table.getAlias()) {
            column.append(table.getName());
        } else {
            column.append(table.getAlias().getName());
        }

        column.append(".");
        column.append(columnName);
        return new Column(column.toString());
    }

}
