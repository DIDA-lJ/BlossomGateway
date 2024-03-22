package blossom.project.core.filter;

import blossom.project.core.context.GatewayContext;

/**
 * @author: linqi
 * Filter接口  过滤器顶层接口
 */
public interface Filter {
    void doFilter(GatewayContext ctx) throws  Exception;

    default int getOrder(){
        FilterAspect annotation = this.getClass().getAnnotation(FilterAspect.class);
        if(annotation != null){
            return annotation.order();
        }
        return Integer.MAX_VALUE;
    };
}
