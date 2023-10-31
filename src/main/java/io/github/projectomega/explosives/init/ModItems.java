package io.github.projectomega.explosives.init;

import io.github.projectomega.explosives.Explosives;
import io.github.projectomega.explosives.item.MK2GrenadeItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Explosives.MOD_ID);

    public static final RegistryObject<Item> MK2_GRENADE = ITEMS.register("mk2_grenade",
            () -> new MK2GrenadeItem(new Item.Properties(), 20 * 4, 5f, 1.3f));
}
