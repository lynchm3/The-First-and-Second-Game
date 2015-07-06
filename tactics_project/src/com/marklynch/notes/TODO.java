package com.marklynch.notes;

public class TODO {

	// its showing "x moved to y" when they stay on the same spot

	// Attack -> weapon selection -> Attack enemy

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

	// stop running when minimized

	// watch the fire emblem battle animations, the 3d ones are pretty good
	// one where the screen turned red and black...

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

	// multi colored log msgs

	// grey out actor if they cant move or atttack?

	// DONE
}
