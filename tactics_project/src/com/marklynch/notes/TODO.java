package com.marklynch.notes;

public class TODO {

	// Should be able to smash window and climb through, want to make overlay
	// image nice so that i can

	// Setting bow to 20 range seems to have frozen the game...

	// if u go beside door, but opposite side to ur attackers they wont go in
	// and attack

	// Are ranged weapons working?

	// inSameBuilding METHODS being used, but shouldnt be needed.
	// if
	// (!attacker.squaresVisibleToThisCharacter.contains(target.squareGameObjectIsOn))
	// return false;

	// Crash coz u can try to move two buttons @ once.

	// Search for the action logging, put it all into the action classes, only
	// log if square is visible to player

	// u[date for the exploding objet is attached to the turns for some reason

	// Stop the shop keeper from blocking the door :/

	// The vision algorithm is just too harsh.

	// The vision algorithm could do some work, it iterates through every point
	// withing X squares, its probably a bit much... BUT... see how it goes.

	// need flag for blockLineOfSight

	// For AIs more than X squares away, use quicker version of path finding,
	// vision calc, etc...

	// Do sight and windows i think

	// Need to run calculateVisibleSquares on all characters on startup/load

	// Maybe put in logic so that guards/shop keepers etc wont stand beside
	// fecking doors (the more static AIs).

	// Trader is writing sign twice :P

	// Have a global random generator, replace calls to Math.random();

	// Make sure to do Garbage Collection after (i.e. as part of, but at the
	// end) saving and loading

	// Building need to have entrance squares. They're on the outside BUT the
	// match the floor of the building and when u step on them it hides the roof
	// overlay

	// House overlay in 2 parts - roof and wall. wall doesn't fade out, could
	// make the bottom wall in the ouse take up 2 or 3 squares.
	// Could also move the overlay up to where the wall starts on the bottom
	// square, but i dunno

	// square ourtside the door should be a doormat

	// The trader is putting the brrom on his sign :/

	// building roof that fades out when u enter, just a big png :)

	// Don't show certain objects, like rooves, in the action popup

	// Merge GameObjectTemplate w/ GameObject
	// Merge WeaponTemplate w/ Weapon

	// If the traders two most valuable objects are the same he'll put it up
	// twice :/

	// So... seeing this with the shopkeeper - he keeps switching between
	// looting and shopkeeping. Need a new looting state, stay in that state
	// until looted the thing, can't reach the thing or whatever

	// Also... the shopkeeper is seeing loot through walls

	// Could merge the different actors? a lot of them. They only diff is their
	// AIRoutine :P

	// Make a big sword that spreads over two screens and character holds it
	// properly

	// Rename "Templates" to Object creator
	// Rename the methods to "Create X"

	// Move the rest of the objects (wolf, tree, superwolf etc) in to Templates
	// class

	// Have an anchor for actors and gameobject for where they will hold
	// eachother...

	// Move the common AI bits to another class. Like the fighting routine...
	// it's used by loads of enemies... defer to it the same way u defer to a
	// group or quest. Some would use the attack routine, some would use the
	// run, and maybe have a configurable hybrid where they run is health too
	// low or group size too low, ALSO... running is super annoying so maybe
	// dont put that in. Maybe just civies and some weird enemies like the ones
	// in DS and bloodborne and nioh

	// EquipAction for items on ground

	// Need to put XP in soon to compel myself :P

	// Fill in the rest of the actions, look at list i've set up.
	// Inventory as well in play, and a way to close it :P

	// INVENTORY

	// View inventory of people and containers also :)

	// drag inventory as well

	// Camera doesn't shift with character if u use keys + menu to move :/

	// CTRL + direction to open menu. CTRL works really well coz u can reach
	// WASD and the arrows. Make it left and right CTRL do the same function.

	// When the state of the button is down could change the color of the text
	// instead of the button (like spotify)

	// Disable/grey out/remove actions that cant be performed.

	// Need arrow in the popup menus like so - [Hunter > ] . Right align the
	// image. Maybe for popup buttons thats a default param, u pass in an
	// image.Maybe for the actions have an icon instead. YES. Foot prints for
	// move, hands for pickup/loot, red hand + text for steal/pickpoket. Sword
	// for attack, red sword for attack friendly (or always make the sword red?)

	// When u mouse over something, lemme know the default function. Image +
	// text in white, or red if u'll be attacked for it (stealing, attacking,
	// tresspassing)

	// Same for the actions in the popups

	// Click on location to move there, one turn at a time, stop if u get
	// attacked / get talked to, probably need to show preview of path they'll
	// take

	// Issue - the tree will drop fruit immediately when its created, if the
	// tree has been hit any time in the past.

	// Have a method "Can sense" or "Can see"... or something for Actors

	// Why dont they move when he goes to bed? I think its coz
	// getAllSquaresAtDistance(X) doesnt work, could ape findPath where limit =
	// X

	// getAllSquaresAtDistance method does NOT work

	// Issue - if the world is constantly alive, people are gonna get stuck...

	// Test with just one follower

