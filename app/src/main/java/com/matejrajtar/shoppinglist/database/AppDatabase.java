package com.matejrajtar.shoppinglist.database;

import android.content.Context;

import androidx.annotation.StringRes;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.matejrajtar.shoppinglist.R;
import com.matejrajtar.shoppinglist.model.Category;
import com.matejrajtar.shoppinglist.model.Product;
import com.matejrajtar.shoppinglist.tasks.migration.Migration;

import java.util.HashMap;

@Database(entities = {Product.class, Category.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CategoryDao categoryDao();

    public abstract ProductDao productDao();

    public static AppDatabase instance(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "database").allowMainThreadQueries().build();
    }

    public void initialize(Context context, HashMap<Long, String> hashMap, Migration.OnMigrationDone callback) {
        Category fruitsAndVegetables = new Category(context.getString(R.string.category_fruitsAndVegetables));
        Category bakery = new Category(context.getString(R.string.category_bakery));
        Category dairyAndCooled = new Category(context.getString(R.string.category_dairyAndCooled));
        Category sweetPantry = new Category(context.getString(R.string.category_sweetPantry));
        Category savoryPantry = new Category(context.getString(R.string.category_savoryPantry));
        Category drinks = new Category(context.getString(R.string.category_drinks));
        Category seasoning = new Category(context.getString(R.string.category_seasoning));
        Category household = new Category(context.getString(R.string.category_household));

        Product aluminium_foil = product(context, R.string.product_aluminium_foil, household, String.valueOf(R.drawable.aluminium_foil), hashMap.get(Long.parseLong("2")));
        Product apples = product(context, R.string.product_apples, fruitsAndVegetables, String.valueOf(R.drawable.apples), hashMap.get(Long.parseLong("1")));
        Product asparagus = product(context, R.string.product_asparagus, fruitsAndVegetables, String.valueOf(R.drawable.asparagus), hashMap.get(Long.parseLong("4")));
        Product aubergine_eggplant = product(context, R.string.product_aubergine_eggplant, fruitsAndVegetables, String.valueOf(R.drawable.aubergine_eggplant), hashMap.get(Long.parseLong("5")));
        Product avocado = product(context, R.string.product_avocado, fruitsAndVegetables, String.valueOf(R.drawable.avocado), hashMap.get(Long.parseLong("6")));
        Product ayran = product(context, R.string.product_ayran, dairyAndCooled, String.valueOf(R.drawable.ayran), hashMap.get(Long.parseLong("7")));
        Product bacon = product(context, R.string.product_bacon, dairyAndCooled, String.valueOf(R.drawable.bacon), hashMap.get(Long.parseLong("8")));
        Product bagels = product(context, R.string.product_bagels, bakery, String.valueOf(R.drawable.bagels), hashMap.get(Long.parseLong("9")));
        Product homebake_bagels = product(context, R.string.product_homebake_bagels, bakery, String.valueOf(R.drawable.homebake_bagels), hashMap.get(Long.parseLong("3")));
        Product baking_paper = product(context, R.string.product_baking_paper, household, String.valueOf(R.drawable.baking_paper), hashMap.get(Long.parseLong("10")));
        Product bamboo_shoots = product(context, R.string.product_bamboo_shoots, savoryPantry, String.valueOf(R.drawable.bamboo_shoots), hashMap.get(Long.parseLong("11")));
        Product banana_chips = product(context, R.string.product_banana_chips, sweetPantry, String.valueOf(R.drawable.banana_chips), hashMap.get(Long.parseLong("12")));
        Product bananas = product(context, R.string.product_bananas, fruitsAndVegetables, String.valueOf(R.drawable.bananas), hashMap.get(Long.parseLong("13")));
        Product basil = product(context, R.string.product_basil, seasoning, String.valueOf(R.drawable.basil), hashMap.get(Long.parseLong("14")));
        Product bay_leaves = product(context, R.string.product_bay_leaves, seasoning, String.valueOf(R.drawable.bay_leaves), hashMap.get(Long.parseLong("15")));
        Product beer = product(context, R.string.product_beer, drinks, String.valueOf(R.drawable.beer), hashMap.get(Long.parseLong("16")));
        Product beetroot = product(context, R.string.product_beetroot, fruitsAndVegetables, String.valueOf(R.drawable.beetroot), hashMap.get(Long.parseLong("17")));
        Product bell_peppers = product(context, R.string.product_bell_peppers, fruitsAndVegetables, String.valueOf(R.drawable.bell_peppers), hashMap.get(Long.parseLong("18")));
        Product berries = product(context, R.string.product_berries, fruitsAndVegetables, String.valueOf(R.drawable.berries), hashMap.get(Long.parseLong("19")));
        Product biscuits = product(context, R.string.product_biscuits, sweetPantry, String.valueOf(R.drawable.biscuits), hashMap.get(Long.parseLong("20")));
        Product black_pepper = product(context, R.string.product_black_pepper, seasoning, String.valueOf(R.drawable.black_pepper), hashMap.get(Long.parseLong("21")));
        Product bouillon = product(context, R.string.product_bouillon, seasoning, String.valueOf(R.drawable.bouillon), hashMap.get(Long.parseLong("22")));
        Product bread = product(context, R.string.product_bread, bakery, String.valueOf(R.drawable.bread), hashMap.get(Long.parseLong("23")));
        Product broccoli = product(context, R.string.product_broccoli, fruitsAndVegetables, String.valueOf(R.drawable.broccoli), hashMap.get(Long.parseLong("24")));
        Product butter = product(context, R.string.product_butter, dairyAndCooled, String.valueOf(R.drawable.butter), hashMap.get(Long.parseLong("25")));
        Product buttermilk = product(context, R.string.product_buttermilk, dairyAndCooled, String.valueOf(R.drawable.buttermilk), hashMap.get(Long.parseLong("26")));
        Product camembert = product(context, R.string.product_camembert, dairyAndCooled, String.valueOf(R.drawable.camembert), hashMap.get(Long.parseLong("27")));
        Product canned_beans = product(context, R.string.product_canned_beans, savoryPantry, String.valueOf(R.drawable.canned_beans), hashMap.get(Long.parseLong("28")));
        Product canned_corn = product(context, R.string.product_canned_corn, savoryPantry, String.valueOf(R.drawable.canned_corn), hashMap.get(Long.parseLong("29")));
        Product canned_tomatoes = product(context, R.string.product_canned_tomatoes, savoryPantry, String.valueOf(R.drawable.canned_tomatoes), hashMap.get(Long.parseLong("30")));
        Product carrots = product(context, R.string.product_carrots, fruitsAndVegetables, String.valueOf(R.drawable.carrots), hashMap.get(Long.parseLong("31")));
        Product cauliflower = product(context, R.string.product_cauliflower, fruitsAndVegetables, String.valueOf(R.drawable.cauliflower), hashMap.get(Long.parseLong("32")));
        Product celery = product(context, R.string.product_celery, fruitsAndVegetables, String.valueOf(R.drawable.celery), hashMap.get(Long.parseLong("33")));
        Product cheese = product(context, R.string.product_cheese, dairyAndCooled, String.valueOf(R.drawable.cheese), hashMap.get(Long.parseLong("34")));
        Product cherry_tomatoes = product(context, R.string.product_cherry_tomatoes, fruitsAndVegetables, String.valueOf(R.drawable.cherry_tomatoes), hashMap.get(Long.parseLong("35")));
        Product chewing_gum = product(context, R.string.product_chewing_gum, household, String.valueOf(R.drawable.chewing_gum), hashMap.get(Long.parseLong("36")));
        Product chia_seeds = product(context, R.string.product_chia_seeds, sweetPantry, String.valueOf(R.drawable.chia_seeds), hashMap.get(Long.parseLong("37")));
        Product chicken = product(context, R.string.product_chicken, dairyAndCooled, String.valueOf(R.drawable.chicken), hashMap.get(Long.parseLong("38")));
        Product chickpeas = product(context, R.string.product_chickpeas, savoryPantry, String.valueOf(R.drawable.chickpeas), hashMap.get(Long.parseLong("39")));
        Product chocolate = product(context, R.string.product_chocolate, sweetPantry, String.valueOf(R.drawable.chocolate), hashMap.get(Long.parseLong("40")));
        Product cider = product(context, R.string.product_cider, drinks, String.valueOf(R.drawable.cider), hashMap.get(Long.parseLong("41")));
        Product cinnamon = product(context, R.string.product_cinnamon, seasoning, String.valueOf(R.drawable.cinnamon), hashMap.get(Long.parseLong("42")));
        Product cocoa_powder = product(context, R.string.product_cocoa_powder, sweetPantry, String.valueOf(R.drawable.cocoa_powder), hashMap.get(Long.parseLong("43")));
        Product coconut_milk = product(context, R.string.product_coconut_milk, savoryPantry, String.valueOf(R.drawable.coconut_milk), hashMap.get(Long.parseLong("44")));
        Product coffee = product(context, R.string.product_coffee, savoryPantry, String.valueOf(R.drawable.coffee), hashMap.get(Long.parseLong("45")));
        Product coke = product(context, R.string.product_coke, drinks, String.valueOf(R.drawable.coke), hashMap.get(Long.parseLong("46")));
        Product coriander = product(context, R.string.product_coriander, seasoning, String.valueOf(R.drawable.coriander), hashMap.get(Long.parseLong("47")));
        Product cranberries = product(context, R.string.product_cranberries, sweetPantry, String.valueOf(R.drawable.cranberries), hashMap.get(Long.parseLong("48")));
        Product cream = product(context, R.string.product_cream, dairyAndCooled, String.valueOf(R.drawable.cream), hashMap.get(Long.parseLong("49")));
        Product croutons = product(context, R.string.product_croutons, savoryPantry, String.valueOf(R.drawable.croutons), hashMap.get(Long.parseLong("50")));
        Product cucumber = product(context, R.string.product_cucumber, fruitsAndVegetables, String.valueOf(R.drawable.cucumber), hashMap.get(Long.parseLong("51")));
        Product cumin = product(context, R.string.product_cumin, seasoning, String.valueOf(R.drawable.cumin), hashMap.get(Long.parseLong("52")));
        Product curry_paste = product(context, R.string.product_curry_paste, savoryPantry, String.valueOf(R.drawable.curry_paste), hashMap.get(Long.parseLong("53")));
        Product danish_bread = product(context, R.string.product_danish_bread, bakery, String.valueOf(R.drawable.danish_bread), hashMap.get(Long.parseLong("54")));
        Product deodorant = product(context, R.string.product_deodorant, household, String.valueOf(R.drawable.deodorant), hashMap.get(Long.parseLong("55")));
        Product detergent = product(context, R.string.product_detergent, household, String.valueOf(R.drawable.detergent), hashMap.get(Long.parseLong("56")));
        Product dill = product(context, R.string.product_dill, seasoning, String.valueOf(R.drawable.dill), hashMap.get(Long.parseLong("57")));
        Product dish_soap = product(context, R.string.product_dish_soap, household, String.valueOf(R.drawable.dish_soap), hashMap.get(Long.parseLong("58")));
        Product dish_sponge = product(context, R.string.product_dish_sponge, household, String.valueOf(R.drawable.dish_sponge), hashMap.get(Long.parseLong("59")));
        Product dishwasher_salt = product(context, R.string.product_dishwasher_salt, household, String.valueOf(R.drawable.dishwasher_salt), hashMap.get(Long.parseLong("60")));
        Product domestos = product(context, R.string.product_domestos, household, String.valueOf(R.drawable.domestos), hashMap.get(Long.parseLong("61")));
        Product dough = product(context, R.string.product_dough, dairyAndCooled, String.valueOf(R.drawable.dough), hashMap.get(Long.parseLong("62")));
        Product dried_mushrooms = product(context, R.string.product_dried_mushrooms, savoryPantry, String.valueOf(R.drawable.dried_mushrooms), hashMap.get(Long.parseLong("63")));
        Product dried_tomatoes = product(context, R.string.product_dried_tomatoes, dairyAndCooled, String.valueOf(R.drawable.dried_tomatoes), hashMap.get(Long.parseLong("64")));
        Product eggs = product(context, R.string.product_eggs, dairyAndCooled, String.valueOf(R.drawable.eggs), hashMap.get(Long.parseLong("65")));
        Product fish = product(context, R.string.product_fish, dairyAndCooled, String.valueOf(R.drawable.fish), hashMap.get(Long.parseLong("66")));
        Product fish_fingers = product(context, R.string.product_fish_fingers, dairyAndCooled, String.valueOf(R.drawable.fish_fingers), hashMap.get(Long.parseLong("67")));
        Product floor_cloths = product(context, R.string.product_floor_cloths, household, String.valueOf(R.drawable.floor_cloths), hashMap.get(Long.parseLong("68")));
        Product flour = product(context, R.string.product_flour, savoryPantry, String.valueOf(R.drawable.flour), hashMap.get(Long.parseLong("69")));
        Product frozen_fruits = product(context, R.string.product_frozen_fruits, dairyAndCooled, String.valueOf(R.drawable.frozen_fruits), hashMap.get(Long.parseLong("70")));
        Product frozen_peas = product(context, R.string.product_frozen_peas, dairyAndCooled, String.valueOf(R.drawable.frozen_peas), hashMap.get(Long.parseLong("71")));
        Product frozen_veggies = product(context, R.string.product_frozen_veggies, dairyAndCooled, String.valueOf(R.drawable.frozen_veggies), hashMap.get(Long.parseLong("72")));
        Product fruit = product(context, R.string.product_fruit, fruitsAndVegetables, String.valueOf(R.drawable.fruit), hashMap.get(Long.parseLong("73")));
        Product garlic_flakes = product(context, R.string.product_garlic_flakes, seasoning, String.valueOf(R.drawable.garlic_flakes), hashMap.get(Long.parseLong("74")));
        Product garlic = product(context, R.string.product_garlic, fruitsAndVegetables, String.valueOf(R.drawable.garlic), hashMap.get(Long.parseLong("75")));
        Product ginger = product(context, R.string.product_ginger, fruitsAndVegetables, String.valueOf(R.drawable.ginger), hashMap.get(Long.parseLong("76")));
        Product gnocchi = product(context, R.string.product_gnocchi, savoryPantry, String.valueOf(R.drawable.gnocchi), hashMap.get(Long.parseLong("77")));
        Product grapes = product(context, R.string.product_grapes, fruitsAndVegetables, String.valueOf(R.drawable.grapes), hashMap.get(Long.parseLong("78")));
        Product green_beans = product(context, R.string.product_green_beans, fruitsAndVegetables, String.valueOf(R.drawable.green_beans), hashMap.get(Long.parseLong("79")));
        Product minced_meat = product(context, R.string.product_minced_meat, dairyAndCooled, String.valueOf(R.drawable.minced_meat), hashMap.get(Long.parseLong("80")));
        Product ham = product(context, R.string.product_ham, dairyAndCooled, String.valueOf(R.drawable.ham), hashMap.get(Long.parseLong("81")));
        Product hollandaise_sauce = product(context, R.string.product_hollandaise_sauce, savoryPantry, String.valueOf(R.drawable.hollandaise_sauce), hashMap.get(Long.parseLong("82")));
        Product honey = product(context, R.string.product_honey, sweetPantry, String.valueOf(R.drawable.honey), hashMap.get(Long.parseLong("83")));
        Product horseradish = product(context, R.string.product_horseradish, savoryPantry, String.valueOf(R.drawable.horseradish), hashMap.get(Long.parseLong("84")));
        Product hummus = product(context, R.string.product_hummus, dairyAndCooled, String.valueOf(R.drawable.hummus), hashMap.get(Long.parseLong("85")));
        Product ice_salad = product(context, R.string.product_ice_salad, fruitsAndVegetables, String.valueOf(R.drawable.ice_salad), hashMap.get(Long.parseLong("86")));
        Product important_stuff = product(context, R.string.product_important_stuff, household, String.valueOf(R.drawable.important_stuff), hashMap.get(Long.parseLong("87")));
        Product instant_mashed_potatoes = product(context, R.string.product_instant_mashed_potatoes, savoryPantry, String.valueOf(R.drawable.instant_mashed_potatoes), hashMap.get(Long.parseLong("88")));
        Product instant_soup = product(context, R.string.product_instant_soup, savoryPantry, String.valueOf(R.drawable.instant_soup), hashMap.get(Long.parseLong("89")));
        Product interdental_toothbrush = product(context, R.string.product_interdental_toothbrush, household, String.valueOf(R.drawable.interdental_toothbrush), hashMap.get(Long.parseLong("90")));
        Product juice = product(context, R.string.product_juice, drinks, String.valueOf(R.drawable.juice), hashMap.get(Long.parseLong("91")));
        Product kaki = product(context, R.string.product_kaki, fruitsAndVegetables, String.valueOf(R.drawable.kaki), hashMap.get(Long.parseLong("92")));
        Product ketchup = product(context, R.string.product_ketchup, savoryPantry, String.valueOf(R.drawable.ketchup), hashMap.get(Long.parseLong("93")));
        Product knedla = product(context, R.string.product_knedla, dairyAndCooled, String.valueOf(R.drawable.knedla), hashMap.get(Long.parseLong("94")));
        Product kohlrabi = product(context, R.string.product_kohlrabi, fruitsAndVegetables, String.valueOf(R.drawable.kohlrabi), hashMap.get(Long.parseLong("95")));
        Product lemon = product(context, R.string.product_lemon, fruitsAndVegetables, String.valueOf(R.drawable.lemon), hashMap.get(Long.parseLong("96")));
        Product lentils = product(context, R.string.product_lentils, savoryPantry, String.valueOf(R.drawable.lentils), hashMap.get(Long.parseLong("97")));
        Product lime = product(context, R.string.product_lime, fruitsAndVegetables, String.valueOf(R.drawable.lime), hashMap.get(Long.parseLong("98")));
        Product liver = product(context, R.string.product_liver, dairyAndCooled, String.valueOf(R.drawable.liver), hashMap.get(Long.parseLong("99")));
        Product mandarins = product(context, R.string.product_mandarins, fruitsAndVegetables, String.valueOf(R.drawable.mandarins), hashMap.get(Long.parseLong("100")));
        Product margarin = product(context, R.string.product_margarin, dairyAndCooled, String.valueOf(R.drawable.margarin), hashMap.get(Long.parseLong("101")));
        Product mayonnaise = product(context, R.string.product_mayonnaise, dairyAndCooled, String.valueOf(R.drawable.mayonnaise), hashMap.get(Long.parseLong("102")));
        Product meat = product(context, R.string.product_meat, dairyAndCooled, String.valueOf(R.drawable.meat), hashMap.get(Long.parseLong("103")));
        Product melon = product(context, R.string.product_melon, fruitsAndVegetables, String.valueOf(R.drawable.melon), hashMap.get(Long.parseLong("104")));
        Product mie_noodles = product(context, R.string.product_mie_noodles, savoryPantry, String.valueOf(R.drawable.mie_noodles), hashMap.get(Long.parseLong("105")));
        Product milk_rice = product(context, R.string.product_milk_rice, dairyAndCooled, String.valueOf(R.drawable.milk_rice), hashMap.get(Long.parseLong("106")));
        Product milk = product(context, R.string.product_milk, dairyAndCooled, String.valueOf(R.drawable.milk), hashMap.get(Long.parseLong("107")));
        Product mineral_water = product(context, R.string.product_mineral_water, drinks, String.valueOf(R.drawable.mineral_water), hashMap.get(Long.parseLong("108")));
        Product mouth_wash = product(context, R.string.product_mouth_wash, household, String.valueOf(R.drawable.mouth_wash), hashMap.get(Long.parseLong("109")));
        Product mozzarella = product(context, R.string.product_mozzarella, dairyAndCooled, String.valueOf(R.drawable.mozzarella), hashMap.get(Long.parseLong("110")));
        Product muesli_bars = product(context, R.string.product_muesli_bars, sweetPantry, String.valueOf(R.drawable.muesli_bars), hashMap.get(Long.parseLong("111")));
        Product multivitamin = product(context, R.string.product_multivitamin, household, String.valueOf(R.drawable.multivitamin), hashMap.get(Long.parseLong("112")));
        Product mushrooms = product(context, R.string.product_mushrooms, fruitsAndVegetables, String.valueOf(R.drawable.mushrooms), hashMap.get(Long.parseLong("113")));
        Product mustard = product(context, R.string.product_mustard, savoryPantry, String.valueOf(R.drawable.mustard), hashMap.get(Long.parseLong("114")));
        Product muesli = product(context, R.string.product_muesli, sweetPantry, String.valueOf(R.drawable.muesli), hashMap.get(Long.parseLong("115")));
        Product nuts = product(context, R.string.product_nuts, fruitsAndVegetables, String.valueOf(R.drawable.nuts), hashMap.get(Long.parseLong("116")));
        Product oatmeal = product(context, R.string.product_oatmeal, sweetPantry, String.valueOf(R.drawable.oatmeal), hashMap.get(Long.parseLong("117")));
        Product oil = product(context, R.string.product_oil, savoryPantry, String.valueOf(R.drawable.oil), hashMap.get(Long.parseLong("118")));
        Product olive_oil = product(context, R.string.product_olive_oil, savoryPantry, String.valueOf(R.drawable.olive_oil), hashMap.get(Long.parseLong("119")));
        Product olives = product(context, R.string.product_olives, savoryPantry, String.valueOf(R.drawable.olives), hashMap.get(Long.parseLong("120")));
        Product onions = product(context, R.string.product_onions, fruitsAndVegetables, String.valueOf(R.drawable.onions), hashMap.get(Long.parseLong("121")));
        Product oregano = product(context, R.string.product_oregano, seasoning, String.valueOf(R.drawable.oregano), hashMap.get(Long.parseLong("122")));
        Product paper_towels = product(context, R.string.product_paper_towels, household, String.valueOf(R.drawable.paper_towels), hashMap.get(Long.parseLong("123")));
        Product paprika_seasoning = product(context, R.string.product_paprika_seasoning, seasoning, String.valueOf(R.drawable.paprika_seasoning), hashMap.get(Long.parseLong("124")));
        Product paprika = product(context, R.string.product_paprika, fruitsAndVegetables, String.valueOf(R.drawable.paprika), hashMap.get(Long.parseLong("125")));
        Product parmesan = product(context, R.string.product_parmesan, dairyAndCooled, String.valueOf(R.drawable.parmesan), hashMap.get(Long.parseLong("126")));
        Product parsley = product(context, R.string.product_parsley, seasoning, String.valueOf(R.drawable.parsley), hashMap.get(Long.parseLong("127")));
        Product pasta = product(context, R.string.product_pasta, savoryPantry, String.valueOf(R.drawable.pasta), hashMap.get(Long.parseLong("128")));
        Product pasta_sauce = product(context, R.string.product_pasta_sauce, savoryPantry, String.valueOf(R.drawable.pasta_sauce), hashMap.get(Long.parseLong("129")));
        Product peanut_butter = product(context, R.string.product_peanut_butter, sweetPantry, String.valueOf(R.drawable.peanut_butter), hashMap.get(Long.parseLong("130")));
        Product pesto = product(context, R.string.product_pesto, savoryPantry, String.valueOf(R.drawable.pesto), hashMap.get(Long.parseLong("131")));
        Product pickles = product(context, R.string.product_pickles, savoryPantry, String.valueOf(R.drawable.pickles), hashMap.get(Long.parseLong("132")));
        Product pineapple = product(context, R.string.product_pineapple, fruitsAndVegetables, String.valueOf(R.drawable.pineapple), hashMap.get(Long.parseLong("133")));
        Product pizza = product(context, R.string.product_pizza, dairyAndCooled, String.valueOf(R.drawable.pizza), hashMap.get(Long.parseLong("134")));
        Product plastic_bags = product(context, R.string.product_plastic_bags, household, String.valueOf(R.drawable.plastic_bags), hashMap.get(Long.parseLong("135")));
        Product popcorn = product(context, R.string.product_popcorn, savoryPantry, String.valueOf(R.drawable.popcorn), hashMap.get(Long.parseLong("136")));
        Product poppy_seeds = product(context, R.string.product_poppy_seeds, sweetPantry, String.valueOf(R.drawable.poppy_seeds), hashMap.get(Long.parseLong("137")));
        Product potato_croquetts = product(context, R.string.product_potato_croquetts, dairyAndCooled, String.valueOf(R.drawable.potato_croquetts), hashMap.get(Long.parseLong("138")));
        Product potatoes = product(context, R.string.product_potatoes, fruitsAndVegetables, String.valueOf(R.drawable.potatoes), hashMap.get(Long.parseLong("139")));
        Product pudding = product(context, R.string.product_pudding, sweetPantry, String.valueOf(R.drawable.pudding), hashMap.get(Long.parseLong("140")));
        Product pumpkin = product(context, R.string.product_pumpkin, fruitsAndVegetables, String.valueOf(R.drawable.pumpkin), hashMap.get(Long.parseLong("141")));
        Product pumpkin_seeds = product(context, R.string.product_pumpkin_seeds, savoryPantry, String.valueOf(R.drawable.pumpkin_seeds), hashMap.get(Long.parseLong("142")));
        Product quark = product(context, R.string.product_quark, dairyAndCooled, String.valueOf(R.drawable.quark), hashMap.get(Long.parseLong("143")));
        Product quinoa = product(context, R.string.product_quinoa, savoryPantry, String.valueOf(R.drawable.quinoa), hashMap.get(Long.parseLong("144")));
        Product radler = product(context, R.string.product_radler, drinks, String.valueOf(R.drawable.radler), hashMap.get(Long.parseLong("145")));
        Product raisins = product(context, R.string.product_raisins, sweetPantry, String.valueOf(R.drawable.raisins), hashMap.get(Long.parseLong("146")));
        Product rama = product(context, R.string.product_rama, dairyAndCooled, String.valueOf(R.drawable.rama), hashMap.get(Long.parseLong("147")));
        Product raspberries = product(context, R.string.product_raspberries, fruitsAndVegetables, String.valueOf(R.drawable.raspberries), hashMap.get(Long.parseLong("148")));
        Product red_onions = product(context, R.string.product_red_onions, fruitsAndVegetables, String.valueOf(R.drawable.red_onions), hashMap.get(Long.parseLong("149")));
        Product rice = product(context, R.string.product_rice, savoryPantry, String.valueOf(R.drawable.rice), hashMap.get(Long.parseLong("150")));
        Product rice_cakes = product(context, R.string.product_rice_cakes, bakery, String.valueOf(R.drawable.rice_cakes), hashMap.get(Long.parseLong("151")));
        Product rice_milk = product(context, R.string.product_rice_milk, drinks, String.valueOf(R.drawable.rice_milk), hashMap.get(Long.parseLong("152")));
        Product rice_noodles = product(context, R.string.product_rice_noodles, savoryPantry, String.valueOf(R.drawable.rice_noodles), hashMap.get(Long.parseLong("153")));
        Product rice_wine_vinegar = product(context, R.string.product_rice_wine_vinegar, savoryPantry, String.valueOf(R.drawable.rice_wine_vinegar), hashMap.get(Long.parseLong("154")));
        Product rosemary = product(context, R.string.product_rosemary, seasoning, String.valueOf(R.drawable.rosemary), hashMap.get(Long.parseLong("155")));
        Product rubbish_bin_bags = product(context, R.string.product_rubbish_bin_bags, household, String.valueOf(R.drawable.rubbish_bin_bags), hashMap.get(Long.parseLong("156")));
        Product rukola = product(context, R.string.product_rukola, fruitsAndVegetables, String.valueOf(R.drawable.rukola), hashMap.get(Long.parseLong("157")));
        Product salad = product(context, R.string.product_salad, fruitsAndVegetables, String.valueOf(R.drawable.salad), hashMap.get(Long.parseLong("158")));
        Product salami = product(context, R.string.product_salami, dairyAndCooled, String.valueOf(R.drawable.salami), hashMap.get(Long.parseLong("159")));
        Product salmon = product(context, R.string.product_salmon, dairyAndCooled, String.valueOf(R.drawable.salmon), hashMap.get(Long.parseLong("160")));
        Product salt = product(context, R.string.product_salt, seasoning, String.valueOf(R.drawable.salt), hashMap.get(Long.parseLong("161")));
        Product sausages = product(context, R.string.product_sausages, dairyAndCooled, String.valueOf(R.drawable.sausages), hashMap.get(Long.parseLong("162")));
        Product schnittlauch = product(context, R.string.product_schnittlauch, seasoning, String.valueOf(R.drawable.schnittlauch), hashMap.get(Long.parseLong("163")));
        Product seafood = product(context, R.string.product_seafood, dairyAndCooled, String.valueOf(R.drawable.seafood), hashMap.get(Long.parseLong("164")));
        Product sesame_seeds = product(context, R.string.product_sesame_seeds, savoryPantry, String.valueOf(R.drawable.sesame_seeds), hashMap.get(Long.parseLong("165")));
        Product shampoo = product(context, R.string.product_shampoo, household, String.valueOf(R.drawable.shampoo), hashMap.get(Long.parseLong("166")));
        Product shower_gel = product(context, R.string.product_shower_gel, household, String.valueOf(R.drawable.shower_gel), hashMap.get(Long.parseLong("167")));
        Product shredded_coconut = product(context, R.string.product_shredded_coconut, sweetPantry, String.valueOf(R.drawable.shredded_coconut), hashMap.get(Long.parseLong("168")));
        Product soap = product(context, R.string.product_soap, household, String.valueOf(R.drawable.soap), hashMap.get(Long.parseLong("169")));
        Product softener = product(context, R.string.product_softener, household, String.valueOf(R.drawable.softener), hashMap.get(Long.parseLong("170")));
        Product soup_veggies = product(context, R.string.product_soup_veggies, fruitsAndVegetables, String.valueOf(R.drawable.soup_veggies), hashMap.get(Long.parseLong("171")));
        Product sour_cabbage = product(context, R.string.product_sour_cabbage, fruitsAndVegetables, String.valueOf(R.drawable.sour_cabbage), hashMap.get(Long.parseLong("172")));
        Product sour_cream = product(context, R.string.product_sour_cream, dairyAndCooled, String.valueOf(R.drawable.sour_cream), hashMap.get(Long.parseLong("173")));
        Product soy_sauce = product(context, R.string.product_soy_sauce, savoryPantry, String.valueOf(R.drawable.soy_sauce), hashMap.get(Long.parseLong("174")));
        Product spaghetti = product(context, R.string.product_spaghetti, savoryPantry, String.valueOf(R.drawable.spaghetti), hashMap.get(Long.parseLong("175")));
        Product spinach = product(context, R.string.product_spinach, dairyAndCooled, String.valueOf(R.drawable.spinach), hashMap.get(Long.parseLong("176")));
        Product leek_spring_onion = product(context, R.string.product_leek_spring_onion, fruitsAndVegetables, String.valueOf(R.drawable.leek_spring_onion), hashMap.get(Long.parseLong("177")));
        Product sugar = product(context, R.string.product_sugar, seasoning, String.valueOf(R.drawable.sugar), hashMap.get(Long.parseLong("178")));
        Product sunflower_seeds = product(context, R.string.product_sunflower_seeds, savoryPantry, String.valueOf(R.drawable.sunflower_seeds), hashMap.get(Long.parseLong("179")));
        Product sushi = product(context, R.string.product_sushi, dairyAndCooled, String.valueOf(R.drawable.sushi), hashMap.get(Long.parseLong("180")));
        Product sweet_potatoes = product(context, R.string.product_sweet_potatoes, fruitsAndVegetables, String.valueOf(R.drawable.sweet_potatoes), hashMap.get(Long.parseLong("181")));
        Product tampons = product(context, R.string.product_tampons, household, String.valueOf(R.drawable.tampons), hashMap.get(Long.parseLong("182")));
        Product tea = product(context, R.string.product_tea, sweetPantry, String.valueOf(R.drawable.tea), hashMap.get(Long.parseLong("183")));
        Product thyme = product(context, R.string.product_thyme, seasoning, String.valueOf(R.drawable.thyme), hashMap.get(Long.parseLong("184")));
        Product tissues = product(context, R.string.product_tissues, household, String.valueOf(R.drawable.tissues), hashMap.get(Long.parseLong("185")));
        Product toast_bread = product(context, R.string.product_toast_bread, bakery, String.valueOf(R.drawable.toast_bread), hashMap.get(Long.parseLong("186")));
        Product tofu = product(context, R.string.product_tofu, dairyAndCooled, String.valueOf(R.drawable.tofu), hashMap.get(Long.parseLong("187")));
        Product toilet_paper = product(context, R.string.product_toilet_paper, household, String.valueOf(R.drawable.toilet_paper), hashMap.get(Long.parseLong("188")));
        Product tomato_paste = product(context, R.string.product_tomato_paste, savoryPantry, String.valueOf(R.drawable.tomato_paste), hashMap.get(Long.parseLong("189")));
        Product tomato_sauce = product(context, R.string.product_tomato_sauce, savoryPantry, String.valueOf(R.drawable.tomato_sauce), hashMap.get(Long.parseLong("190")));
        Product tomatoes = product(context, R.string.product_tomatoes, fruitsAndVegetables, String.valueOf(R.drawable.tomatoes), hashMap.get(Long.parseLong("191")));
        Product toothbrush = product(context, R.string.product_toothbrush, household, String.valueOf(R.drawable.toothbrush), hashMap.get(Long.parseLong("192")));
        Product toothpaste = product(context, R.string.product_toothpaste, household, String.valueOf(R.drawable.toothpaste), hashMap.get(Long.parseLong("193")));
        Product tortillas = product(context, R.string.product_tortillas, bakery, String.valueOf(R.drawable.tortillas), hashMap.get(Long.parseLong("194")));
        Product tuna = product(context, R.string.product_tuna, savoryPantry, String.valueOf(R.drawable.tuna), hashMap.get(Long.parseLong("195")));
        Product vanilla_extract = product(context, R.string.product_vanilla_extract, sweetPantry, String.valueOf(R.drawable.vanilla_extract), hashMap.get(Long.parseLong("196")));
        Product vanilla_sugar = product(context, R.string.product_vanilla_sugar, sweetPantry, String.valueOf(R.drawable.vanilla_sugar), hashMap.get(Long.parseLong("197")));
        Product vianocka = product(context, R.string.product_vianocka, bakery, String.valueOf(R.drawable.vianocka), hashMap.get(Long.parseLong("198")));
        Product vinegar = product(context, R.string.product_vinegar, savoryPantry, String.valueOf(R.drawable.vinegar), hashMap.get(Long.parseLong("199")));
        Product whipped_cream = product(context, R.string.product_whipped_cream, dairyAndCooled, String.valueOf(R.drawable.whipped_cream), hashMap.get(Long.parseLong("200")));
        Product wine = product(context, R.string.product_wine, drinks, String.valueOf(R.drawable.wine), hashMap.get(Long.parseLong("201")));
        Product yogurt = product(context, R.string.product_yogurt, dairyAndCooled, String.valueOf(R.drawable.yogurt), hashMap.get(Long.parseLong("202")));
        Product zucchini = product(context, R.string.product_zucchini, fruitsAndVegetables, String.valueOf(R.drawable.zucchini), hashMap.get(Long.parseLong("203")));

        CategoryDao categoryDao = categoryDao();
        categoryDao.insert(
                fruitsAndVegetables,
                bakery,
                dairyAndCooled,
                sweetPantry,
                savoryPantry,
                drinks,
                seasoning,
                household);

        ProductDao productDao = productDao();
        productDao.insert(
                apples, aluminium_foil, homebake_bagels, asparagus, aubergine_eggplant, avocado, ayran, bacon, bagels, baking_paper, bamboo_shoots, banana_chips, bananas, basil, bay_leaves, beer, beetroot, bell_peppers, berries, biscuits, black_pepper, bouillon, bread, broccoli, butter, buttermilk, camembert,
                canned_beans, canned_corn, canned_tomatoes, carrots, cauliflower, celery, cheese, cherry_tomatoes, chewing_gum, chia_seeds, chicken, chickpeas, chocolate, cider, cinnamon, cocoa_powder, coconut_milk, coffee, coke, coriander, cranberries, cream, croutons, cucumber, cumin, curry_paste, danish_bread,
                deodorant, detergent, dill, dish_soap, dish_sponge, dishwasher_salt, domestos, dough, dried_mushrooms, dried_tomatoes, eggs, fish, fish_fingers, floor_cloths, flour, frozen_fruits, frozen_peas, frozen_veggies, fruit, garlic_flakes, garlic, ginger, gnocchi, grapes, green_beans, minced_meat, ham,
                hollandaise_sauce, honey, horseradish, hummus, ice_salad, important_stuff, instant_mashed_potatoes, instant_soup, interdental_toothbrush, juice, kaki, ketchup, knedla, kohlrabi, lemon, lentils, lime, liver, mandarins, margarin, mayonnaise, meat, melon, mie_noodles, milk_rice, milk, mineral_water,
                mouth_wash, mozzarella, muesli_bars, multivitamin, mushrooms, mustard, muesli, nuts, oatmeal, oil, olive_oil, olives, onions, oregano, paper_towels, paprika_seasoning, paprika, parmesan, parsley, pasta, pasta_sauce, peanut_butter, pesto, pickles, pineapple, pizza, plastic_bags, popcorn, poppy_seeds,
                potato_croquetts, potatoes, pudding, pumpkin, pumpkin_seeds, quark, quinoa, radler, raisins, rama, raspberries, red_onions, rice, rice_cakes, rice_milk, rice_noodles, rice_wine_vinegar, rosemary, rubbish_bin_bags, rukola, salad, salami, salmon, salt, sausages, schnittlauch, seafood, sesame_seeds,
                shampoo, shower_gel, shredded_coconut, soap, softener, soup_veggies, sour_cabbage, sour_cream, soy_sauce, spaghetti, spinach, leek_spring_onion, sugar, sunflower_seeds, sushi, sweet_potatoes, tampons, tea, thyme, tissues, toast_bread, tofu, toilet_paper, tomato_paste, tomato_sauce, tomatoes,
                toothbrush, toothpaste, tortillas, tuna, vanilla_extract, vanilla_sugar, vianocka, vinegar, whipped_cream, wine, yogurt, zucchini
        );

        callback.onMigrationDone();
    }

    private Product product(Context context, @StringRes int resId, Category category, String imageUrl, String inCart) {
        return new Product(category.name(), context.getString(resId), imageUrl, inCart, false);
    }
}