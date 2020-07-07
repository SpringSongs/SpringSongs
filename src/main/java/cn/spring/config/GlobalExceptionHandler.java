package cn.spring.config;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.spring.util.R;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 未被关注的异常信息，统一返回给客户端为“系统异常”
     *
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public R handler(RuntimeException e) {
        e.printStackTrace();
        return  R.error(500, "系统异常");
    }

    /**
     * Hibernate Validator参数校验异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public R handler(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().get(0);
        return  R.error(500, objectError.getDefaultMessage());
    }

    /**
     * Spring Validator参数校验异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public R handler(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            String message = constraintViolation.getMessage();
            if (!StringUtils.isEmpty(message)) {
                //直接返回第一个错误信息
                return R.error(500, message);
            }
        }
        return R.error(500, "参数错误");
    }
}
