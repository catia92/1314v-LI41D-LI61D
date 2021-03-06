package isel.mpd.binding;

import static isel.mpd.typesystem.Primitives.wrap;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class FieldsBinder extends MemberBinder {

	private <T> Field getField(T instance, String key, Object value) {
		Class<?> valueClass = value == null ? null : value.getClass();
		for (Field f : getAllFields(instance.getClass())) {
			if (f.getName().equals(key)
					&& wrap(f.getType()).isAssignableFrom(valueClass)) {
				f.setAccessible(true);
				return f;
			}
		}
		return null;
	}

	public FieldsBinder() {

	}

	static Field[] getAllFields(Class<?> c) {
		ArrayList<Field> allFields = new ArrayList<>();

		while (c != Object.class) {
			Field[] fields = c.getDeclaredFields();
			allFields.addAll(Arrays.asList(fields));

			c = c.getSuperclass();
		}

		return allFields.toArray(new Field[] {});
	}

	@Override
	protected <T> boolean bindMemberInternal(T newT, String key, Object value) {
		Field field = getField(newT, key, value);
		Class<?> valueClass = value == null ? null : value.getClass();
		if (field != null) {
			if (wrap(field.getType()).isAssignableFrom(valueClass)) {
				try {
					field.setAccessible(true);
					field.set(newT, value);
					return true;
				} catch (IllegalArgumentException | IllegalAccessException ex) {
					throw new RuntimeException(ex);
				}
			}
		}
		return false;

	}

	/* (non-Javadoc)
	 * @see isel.mpd.binding.MemberBinder#getMember(java.lang.Class, java.lang.String)
	 */
	@Override
	protected <T> AccessibleObject getMember(Class<T> targetClass, String key) {
		return null;
	}

}
