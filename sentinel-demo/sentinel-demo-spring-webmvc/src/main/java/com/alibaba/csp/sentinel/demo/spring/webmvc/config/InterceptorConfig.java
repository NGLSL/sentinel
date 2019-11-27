/*
 * Copyright 1999-2019 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.demo.spring.webmvc.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.SentinelInterceptor;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.SentinelTotalInterceptor;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.config.SentinelWebMvcConfig;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.config.SentinelWebMvcTotalConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Config sentinel interceptor
 *
 * @author kaizi2009
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //Add sentinel interceptor
        addSpringMvcInterceptor(registry);

        //If you want to sentinel the total flow, you can add total interceptor
        addSpringMvcTotalInterceptor(registry);
    }

    private void addSpringMvcInterceptor(InterceptorRegistry registry) {
        //Config
        SentinelWebMvcConfig config = new SentinelWebMvcConfig();

        config.setBlockExceptionHandler((request, response, e) -> {
            //Depending on your situation, you can choose to process or throw
            boolean needThrow = true;
            if (needThrow) {
                throw e;
            } else {
                //Write string or json string;
                response.getWriter().write("Blocked by sentinel");
            }
        });

        //Custom configuration if necessary
        config.setHttpMethodSpecify(true);
        config.setOriginParser(request -> request.getHeader("S-user"));

        //Add sentinel interceptor
        registry.addInterceptor(new SentinelInterceptor(config)).addPathPatterns("/**");
    }

    private void addSpringMvcTotalInterceptor(InterceptorRegistry registry) {
        //Config
        SentinelWebMvcTotalConfig config = new SentinelWebMvcTotalConfig();

        //Custom configuration if necessary
        config.setRequestAttributeName("my_sentinel_spring_mvc_total_entity_container");
        config.setTotalResourceName("my-spring-mvc-total-url-request");

        //Add sentinel interceptor
        registry.addInterceptor(new SentinelTotalInterceptor(config)).addPathPatterns("/**");
    }
}