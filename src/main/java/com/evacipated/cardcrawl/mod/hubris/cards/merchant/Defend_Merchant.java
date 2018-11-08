package com.evacipated.cardcrawl.mod.hubris.cards.merchant;

import basemod.helpers.BaseModCardTags;
import com.evacipated.cardcrawl.mod.hubris.CardNoSeen;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@CardNoSeen
public class Defend_Merchant extends AbstractCard
{
    public static final String ID = "hubris:Defend_Merchant";
    public static final String IMG = "red/skill/defend";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BLOCK_AMT = 5;
    private static final int UPGRADE_BLOCK_AMT = 3;

    public Defend_Merchant()
    {
        super(ID, NAME, IMG, IMG, COST, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.BASIC, CardTarget.SELF);

        baseBlock = BLOCK_AMT;
        tags.add(BaseModCardTags.BASIC_DEFEND);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (Settings.isDebug) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, 50));
        } else {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        }
    }

    @Override
    public void upgrade()
    {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK_AMT);
        }
    }

    @Override
    public AbstractCard makeCopy()
    {
        return new Defend_Merchant();
    }
}
