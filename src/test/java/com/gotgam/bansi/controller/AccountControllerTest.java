package com.gotgam.bansi.controller;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AccountController.class)
@WebAppConfiguration
public class AccountControllerTest {
}
