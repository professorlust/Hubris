package com.evacipated.cardcrawl.mod.hubris.relics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.hubris.fakes.FakeMerchant;
import com.evacipated.cardcrawl.mod.hubris.relics.abstracts.HubrisRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;

public class NiceRug extends HubrisRelic
{
    public static final String ID = "hubris:NiceRug";
    private static final int GOLD_AMT = 50;
    private ShopRoom shopRoom;

    public NiceRug()
    {
        super(ID, "niceRug.png", RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0] + GOLD_AMT + DESCRIPTIONS[1];
    }

    @Override
    public void justEnteredRoom(AbstractRoom room)
    {
        if (room instanceof ShopRoom) {
            shopRoom = (ShopRoom) room;
        }
    }

    @Override
    public void update()
    {
        super.update();

        if (shopRoom != null && shopRoom.merchant != null) {
            shopRoom.merchant = new FakeMerchant(shopRoom.merchant);
            shopRoom = null;
        }
    }

    @Override
    public void atBattleStart()
    {
        flash();

        int amt = AbstractDungeon.player.gold / GOLD_AMT;

        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PlatedArmorPower(AbstractDungeon.player, amt), amt));
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    public static void renderRug(SpriteBatch sb, AbstractPlayer player)
    {
        if (player.hasRelic(ID)) {
            float width = 512.0f * Settings.scale;
            float height = 512.0f * Settings.scale;

            Color prevColor = sb.getColor();
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.MERCHANT_RUG_IMG,
                    player.drawX + 30.0f * Settings.scale - width / 2.0f, player.drawY - height / 4.0f,
                    width, height,
                    0, 0, 512, 512,
                    true, false);
            sb.setColor(prevColor);
        }
    }

    @Override
    public AbstractRelic makeCopy()
    {
        return new NiceRug();
    }
}
