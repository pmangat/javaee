package com.javaeesamples.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CustomerServiceTests.class, EmployeeServiceTests.class })
public class AllTestsSuite {
}