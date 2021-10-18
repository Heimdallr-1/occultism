/*
 * MIT License
 *
 * Copyright 2021 vemerion
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.klikli_dev.occultism.handlers;

import java.util.List;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.github.klikli_dev.occultism.common.entity.GuardianFamiliarEntity;
import com.github.klikli_dev.occultism.common.entity.HeadlessFamiliarEntity;
import com.github.klikli_dev.occultism.registry.OccultismAdvancements;
import com.github.klikli_dev.occultism.registry.OccultismEntities;
import com.github.klikli_dev.occultism.util.FamiliarUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Occultism.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FamiliarEventHandler {

    @SubscribeEvent
    public static void livingDeathEvent(LivingDeathEvent event) {
        guardianUltimateSacrifice(event);
        headlessStealHead(event);
    }

    @SubscribeEvent
    public static void livingDamageEvent(LivingDamageEvent event) {
        if (!(event.getSource().getEntity() instanceof PlayerEntity))
            return;
        PlayerEntity player = (PlayerEntity) event.getSource().getEntity();

        if (!FamiliarUtil.isFamiliarEnabled(player, OccultismEntities.HEADLESS_FAMILIAR.get()))
            return;

        EntityType<?> headType = event.getEntityLiving().getType();

        if (!FamiliarUtil.hasFamiliar(player, OccultismEntities.HEADLESS_FAMILIAR.get(),
                h -> h.getHeadType() == headType))
            return;

        event.setAmount(event.getAmount() * 1.3f);
    }

    private static void headlessStealHead(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof PlayerEntity))
            return;
        PlayerEntity player = (PlayerEntity) event.getSource().getEntity();

        if (!FamiliarUtil.isFamiliarEnabled(player, OccultismEntities.HEADLESS_FAMILIAR.get()))
            return;

        List<HeadlessFamiliarEntity> headlesses = FamiliarUtil.getAllFamiliars(player,
                OccultismEntities.HEADLESS_FAMILIAR.get());

        if (event.getEntityLiving().getType() == OccultismEntities.CTHULHU_FAMILIAR.get())
            OccultismAdvancements.FAMILIAR.trigger(player, FamiliarTrigger.Type.HEADLESS_CTHULHU_HEAD);

        headlesses.forEach(h -> h.setHeadType(event.getEntityLiving().getType()));
    }

    private static void guardianUltimateSacrifice(LivingDeathEvent event) {
        if (event.getSource().isBypassInvul() || !(event.getEntity() instanceof PlayerEntity))
            return;

        PlayerEntity player = (PlayerEntity) event.getEntity();
        if (!FamiliarUtil.isFamiliarEnabled(player, OccultismEntities.GUARDIAN_FAMILIAR.get()))
            return;

        GuardianFamiliarEntity guardian = FamiliarUtil.getFamiliar(player, OccultismEntities.GUARDIAN_FAMILIAR.get());
        if (guardian == null)
            return;

        if (!guardian.sacrifice())
            return;

        event.setCanceled(true);
        player.setHealth(1);
        player.removeAllEffects();
        player.addEffect(new EffectInstance(Effects.REGENERATION, 20 * 10, 1));
        player.addEffect(new EffectInstance(Effects.ABSORPTION, 20 * 5, 1));
    }
}
