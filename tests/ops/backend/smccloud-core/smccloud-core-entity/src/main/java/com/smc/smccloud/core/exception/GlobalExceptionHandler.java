package com.smc.smccloud.core.exception;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLTimeoutException;

@Slf4j
@RestControllerAdvice(basePackages = {"com.smc.smccloud.web"})
public class GlobalExceptionHandler {

    @Resource
    private HttpServletRequest httpServletRequest;

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultVo exceptionHandler(HttpRequestMethodNotSupportedException e) {
        log.error("URI: {}, 不支持{}请求", httpServletRequest.getRequestURI());
        return ResultVo.failure("不支持' " + e.getMethod() + "'请求");
    }

    /**
     * 处理空指针的异常
     */
    @ExceptionHandler(NullPointerException.class)
    public ResultVo exceptionHandler(NullPointerException e) {
        log.error("URI: {}, 发生空指针异常", httpServletRequest.getRequestURI(), e);
        return ResultVo.failure("发生空指针异常");
    }

    /**
     * 请求参数异常
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResultVo handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("URI: {}, 请求参数异常: {}", httpServletRequest.getRequestURI(), e.getMessage());
        return ResultVo.failure("请求参数异常: " + e.getMessage());
    }

    /**
     * 拦截业务处理异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ResultVo exceptionHandler(RuntimeException e) {
        log.error("URI: {}, 未知运行异常: {}", httpServletRequest.getRequestURI(), e.getMessage(), e);
        return ResultVo.failure("未知运行异常:" + e.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public ResultVo handleException(Exception e) {
        log.error("URI: {}, 未知系统异常: {}", httpServletRequest.getRequestURI(), e.getMessage(), e);
        return ResultVo.failure("未知系统异常");
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ResultVo handleBusinessException(BusinessException e) {
        log.error("URI: {}, 业务异常: {}", httpServletRequest.getRequestURI(), e.getMessage(), e);
        return ResultVo.failure(e.getMessage());
    }


    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVo<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder sb = new StringBuilder();
        sb.append("参数校验失败: ");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage()).append(", ");
        }
        log.error(sb.toString());
        log.error("URI: {}, {}", httpServletRequest.getRequestURI(), sb.toString());
        return ResultVo.failure(sb.toString());
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public ResultVo<String> handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder sb = new StringBuilder();
        sb.append("参数校验失败: ");
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            sb.append(violation.getMessage()).append(",");
        }
        log.error(sb.toString());
        log.error("URI: {}, {}", httpServletRequest.getRequestURI(), sb.toString());
        return ResultVo.failure(sb.toString());
    }

    @ExceptionHandler({NotReadablePropertyException.class})
    @ResponseBody
    public ResultVo<String> handleNotReadablePropertyException(NotReadablePropertyException e) {
        log.error("URI: {}, 参数校验失败:{}", httpServletRequest.getRequestURI(), e.getMessage());
        return ResultVo.failure("参数校验失败: ", e.getMessage());
    }

    /**
     * 处理由断言，IllegalArgumentException抛出得异常信息
     *
     * @return java.lang.String
     */
    @ResponseBody
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResultVo<String> handleArgError(IllegalArgumentException e) {
        log.error("URI: {}, 参数校验失败: {}", httpServletRequest.getRequestURI(), e.getMessage());
        return ResultVo.failure("参数校验失败: ", e.getMessage());
    }

    /**
     * 查询超时异常
     */
    @ExceptionHandler(value = {QueryTimeoutException.class,SQLTimeoutException.class})
    public ResultVo<String> handQuetySqlTimeOutException(SQLTimeoutException e) {
        log.error("URI: {}, 请求异常: {}", httpServletRequest.getRequestURI(), e.getMessage());
        return ResultVo.failure("查询超时");
    }

}