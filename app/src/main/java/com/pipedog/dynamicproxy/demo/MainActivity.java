package com.pipedog.dynamicproxy.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.pipedog.dynamicproxy.demo.dyc.ConsoleLogger;
import com.pipedog.dynamicproxy.demo.dyc.Logger;
import com.pipedog.dycproxy.DynamicProxy;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test();
    }


    private void test() {
        DynamicProxy proxy = new DynamicProxy.Builder()
                .target(new ConsoleLogger())
                .addProcessor(new ConsoleLogger.ProcessorImpl())
                .build();
        Logger logger = proxy.getProxy();
        logger.log("This is log message.");
    }

}