package de.fw.passwordsafe.cucumber;

import de.fw.passwordsafe.PasswortverwaltungApp;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = PasswortverwaltungApp.class)
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
