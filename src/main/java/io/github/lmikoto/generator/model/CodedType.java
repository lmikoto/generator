package io.github.lmikoto.generator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liuyang
 * 2020/7/28 10:59 上午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodedType {

    /**
     * 值
     */
    private int value;
    /**
     * 备注
     */
    private String remark;
    /**
     * 键名
     */
    private String name;
}