	// Mark items as belonging to player? so that no1 will steal them? or just
	// mark some as
	// quest? Remember to release objects from quest also.

	// distance from leader in the group should be based on size, otherwise
	// it'll end up with weird formations

	// Theres a serious swap locations issue going on, people are getting stuck,
	// particularly the huners when the go by the south wall of the lodge

	// Put in 12 followers of the hunter lead, then we'll see/

	// NEED PENGUINS, can use graphics from club penguin coz its the funniest
	// thing ever

	// To find a colour pallet (per area, for the whole game / per town / per
	// faction) maybe have the app randomly cycle through folour filters and see
	// what works.
	// BUT each area should definitel have it's own color filter

	// Draw nuts on the tree and if you hit the tree the nuts drop
	// Tree class draw it's inventory in top half
	// When u hit the tree it moves it's nuts from it's inventory to the ground
	// :)
	// Maybe after T(h)ree hits? Or when health goes below 20%? Or if u hit it
	// with a blunt weapon?

	// When someone is sleeping make them impassable, need extra special logic
	// for sleeping. Heal, get up after X amount of time, be impassable, needs a
	// spcific status/flag for actor.

	// Group members can follow their leaders in to buildings :D
	// If there's multiple followers they get stuck leapfrogging eachother @ the
	// door.
	// The group of hunters follow the leader to bed, lol

	// Update the hunter routine to attack any wild animal within 5 squares
	// if ur gone for more than 20 turns then the hunters should start a camp
	// fire or go back to the drawing board

	// TALK :)
	// Talk can act like the AI, if quest != null, defer to the the quests talk
	// method :)

	// Don't worry too much about attacking logic yet, will fix it up when I
	// decide what way to go w/ fights

	// Look @ the wild 8

	// Move calls to Game.level.logOnScreen in to relevant Action classes

	// Skill - counter (lvl 1 counter with equipped weapon if withing range, lvl
	// 2 counter with weapon any weapon in inventory if within range)

	// Turn on highlights when using mouse as well as arrows

	// Player controls are mangled

	// Small game - Don't destroy their Easel :P
	// Small game - there's water arrows beside them, three sets, and a bow, u
	// can take one set of arrows and a bow without them getting angry. Once u
	// accept the mission that is...
	// Maybe u could mess with the easel to say that the wolf is weak to fire
	// If u inspect the easel it says hes supceptible to water and immune to
	// fire
	// U can re-imbue the hunter's weapons on the ground to do fire damage
	// OR steal the arrows
	// OR disenchant the arrows
	// I dunno
	// I want someone in the town that's like - don't attack the wolves, u need
	// to stop these hunters!!! YES!
	// Could distract hunters by dropping junk near them :P
	// Could distract hunters by conjuring a wolf familiar
	// The hunters should still sleep at night when planning the hunt, the board
	// is there though, so u'll know they're there, they'll take their weapons
	// to bed with them tho.
	// This is getting over complicated and out of hand :P
	// The cub could become some sort of guardian spirit for you
	// The one who helps u is an enviromentalist, teaches u skill "remove
	// imbuement" and says "This should come in handy
	// SHopkeeper nearby sells imbue with fire
	// The hunting board plan says that super wolf is healed by fire
	// Look up fire wolf legends
	// Could name firewolf after that wolf in that zelda-like game. konami?
	// ko... something? Okami! Look up the legend that its based on. Im sure
	// there was another name for the dog-god
	// Cub is white with tint of blue
	// DOn't make the hunters so bad, they're down on their luck, there's no
	// winners :) grey area for all

	// Ancient roman town, british town, celtic town

	// To add to save - attackers, building, pack

	// Hunter AIT should have interrupt where it attacks animals within 10
	// squares

	// The way it is now, pack members that are far away will still know about
	// the attackers and come running... need a sight limit, a sound limit, need
	// to be handled on an individual level... or have a different concept other
	// than pack and keep pack for animals and bandits.

	// Quest - basic hunting mission
	// SUPERWOLF
	// Good to start with but boring, need to embelish it
	// You with the hunters
	// They give you a bow
	// The wolf will prioritise attacking the hunters because it recognises them
	// "We've had many a tussle with SUPERWOLF, she's the ultimate prize"
	// The wolves won't attack you unless you attack them
	// You have to option to kill the hunters instead - need to hint at this
	// You can also just let the hunt play out without attacking anyone
	// But then the hunter bros will attack you
	// If you kill the wolf and go in to her cave you find cubs :'(
	// And the hunters will go and try to kill the cubs and u can stop them
	// there too
	// And if u kill the hunters... I dunno... I haven't worked out how that
	// will go
	// PERK - friend of the forest / pack master / member of the pack.
	// SOmething... wild animals are nicer towards you.
	// And the wolves get something valuable they were hiding in their cave and
	// give it to you
	// Need to make it clear that the hunter bros are dickheads. Make ur blood
	// boil and make u want to kill them. Look up videos of assholes with tiny
	// dicks talking about how great hunting is
	// How do I word it in the quests? Go to SUPERWOLF'S cave. THEN.... i dunno.
	// just nothing. or... like a pending thing. "finish the fight to continue",
	// Orphan cub follows you? BUT... that means you miss out on the cub if u
	// dont kill the mammy.
	// Maybe u follow the wolves in to the cave, and the baby starts following u
	// anyway
	// On the walk to the wolve's cave Brent and Bront shoot cute birds? Stamp
	// on plants?
	// Don't make them TOO obvious. THere is a balance. Just sociopaths lack of
	// empathy.
	// AI for cub, if it sees its dead mother it runs over...
	// Cub is white or a weird color. White with light blue eyes. Or yellow
	// brown eyes like the witcher. Could call him Geralt.
	// The super wolf is magic. Has fire power. Weak against water. The Hunters
	// will tell u that. Give u special arrows + bow. Super Wolf is very hard to
	// kill without them (that should keep her alive ouside of the quest!)
	// Actually, don't make the superwolf benevolent or anything, everyone needs
	// to be a grey area, even wolves.
	// Wolf cub is water powered, immune to water and fire.

