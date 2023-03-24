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

    POTION("Potion", 28, 4, Set.of(Category.CONCOCTIONS), 1),
    FIRESAND("Firesand", 28, 4, Set.of(Category.CONCOCTIONS, Category.UNBURIED_TREASURES), 1),
    WOODEN_CHAIR("Wooden Chair", 42, 6, Set.of(Category.FURNISHINGS, Category.WOODWORKS), 1),
    GRILLED_CLAM("Grilled Clam", 28, 4, Set.of(Category.FOODSTUFFS, Category.MARINE_MERCHANDISE), 1),
    NECKLACE("Necklace", 28, 4, Set.of(Category.ACCESSORIES, Category.WOODWORKS), 1),
    CORAL_RING("Coral Ring", 42, 6, Set.of(Category.ACCESSORIES, Category.MARINE_MERCHANDISE), 1),
    BARBUT("Barbut", 42, 6, Set.of(Category.ATTIRE, Category.METALWORKS), 1),
    MACUAHUITL("Macuahuitl", 42,6, Set.of(Category.ARMS, Category.WOODWORKS), 1),
    SAUERKRAUT("Sauerkraut", 40,4, Set.of(Category.PRESERVED_FOOD), 1),
    BAKED_PUMPKIN("Baked Pumpkin", 40, 4, Set.of(Category.FOODSTUFFS), 1),
    TUNIC("Tunic", 72, 6, Set.of(Category.ATTIRE, Category.TEXTILES), 1),
    CULINARY_KNIFE("Culinary Knife", 44,4, Set.of(Category.SUNDRIES, Category.CREATURE_CREATIONS), 1),
    BRUSH("Brush", 44,4, Set.of(Category.SUNDRIES, Category.WOODWORKS), 1),
    BOILED_EGG("Boiled Egg", 44, 4, Set.of(Category.FOODSTUFFS, Category.CREATURE_CREATIONS), 1),
    HORA("Hora", 72, 6, Set.of(Category.ARMS, Category.CREATURE_CREATIONS), 1),
    EARRINGS("Earrings", 44,4, Set.of(Category.ACCESSORIES, Category.CREATURE_CREATIONS), 1),
    BUTTER("Butter", 44,4, Set.of(Category.INGREDIENTS, Category.CREATURE_CREATIONS), 1),
    BRICK_COUNTER("Brick Counter", 48, 6, Set.of(Category.FURNISHINGS, Category.UNBURIED_TREASURES), 1),
    BRONZE_SHEEP("Bronze Sheep", 64, 8, Set.of(Category.FURNISHINGS, Category.METALWORKS), 1),
    GROWTH_FORMULA("Growth Formula", 136, 8, Set.of(Category.CONCOCTIONS), 1),
    GARNET_RAPIER("Garnet Rapier", 136, 8, Set.of(Category.ARMS, Category.UNBURIED_TREASURES), 1),
    SPRUCE_ROUND_SHIELD("Spruce Round Shield", 136, 8, Set.of(Category.ATTIRE, Category.WOODWORKS), 1),
    SHARK_OIL("Shark Oil", 136, 8, Set.of(Category.SUNDRIES, Category.MARINE_MERCHANDISE), 1),
    SILVER_EAR_CUFFS("Silver Ear Cuffs", 136, 8, Set.of(Category.ACCESSORIES, Category.METALWORKS), 1),
    SWEET_POPOTO("Sweet Popoto", 72, 6, Set.of(Category.CONFECTIONS), 1),
    PARSNIP_SALAD("Parsnip Salad", 48, 4, Set.of(Category.FOODSTUFFS), 1),
    CARAMELS("Caramels", 81, 6, Set.of(Category.CONFECTIONS), 1),
    RIBBON("Ribbon", 54,6, Set.of(Category.ACCESSORIES, Category.TEXTILES), 1),
    ROPE("Rope", 36, 4, Set.of(Category.SUNDRIES, Category.TEXTILES), 1),
    CAVALIERS_HAT("Cavalier's Hat", 81,6, Set.of(Category.ATTIRE, Category.TEXTILES), 1),
    HORN("Horn", 81, 6, Set.of(Category.SUNDRIES, Category.CREATURE_CREATIONS), 1),
    SALT_COD("Salt Cod", 54, 6, Set.of(Category.PRESERVED_FOOD, Category.MARINE_MERCHANDISE), 1),
    SQUID_INK("Squid Ink", 36, 4, Set.of(Category.INGREDIENTS, Category.MARINE_MERCHANDISE), 1),
    ESSENTIAL_DRAUGHT("Essential Draught", 54, 6, Set.of(Category.CONCOCTIONS, Category.MARINE_MERCHANDISE), 1),
    ISLEBERRY_JAM("Isleberry Jam", 78,6, Set.of(Category.INGREDIENTS), 1),
    TOMATO_RELISH("Tomato Relish", 52,4, Set.of(Category.INGREDIENTS), 1),
    ONION_SOUP("Onion Soup", 78,6, Set.of(Category.FOODSTUFFS), 1),
    ISLEFISH_PIE("Islefish Pie", 78, 6, Set.of(Category.CONFECTIONS, Category.MARINE_MERCHANDISE), 1),
    CORN_FLAKES("Corn Flakes", 52,4, Set.of(Category.PRESERVED_FOOD), 1),
    PICKLED_RADISH("Pickled Radish", 104,8, Set.of(Category.PRESERVED_FOOD), 1),
    IRON_AXE("Iron Axe", 72,8, Set.of(Category.ARMS, Category.METALWORKS), 1),
    QUARTZ_RING("Quartz Ring", 72, 8, Set.of(Category.ACCESSORIES, Category.UNBURIED_TREASURES), 1),
    PORCELAIN_VASE("Porcelain Vase", 72, 8, Set.of(Category.SUNDRIES, Category.UNBURIED_TREASURES), 1),
    VEGETABLE_JUICE("Vegetable Juice", 78, 6, Set.of(Category.CONCOCTIONS), 1),
    PUMPKIN_PUDDING("Pumpkin Pudding", 78, 6, Set.of(Category.CONFECTIONS), 1),
    SHEEPFLUFF_RUG("Sheepfluff Rug", 90,6, Set.of(Category.FURNISHINGS, Category.CREATURE_CREATIONS), 1),
    GARDEN_SCYTHE("Garden Scythe", 90,6, Set.of(Category.SUNDRIES, Category.METALWORKS), 1),
    BED("Bed", 120,8, Set.of(Category.FURNISHINGS, Category.TEXTILES), 1),
    SCALE_FINGERS("Scale Fingers", 120,8, Set.of(Category.ATTIRE, Category.CREATURE_CREATIONS), 1),
    CROOK("Crook", 120,8, Set.of(Category.ARMS, Category.WOODWORKS), 1),
    CORAl_SWORD("Coral Sword", 72,8, Set.of(Category.ARMS, Category.MARINE_MERCHANDISE), 2),
    COCONUT_JUICE("Coconut Juice", 36, 4, Set.of(Category.CONFECTIONS, Category.CONCOCTIONS), 2),
    HONEY("Honey", 36, 4, Set.of(Category.CONFECTIONS, Category.INGREDIENTS), 2),
    SEASHINE_OPAL("Seashine Opal", 80,8, Set.of(Category.UNBURIED_TREASURES), 2),
    DRIED_FLOWERS("Dried Flowers", 54,6, Set.of(Category.SUNDRIES, Category.FURNISHINGS), 2),
    POWDERED_PAPRIKA("Powdered Paprika", 52,4, Set.of(Category.INGREDIENTS, Category.CONCOCTIONS), 2),
    CAWL_CENNIN("Cawl Cennin", 90,6, Set.of(Category.CONCOCTIONS, Category.CREATURE_CREATIONS), 2),
    ISLOAF("Isloaf", 52,4, Set.of(Category.FOODSTUFFS, Category.CONCOCTIONS), 2),
    POPOTO_SALAD("Popoto Salad", 52,4, Set.of(Category.FOODSTUFFS), 2),
    DRESSING("Dressing", 52,4, Set.of(Category.INGREDIENTS), 2);

    public static final Map<String, Product> productMap = Arrays.stream(values()).collect(Collectors.toMap(Product::getName, product -> product));
    public static final ProductCategoryTime categoryTimeMap = new ProductCategoryTime(values());

    public static final Map<Product, CycleValuePattern> allNonexistentCycleValuePatternMap = Arrays.stream(values()).collect(Collectors.toMap(product -> product, product -> new CycleValuePattern(List.of(new CycleValue(0, Supply.NONEXISTENT, DemandShift.NONE, -15)),1, "")));
    public static final Map<Product, CycleValuePattern> allSufficientCycleValuePatternMap = Arrays.stream(values()).collect(Collectors.toMap(product -> product, product -> new CycleValuePattern(List.of(new CycleValue(0, Supply.SUFFICIENT, DemandShift.NONE, 0)),1, "")));
    public static final Map<Product, CycleValuePattern> allInsufficientCycleValuePatternMap = Arrays.stream(values()).collect(Collectors.toMap(product -> product, product -> new CycleValuePattern(List.of(new CycleValue(0, Supply.INSUFFICIENT, DemandShift.NONE, -8)),1, "")));

    private final String name;
    private final int value;
    private final int duration;
    private final Set<Category> categories;
    private final int season;
}
