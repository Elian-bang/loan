package org.example.loan.dto;

import lombok.*;
import org.example.loan.exception.BaseException;
import org.example.loan.exception.ResultType;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultObject implements Serializable {

    public String code;

    public String desc;

    public ResultObject(ResultType resultType) {
        this.code = resultType.getCode();
        this.desc = resultType.getDesc();
    }

    public ResultObject(ResultType resultCode, String message) {
        this.code = resultCode.getCode();
        this.desc = message;
    }

    public ResultObject(BaseException e) {
        this.code = e.getCode();
        this.desc = e.getDesc();
    }

    public static ResultObject getSuccess() {
        return new ResultObject(ResultType.SUCCESS);
    }
}