	// General - try to make it in such a way that the quest end points are in
	// harder areas, further out than the quest start points, so that u can, but
	// rarely stumble across them. Like with the wolf. 1. It's further out than
	// the village. 2. Super wolf is hard to kill on ur own and without water.

	// Quest - Find out how the shopkeeper is making so much money.
	// He never sells a thing, but lives like a king
	// Has a chest beside him with incriminating info
	// goes and meets a demon @ night
	// Don't make him shopkeeper class or the hunters will use him for selling
	// :P

	// So... if an attacker is out of sight, don't remove him from the list,
	// just... ignoer them. Would be weird if breaking LoS made them forget
	// about you

	// Make sure attackers persist through saves, kinda want to call it
	// beligerenents, or have a fight class... with teams...

	// If leader dies, highest level member becomes leader of pack

	// Remove pack from list is members.size() == 0

	// Check that buildings load back up

	// My inSameRoom thing kinda sucks coz they wont shoot if in a doorway.
	// Doorways could have a list of rooms they belong too (well... 2)

	// Button in game/eidtor draw roofs/don't draw roofs

	// Wolf only moves once
	// Hunter seems to get too close when attacking
	// Make walls thinner, but thicker than door
	// Make door not retarded, bit thinned then door
	// Make the roof go out further (maybe only on spread on to squares that
	// dont have roof)

	// Technical - watch creation of too many string literals

	// So witht e square deisgn, maybe have the image the way it is now, then
	// have gradients underneath
	// So the color in the senter is the color of the image and then it blends
	// out to the foursides and four corners

	// on save equipped weapon needs to be saved better

	// the messyness with guids is messy - so far bed, faction and equipped
	// weapon... If i at least hide them behind getters and setters i can
	// control it... COULD move them in to adapter like i do for the factions...

	// In the ediotr i should highlight characters with their faction color for
	// clarity

	// loadImages is being called too many times when loading from a save file

	// Technical
	// That get all paths to all squares thing seems ridic

	// If blocked (from bed or something) the character should show that they're
	// angry, and some might even attack... depending on their aggressiveness
	// attribute and how much they like u

	// Technical
	// Actually... i'm pretty sure i'm working out a path to every square for
	// everyone all the time, maybe multiple times per frame, so there's ur
	// issue ;)

	// Technical
	// In these AI routine getNearest bullshit i should get nearest by straight
	// line then see if there's a path and settle for that.

	// Technical
	// In get best path, work towards the target, pick squares that head in the
	// general direction of the target first

	// Technical
	// Also use lower res grass the further u zoom

	// Could move one AI per frame :P or like one 60th of the AIs per frame.....

	// Technical
	// Look out for Math.sqrt!!! Saw it in Cat.java... look at
	// actor.straightlinedistanceTo...

	// Technical
	// Also a lot of unnecesary calls to newColor(), pre-calculate, pre-load
	// everything u can...

	// Technical
	// If game is struggling with running AI for all characters (which it will)
	// then only run those within X squares. This will involve going through the
	// squares rather than through gameObject and factions, but I think thats
	// OK. SO, u set minX and maxX, minY and maxY to go through the squares[][].
	// Can move the range in and out based on performance try to keep in 59-60
	// fps. Special factors include when the user is in the corner of the map,
	// also have some that are excluded from the freeze like active quest
	// characters and characters are in a fight.

	// Technical
	// don't have to check all squares for path, only those u can reach in a
	// straight line :P OR SOMETHING... thought I had something, anyway, the
	// ppath finding needs to be seriously fucking optimized, potentially some
	// cheating, but keep going until u get a performance hit

	// Time to make it look prettier
	// 1. shoot projectile
	// 2. turn wolf in to carcass

	// HAve an anchor for people like the shopkeeper - a square + a distance
	// they can venture away from it. Could be one of their interrupts - if(have
	// anchor AND not close enough to it){go back to rance of anchor}. This
	// allows them to wander around, but means the player can still find them.

	// look out for calls to this, unnecessary
	// this.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH

