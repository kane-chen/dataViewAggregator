package cn.kane.web.view.aggregator.util.groovy.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractFieldAnnotationHandler<A extends Annotation> implements AnnotationHandler {

	@Override
	public void handle(Object instance) throws Exception {
		this.export(instance);
	}

	public <T> T export(T t) throws Exception {
		if (null == t) {
			return null;
		}
		Field[] fields = t.getClass().getDeclaredFields();
		Map<Field, A> hsfAnnoFields = getFieldsWithintheAnno(fields);
		if (null == hsfAnnoFields || hsfAnnoFields.isEmpty()) {
			return t;
		}
		for (Field field : hsfAnnoFields.keySet()) {
			A anno = hsfAnnoFields.get(field);
			t = exportFieldValue(t, field, anno);
		}
		return t;
	}

	private <T> T exportFieldValue(T t, Field field, A anno) throws Exception {
		field.setAccessible(true);
		Object value = null;
		value = this.getValueByAnnotation(anno);
		field.set(t, value);
		return t;
	}

	protected abstract Object getValueByAnnotation(A anno);

	protected abstract Class<A> getSpecialClass();

	@SuppressWarnings("unchecked")
	private Map<Field, A> getFieldsWithintheAnno(Field[] fields) {
		if (null == fields) {
			return null;
		}
		Map<Field, A> targetFieldAnnoMapping = new HashMap<Field, A>();
		for (Field field : fields) {
			Annotation[] annotations = field.getDeclaredAnnotations();
			if (null == annotations) {
				break;
			}
			for (Annotation anno : annotations) {
				if (this.getSpecialClass().isInstance(anno)) {
					targetFieldAnnoMapping.put(field, (A) anno);
					break;
				}
			}
		}
		return targetFieldAnnoMapping;
	}
}
