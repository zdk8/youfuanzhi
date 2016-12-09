package cn.com.hvit.framework.kon.util;


import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Properties;

/**
 * Created by Administrator on 2016/7/21.
 * mybatis 通用分页拦截器
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class}),
@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
public class PageHelper implements Interceptor {

    private static final Logger logger = Logger.getLogger(cn.com.hvit.framework.kon.util.PageHelper.class);

    public static final ThreadLocal<Page> localPage = new ThreadLocal<Page>();

    /**
     * 开始分页
     * @param pageNum
     * @param pagSize
     */
    public static void startPage(int pageNum,int pagSize){
        localPage.set(new Page(pageNum,pagSize));
    }

    /**
     * 结束分页并返回结果，该方法必须被调用，否则localPage会一直保存下去，直到下一次startPage
     * @return
     */
    public static Page endPage(){
        Page page = localPage.get();
        localPage.remove();
        return page;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if(localPage.get() == null){
           return invocation.proceed();
        }

        if(invocation.getTarget() instanceof StatementHandler){
            StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
            MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
            //分离代理对象链（由于目标类可能被对多个拦截器拦截，从而形成多次代理，
            //通过下面两次循环可以分离出最原始的目标类
            while(metaStatementHandler.hasGetter("h")){
                Object object = metaStatementHandler.getValue("h");
                metaStatementHandler = SystemMetaObject.forObject(object);
            }
            //分离最后一个代理对象的目标类
            while (metaStatementHandler.hasGetter("target")){
                Object object = metaStatementHandler.getValue("target");
                metaStatementHandler = SystemMetaObject.forObject(object);
            }
            MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
            //分页信息if(LocalPage.get() != null){
            Page page = localPage.get();
            BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
            //分页参数作为参数对象parameterObject的一个属性
            String sql = boundSql.getSql();
            //重写sql
            String pageSql = buildPageSql(sql,page);
            //重写分页sql
            metaStatementHandler.setValue("delegate.boundSql.sql",pageSql);
            Connection connection = (Connection) invocation.getArgs()[0];
            //重设分页参数里的总页数等
            setPageParameter(sql,connection,mappedStatement,boundSql,page);
            //将执行权交给下一个拦截器
            return invocation.proceed();
        }else if(invocation.getTarget() instanceof ResultSetHandler){
            Object result = invocation.proceed();
            Page page = localPage.get();
            page.setResults((List) result);
            return result;
        }
        return null;
    }

    /**
     * 获取总记录数
     * @param sql
     * @param connection
     * @param mappedStatement
     * @param boundSql
     * @param page
     */
    private void setPageParameter(String sql, Connection connection, MappedStatement mappedStatement, BoundSql boundSql, Page page) {
        //记录总记录数
        String countSql = "select count(0) from (" + sql + ")";
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try{
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(),countSql,
                    boundSql.getParameterMappings(),boundSql.getParameterObject());
            setParameters(countStmt,mappedStatement,countBS,boundSql.getParameterObject());
            rs = countStmt.executeQuery();
            int totalCount  = 0;
            if(rs.next()){
                totalCount  = rs.getInt(1);
            }
            page.setTotal(totalCount);
            int totalPage = totalCount / page.getPageSize() + ((totalCount % page.getPageSize() == 0)?0:1);
            page.setPages(totalPage);

        } catch (SQLException e) {
            logger.error("Ignore this exception",e);
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.error("Ignore this exception", e);
                e.printStackTrace();
            }
            try{
                countStmt.close();
            } catch (SQLException e) {
                logger.error("Ignore this exception", e);
                e.printStackTrace();
            }
        }
    }

    /**
     * 代入参数值
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject) throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement,parameterObject,boundSql);
        parameterHandler.setParameters(ps);
    }

    /**
     * 修改原sql为分页sql
     * @param sql
     * @param page
     * @return
     */
    private String buildPageSql(String sql, Page page) {
        StringBuilder pageSql = new StringBuilder(200);
        pageSql.append("select * from ( select temp.*, rownum row_id from ( ");
        pageSql.append(sql);
        pageSql.append(" ) temp where rownum <= ").append(page.getEndRow());
        pageSql.append(") where row_id > ").append(page.getStartRow());
        return pageSql.toString();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler || target instanceof ResultSetHandler){
            return Plugin.wrap(target,this);
        }else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }

    public static class Page<E> {
        private int pageNum;
        private int pageSize;
        private int startRow;
        private int endRow;
        private long total;
        private int pages;
        private List<E> results;

        public Page(int pageNum,int pageSize){
            this.pageNum =pageNum;
            this.pageSize = pageSize;
            this.startRow = pageNum > 0 ? (pageNum -1) * pageSize : 0;
            this.endRow = pageNum * pageSize;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getStartRow() {
            return startRow;
        }

        public void setStartRow(int startRow) {
            this.startRow = startRow;
        }

        public int getEndRow() {
            return endRow;
        }

        public void setEndRow(int endRow) {
            this.endRow = endRow;
        }

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public List<E> getResults() {
            return results;
        }

        public void setResults(List<E> results) {
            this.results = results;
        }

        @Override
        public String toString() {
            return "Page{" +
                    "pageNum=" + pageNum +
                    ",pageSize=" + pageSize +
                    ",startRow=" + startRow +
                    ",endRow=" + endRow +
                    ",total=" +total +
                    ",page=" + pages +
                    "}";
        }
    }
}
