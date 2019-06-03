package com.liang.guli.eduservice.handler;


import com.liang.guli.educommon.R;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "自定义异常")
public class EduException extends RuntimeException {


    @ApiModelProperty(value = "状态码")
    private Integer code;
    @ApiModelProperty(value = "异常消息")
    private String msg;


    @ExceptionHandler(EduException.class)
    @ResponseBody
    public R error(EduException e){
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
