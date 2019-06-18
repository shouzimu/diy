package com.dh.rpc.register;

import org.testng.annotations.Test;

@Test
public class RegisterHandlerTest {


    @Test
    public void testScannerClass() {
        RegisterHandler handler = new RegisterHandler();
        handler.scannerClass(new String[]{"com.dh.rpc.register"});
    }


}
