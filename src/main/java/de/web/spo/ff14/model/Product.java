package de.web.spo.ff14.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@AllArgsConstructor
@ToString
public enum Product {

    POTION("Potion", 28, 4, Set.of(Category.CONCOCTIONS)),
    FIRESAND("Firesand", 28, 4, Set.of(Category.CONCOCTIONS, Category.UNBURIED_TREASURES)),
    WOODEN_CHAIR("Wooden Chair", 42, 6, Set.of(Category.FURNISHINGS, Category.WOODWORKS)),
    GRILLED_CLAM("Grilled Clam", 28, 4, Set.of(Category.FOODSTUFFS, Category.MARINE_MERCHANDISE)),
    NECKLACE("Necklace", 28, 4, Set.of(Category.ACCESSORIES, Category.WOODWORKS)),
    CORAL_RING("Coral Ring", 42, 6, Set.of(Category.ACCESSORIES, Category.MARINE_MERCHANDISE)),
    BARBUT("Barbut", 42, 6, Set.of(Category.ATTIRE, Category.METALWORKS)),
    MACUAHUITL("Macuahuitl", 42,6, Set.of(Category.ARMS, Category.WOODWORKS)),
    SAUERKRAUT("Sauerkraut", 40,4, Set.of(Category.PRESERVED_FOOD)),
    BAKED_PUMPKIN("Baked Pumpkin", 40, 4, Set.of(Category.FOODSTUFFS)),
    TUNIC("Tunic", 72, 6, Set.of(Category.ATTIRE, Category.TEXTILES)),
    CULINARY_KNIFE("Culinary Knife", 44,4, Set.of(Category.SUNDRIES, Category.CREATURE_CREATIONS)),
    BRUSH("Brush", 44,4, Set.of(Category.SUNDRIES, Category.WOODWORKS)),
    BOILED_EGG("Boiled Egg", 44, 4, Set.of(Category.FOODSTUFFS, Category.CREATURE_CREATIONS)),
    HORA("Hora", 72, 6, Set.of(Category.ARMS, Category.CREATURE_CREATIONS)),
    EARRINGS("Earrings", 44,4, Set.of(Category.ACCESSORIES, Category.CREATURE_CREATIONS)),
    BUTTER("Butter", 44,4, Set.of(Category.INGREDIENTS, Category.CREATURE_CREATIONS)),
    BRICK_COUNTER("Brick Counter", 48, 6, Set.of(Category.FURNISHINGS, Category.UNBURIED_TREASURES)),
    BRONZE_SHEEP("Bronze Sheep", 64, 8, Set.of(Category.FURNISHINGS, Category.METALWORKS)),
    GROWTH_FORMULA("Growth Formula", 136, 8, Set.of(Category.CONCOCTIONS)),
    GARNET_RAPIER("Garnet Rapier", 136, 8, Set.of(Category.ARMS, Category.UNBURIED_TREASURES)),
    SPRUCE_ROUND_SHIELD("Spruce Round Shield", 136, 8, Set.of(Category.ATTIRE, Category.WOODWORKS)),
    SHARK_OIL("Shark Oil", 136, 8, Set.of(Category.SUNDRIES, Category.MARINE_MERCHANDISE)),
    SILVER_EAR_CUFFS("Silver Ear Cuffs", 136, 8, Set.of(Category.ACCESSORIES, Category.METALWORKS)),
    SWEET_POPOTO("Sweet Popoto", 72, 6, Set.of(Category.CONFECTIONS)),
    PARSNIP_SALAD("Parsnip Salad", 48, 4, Set.of(Category.FOODSTUFFS)),
    CARAMELS("Caramels", 81, 6, Set.of(Category.CONFECTIONS)),
    RIBBON("Ribbon", 54,6, Set.of(Category.ACCESSORIES, Category.TEXTILES)),
    ROPE("Rope", 36, 4, Set.of(Category.SUNDRIES, Category.TEXTILES)),
    CAVALIERS_HAT("Cavalier's Hat", 81,6, Set.of(Category.ATTIRE, Category.TEXTILES)),
    HORN("Horn", 81, 6, Set.of(Category.SUNDRIES, Category.CREATURE_CREATIONS)),
    SALT_COD("Salt Cod", 54, 6, Set.of(Category.PRESERVED_FOOD, Category.MARINE_MERCHANDISE)),
    SQUID_INK("Squid Ink", 36, 4, Set.of(Category.INGREDIENTS, Category.MARINE_MERCHANDISE)),
    ESSENTIAL_DRAUGHT("Essential Draught", 54, 6, Set.of(Category.CONCOCTIONS, Category.MARINE_MERCHANDISE)),
    ISLEBERRY_JAM("Isleberry Jam", 78,6, Set.of(Category.INGREDIENTS)),
    TOMATO_RELISH("Tomato Relish", 52,4, Set.of(Category.INGREDIENTS)),
    ONION_SOUP("Onion Soup", 78,6, Set.of(Category.FOODSTUFFS)),
    ISLEFISH_PIE("Islefish Pie", 78, 6, Set.of(Category.CONFECTIONS, Category.MARINE_MERCHANDISE)),
    CORN_FLAKES("Corn Flakes", 52,4, Set.of(Category.PRESERVED_FOOD)),
    PICKLED_RADISH("Pickled Radish", 104,8, Set.of(Category.PRESERVED_FOOD)),
    IRON_AXE("Iron Axe", 72,8, Set.of(Category.ARMS, Category.METALWORKS)),
    QUARTZ_RING("Quartz Ring", 72, 8, Set.of(Category.ACCESSORIES, Category.UNBURIED_TREASURES)),
    PORCELAIN_VASE("Porcelain Vase", 72, 8, Set.of(Category.SUNDRIES, Category.UNBURIED_TREASURES)),
    VEGETABLE_JUICE("Vegetable Juice", 78, 6, Set.of(Category.CONCOCTIONS)),
    PUMPKIN_PUDDING("Pumpkin Pudding", 78, 6, Set.of(Category.CONFECTIONS)),
    SHEEPFLUFF_RUG("Sheepfluff Rug", 90,6, Set.of(Category.FURNISHINGS, Category.CREATURE_CREATIONS)),
    GARDEN_SCYTHE("Garden Scythe", 90,6, Set.of(Category.SUNDRIES, Category.METALWORKS)),
    BED("Bed", 120,8, Set.of(Category.FURNISHINGS, Category.TEXTILES)),
    SCALE_FINGERS("Scale Fingers", 120,8, Set.of(Category.ATTIRE, Category.CREATURE_CREATIONS)),
    CROOK("Crook", 120,8, Set.of(Category.ARMS, Category.WOODWORKS));

    public static final Map<String, Product> productMap = Arrays.stream(values()).collect(Collectors.toMap(Product::getName, product -> product));
    public static final ProductCategoryTime categoryTimeMap = new ProductCategoryTime(values());

    public static final Map<Product, CycleValuePattern> allNonexistentCycleValuePatternMap = Arrays.stream(values()).collect(Collectors.toMap(product -> product, product -> new CycleValuePattern(List.of(new CycleValue(Supply.NONEXISTENT, DemandShift.NONE, -15)),1, "")));
    public static final Map<Product, CycleValuePattern> allSufficientCycleValuePatternMap = Arrays.stream(values()).collect(Collectors.toMap(product -> product, product -> new CycleValuePattern(List.of(new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 0)),1, "")));
    public static final Map<Product, CycleValuePattern> allInsufficientCycleValuePatternMap = Arrays.stream(values()).collect(Collectors.toMap(product -> product, product -> new CycleValuePattern(List.of(new CycleValue(Supply.INSUFFICIENT, DemandShift.NONE, -8)),1, "")));

    private final String name;
    private final int value;
    private final int duration;
    private final Set<Category> categories;
}
