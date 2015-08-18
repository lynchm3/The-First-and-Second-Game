package com.marklynch;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public abstract class CustomizedTypeAdapterFactory<C> implements
		TypeAdapterFactory {
	private final Class<C> customizedClass;

	public CustomizedTypeAdapterFactory(Class<C> customizedClass) {
		this.customizedClass = customizedClass;
	}

	@Override
	@SuppressWarnings("unchecked")
	// we use a runtime check to guarantee that 'C' and 'T' are equal
	public final <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
		return type.getRawType() == customizedClass ? (TypeAdapter<T>) customizeMyClassAdapter(
				gson, (TypeToken<C>) type) : null;
	}

	private TypeAdapter<C> customizeMyClassAdapter(Gson gson, TypeToken<C> type) {
		final TypeAdapter<C> delegate = gson.getDelegateAdapter(this, type);
		final TypeAdapter<JsonElement> elementAdapter = gson
				.getAdapter(JsonElement.class);
		return new TypeAdapter<C>() {
			@Override
			public void write(JsonWriter out, C value) throws IOException {
				beforeWrite(value);
				JsonElement tree = delegate.toJsonTree(value);
				elementAdapter.write(out, tree);
			}

			@Override
			public C read(JsonReader in) throws IOException {
				return afterRead(delegate.fromJsonTree(elementAdapter.read(in)));
			}
		};
	}

	/**
	 * Override this to muck with {@code toSerialize} before it is written to
	 * the outgoing JSON stream.
	 * 
	 * @return
	 */
	protected abstract void beforeWrite(C c);

	/**
	 * Override this to muck with {@code deserialized} before it parsed into the
	 * application type.
	 */
	protected abstract C afterRead(C c);
}