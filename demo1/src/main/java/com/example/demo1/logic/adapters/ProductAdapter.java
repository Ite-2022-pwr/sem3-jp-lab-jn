package com.example.demo1.logic.adapters;

import com.example.demo1.logic.models.Product;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class ProductAdapter extends TypeAdapter<Product> {
    @Override
    public void write(JsonWriter jsonWriter, Product product) throws IOException {

    }

    @Override
    public Product read(JsonReader reader) throws IOException {

        Product product = new Product();
        reader.beginObject();
        String fieldname = null;

        while (reader.hasNext()) {
            JsonToken token = reader.peek();

            if (token.equals(JsonToken.NAME)) {
                fieldname = reader.nextName();
            }

            if ("name".equals(fieldname)) {
                token = reader.peek();
                product.setName(reader.nextString());
            }

            if ("carbs".equals(fieldname)) {
                token = reader.peek();
                product.setCarbs(reader.nextInt());
            }

            if ("fat".equals(fieldname)) {
                token = reader.peek();
                product.setFat(reader.nextInt());
            }   

            if ("protein".equals(fieldname)) {
                token = reader.peek();
                product.setProtein(reader.nextInt());
            }

            if ("category".equals(fieldname)) {
                token = reader.peek();
                product.setCategory(Product.PRODUCT_CATEGORY.valueOf(reader.nextString()));
            }


        }
        reader.endObject();
        return product;
    }
}
