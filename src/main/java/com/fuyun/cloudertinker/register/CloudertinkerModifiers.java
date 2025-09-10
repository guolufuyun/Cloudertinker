package com.fuyun.cloudertinker.register;

import com.fuyun.cloudertinker.Modifiers.ArmorModifiers.*;
import com.fuyun.cloudertinker.Modifiers.BaseModifiers.*;
import com.fuyun.cloudertinker.Modifiers.OnlyBowMOdifiers.*;
import com.fuyun.cloudertinker.Modifiers.ToolModifiers.Darkness;
import com.fuyun.cloudertinker.Modifiers.ToolModifiers.Magent;
import com.fuyun.cloudertinker.Modifiers.ToolModifiers.Precipitate;
import com.fuyun.cloudertinker.Modifiers.WarpenModifiers.*;
import com.fuyun.cloudertinker.Modifiers.anvil.*;
import com.fuyun.cloudertinker.Modifiers.anvil.Slots.*;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

import static com.fuyun.cloudertinker.Cloudertinker.MOD_ID;

public class CloudertinkerModifiers {

    public static ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(MOD_ID);
    //词条的注册在这里
    //这里用1234来对应我举例这个的例子的四个名位置（laomochuji）
    //1，4填类名
    //2不要和其他模组的重复，可大写可小写,用于引用
    //3只能小写，并且影响你zh_cn的键名
    //一般建议2,3写一样的
//    public static final StaticModifier<LaoMoChuJi> laomochuji=MODIFIERS.register("laomochuji", LaoMoChuJi::new);   //测试
    public static final StaticModifier<Synergy> synergy=MODIFIERS.register("synergy", Synergy::new);
    public static final StaticModifier<Fire_react> fire_react=MODIFIERS.register("fire_react", Fire_react::new);
    public static final StaticModifier<Superheat> superheat=MODIFIERS.register("superheat", Superheat::new);
    public static final StaticModifier<Stalwart> stalwart=MODIFIERS.register("stalwart", Stalwart::new);
    public static final StaticModifier<Precipitate> precipitate=MODIFIERS.register("precipitate", Precipitate::new);
    public static final StaticModifier<Precipitatearmor> precipitatearmor=MODIFIERS.register("precipitatearmor", Precipitatearmor::new);
    public static final StaticModifier<Twilight> twilight=MODIFIERS.register("twilight", Twilight::new);
    public static final StaticModifier<Cuttinggirl> cuttinggirl=MODIFIERS.register("cuttinggirl", Cuttinggirl::new);
    public static final StaticModifier<Lichcurse> lichcurse=MODIFIERS.register("lichcurse", Lichcurse::new);
    public static final StaticModifier<Lichprotect> lichprotect=MODIFIERS.register("lichprotect", Lichprotect::new);
    public static final StaticModifier<Clousetoduskarmor> clousetoduskarmor=MODIFIERS.register("clousetoduskarmor", Clousetoduskarmor::new);
    public static final StaticModifier<Clousetodusk> clousetodusk=MODIFIERS.register("clousetodusk", Clousetodusk::new);
    public static final StaticModifier<Sacrificeblood> sacrificeblood=MODIFIERS.register("sacrificeblood", Sacrificeblood::new);
    public static final StaticModifier<Drawblood> drawblood=MODIFIERS.register("drawblood", Drawblood::new);
    public static final StaticModifier<Prublazeshadow> prublazeshadow=MODIFIERS.register("prublazeshadow", Prublazeshadow::new);
    public static final StaticModifier<Minotaurcharge> minotaurcharge=MODIFIERS.register("minotaurcharge", Minotaurcharge::new);
    public static final StaticModifier<Hydrabitten> hydrabitten=MODIFIERS.register("hydrabitten", Hydrabitten::new);
    public static final StaticModifier<Hydrafire> hydrafire=MODIFIERS.register("hydrafire", Hydrafire::new);
    public static final StaticModifier<Hydralife> hydralife=MODIFIERS.register("hydralife", Hydralife::new);
    public static final StaticModifier<Knight_honorable> knight_honorable=MODIFIERS.register("knight_honorable", Knight_honorable::new);
    public static final StaticModifier<Breakphantom> breakphantom=MODIFIERS.register("breakphantom", Breakphantom::new);
    public static final StaticModifier<Phantom> phantom=MODIFIERS.register("phantom", Phantom::new);
    public static final StaticModifier<Resentment> resentment=MODIFIERS.register("resentment", Resentment::new);
    public static final StaticModifier<Relieveresentment> relieveresentment=MODIFIERS.register("relieveresentment", Relieveresentment::new);
    public static final StaticModifier<Readyboom> readyboom=MODIFIERS.register("readyboom", Readyboom::new);
    public static final StaticModifier<NoDieNoEND> nodienoend=MODIFIERS.register("nodienoend", NoDieNoEND::new);
    public static final StaticModifier<Chill_aura> chillaura=MODIFIERS.register("chillaura", Chill_aura::new);
    public static final StaticModifier<Frosttought> frosttought=MODIFIERS.register("frosttought", Frosttought::new);
    public static final StaticModifier<Frostcraft> frostcraft=MODIFIERS.register("frostcraft", Frostcraft::new);
    public static final StaticModifier<Frostsword> frostsword=MODIFIERS.register("frostsword", Frostsword::new);
    public static final StaticModifier<Frostbomb> frostbomb=MODIFIERS.register("frostbomb", Frostbomb::new);
    public static final StaticModifier<Frostaround> frostaround=MODIFIERS.register("frostaround", Frostaround::new);
    public static final StaticModifier<Seektraces> seektraces=MODIFIERS.register("seektraces", Seektraces::new);
    public static final StaticModifier<Differencebetween> differencebetween=MODIFIERS.register("differencebetween", Differencebetween::new);
    public static final StaticModifier<Habitat> habitat=MODIFIERS.register("habitat", Habitat::new);
    public static final StaticModifier<Territorialstruggle> territorialstruggle=MODIFIERS.register("territorialstruggle", Territorialstruggle::new);
    public static final StaticModifier<Timeflies> timeflies=MODIFIERS.register("timeflies", Timeflies::new);
    public static final StaticModifier<Overmygo> overmygo=MODIFIERS.register("overmygo", Overmygo::new);
    public static final StaticModifier<Overlost> overlost=MODIFIERS.register("overlost", Overlost::new);
    public static final StaticModifier<Variablood> variablood=MODIFIERS.register("variablood", Variablood::new);
    public static final StaticModifier<Unrestrainedsystem> unrestrainedsystem=MODIFIERS.register("unrestrainedsystem", Unrestrainedsystem::new);
    public static final StaticModifier<Variabloodarmor> variabloodarnor=MODIFIERS.register("variabloodarmor", Variabloodarmor::new);
    public static final StaticModifier<Winderosion> winderosion=MODIFIERS.register("winderosion", Winderosion::new);
    public static final StaticModifier<Magicforce> magicforce=MODIFIERS.register("magicforce", Magicforce::new);
    public static final StaticModifier<Highlandwatching> highlandwatching=MODIFIERS.register("highlandwatching", Highlandwatching::new);
    public static final StaticModifier<Frostcondense> frostcondense=MODIFIERS.register("frostcondense", Frostcondense::new);
    public static final StaticModifier<Steelbone> steelbone=MODIFIERS.register("steelbone", Steelbone::new);
    public static final StaticModifier<Bonebullet> bonebullet=MODIFIERS.register("bonebullet", Bonebullet::new);
    public static final StaticModifier<Frostbone> frostbone=MODIFIERS.register("frostbone", Frostbone::new);
    public static final StaticModifier<Hatred> hatred=MODIFIERS.register("hatred", Hatred::new);
    public static final StaticModifier<Chainspike> chainspike=MODIFIERS.register("chainspike", Chainspike::new);
    public static final StaticModifier<Brokenimpulse> brokenimpulse=MODIFIERS.register("brokenimpulse", Brokenimpulse::new);
    public static final StaticModifier<Magent> magent=MODIFIERS.register("magent", Magent::new);
    public static final StaticModifier<Fragile> fragile=MODIFIERS.register("fragile", Fragile::new);
    public static final StaticModifier<Forestquest> forestquest=MODIFIERS.register("forestquest", Forestquest::new);
    public static final StaticModifier<Questprotect> questprotect=MODIFIERS.register("questprotect", Questprotect::new);
    public static final StaticModifier<Crumblehorn> crumblehorn=MODIFIERS.register("crumblehorn", Crumblehorn::new);
    public static final StaticModifier<Harmonize_Armor> harmonizearmor=MODIFIERS.register("harmonizearmor", Harmonize_Armor::new);
    public static final StaticModifier<Harmonize> harmonize=MODIFIERS.register("harmonize", Harmonize::new);
    public static final StaticModifier<Supercold> supercold=MODIFIERS.register("supercold", Supercold::new);
    public static final StaticModifier<Growhedge> growhedge=MODIFIERS.register("growhedge", Growhedge::new);
    public static final StaticModifier<Melody> melody=MODIFIERS.register("melody", Melody::new);
    public static final StaticModifier<Druidseed> druidseed=MODIFIERS.register("druidseed", Druidseed::new);
    public static final StaticModifier<Zombietoba> zombietoba=MODIFIERS.register("zombietoba", Zombietoba::new);
    public static final StaticModifier<Allscepters> allscepters=MODIFIERS.register("allscepters", Allscepters::new);
    public static final StaticModifier<BlockCold> blockcold=MODIFIERS.register("blockcold", BlockCold::new);
    public static final StaticModifier<ChampionHonor> championhonor=MODIFIERS.register("championhonor", ChampionHonor::new);
    public static final StaticModifier<Mysteriousveil> mysteriousveil=MODIFIERS.register("mysteriousveil", Mysteriousveil::new);
    public static final StaticModifier<MagalaTyranny> magalatyranny=MODIFIERS.register("magalatyranny", MagalaTyranny::new);
    public static final StaticModifier<Antivirus> antivirus=MODIFIERS.register("antivirus", Antivirus::new);
    public static final StaticModifier<Ravenfeather> ravenfeather=MODIFIERS.register("ravenfeather", Ravenfeather::new);
    public static final StaticModifier<Posionspike> posionspike=MODIFIERS.register("posionspike", Posionspike::new);
    public static final StaticModifier<Coalescence> coalescence=MODIFIERS.register("coalescence", Coalescence::new);
    public static final StaticModifier<Steelbody> steelbody=MODIFIERS.register("steelbody", Steelbody::new);
    public static final StaticModifier<Heatsword> heatsword=MODIFIERS.register("heatsword", Heatsword::new);
    public static final StaticModifier<Highspirits> highspirits=MODIFIERS.register("highspirits", Highspirits::new);
    public static final StaticModifier<Twilightarmor> twilightarmor=MODIFIERS.register("twilightarmor", Twilightarmor::new);
    public static final StaticModifier<Darkness> darkness=MODIFIERS.register("darkness", Darkness::new);
    public static final StaticModifier<Darknessarmor> darknesssrmor=MODIFIERS.register("darknessarmor", Darknessarmor::new);
    public static final StaticModifier<Absorb> absorb=MODIFIERS.register("absorb", Absorb::new);
    public static final StaticModifier<Takeroot> takeroot=MODIFIERS.register("takeroot", Takeroot::new);
    public static final StaticModifier<Takerootarmor> takerootarmor=MODIFIERS.register("takerootarmor", Takerootarmor::new);
    public static final StaticModifier<Infect> infect=MODIFIERS.register("infect", Infect::new);
    public static final StaticModifier<React> react=MODIFIERS.register("react", React::new);


    public static final StaticModifier<Text> text=MODIFIERS.register("text", Text::new);



}