	// character that has never laughed, and u have to find a way to make it
	// laugh, make it dark, like he only laughs when u kill someone on front of
	// him, and he loves it tooo much and starts killing people. maybe u take
	// him as a companion and are trying to work out how to kill him, and when u
	// eventually get in a fight when hes around, thats when u work it out. Or
	// maybe, forget the laughing thing, hes just a regular companion, whos
	// never been outside his town before, and the murder sets him off and he
	// snaps. Says he has to go home, then when u go visit his town/home next
	// time, everyone's been slaughtered by him. Quest is only triggered, only
	// shows in the quest log when he myteriously leaves after taking part in
	// his first battle. Something like "Paul, a companion, just ran off
	// mysteriously in the middle of a fight, I hope he's OK". U can follow him
	// immediately or go to his town later. ANyway, Paul should go home, sleep,
	// then in the morning trigger psycho mode AI.

	// In shadow of mordor the bosses have special weaknesses, like fear of
	// horses... look them up, would be a good ide, more interesting than
	// regular enviromental weaknesses, the quest giver could let u know, or
	// provide other ways for the user to find out, maybe the mind reader, could
	// make one "learn biggest fear", that may be helpful or useless

	// On the character design side of things, look at scribblenauts, the way
	// they're a few simple pieces pasted together. Plus its ogt that simulation
	// side to it.

	// GameObject/GameObject templace needs to be abstract and u have to
	// overwrite makeCopy

	// U should get better prices when bartering if someone likes you

	// Could have algorithm to work out how much someone likes u (+10 same
	// faction, -5 wrong race, -5 ur ugly) like in 4x games. Could also display
	// this info to the user. Maybe a special power.

	// Have to look in to turning some classes of gameobject and actor in to
	// boolean flags

	// Shopkeeper class has array of templates that u can buy from them. Or
	// maybe array of objects? ye, guck it, just have an inventory like a normal
	// person, but a massive one. And make sure they don't give shit away :P

	// AIs need to buy new equipment: make array of squipment available in shop,
	// shorten to what u can afford, then shorten that down to the best of each
	// class, then compare to current equipment in each class to decide what to
	// buy. Maybe take into account the damage difference... etc. Maybe I could
	// have an algorithm to give each item a rating that'll help decide.

	// Ye, then they go in to sell mode and sell the less the inferior
	// equipment, then probably keep that bit of money in hand

	// Also, sell unused equipment

	// The actor class will allow looting of live people and selling to dead
	// people

	// Also... selling method at the moment is really just giving :P

	// if u walk in to an ally they swap squares with you

	// Watch for calls to
	// Game.level.activeActor.calculatePathToAllSquares(Game.level.squares);
	// Game.level.activeActor.calculatePathToAllSquares(Game.level.squares);
	// Game.level.activeActor.calculateReachableSquares(Game.level.squares);
	// Game.level.activeActor.calculateAttackableSquares(Game.level.squares);

	//

	// inventory is limited to 25

	// Make the icon under the mouse when u have something selected bigger

	// Make Terrain square and inventory square share the same incestor, the
	// current tree is hairy

	// Need some sort of clean window system, its getting gross

	// Need to draw highlight and cursor and move line for inventory

	// LevelInput and EditorInput can surely be merged? subclass at leats,
	// theyre fucking gross

	// Split inventory and ground... subclass

	// Put the level in a smaller window when the editor is on.....

	// make the background a tile set of mountains, make it move and zoom
	// correctly...

	// Inventory to be drawn + controlled like th level is.

	// Skill that lets you know what people have in their inventory!!(perception
	// tree?)

	// Access inventory and move objects out of the inventory

	// Maybe always show popup when placing an object...

	// if showInventoryItems make draw loop thru the items and show them

	// Use fitsInInventory flag, when u move an object or create a new object on
	// top of an object it will ask what u want to put it in to with a pop up :)
	// Matbe need another flag canStoreItemsInInventory

	// Take away the ability to put an actor on a no share square, i already did
	// it for objects. ACTUALLY... lets do actor templates to start with

	// GameObject.canShareSquare

	// GameObject.canBePlacedInInventory

	// GameObject.canHoldObjects

	// A filter in game object tab, by distance from center of screen and type
	// (weapon, object, exploding object....)

	// Still using squareObjectIsOn... but also have attribute
	// InventoryThisItemBelongsTo...

	// merge up the UI elements @ some stage, they're getting silly. start with
	// the popup classes

	// If u were to move an actor from 2,2 to 0,0, it stil says "actor @ 2,2" in
	// the attributes

	// put canShareSquare in to path finding

	// Put EDITOR STATE somewhere on screen

	// Actors that can fit in inventory? Like a fairy or demon or something, and
	// u can let it out and it'll do stuff...

	// I wonder if i can build in a working economy, google what games have
	// attempted tis b4, probably just dwarf fortress in any meaningful way...

	// Concept of territory for factions, when u zoom out far enough the map is
	// colored in the different faction colors so that u can see the territory,
	// could refer to them as "countries" :) Territory is exerted out from
	// posts/buildings/...something

