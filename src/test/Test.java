package test;

import lib.jdk22.*;

public class Test {
    private final CJLib c = new CJLib();

    public Test() {
        rands();
    }

    void rands() {
        c.cout("random num is "+c.srand(1,10));
    }
}
