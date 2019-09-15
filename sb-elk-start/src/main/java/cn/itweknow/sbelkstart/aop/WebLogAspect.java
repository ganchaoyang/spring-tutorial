package cn.itweknow.sbelkstart.aop;

import cn.itweknow.sbelkstart.annotation.ControllerWebLog;
import cn.itweknow.sbelkstart.db.mapper.WebLogMapper;
import cn.itweknow.sbelkstart.db.model.WebLog;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 接口日志切面
 *
 * @author ganchaoyang
 */
@Aspect
@Component
@Order(100)
public class WebLogAspect {

    private Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    @Autowired
    private WebLogMapper webLogMapper;

    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    private static final String START_TIME = "startTime";

    private static final String REQUEST_PARAMS = "requestParams";

    @Pointcut("execution(* cn.itweknow.sbelkstart.controller..*.*(..))")
    public void webLog() {}

    @Before(value = "webLog()&& @annotation(controllerWebLog)")
    public void doBefore(JoinPoint joinPoint, ControllerWebLog controllerWebLog) {
        // 开始时间。
        long startTime = System.currentTimeMillis();
        Map<String, Object> threadInfo = new HashMap<>();
        threadInfo.put(START_TIME, startTime);
        // 请求参数。
        StringBuilder requestStr = new StringBuilder();
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            for (Object arg : args) {
                requestStr.append(arg.toString());
            }
        }
        threadInfo.put(REQUEST_PARAMS, requestStr.toString());
        threadLocal.set(threadInfo);
        logger.info("{}接口开始调用:requestData={}", controllerWebLog.name(), threadInfo.get(REQUEST_PARAMS));
    }

    @AfterReturning(value = "webLog()&& @annotation(controllerWebLog)", returning = "res")
    public void doAfterReturning(ControllerWebLog controllerWebLog, Object res) {
        Map<String, Object> threadInfo = threadLocal.get();
        long takeTime = System.currentTimeMillis() - (long) threadInfo.getOrDefault(START_TIME, System.currentTimeMillis());
        if (controllerWebLog.intoDb()) {
            insertResult(controllerWebLog.name(), (String) threadInfo.getOrDefault(REQUEST_PARAMS, ""),
                    JSON.toJSONString(res), takeTime);
        }
        threadLocal.remove();
        logger.info("{}接口结束调用:耗时={}ms,result={}", controllerWebLog.name(),
                takeTime, res);
    }

    @AfterThrowing(value = "webLog()&& @annotation(controllerWebLog)", throwing = "throwable")
    public void doAfterThrowing(ControllerWebLog controllerWebLog, Throwable throwable) {
        Map<String, Object> threadInfo = threadLocal.get();
        if (controllerWebLog.intoDb()) {
            insertError(controllerWebLog.name(), (String) threadInfo.getOrDefault(REQUEST_PARAMS, ""),
                    throwable);
        }
        threadLocal.remove();
        logger.error("{}接口调用异常，异常信息{}",controllerWebLog.name(), throwable);
    }


    public void insertResult(String operationName, String requestStr, String responseStr, long takeTime) {
        WebLog webLog = new WebLog();
        webLog.setCreateTime(new Date());
        webLog.setError(false);
        webLog.setOperationName(operationName);
        webLog.setRequest(requestStr);
        webLog.setResponse(responseStr);
        webLog.setTaketime(takeTime);
        webLogMapper.insert(webLog);
    }


    public void insertError(String operationName, String requestStr, Throwable throwable) {
        WebLog webLog = new WebLog();
        webLog.setCreateTime(new Date());
        webLog.setError(true);
        webLog.setOperationName(operationName);
        webLog.setRequest(requestStr);
        webLog.setStack(throwable.getStackTrace().toString());
        webLogMapper.insert(webLog);
    }

}
