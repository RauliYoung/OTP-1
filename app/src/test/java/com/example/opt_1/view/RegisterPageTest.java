package com.example.opt_1.view;

import static org.mockito.Mockito.verify;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

@RunWith(BlockJUnit4ClassRunner.class)
public class RegisterPageTest extends TestCase {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock private RegisterPage registerpage;

    @Before
    public void testOnCreate() {
        MockitoAnnotations.openMocks(this);
       registerpage = new RegisterPage();
    }

    @Test
    public void testCheckValidCharacterLenght() {
    }

    @Test
    public void testCheckPasswordLength() {

        String password = "TestPass54";
        verify(registerpage).checkPasswordLength(password);
        Mockito.when(registerpage.checkPasswordLength(password).equals(false)).thenReturn(true);
    }
}