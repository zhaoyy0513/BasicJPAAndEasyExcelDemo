package zhaoyy.poidemo.util;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

/**
 * @author zhaoyuyang
 * @createTime 2019/11/25 0025 11:05
 */
public class R extends ResponseEntity {

    public R(HttpStatus status) {
        super(status);
    }

    public R(Object body, HttpStatus status) {
        super(body, status);
    }

    public R(MultiValueMap headers, HttpStatus status) {
        super(headers, status);
    }

    public R(Object body, MultiValueMap headers, HttpStatus status) {
        super(body, headers, status);
    }
}
