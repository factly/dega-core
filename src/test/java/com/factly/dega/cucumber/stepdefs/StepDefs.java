package com.factly.dega.cucumber.stepdefs;

import com.factly.dega.CoreApp;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = CoreApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
