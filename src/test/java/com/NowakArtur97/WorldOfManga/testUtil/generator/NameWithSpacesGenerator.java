package com.NowakArtur97.WorldOfManga.testUtil.generator;

import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

import java.lang.reflect.Method;

public class NameWithSpacesGenerator extends ReplaceUnderscores {

    @Override
    public String generateDisplayNameForClass(Class<?> testClass) {

        String classMethodName = super.generateDisplayNameForClass(testClass);

        return addSpacesBetweenWords(classMethodName);
    }

    @Override
    public String generateDisplayNameForNestedClass(Class<?> nestedClass) {

        String nestedClassMethodName = nestedClass.getSimpleName();

        return addSpacesBetweenWords(nestedClassMethodName);
    }

    @Override
    public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {

        String testMethodName = testMethod.getName();

        String wordEndingTestName = "should";
        int indexOfShouldWord = testMethodName.indexOf(wordEndingTestName);

        String signReplacedInMethodName = "_";
        String signReplacingInMethodName = " ";

        return testMethodName.substring(0, indexOfShouldWord - 1).replace(signReplacedInMethodName,
                signReplacingInMethodName);
    }

    private String addSpacesBetweenWords(String className) {

        int wordLength = className.length();

        StringBuilder result = new StringBuilder();
        result.append(className.charAt(0));

        for (int i = 1; i < wordLength; i++) {

            if (isUpperCase(className, i) && isNotUITest(className, i)) {

                result.append(' ');
            }

            result.append(className.charAt(i));
        }

        return result.toString();
    }

    private boolean isNotUITest(String className, int i) {
        return !className.substring(i - 1, i + 1).equals("UI");
    }

    private boolean isUpperCase(String className, int i) {
        return Character.isUpperCase(className.charAt(i));
    }
}
