package com.dh.rpc.register;

import org.testng.annotations.Test;

@Test
public class RegisterHandlerTest {


    @Test
    public void testScannerClass() {
        RegisterHandler handler = new RegisterHandler();
        handler.scannerClass("com.dh.rpc.register");
    }


}
