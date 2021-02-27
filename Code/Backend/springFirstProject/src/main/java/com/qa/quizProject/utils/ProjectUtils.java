package com.qa.quizProject.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.beans.BeanUtils.copyProperties;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectUtils {

	public static void mergeNotNull(Object source, Object target) {
		copyProperties(source, target, getNullPropertyNames(source));
	}

	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper wrappedSourceObject = new BeanWrapperImpl(source);
		
		Set<String> propertyNames = new HashSet<>();
		for (PropertyDescriptor propertyDescriptors : wrappedSourceObject.getPropertyDescriptors()) {
            if (wrappedSourceObject.getPropertyValue(propertyDescriptors.getName()) == null)
                propertyNames.add(propertyDescriptors.getName());
        }
        return propertyNames.toArray(new String[propertyNames.size()]);
	}
	
}
