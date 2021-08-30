package de.svi.svis5g.info.cucumber;

import de.svi.svis5g.info.SvisInfoApp;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = SvisInfoApp.class)
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
