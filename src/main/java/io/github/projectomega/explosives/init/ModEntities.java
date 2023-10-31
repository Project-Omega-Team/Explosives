package io.github.projectomega.explosives.init;

import java.util.function.BiFunction;
import io.github.projectomega.explosives.Explosives;
import io.github.projectomega.explosives.entity.MK2GrenadeEntity;
import io.github.projectomega.explosives.entity.ThrowableGrenadeEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
        public static final DeferredRegister<EntityType<?>> ENTITIES =
                        DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Explosives.MOD_ID);

        public static final RegistryObject<EntityType<ThrowableGrenadeEntity>> THROWABLE_GRENADE =
                        registerProjectile("throwable_grenade", ThrowableGrenadeEntity::new);

        public static final RegistryObject<EntityType<ThrowableGrenadeEntity>> MK2_GRENADE =
                        registerProjectile("mk2_grenade", MK2GrenadeEntity::new);

        private static <T extends Entity> RegistryObject<EntityType<T>> registerProjectile(
                        String id, BiFunction<EntityType<T>, Level, T> function) {
                return ENTITIES.register(id,
                                () -> EntityType.Builder.of(function::apply, MobCategory.MISC)
                                                .sized(0.25F, 0.25F).setTrackingRange(100)
                                                .setUpdateInterval(1).noSummon().fireImmune()
                                                .setShouldReceiveVelocityUpdates(true).build(id));
        }
}