	// Could have to protect one group/person from another. In the design I'd
	// put in a choke point where you can stand. If your have a good standing
	// with the attackers you could just stand in their path and work as a choke
	// point so that they can't get to te victim.

	// At a certain point u get a quest/message/cutscene (the map could zoom all
	// the way out and show it) about a flash of light. The message also tells
	// you how many km and in what direction. The message also points out that
	// others will be heading towards it too. At this point a bunch of random
	// ais go towards the light too. (Or maybe the AIs are selected based on
	// criteria like same distance as u, same level as u). The flash of light
	// could be a tower or area or whatever with loads of monsters in it, and
	// something awesome in the middle. Maybe a kind of maze to force u and the
	// AIs to bump in to eachother. So when u arrive there'll probably already
	// be enemis and allies already there. AND they'll probably still be
	// arriving after it.

	// An option to turn quest markers on/off. So u can make it like an old
	// school RPG where u have to read and work shit out. U can put pins on the
	// map urself so that u can mark the names of areas and shit. Maybe another
	// option to mark towns on ur map???? I dunno.... I kind of like the idea of
	// the whole map being blank at thee start. U'll have to explore or follow
	// directions from NPCs. Paths between points of interest are important tho,
	// roads/paths/trails.

	// If an AI can't reach something, they should at least most to the closest
	// square that they can reach. Or give up and do something else i guess...

	// Add object button in PopupSelectObject?, can copy from
	// ObjectsSettingsWindow

	// Editor.gameObjectClicked shoule be rplaced with squareClicked
	// Level/Game.gameObjectClicked shoule be rplaced with squareClicked
	// In userInputEditor and TWICE in UserInputLevel, look for inventory.size()

	// ADD_ACTOR and ADD_OBJECT need to check shareSquare

	// An enemy (or enemy type) that always runs away, and is fast, so u need a
	// good speed stat to catch him and beat him

	// Should be able to place item on square that has unsharable object, make
	// the object u place sharable, this also applies to moving objects...

	// Show a toast "NO SPACE FOR ACTOR" if you try to add a new actor to a
	// square that has no share sapce

	// The update() method in the windows and popup seem unneccesary, when is
	// update called?

	// "Ergonomically terrific!" from a game covered in this
	// https://www.rockpapershotgun.com/2017/01/26/have-you-played-mega-lo-mania/
	// , see comments, would be nice to have quirky silly shit like this :)

	// Issue, what if an object is sharing a square and u turn off
	// canShareSquare?? (in
	// editor)

	// IDEA: the ability to move objects
	// There's a raid on the way and u can move shit around to protect urself
	// Also... the guys coming for the raid have always existed, so u could have
	// killed them before if u met them in the game

	// OBJECT HOLDER
	// Set it up in the test data
	// Set up every time u create object
	// every time u move object
	// every time u put and object in an inventory

	// concept of "passable" or "can be traversed" for objects...

	// contextual option to "eat" people ur attacking, does damage to them,
	// heals u a bit

	// when u click on stacked game objects u'll just get the first
	// one,Game.squareMouseIsOver.inventory.gameObjects.get(0), time to
	// implement pop up windows

	// Context menu should show some details and the options. Options should
	// include "View full deatils"

	// Right click opens context menu, left click does default (talk to allies,
	// attack hostiles...), the deafult action shows under the mouse when u
	// hover (maybe in the form of icons rather than text?) (maybe thats an
	// option in the options menu - icon, text, icon + text or nothing)

	// Put this in marketing blurb - The contextual options on click will
	// encourage experimentation

	// make inventory

	// drawInventory boolean decides whether the inventory is drawn or not
	// For a chest it won't be drawn, for an empty square it will
	// remove weapons collection that objects have

	// check the make copy methods, i think they might b shit

	// Maybe change objects to "loose objects"
	// And have an inventory for each character that u can view

	// Just have ADD OBJECT, which asks Weapon, GameObject or Explosive Object

	// Select owner by clicking on person.
	// When you click on the person you get asked whether to set them or the
	// faction as the owner... or dont have ownership on a factin lvl... i
	// dunno...

	// Need object, square, and actor templates

	// Need concept of vision for player character and enemy characters

	// Vision should definitely be an attribute that you can upgrade

	// Ability - show NPC vision (you can right click on an NPC and select see
	// vision) Then you can see whether they can see you or not. Good for
	// sneaking, stealing shit.

	// InterfaceSelectionWindow and ClassSelection Window can be merged or
	// subclassed in to eachother

	// location and owner interfaces
	// Object and SQuare implement location
	// Actor and Faction implement owner

	// When adding a new object select whtehter it'll be onject class or
	// exploder class

	// Add a title to the class selection window and instance selection window

	// Slider for editor to only list items within X distance, or those on
	// screen.
	// So for actors for example, you can slid all the way to the right to list
	// all actors, or all the way to the left and it will only list those on
	// screen in the settings menu on the left

	// Junk objects automatically give you money, like in Dishonoured

	// TODO write a cat that will run away from characters that get too close,
	// and will run off the screen after being disturbed too many times :D
	// Little details like this make a good game great

