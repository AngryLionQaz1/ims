package com.snow.ims;

import com.snow.ims.common.pool.StandardThreadExecutor;
import org.junit.Test;

public class sx {


    @Test
    public void test(){


        StandardThreadExecutor  pool=new StandardThreadExecutor();
        pool.execute(()->System.out.println("ssssssssssss"));



    }



}
