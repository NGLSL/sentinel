/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.ApiDefinitionEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.GatewayFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.*;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigFactory;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Properties;

/**
 * @author Eric Zhao
 * @since 1.4.0
 */
@Configuration
public class NacosConfig {
    /**
     * 注⼊配置⽂件中的信息
     */
    @Value("${nacos.serverAddr}")
    private String serverAddr;
    @Value("${nacos.namespace}")
    private String namespace;
    @Value("${nacos.username}")
    private String username;
    @Value("${nacos.password}")
    private String password;

    /**
     * 流控规则的编码器
     *
     * @return
     */
    @Bean
    public Converter<List<FlowRuleEntity>, String> flowRuleEntityEncoder() {
        return (source -> JSON.toJSONString(source, true));
    }

    /**
     * 流控规则的解码器
     *
     * @return
     */
    @Bean
    public Converter<String, List<FlowRuleEntity>> flowRuleEntityDecoder() {
        return source -> JSON.parseArray(source, FlowRuleEntity.class);
    }

    /**
     * 创建 Nacos 的配置服务
     *
     * @return
     * @throws Exception
     */
    @Bean
    public ConfigService nacosConfigService() throws Exception {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        properties.put(PropertyKeyConst.NAMESPACE, namespace);
        properties.put(PropertyKeyConst.USERNAME, username);
        properties.put(PropertyKeyConst.PASSWORD, password);
        return ConfigFactory.createConfigService(properties);
    }

    @Bean
    public Converter<List<DegradeRuleEntity>, String> degradeFlowRuleEntityEncoder() {
        return (source -> JSON.toJSONString(source, true));
    }

    @Bean
    public Converter<String, List<DegradeRuleEntity>> degradeFlowRuleEntityDecoder() {
        return source -> JSON.parseArray(source, DegradeRuleEntity.class);
    }

    @Bean
    public Converter<List<ParamFlowRuleEntity>, String> paramFlowRuleEntityEncoder() {
        return (source -> JSON.toJSONString(source, true));
    }

    @Bean
    public Converter<String, List<ParamFlowRuleEntity>> paramFlowRuleEntityDecoder() {
        return source -> JSON.parseArray(source, ParamFlowRuleEntity.class);
    }

    @Bean
    public Converter<List<AuthorityRuleEntity>, String> authorityRuleEntityEncoder() {
        return (source -> JSON.toJSONString(source, true));
    }

    @Bean
    public Converter<String, List<AuthorityRuleEntity>> authorityRuleEntityDecoder() {
        return source -> JSON.parseArray(source, AuthorityRuleEntity.class);
    }

    @Bean
    public Converter<List<SystemRuleEntity>, String> systemRuleEntityEncoder() {
        return (source -> JSON.toJSONString(source, true));
    }

    @Bean
    public Converter<String, List<SystemRuleEntity>> systemRuleEntityDecoder() {
        return source -> JSON.parseArray(source, SystemRuleEntity.class);
    }

    @Bean
    public Converter<List<ApiDefinitionEntity>, String> gatewayApiRuleEntityEncoder() {
        return (source -> JSON.toJSONString(source, true));
    }

    @Bean
    public Converter<String, List<ApiDefinitionEntity>> gatewayApiRuleEntityDecoder() {
        return source -> JSON.parseArray(source, ApiDefinitionEntity.class);
    }

    @Bean
    public Converter<List<GatewayFlowRuleEntity>, String> gatewayFlowRuleEntityEncoder() {
        return (source -> JSON.toJSONString(source, true));
    }

    @Bean
    public Converter<String, List<GatewayFlowRuleEntity>> gatewayFlowRuleEntityDecoder() {
        return source -> JSON.parseArray(source, GatewayFlowRuleEntity.class);
    }
}
