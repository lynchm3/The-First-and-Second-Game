package com.marklynch.notes;

public class TODO {

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
