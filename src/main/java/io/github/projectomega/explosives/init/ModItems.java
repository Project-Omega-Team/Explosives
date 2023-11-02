package io.github.projectomega.explosives.init;

import io.github.projectomega.explosives.Explosives;
import io.github.projectomega.explosives.item.M67GrenadeItem;
import io.github.projectomega.explosives.item.MK2GrenadeItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
        public static final DeferredRegister<Item> ITEMS =
                        DeferredRegister.create(ForgeRegistries.ITEMS, Explosives.MOD_ID);

        // TODO: Blanace numbers / adjust constructors as needed for special grenades
        public static final RegistryObject<Item> MK2_GRENADE = ITEMS.register("mk2_grenade",
                        () -> new MK2GrenadeItem(new Item.Properties(), 20 * 4, 5f, 1.3f));
        public static final RegistryObject<Item> M67_GRENADE = ITEMS.register("m67_grenade",
                        () -> new M67GrenadeItem(new Item.Properties(), 20 * 4, 5f, 1.3f));
        public static final RegistryObject<Item> IMPACT_GRENADE = ITEMS.register("impact_grenade",
                        () -> new M67GrenadeItem(new Item.Properties(), 20 * 4, 5f, 1.3f));
        public static final RegistryObject<Item> INCENDIARY_GRENADE = ITEMS.register(
                        "incendiary_grenade",
                        () -> new M67GrenadeItem(new Item.Properties(), 20 * 4, 5f, 1.3f));
        public static final RegistryObject<Item> THERMOBARIC_GRENADE = ITEMS.register(
                        "thermobaric_grenade",
                        () -> new M67GrenadeItem(new Item.Properties(), 20 * 4, 5f, 1.3f));
        public static final RegistryObject<Item> SMOKE_GRENADE = ITEMS.register("smoke_grenade",
                        () -> new M67GrenadeItem(new Item.Properties(), 20 * 4, 5f, 1.3f));
        public static final RegistryObject<Item> SEMTEX = ITEMS.register("semtex",
                        () -> new M67GrenadeItem(new Item.Properties(), 20 * 4, 5f, 1.3f));
        public static final RegistryObject<Item> MOLOTOV = ITEMS.register("molotov",
                        () -> new M67GrenadeItem(new Item.Properties(), 20 * 4, 5f, 1.3f));
        public static final RegistryObject<Item> GAS_GRENADE = ITEMS.register("gas_grenade",
                        () -> new M67GrenadeItem(new Item.Properties(), 20 * 4, 5f, 1.3f));
        public static final RegistryObject<Item> FLASH_GRENADE = ITEMS.register("flash_grenade",
                        () -> new M67GrenadeItem(new Item.Properties(), 20 * 4, 5f, 1.3f));
        public static final RegistryObject<Item> EMP_GRENADE = ITEMS.register("emp_grenade",
                        () -> new M67GrenadeItem(new Item.Properties(), 20 * 4, 5f, 1.3f));
        public static final RegistryObject<Item> DECOY_GRENADE = ITEMS.register("decoy_grenade",
                        () -> new M67GrenadeItem(new Item.Properties(), 20 * 4, 5f, 1.3f));
        public static final RegistryObject<Item> CLUSTER_GRENADE = ITEMS.register("cluster_grenade",
                        () -> new M67GrenadeItem(new Item.Properties(), 20 * 4, 5f, 1.3f));
}
