package com.example.smartpantrymanager.database;

import android.content.Context;

import com.example.smartpantrymanager.model.Recipe;
import com.example.smartpantrymanager.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;


public class RecipeSeeder {

    public static void seedIfEmpty(Context context) {
        RecipeDao dao = AppDatabase.getInstance(context).recipeDao();


        if (dao.getRecipeCount() > 0) {
            return;
        }


        add(dao, "Tomato Omelette",
                "1. Beat the eggs.\n2. Chop the tomato.\n3. Melt the butter in a pan.\n4. Add the tomato and eggs and cook until set.",
                "egg,2,pcs", "tomato,1,pcs", "butter,10,g");

        add(dao, "Cheese Omelette",
                "1. Beat the eggs.\n2. Melt the butter in a pan.\n3. Pour in the eggs.\n4. Add the cheese, fold and cook until golden.",
                "egg,3,pcs", "cheese,50,g", "butter,10,g");

        add(dao, "Scrambled Eggs on Toast",
                "1. Toast the bread.\n2. Melt the butter in a pan.\n3. Add beaten eggs and stir until soft.\n4. Serve on the toast.",
                "egg,2,pcs", "bread,2,pcs", "butter,10,g");

        add(dao, "Cheese Toastie",
                "1. Butter the bread.\n2. Put the cheese between the slices.\n3. Fry in a pan until golden on both sides.",
                "bread,2,pcs", "cheese,50,g", "butter,10,g");

        add(dao, "Pancakes",
                "1. Mix the flour, sugar, egg and milk into a smooth batter.\n2. Pour a little into a hot pan.\n3. Flip when bubbles appear and cook the other side.",
                "flour,200,g", "milk,300,ml", "egg,1,pcs", "sugar,20,g");

        add(dao, "Banana Pancakes",
                "1. Mash the bananas.\n2. Mix with the eggs and flour.\n3. Fry spoonfuls in a pan until golden on both sides.",
                "banana,2,pcs", "egg,2,pcs", "flour,50,g");

        add(dao, "French Toast",
                "1. Mix the eggs and milk in a bowl.\n2. Dip the bread in the mixture.\n3. Fry in butter until golden on both sides.",
                "bread,4,pcs", "egg,2,pcs", "milk,100,ml", "butter,20,g");

        add(dao, "Banana Porridge",
                "1. Heat the oats and milk in a pot.\n2. Stir until thick.\n3. Slice the banana on top and add the sugar.",
                "oats,80,g", "milk,300,ml", "banana,1,pcs", "sugar,10,g");

        add(dao, "Rice and Beans",
                "1. Cook the rice.\n2. Fry the chopped onion in oil.\n3. Add the beans and heat through.\n4. Serve on the rice.",
                "rice,200,g", "beans,200,g", "onion,1,pcs", "oil,15,ml");

        add(dao, "Egg Fried Rice",
                "1. Cook the rice and let it cool.\n2. Fry the chopped onion in oil.\n3. Add the eggs and scramble them.\n4. Mix in the rice and fry for 3 minutes.",
                "rice,200,g", "egg,2,pcs", "onion,1,pcs", "oil,15,ml");

        add(dao, "Chicken and Rice",
                "1. Cut the chicken into pieces.\n2. Fry the onion and chicken in oil until cooked.\n3. Cook the rice separately.\n4. Serve the chicken on the rice.",
                "chicken,300,g", "rice,200,g", "onion,1,pcs", "oil,15,ml");

        add(dao, "Chicken Veggie Stir-fry",
                "1. Slice the chicken, carrots and onion.\n2. Heat the oil in a pan.\n3. Fry the chicken until cooked.\n4. Add the vegetables and fry for 5 minutes.",
                "chicken,300,g", "carrot,2,pcs", "onion,1,pcs", "oil,15,ml");

        add(dao, "Tomato Pasta",
                "1. Boil the pasta.\n2. Fry the onion in oil.\n3. Add chopped tomatoes and cook until soft.\n4. Mix the sauce with the pasta.",
                "pasta,200,g", "tomato,3,pcs", "onion,1,pcs", "oil,15,ml");

        add(dao, "Cheesy Pasta",
                "1. Boil the pasta.\n2. Melt the butter in a pot.\n3. Add the milk and cheese and stir until smooth.\n4. Mix with the pasta.",
                "pasta,200,g", "cheese,100,g", "milk,100,ml", "butter,20,g");

        add(dao, "Tuna Pasta",
                "1. Boil the pasta.\n2. Fry the onion in oil.\n3. Add the tuna and heat through.\n4. Mix with the pasta.",
                "pasta,200,g", "tuna,1,pcs", "onion,1,pcs", "oil,15,ml");

        add(dao, "Mashed Potatoes",
                "1. Peel and boil the potatoes until soft.\n2. Drain the water.\n3. Mash with the butter and milk.",
                "potato,4,pcs", "butter,30,g", "milk,50,ml");

        add(dao, "Fried Potatoes and Onion",
                "1. Slice the potatoes and onion.\n2. Heat the oil in a pan.\n3. Fry until golden and soft.",
                "potato,3,pcs", "onion,1,pcs", "oil,30,ml");

        add(dao, "Vegetable Soup",
                "1. Chop the potatoes, carrots, onion and tomatoes.\n2. Put them in a pot with water.\n3. Simmer for 30 minutes until soft.",
                "potato,2,pcs", "carrot,2,pcs", "onion,1,pcs", "tomato,2,pcs");

        add(dao, "Lentil Curry",
                "1. Fry the onion in oil.\n2. Add the chopped tomatoes.\n3. Add the lentils and water.\n4. Simmer for 25 minutes until soft.",
                "lentil,200,g", "onion,1,pcs", "tomato,2,pcs", "oil,15,ml");

        add(dao, "Beans on Toast",
                "1. Heat the beans in a pot.\n2. Toast and butter the bread.\n3. Pour the beans on top.",
                "beans,200,g", "bread,2,pcs", "butter,10,g");
    }


    private static void add(RecipeDao dao, String name, String steps, String... rows) {
        long recipeId = dao.insertRecipe(new Recipe(name, steps));

        List<RecipeIngredient> list = new ArrayList<>();
        for (String row : rows) {
            String[] part = row.split(",");
            list.add(new RecipeIngredient(
                    (int) recipeId,
                    part[0].trim(),
                    Double.parseDouble(part[1].trim()),
                    part[2].trim()));
        }
        dao.insertIngredients(list);
    }
}