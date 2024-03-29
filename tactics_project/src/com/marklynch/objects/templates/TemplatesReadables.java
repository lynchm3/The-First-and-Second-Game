package com.marklynch.objects.templates;

import com.marklynch.Game;
import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Signpost;
import com.marklynch.objects.inanimateobjects.WantedPoster;

public class TemplatesReadables {

	public TemplatesReadables() {
		Templates.ROCK_WITH_ETCHING = new GameObject();
		Templates.ROCK_WITH_ETCHING.name = "Rock with Etching";
		Templates.ROCK_WITH_ETCHING.setImageAndExtrapolateSize("rock_with_etching.png");
		Templates.ROCK_WITH_ETCHING.totalHealth = Templates.ROCK_WITH_ETCHING.remainingHealth = 200;
		Templates.ROCK_WITH_ETCHING.weight = 200f;
		Templates.ROCK_WITH_ETCHING.value = 14;
		Templates.ROCK_WITH_ETCHING.anchorX = 0;
		Templates.ROCK_WITH_ETCHING.anchorY = 0;
		Templates.ROCK_WITH_ETCHING.bigShadow = true;
		Templates.ROCK_WITH_ETCHING.canShareSquare = false;
		Templates.ROCK_WITH_ETCHING.fitsInInventory = false;
		Templates.ROCK_WITH_ETCHING.templateId = GameObject.generateNewTemplateId();
		Templates.ROCK_WITH_ETCHING.flipYAxisInMirror = false;
		Templates.ROCK_WITH_ETCHING.moveable = false;

		Templates.SCROLL = new GameObject();
		Templates.SCROLL.name = "Scroll";
		Templates.SCROLL.setImageAndExtrapolateSize("scroll.png");
		Templates.SCROLL.totalHealth = Templates.SCROLL.remainingHealth = 1;
		Templates.SCROLL.weight = 2f;
		Templates.SCROLL.value = 100;
		Templates.SCROLL.anchorX = 16;
		Templates.SCROLL.anchorY = 16;
		Templates.SCROLL.templateId = GameObject.generateNewTemplateId();

		Templates.SIGN = new GameObject();
		Templates.SIGN.name = "Sign";
		Templates.SIGN.setImageAndExtrapolateSize("sign.png");
		Templates.SIGN.totalHealth = Templates.SIGN.remainingHealth = 100;
		Templates.SIGN.weight = 76;
		Templates.SIGN.value = 15;
		Templates.SIGN.anchorX = 0;
		Templates.SIGN.anchorY = 0;
		Templates.SIGN.bigShadow = true;
		Templates.SIGN.canShareSquare = false;
		Templates.SIGN.fitsInInventory = false;
		Templates.SIGN.moveable = false;
		Templates.SIGN.templateId = GameObject.generateNewTemplateId();
		Templates.SIGN.flipYAxisInMirror = false;
		Templates.SIGN.moveable = false;

		Templates.WANTED_POSTER = new WantedPoster();
		Templates.WANTED_POSTER.name = "Wanted Poster";
		Templates.WANTED_POSTER.setImageAndExtrapolateSize("wanted_poster.png");
		Templates.WANTED_POSTER.totalHealth = Templates.WANTED_POSTER.remainingHealth = 100;
		Templates.WANTED_POSTER.weight = 77;
		Templates.WANTED_POSTER.value = 18;
		Templates.WANTED_POSTER.anchorX = 0;
		Templates.WANTED_POSTER.anchorY = 0;
		Templates.WANTED_POSTER.templateId = GameObject.generateNewTemplateId();

		Templates.SIGNPOST = new Signpost();
		Templates.SIGNPOST.name = "Signpost";
		Templates.SIGNPOST.setImageAndExtrapolateSize("signpost.png");
		Templates.SIGNPOST.totalHealth = Templates.SIGNPOST.remainingHealth = 100;
		Templates.SIGNPOST.heightRatio = 1.25f;
		Templates.SIGNPOST.drawOffsetRatioY = -0.25f;
		Templates.SIGNPOST.drawOffsetY = Templates.SIGNPOST.drawOffsetRatioY * Game.SQUARE_HEIGHT;
		Templates.SIGNPOST.weight = 124f;
		Templates.SIGNPOST.value = 31;
		Templates.SIGNPOST.canShareSquare = false;
		Templates.SIGNPOST.fitsInInventory = false;
		Templates.SIGNPOST.moveable = false;
		Templates.SIGNPOST.bigShadow = true;
		Templates.SIGNPOST.templateId = GameObject.generateNewTemplateId();
		Templates.SIGNPOST.flipYAxisInMirror = false;
		Templates.SIGNPOST.moveable = false;

		Templates.DOCUMENTS = new GameObject();
		Templates.DOCUMENTS.name = "Documents";
		Templates.DOCUMENTS.setImageAndExtrapolateSize("documents.png");
		Templates.DOCUMENTS.totalHealth = Templates.DOCUMENTS.remainingHealth = 5;
		Templates.DOCUMENTS.weight = 2f;
		Templates.DOCUMENTS.value = 0;
		Templates.DOCUMENTS.anchorX = 12;
		Templates.DOCUMENTS.anchorY = 28;
		Templates.DOCUMENTS.templateId = GameObject.generateNewTemplateId();
		Templates.DOCUMENTS.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE,
				new Stat(HIGH_LEVEL_STATS.SLASH_DAMAGE, 1));

		Templates.MESSAGE = new GameObject();
		Templates.MESSAGE.name = "Message";
		Templates.MESSAGE.setImageAndExtrapolateSize("message.png");
		Templates.MESSAGE.totalHealth = Templates.DOCUMENTS.remainingHealth = 5;
		Templates.MESSAGE.weight = 2f;
		Templates.MESSAGE.value = 0;
		Templates.MESSAGE.anchorX = 12;
		Templates.MESSAGE.anchorY = 28;
		Templates.MESSAGE.templateId = GameObject.generateNewTemplateId();
		Templates.MESSAGE.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE, new Stat(HIGH_LEVEL_STATS.SLASH_DAMAGE, 1));

	}

}
