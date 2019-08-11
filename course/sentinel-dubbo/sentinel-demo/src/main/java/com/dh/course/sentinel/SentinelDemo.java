package com.dh.course.sentinel;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/alibaba/Sentinel/wiki/%E4%BB%8B%E7%BB%8D
 *
 * 启动参数
 * -Dcsp.sentinel.dashboard.server=localhost:8080
 */
public class SentinelDemo {

    private static final String resource = "sentinelDemo";

    private static void initFlow(){
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource(resource);
        //默认限制qps
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(20);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

    public static void main(String[] args) {
        initFlow();
        while (true){
            Entry entry = null;
            try {
                Thread.sleep(20);
                entry= SphU.entry(resource);
                System.out.println("success---");
            } catch (BlockException | InterruptedException e) {
                e.printStackTrace();
            }finally {
                if(null != entry){
                    entry.exit();
                }
            }
        }
    }


}
