package com.NowakArtur97.WorldOfManga.testUtil.generator;

import java.lang.reflect.Method;

import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

public class ReplaceUnderscoresGenerator extends ReplaceUnderscores {

	private final String wordEndingTestName = "should";

	private final String signReplacedInMethodName = "_";

	private final String signReplacingInMethodName = " ";

	@Override
	public String generateDisplayNameForClass(Class<?> testClass) {

		String classMethodName = super.generateDisplayNameForClass(testClass);

		String displayName = addSpacesBetweenWords(classMethodName);

		return displayName;
	}

	@Override
	public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {

		String testMethodName = testMethod.getName();

		int indexOfShouldWord = testMethodName.indexOf(wordEndingTestName);

		String displayName = testMethodName.substring(0, indexOfShouldWord - 1).replace(signReplacedInMethodName, signReplacingInMethodName);

		return displayName;
	}

	private String addSpacesBetweenWords(String classMethodName) {

		int wordLength = classMethodName.length();

		StringBuilder result = new StringBuilder();
		result.append(classMethodName.charAt(0));

		for (int i = 1; i < wordLength; i++) {

			if (Character.isUpperCase(classMethodName.charAt(i))) {

				result.append(' ');
			}

			result.append(classMethodName.charAt(i));
		}

		return result.toString();
	}
}