	// I need a style.
	// Side pane for game time :)
	// Lets say 300px and on the left at the moment

	// SpeechParts selector in speechpartscriptevent too

	// When adding an actor to the speechPart, make sure u add in his GUID too
	// :D

	// WHen removing actor from speechpart remember to delete their guid

	// End level event doesn't have text section in attributes pane

	// Add Actor butto in SpeechPart is adding and actor to both speech parts

	// Text for the speech isnt showing up in the attributes window, no idea why

	// DOoooooooooooooooooo new class for holding Text instead of Object[]
	// Will work muuuuuch better
	// TEXT(Objects... objects)

	// public void postLoad() {
	// for (ScriptEvent scriptEvent : scriptEvents) {
	// scriptEvent.postLoad();
	// }
	// for (ScriptTrigger scriptTrigger : scriptTriggers) {
	// scriptTrigger.postLoad();
	// }
	//
	// HERE
	// SPEECHEVENTS.POSTLOAD()
	//
	// }

	// when changing the arraylist in speech event need to alter the guid too.
	// OR have preSave() method!?!

	// Speechpart needs total postLoad()

	// preSave() method... probably... maybe... defo...

	// Snow that falls and lands on the ground (shader? probably not...)

	// Edit in editor for strings in script events

	// negative values for ints and floats

	// Copy button for actors

	// The script trigger that triggers when a script event is completed might
	// not work at the moment in the editor coz we're making copies :P

	// Triggers - obj attacked, object health < x, object
	// !destroyed

	// multiple triggers per event

	// Changing object drawable using postload + imagePath

	// Put together a level, something to work with from the get-go

	// After a load - images, colors, weapons wont line up... weapons is the big
	// one coz its multiple selection...

	// Cant change the rgba of colours coz of the attributes bit assuming
	// everything is an
	// int :D

	// change to the imagetexture wont save :D it doesnt update the imagepath,
	// just the texture

	// highlight decorations when selected in the list

	// Put in some opengl 2d particle effects/ water effects/ special effects, i
	// dunno, give it a google

	// could use the save/load functionality to make a undo/redo process in the
	// editor :D

	// colors need to be handled better in the save/load process

	// color delete button... hmmmmmmm.............. keep minimum to one
	// color... if faction has color... put them at color(0) after the delete

	// decorations tab (will need boolean), anchor the decorations to a square i
	// think...

	// esc button usage, particularly when ur going full screen

	// weapons attribute :/
	// could limit the user to 5 weapons.........
	// so the attributes would be weapon0 to weapon4

	// image attribute.......

	// missing actor attributes...
	// title
	// actorLevel
	// travelDistance
	// equipped weapon

	// missing object attributes
	// weapons

	// next i need to handle Lists and booleans generically (TOTES DOABLE)
	// for lists show them the options 0 to X, they tap one of them, BOOM
	// booleans can be toggles

	// Tabs on left of screen - faction, actors, object, scriptevent, script
	// trigger, weapon,
	// level, squares, relationships, decoration

	// I think the attributes window should be beside the settins windwo

	// circle around selected character looks kinda cool... could have circle
	// around selected and trianlge around target... or something...

	// Info dialog thing on right of screen

	// Ghost object / Ghost actor should appear over the square when ur in add
	// mode and... hovering over a square

	// check unnessesary exceptions, like the one in WindowButton

	// I want whatever ur hovering over to light up, this no light up shite it
	// BULLSHIT
	// Could just be an outline of the square... i dunno

	// Under the mouse it should say MOVE,SELECT,ATTACK,HEAL like a contextual A
	// button. Mayeb the hover details should come out of the mouse?!
	// Interesting...

	// look up how to switch between textures and quads in opengl, coz the way
	// im doing it feels like BS

	// buttons do not resize nicely

	// buttons do not take the click from the level

	// vsync in settings Display.setVSyncEnabled(true);

	// FUCK PIVOT
	// board size (text box)
	// add/remove/edit faction (text box, check box, button, dropdown)
	// add/remove/edit character
	// add/remove/edit object
	// add/remove/edit script event
	// add/remove/edit script trigger

	// So GUI items - theres swing, and a bunch of libs that provide gui
	// elements for lwjgl...
	// http://wiki.lwjgl.org/wiki/Game_Engines_and_Libraries_Using_LWJGL#GUI_Libraries
	// i dunnooooo.....

	// I think I need a level editor...
	// Based on Classes...
	// How hard could it be???
	// Look at the map maker for TT... Might reveal some info on the mechanics
	// they use...
	// I want to be able to try out a level im playing immediately (see aoe2,
	// the TT lvl editor is bollox and must have seriously hindered development)

	// unit tests + monkey...

	// random generator button for the level editor... might help people (me...)
	// with creativity

	// Why does script trigger get level?

	// Need to maintain an array of active events

	// multiple triggers for an event :D

	// I've put in the ability to have multiple scriptevents at a time, but...
	// need to sort out some sort of precedence, particularly for clicks
	// (maybe...)

	// Refactor day

	// Pretty art day

