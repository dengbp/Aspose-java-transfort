package com.yrnet.transfer.common.log;

import ch.qos.logback.classic.filter.LevelFilter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.spi.FilterReply;
import org.apache.commons.lang3.StringUtils;

/**
 * @author dengbp
 * @ClassName CustomizedFilter
 * @Description TODO
 * @date 10/26/21 11:27 AM
 */
public class CustomizedFilter extends LevelFilter {


    /**
     * Description FilterReply有三种枚举值：
     * DENY：表示不用看后面的过滤器了，这里就给拒绝了，不作记录。
     * NEUTRAL：表示需不需要记录，还需要看后面的过滤器。若所有过滤器返回的全部都是NEUTRAL，那么需要记录日志。
     * ACCEPT：表示不用看后面的过滤器了，这里就给直接同意了，需要记录。
     * @param event
     * @return ch.qos.logback.core.spi.FilterReply
     * @Author dengbp
     * @Date 11:31 AM 10/26/21
     **/

    @Override
    public FilterReply decide(ILoggingEvent event) {
        String filterContext = "io_grpc_netty_shaded_netty_tcnative_osx_x86_64";
        if (StringUtils.isBlank(event.getMessage()) && event.getMessage().contains(filterContext)){
            return FilterReply.DENY;
        }

        return FilterReply.NEUTRAL;
    }
}
