package blossom.project.core.filter.gray;

import blossom.project.core.context.GatewayContext;
import blossom.project.core.filter.Filter;
import blossom.project.core.filter.FilterAspect;
import lombok.extern.slf4j.Slf4j;

import static blossom.project.common.constant.FilterConst.*;

/**
 * @author: linqi
 * GrayFilter
 * 灰度发布过滤器
 */

@Slf4j
@FilterAspect(id = GRAY_FILTER_ID,
        name = GRAY_FILTER_NAME,
        order = GRAY_FILTER_ORDER)
public class GrayFilter implements Filter {
    public static final String GRAY = "true";
    @Override
    public void doFilter(GatewayContext ctx) throws Exception {
        //测试灰度功能待时候使用  我们可以手动指定其是否为灰度流量
        String gray = ctx.getRequest().getHeaders().get("gray");
        if (GRAY.equals(gray)) {
            ctx.setGray(true);
            return;
        }
        //选取部分的灰度用户
        String clientIp = ctx.getRequest().getClientIp();
        //等价于对1024取模
        int res = clientIp.hashCode() & (1024 - 1);
        if (res == 1) {
            //1024分之一的概率
            ctx.setGray(true);
        }

    }
}