	// Try build a full level...
	// Needs dialog at the start
	// Needs a goal
	// Needs dialog at the end
	// What size is a FE level? Awakenings chapter 9 is 20x20, chapter 5 is
	// 16x21, prologue chapter is 14x15,
	// http://fireemblem.wikia.com/wiki/List_of_chapters_in_Fire_Emblem:_Awakening

	// bug - pow doesnt show up coz of the hover preview

	// http://www.reddit.com/r/gamedev

	// Public domain map to build my world around, this link may be public
	// domain stuff... http://www.usgs.gov/pubprod/

	// write equipBestWeapon method - create fights at that distance and kill
	// dem bitches... interesting problem coz u know the enemy is gonna select
	// the best weapon to counter you... need to take that in to a/c in the sort
	// method... maybe... it's quite a confusing problem. BUT... it's an AI
	// problem, not a game mechanics problem, so it's fine to be complicated.
	// ALSO... need to check this for all sorting of best fights. If weapon will
	// kill someone or cant be countered... its the best :D
	// HERE IT IS FOR ALL RANGES (so for not all ranges it sshould be easier...)
	// do it from the enemy's point of view......
	// so all attacks from all ranges......
	// work out how they'll counter......
	// then compile their selections the (0) after sorting...
	// switch to our guy, using those fights... pick the best...

	// ANALYZE TT VS FE (there might be a youtube video or an aticle about it.
	// See where one beats the other.)

	// The way they hold the selected weapon is really cool, build that in, an
	// anchor point for the weapons (for where the persons hand(s) are), and
	// maybe arms with anchor points... and maybe some elbows w/ anchor point on
	// those arms... animate them the way that u do for 3d items like that....
	// (move the camera, not the arm...)

	// do a withinBounds() method for the buttons

	// animate the buttons comin out of the playa

	// When you hover over a square you can move to show where you can reach and
	// with what weapon... ot something... i dunno, maybe just highlight the
	// squares

	// Push

	// In the fight sorter, should take enemy damage post attack in to account,
	// they keep attacking that guy w/ 100 health :D

	// pushing (the skips, enemies, allies)

	// third (green) team with relationships towards others

	// try make a 16 bit character for the game board out of a profile image,
	// all pixels and shit and awesome, i could use like any body... once it
	// shrunk down enough it wont matter...

	// Character made out of a creepy doll face!

	// do profile image of character w/ gas mask in photoshop

	// weapon in the dumpster

	// attributes along bottom of character's square

	// More AI (see the enum list at the top of Faction.java)

	// For decoration - a can, a bottle, rubbish bag

	// The VS looks really cool on its own, maybe move the bars out so that u
	// can see the VS, then make the bars drain in the same direction

	// advantage/disadvantage (goes on top of weapon, tick VS X), special
	// effects(sleep, confuse, poison etc.)

	// Look up what type of text is most legible

	// I think the things should empty from a different direction, dont like the
	// middle. BUT... the text is getting hard to read :D

	// fade out the other baddies when looking at preview, or hide them
	// completely... or... i dunno... but its overlapping w/ weapon images on
	// other enemies squares which is shite.

	// use different colour than white for highlights, its confusing

	// blocked symbol if they have no weapon to attack with, or maybe leave it
	// as nothing?

	// make a single item for all these reused colors, WAY too many calls to new
	// color
	// dialog has a weird drawing thing...
	// SOmething clever where the health bar pulls back to reveal a skull or
	// something :P
	// also... need textures rather than flat color probably
	// Need to find a way to make the preview clearer, at the moment its just
	// messy and confusing. Maybe lines. Or health bars only as big as a weapon
	// symbol... Also, health bars need a bg (same as the normal health bars
	// probably...

	// see if theres a way to force the white lines to draw even when they're
	// tiny, maybe if i draw lines rather than quads?

	// make the white lines translucent? that might look better

	// Make a util to fill an area with a texture

	// seperate the attack + counter attack by 250ms

	// Look up "the mechanics of fire emblem"

	// feck it in to Android and c what happens ;)

	// enemy move grid all/single/toggle multiple

	// button to show the battle preview info for all enemies

	// undo button for moves

	// Special preview for when neither of them have weapons :D, when
	// fights.size() == 0

	// Look up strategy guides for similar games, will help with AI writing,
	// will help clarify some of the subtleties in these games

	// DONE

	// go through the code and add comments b4 i foget what the fuck i was doing

	// merge actor and game object?

	// see if u can find a font that will work over 60

	// should i take some time out and make it look prettier? might make me
	// appreciate it more. Like take a saturday and do graphical tweaks :) YUS

	// the bars look a lot better on the right, that could just be because of
	// the player model

	// Shit written on walls/ground like stealth bastard. Text could take up a
	// whole row of a grid. One word per row. Cool style. "YOUR TURN",
	// "YOU WIN", "HERO VANQUISHED", "YOU DIED", Dark souls - (BONFIRE LIT, YOU
	// DIED, YOU DEFEATED), i particularly like bonfire lit, coz it seemed so
	// retarded. Bloodborne - "Prey Slaughtered" VERY COOL

