package com.marklynch.notes;

public class TODO {

	// TO DO

	// attacking enemies
	// currently when i click on an enemy it just selects them
	// in TT you attack enemies by selecting the sword then the square to
	// attack, this is a good system, flexible but slow
	// also... in TT it doesn't show u where u can attack given ur start pos
	// in FE you move beside them and attack

	// ranges -
	// currently weapon range works as any range up to it's range value
	// So if range is 3, the weapon can attack 1,2 or 3 blocks away

	// WRITE A RULE BOOK

	// More enemies on screen for better testing

	// Colorize the log to match enemy/ally

	// Log on the screen
	// e.g. X moved to Y
	// e.g. X attacked Y for Z damage

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

	// DONE
}
