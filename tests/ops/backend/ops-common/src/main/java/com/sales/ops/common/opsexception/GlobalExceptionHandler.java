package com.sales.ops.common.opsexception;

import com.sales.ops.dto.util.CommonResult;
import com.smc.smccloud.core.exception.BusinessException;
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
@RestControllerAdvice(basePackages = {"com.sales.ops.web.controller"})
public class GlobalExceptionHandler {

    @Resource
    private HttpServletRequest httpServletRequest;

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public CommonResult exceptionHandler(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return CommonResult.failure("不支持' " + e.getMethod() + "'请求");
    }

    /**
     * 处理空指针的异常
     */
    @ExceptionHandler(NullPointerException.class)
    public CommonResult exceptionHandler(NullPointerException e) {
        log.error(e.getMessage(), e);
        return CommonResult.failure("发生空指针异常");
    }

    /**
     * 请求参数异常
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public CommonResult handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error(e.getMessage(), e);
        //bugid:15192 c14717 2024-09-10
        log.error("URI: {}, 请求参数异常: {}", httpServletRequest.getRequestURI(), e.getMessage());
        return CommonResult.failure("请求参数异常: " + e.getMessage());
    }

    /**
     * 拦截业务处理异常
     */
    @ExceptionHandler(RuntimeException.class)
    public CommonResult exceptionHandler(RuntimeException e) {
        log.error("业务处理异常:", e);
        return CommonResult.failure("业务处理异常:" + e.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public CommonResult handleException(Exception e) {
        log.error("未知系统异常: {}", e.getMessage(), e);
        return CommonResult.failure("未知系统异常");
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public CommonResult handleBusinessException(BusinessException e) {
        log.error("业务处理异常: {}", e.getMessage(), e);
        return CommonResult.failure(e.getMessage());
    }


    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder sb = new StringBuilder();
        sb.append("参数校验失败: ");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage()).append(", ");
        }
        log.error(sb.toString());
        return CommonResult.failure(sb.toString());
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public CommonResult<String> handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder sb = new StringBuilder();
        sb.append("参数校验失败: ");
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            sb.append(violation.getMessage()).append(",");
        }
        log.error(sb.toString());
        return CommonResult.failure(sb.toString());
    }

    @ExceptionHandler({NotReadablePropertyException.class})
    @ResponseBody
    public CommonResult<String> handleNotReadablePropertyException(NotReadablePropertyException e) {
        log.error("参数校验失败: {}", e.getMessage());
        return CommonResult.failure("参数校验失败: ", e.getMessage());
    }

    /**
     * 处理由断言，IllegalArgumentException抛出得异常信息
     *
     * @return java.lang.String
     */
    @ResponseBody
    @ExceptionHandler(value = IllegalArgumentException.class)
    public CommonResult<String> handleArgError(IllegalArgumentException e) {
        return CommonResult.failure("参数校验失败: ", e.getMessage());
    }

    /**
     * 查询超时异常
     */
    @ExceptionHandler(value = {QueryTimeoutException.class,SQLTimeoutException.class})
    public ResultVo<String> handQuetySqlTimeOutException(SQLTimeoutException e) {
        return ResultVo.failure("查询超时");
    }

}