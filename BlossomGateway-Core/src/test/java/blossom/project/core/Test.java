package blossom.project.core;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author linqi
 * @version 1.0.0
 * @description
 */

public class Test {
    public static final AtomicInteger ai = new AtomicInteger(10);

    public static void main(String[] args) {
        //System.out.println(ai);
        //System.out.println(ai.incrementAndGet());
        //
        //String protocol = null;
        //Assert.isNull(protocol, "protocal can not be null!");
        // new GatewayContext.GatewayContextBuilder().setNettyContext(null)
        //         .setRule(new Rule())
        //        .build();

        Properties properties = System.getProperties();
        System.out.println(properties);
    }
}