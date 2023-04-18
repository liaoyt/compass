package com.oppo.cloud.common.domain.flink.enums;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Stream;

@Getter
@Slf4j
public enum EDiagnosisRule {
    TmMemoryHigh(0, "内存利用率高", "rgb(255, 114, 46)", "计算内存的使用率，如果使用率高于阈值，则增加内存",
            EDiagnosisRuleType.ResourceRule.getCode()),
    TmMemoryLow(1, "内存利用率低", "rgb(255, 114, 46)", "计算内存的使用率，如果使用率低于阈值，则降低内存",
            EDiagnosisRuleType.ResourceRule.getCode()),
    JmMemoryRule(2, "JM内存优化", "rgb(255, 114, 46)", "根据tm个数计算jm内存的建议值",
            EDiagnosisRuleType.ResourceRule.getCode()),
    JobNoTraffic(3, "作业无流量", "rgb(255, 114, 46)", "检测作业的kafka source算子是否没有流量",
            EDiagnosisRuleType.ResourceRule.getCode()),
    SlowVerticesRule(4, "存在慢算子", "rgb(255, 114, 46)", "检测作业是否存在慢算子",
            EDiagnosisRuleType.RuntimeExceptionRule.getCode()),
    TmManagedMemory(5, "TM管理内存优化", "rgb(255, 114, 46)",
            "计算作业管理内存的使用率，给出合适的管理内存建议值", EDiagnosisRuleType.ResourceRule.getCode()),
    TmNoTraffic(7, "部分TM空跑", "rgb(255, 114, 46)", "检测是否有tm没有流量，并且cpu和内存也没有使用",
            EDiagnosisRuleType.ResourceRule.getCode()),
    ParallelGrow(8, "并行度不够", "rgb(255, 114, 46)", "检测作业是否因为并行度不够引起延迟",
            EDiagnosisRuleType.ResourceRule.getCode()),
    AvgCpuHighRule(10, "CPU均值利用率高", "rgb(255, 114, 46)",
            "计算作业的CPU均值使用率，如果高于阈值，则增加cpu", EDiagnosisRuleType.ResourceRule.getCode()),
    AvgCpuLowRule(11, "CPU均值利用率低", "rgb(255, 114, 46)",
            "计算作业的CPU均值使用率，如果低于阈值，则降低cpu", EDiagnosisRuleType.ResourceRule.getCode()),
    PeekDurationResourceRule(12, "CPU峰值利用率高", "rgb(255, 114, 46)",
            "计算作业的CPU峰值使用率，如果高于阈值，则增加cpu", EDiagnosisRuleType.ResourceRule.getCode()),
    BackPressure(15, "存在反压算子", "rgb(255, 114, 46)",
            "检测作业是否存在反压算子", EDiagnosisRuleType.RuntimeExceptionRule.getCode()),
    JobDelay(16, "作业延迟高", "rgb(255, 114, 46)", "检测作业的kafka延迟是否高于阈值",
            EDiagnosisRuleType.RuntimeExceptionRule.getCode()),
    ;
    @JSONField(value = true)
    private final int code;
    @JSONField(value = true)
    private final String name;
    @JSONField(value = true)
    private final String color;
    @JSONField(value = true)
    private final String desc;
    @JSONField(value = true)
    private final int ruleType;

    EDiagnosisRule(int code, String name, String color, String desc, int ruleType) {
        this.code = code;
        this.name = name;
        this.color = color;
        this.desc = desc;
        this.ruleType = ruleType;
    }

    public static EDiagnosisRule valueOf(int code) {
        return Stream.of(EDiagnosisRule.values()).filter(x -> x.getCode() == code).findFirst().orElse(null);
    }

}
