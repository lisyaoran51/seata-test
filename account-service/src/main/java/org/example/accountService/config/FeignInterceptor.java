package org.example.accountService.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {

        template.header(RootContext.KEY_XID, RootContext.getXID());

        log.info("feign req: {}", template.toString());
    }
}
