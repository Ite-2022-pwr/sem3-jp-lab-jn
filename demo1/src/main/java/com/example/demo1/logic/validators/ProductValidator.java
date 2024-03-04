package com.example.demo1.logic.validators;

import com.example.demo1.logic.FileHandler;
import com.example.demo1.logic.models.Product;

import java.io.IOException;
import java.text.ParseException;

public class ProductValidator {

    public static Product parse(String fat, String protein, String carbs, String name, Product.PRODUCT_CATEGORY category, FileHandler Fh) throws IOException, ParseException {

        if(name.equals("")){
            throw new ParseException("Nie podano nazwy produktu",0);
        }

        Product[] products = Fh.readProductFile();
        for(Product product : products){
            if(product.getName().equals(name)){
                throw new ParseException("Taki produkt już istnieje, podaj inną nazwę",0);
            }
        }

        if(fat.equals("")){
            throw new ParseException("Nie podano ilości tłuszczu",0);
        }

        int fatParsed;
        try{
            fatParsed = Integer.parseInt(fat);

            if(fatParsed < 0){
                throw new ParseException("Ilość tłuszczów nie jest liczbą dodatnią",0);
            }
        } catch (NumberFormatException e){
            throw new ParseException("Ilość tłuszczów nie jest liczbą",0);
        }

        int proteinParsed;
        try{
            proteinParsed = Integer.parseInt(protein);

            if(proteinParsed  < 0){
                throw new ParseException("Ilość białka nie jest liczbą dodatnią",0);
            }
        } catch (NumberFormatException e){
            throw new ParseException("Ilość białka nie jest liczbą",0);
        }


        int carbsParsed;
        try{
            carbsParsed = Integer.parseInt(carbs);

            if(carbsParsed < 0){
                throw new ParseException("Ilość węglowodanów nie jest liczbą dodatnią",0);
            }
        } catch (NumberFormatException e){
            throw new ParseException("Ilość węglowodanów nie jest liczbą",0);
        }

        return new Product(name,carbsParsed,fatParsed,proteinParsed,category);
    }


    public static Product parse(String fat, String protein, String carbs, String name, Product.PRODUCT_CATEGORY category, FileHandler Fh, boolean checkUniqueName) throws IOException, ParseException {

        if(name.equals("")){
            throw new ParseException("Nie podano nazwy produktu",0);
        }

        if(checkUniqueName){
            Product[] products = Fh.readProductFile();
            for(Product product : products){
                if(product.getName().equals(name)){
                    throw new ParseException("Taki produkt już istnieje, podaj inną nazwę",0);
                }
            }
        }


        if(fat.equals("")){
            throw new ParseException("Nie podano ilości tłuszczu",0);
        }

        int fatParsed;
        try{
            fatParsed = Integer.parseInt(fat);

            if(fatParsed < 0){
                throw new ParseException("Ilość tłuszczów nie jest liczbą dodatnią",0);
            }
        } catch (NumberFormatException e){
            throw new ParseException("Ilość tłuszczów nie jest liczbą",0);
        }

        int proteinParsed;
        try{
            proteinParsed = Integer.parseInt(protein);

            if(proteinParsed  < 0){
                throw new ParseException("Ilość białka nie jest liczbą dodatnią",0);
            }
        } catch (NumberFormatException e){
            throw new ParseException("Ilość białka nie jest liczbą",0);
        }


        int carbsParsed;
        try{
            carbsParsed = Integer.parseInt(carbs);

            if(carbsParsed < 0){
                throw new ParseException("Ilość węglowodanów nie jest liczbą dodatnią",0);
            }
        } catch (NumberFormatException e){
            throw new ParseException("Ilość węglowodanów nie jest liczbą",0);
        }

        return new Product(name,carbsParsed,fatParsed,proteinParsed,category);
    }
}