	// Work on user manual, make it COOL

	// Work on design rules

	// match enemy weapons with yours for what they'll counter w/, or draw
	// arrows from one to the other :D MESSYI

	// SORT THE NOTES IN TO CATEGORIES

	// Underneath of bar goes from white to black as they're dying, also colored
	// part of bar goes down

	// match enemy weapons with yours for what they'll counter w/, or draw
	// arrows from one to the other :D MESSYI

	// DONE

	// Dialogs stay open if open after ending turn

	// attacking enemies
	// currently when i click on an enemy it just selects them
	// in TT you attack enemies by selecting the sword then the square to
	// attack, this is a good system, flexible but slow
	// also... in TT it doesn't show u where u can attack given ur start pos
	// in FE you move beside them and attack

	// ranges -
	// currently weapon range works as any range up to it's range value
	// So if range is 3, the weapon can attack 1,2 or 3 blocks away
	// Does it make sense u can use a bow at short range? kinda does, and
	// doesn't...
	// It's close enough to get hit b a sword so......
	// Also, i think all the box dudes should have weak daggers by default, and
	// be shit at using them :D

	// WRITE A RULE BOOK, this is cool and appreciated by retro fans and easy to
	// do
	// ALSO... it must be completely pointless coz if u need instructions ur app
	// is broken

	// More enemies on screen for better testing

	// Colorize the log to match enemy/ally

	// When u highlight a square to move to, also highlight where u can attack
	// if you move there... LOTS of highlighting.
	// Maybe like. X on the squares u can attack
	// OOOOORRRRR show the icon of the weapon(s) that can attack there ;)
	// YES, start putting in all these useful icons....

	// AI to take over on their turn
	// DONE - Phase 1. End their turn
	// DONE - Phase 2. Move their dudes randomly
	// DONE - Phase 3. Put timers in so that u can see them move
	// Move towards nearest enemy
	// Phase 4. Turn level plan
	// Phase 5. Battle level plan

	// There's got to be a better way to calculate distances for EVERY square
	// from the selected guy (mark selected guys as zero, all others as
	// integer.max
	// got around in a circle and set all free squares to 1 (or 0 + travel cost)
	// go around all the 1s and set all free squares that dont have a number to
	// (1 + travelcost)
	// continue until u run out of squares w/ numbers to go around
	// this sounds a lot better :'D
	// blllllllergh
	// so if travel distance is 5 and attack range is 2 1-5 is reachable, 1-7 is
	// attackable (unless we have a min range!!!

	// Don't highlight AI square when u mouse over it...

	// object should give indication it contains something,
	// a little twinkle or something
	// so that user doesnt have to click everything

	// show path for attack squares too
	// cant necessarily attack squares u can walk if ur min range isnt 1

	// undo button

	// how about using neural networking to teach different types of strategies?
	// offensive, defensive...
	// would have to be a random setup every time
	// visibles are - empty squares, enemies squares, allies squares,
	// impasssable squares

	// tkae time to move.....

	// make the dialog stay on screen by moving to other corners

	// put photoshop on ur phone
	// Square.actor, Square.inanimateObject
	// move to 128 px per aquare
	// scrolling ;) that's a good one!
	// convert to cm/inches for mouse dragging calculation!?
	// calc reachable and attackable squares is being called twice in update...

	// route drawer(the route is sorta worked out when u finding which squares
	// are available to to) (when we end on a square could have
	// routeTo.add(this.squaresInPath.clone)
	// //WAIT... need to add ROUTETO even if square is not at the end of a route
	// :D
	// For now I could just highlight squares in the route, draw the lines and
	// arrows later
	// route drawn needs to show how many steps it takes... probably... i
	// think..... or how many u'll have left????
	// dialogs
	// factions
	// attacking

	// Weapons and weapon reach
	// On the square for weapon reach draw
	// How it will compare to the enemey

	// SWORD^ VS AXEv
	// 90% VS 50%
	// 11x2 VS 12x1

	// BOW VS 0
	// 80% VS 0%
	// 20 VS 0

	// Color blue/red/yellow depending on how u'll do

	// Go to some museuoms and take ohots of shit to use

	// Read some devblogs and indepth articles (RPS should point me in the right
	// direction) (see if i can filter by blogs/diaries/stories)

	// Take a photo of ur own face u dork!!

	// Make all the over the top details configurable
	// The weapons icons beside the characters
	// The wepaons icons on where you can attack
	// maybe ur weapon icons are always on the left and the enemies are always
	// on the right, w/ a VS or something... i dunno... VS what they'd retaliate
	// with, i'm sure i'll work it out CONCEPT GO

	// In log text, make it personal to the player / target / weapons that were
	// used, so that the actors / weapons / styles will grow on the player
	// Also... if they do something clever, tell them (ITS SUPER EFFECTIVE!!!!!)
	// (Robots don't like lightning strikes, ShitBot 3000 has been overcharged)
	// or speach bubbles rather than log text? or speachbubbles and log the
	// speach like aoe2)
}
