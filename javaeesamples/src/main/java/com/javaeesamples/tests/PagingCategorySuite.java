package com.javaeesamples.tests;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Categories.class)
@IncludeCategory(PagingCategory.class)
@SuiteClasses({ CustomerServiceTests.class, EmployeeServiceTests.class })
public class PagingCategorySuite {
}
