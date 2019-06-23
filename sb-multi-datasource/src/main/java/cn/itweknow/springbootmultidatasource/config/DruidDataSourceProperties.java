package cn.itweknow.springbootmultidatasource.config;

/**
 * @author ganchaoyang
 * @date 2019/1/22 18:32
 * @description
 */
public class DruidDataSourceProperties {

    /**
     * 数据源名称
     */
    private String  name;

    /**
     * 数据库连接url
     */
    private String  url;

    /**
     * 数据库用户名
     */
    private String  username;

    /**
     * 数据库密码
     */
    private String  password;

    /**
     * 驱动
     */
    private String driverClassName;

    /**
     * 连接池初始连接数
     */
    private int     initialSize = 5;

    /**
     * 连接池最大连接数
     */
    private int maxActive = 50;

    /**
     * 空闲的最小连接数量, 相当于线程池的最小连接数
     */
    private int minIdle = 5;

    /**
     * 获取连接时最大等待时间,毫秒
     */
    private int maxWait = 60000;

    /**
     * 配置间隔多久才进行一次检测需要关闭的空闲连接，单位是毫秒 ,默认1分钟
     */
    private int timeBetweenEvictionRunsMillis = 60000;

    /**
     * 配置一个连接在池中最小生存的时间，超过该时间的空闲链接将被关闭,默认5分钟
     */
    private int minEvictableIdleTimeMillis = 300000;

    /**
     * 验证链接是否有效的sql
     */
    private String  validationQuery = "SELECT 'x'";

    /**
     * 检测连接是否有效的超时时间
     */
    private int validationQueryTimeout = 3000;

    /**
     * 空闲时检测链接是否有效
     */
    private boolean testWhileIdle = true;

    /**
     * 链接被借出时检查是否有效,影响性能,默认关闭
     */
    private boolean testOnBorrow = false;

    /**
     * 当链接返还时检查连接是否有效,影响性能,默认关闭
     */
    private boolean testOnReturn = false;

    /**
     * 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle,
     * 在mysql下建议关闭。
     */
    private boolean poolPreparedStatements = false;

    /**
     * poolPreparedStatements为false的情况,该值不起作用
     */
    private int maxOpenPreparedStatements = 20;
    /**
     * 是否启用数据源的监控,spring-web应用建议打开
     */
    private boolean enableMonitor = true;

    /**
     * 当启用监控后, 是否打印慢sql
     */
    private boolean logSlowSql = true;
    /**
     * 多少毫秒的sql认为是慢sql, 默认1秒
     */
    private int slowSqlMillis = 1000;

    /**
     * 是否合并sql, 同一个PreparedStatements但where条件不同会被认为是一个sql
     */
    private boolean mergeSql = true;

    public boolean isEnableMonitor() {
        return enableMonitor;
    }

    public void setEnableMonitor(boolean enableMonitor) {
        this.enableMonitor = enableMonitor;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public int getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    /**
     * @return the validationQueryTimeout
     */
    public int getValidationQueryTimeout() {
        return validationQueryTimeout;
    }

    /**
     * @param validationQueryTimeout the validationQueryTimeout to set
     */
    public void setValidationQueryTimeout(int validationQueryTimeout) {
        this.validationQueryTimeout = validationQueryTimeout;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public boolean isPoolPreparedStatements() {
        return poolPreparedStatements;
    }

    public void setPoolPreparedStatements(boolean poolPreparedStatements) {
        this.poolPreparedStatements = poolPreparedStatements;
    }

    public int getMaxOpenPreparedStatements() {
        return maxOpenPreparedStatements;
    }

    public void setMaxOpenPreparedStatements(int maxOpenPreparedStatements) {
        this.maxOpenPreparedStatements = maxOpenPreparedStatements;
    }

    public boolean isLogSlowSql() {
        return logSlowSql;
    }

    public void setLogSlowSql(boolean logSlowSql) {
        this.logSlowSql = logSlowSql;
    }

    public int getSlowSqlMillis() {
        return slowSqlMillis;
    }

    public void setSlowSqlMillis(int slowSqlMillis) {
        this.slowSqlMillis = slowSqlMillis;
    }

    public boolean isMergeSql() {
        return mergeSql;
    }

    public void setMergeSql(boolean mergeSql) {
        this.mergeSql = mergeSql;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public DruidDataSourceProperties setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
        return this;
    }
}
