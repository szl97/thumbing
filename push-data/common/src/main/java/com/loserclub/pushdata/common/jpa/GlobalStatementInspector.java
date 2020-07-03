package com.loserclub.pushdata.common.jpa;

import com.loserclub.pushdata.common.constants.EntityConstants;
import com.loserclub.pushdata.common.utils.context.SpringContextUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.Statements;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.update.Update;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 全局sql过滤器 仅针对sql 可拓展
 * 参考mybatis-plus得租户过滤实现 其实这一套也可以在mybatis里用 一样可以过滤自定义sql 非常强大 当然有点性能开销
 *
 * @author 王吉
 * @date 2020-04-27 14:01
 */
@Slf4j
@Data
public class GlobalStatementInspector implements StatementInspector {
    private GlobalFilterHandler globalFilterHandler;

    @Override
    public String inspect(String sql) {
        try {
            StringBuilder sqlStringBuilder = new StringBuilder();
            Statements statements = CCJSqlParserUtil.parseStatements(sql);
            if (globalFilterHandler == null) {
                globalFilterHandler = SpringContextUtils.getBean(GlobalFilterHandler.class);
                Assert.notNull(globalFilterHandler, "尚未注入全局filter处理器");
            }
            int i = 0;
            Iterator var6 = statements.getStatements().iterator();

            while (var6.hasNext()) {
                Statement statement = (Statement) var6.next();
                if (null != statement) {
                    if (i++ > 0) {
                        sqlStringBuilder.append(';');
                    }

                    sqlStringBuilder.append(this.processParser(statement));
                }
            }
            return sqlStringBuilder.toString();
        } catch (Exception ex) {
            log.error("解析sql失败：" + ex.getMessage());
        }

        return sql;
    }

    private String processParser(Statement statement) {
        //仅处理select查询
        if (statement instanceof Select) {
            this.processSelectBody(((Select) statement).getSelectBody());
        } else if (statement instanceof Update) {
            this.processUpdate((Update) statement);
        } else if (statement instanceof Delete) {
            statement = this.processDelete((Delete) statement);
        }
        /**
         * 返回处理后的SQL
         */
        return statement.toString();
    }

    private void processUpdate(Update update) {
        Table table = update.getTable();
        for (Expression expression : globalFilterHandler.getGlobalExpressions(table)) {
            update.setWhere(this.builderExpression(update.getWhere(), expression));
        }
    }

    private Statement processDelete(Delete delete) {
        for (Expression expression : globalFilterHandler.getGlobalExpressions(delete.getTable())) {
            delete.setWhere(this.builderExpression(delete.getWhere(), expression));
        }
        if (globalFilterHandler.getHasLogicTables().contains(delete.getTable().getName())) {
            Update update = new Update();
            update.setTable(delete.getTable());
            update.setWhere(delete.getWhere());
            Column logicColumn = new Column(EntityConstants.DR);
            List<Column> columns = new ArrayList<>();
            columns.add(logicColumn);
            update.setColumns(columns);
            Expression logicDeleteExpression = new LongValue(EntityConstants.LOGIC_DELETE_VALUE);
            List<Expression> expressions = new ArrayList<>();
            expressions.add(logicDeleteExpression);
            update.setExpressions(expressions);
            return update;
        }
        return delete;
    }

    public void processSelectBody(SelectBody selectBody) {
        if (selectBody instanceof PlainSelect) {
            this.processPlainSelect((PlainSelect) selectBody);
        } else if (selectBody instanceof WithItem) {
            WithItem withItem = (WithItem) selectBody;
            if (withItem.getSelectBody() != null) {
                this.processSelectBody(withItem.getSelectBody());
            }
        } else {
            SetOperationList operationList = (SetOperationList) selectBody;
            if (operationList.getSelects() != null && operationList.getSelects().size() > 0) {
                operationList.getSelects().forEach(this::processSelectBody);
            }
        }

    }


    protected void processPlainSelect(PlainSelect plainSelect) {
        FromItem fromItem = plainSelect.getFromItem();
        if (fromItem instanceof Table) {
            Table fromTable = (Table) fromItem;
            for (Expression expression : globalFilterHandler.getGlobalExpressions(fromTable)) {
                plainSelect.setWhere(this.builderExpression(plainSelect.getWhere(), expression));
            }
        } else {
            this.processFromItem(fromItem);
        }

        List<Join> joins = plainSelect.getJoins();
        if (joins != null && joins.size() > 0) {
            joins.forEach((j) -> {
                this.processJoin(j);
                this.processFromItem(j.getRightItem());
            });
        }

    }

    protected Expression builderExpression(Expression currentExpression, Expression addExpression) {
        Object appendExpression = addExpression;

        if (currentExpression == null) {
            return (Expression) appendExpression;
        } else {
            if (currentExpression instanceof BinaryExpression) {
                BinaryExpression binaryExpression = (BinaryExpression) currentExpression;
                this.doExpression(binaryExpression.getLeftExpression());
                this.doExpression(binaryExpression.getRightExpression());
            } else if (currentExpression instanceof InExpression) {
                InExpression inExp = (InExpression) currentExpression;
                ItemsList rightItems = inExp.getRightItemsList();
                if (rightItems instanceof SubSelect) {
                    this.processSelectBody(((SubSelect) rightItems).getSelectBody());
                }
            }

            return currentExpression instanceof OrExpression ? new AndExpression(new Parenthesis(currentExpression), (Expression) appendExpression) : new AndExpression(currentExpression, (Expression) appendExpression);
        }
    }

    protected void doExpression(Expression expression) {
        if (expression instanceof FromItem) {
            this.processFromItem((FromItem) expression);
        } else if (expression instanceof InExpression) {
            InExpression inExp = (InExpression) expression;
            ItemsList rightItems = inExp.getRightItemsList();
            if (rightItems instanceof SubSelect) {
                this.processSelectBody(((SubSelect) rightItems).getSelectBody());
            }
        }

    }


    protected Column getAliasColumn(Table table, String columnName) {
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

    protected void processFromItem(FromItem fromItem) {
        if (fromItem instanceof SubJoin) {
            SubJoin subJoin = (SubJoin) fromItem;
            if (subJoin.getJoinList() != null) {
                subJoin.getJoinList().forEach(this::processJoin);
            }

            if (subJoin.getLeft() != null) {
                this.processFromItem(subJoin.getLeft());
            }
        } else if (fromItem instanceof SubSelect) {
            SubSelect subSelect = (SubSelect) fromItem;
            if (subSelect.getSelectBody() != null) {
                this.processSelectBody(subSelect.getSelectBody());
            }
        } else if (fromItem instanceof ValuesList) {
            log.debug("Perform a subquery, if you do not give us feedback");
        } else if (fromItem instanceof LateralSubSelect) {
            LateralSubSelect lateralSubSelect = (LateralSubSelect) fromItem;
            if (lateralSubSelect.getSubSelect() != null) {
                SubSelect subSelect = lateralSubSelect.getSubSelect();
                if (subSelect.getSelectBody() != null) {
                    this.processSelectBody(subSelect.getSelectBody());
                }
            }
        }

    }

    protected void processJoin(Join join) {
        if (join.getRightItem() instanceof Table) {
            Table fromTable = (Table) join.getRightItem();
            for (Expression expression : globalFilterHandler.getGlobalExpressions(fromTable)) {
                join.setOnExpression(this.builderExpression(join.getOnExpression(), expression));
            }
        }

    }
}
