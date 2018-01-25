package com.marklynch.objects.templates;

import com.marklynch.objects.GameObject;
import com.marklynch.objects.Readable;
import com.marklynch.objects.Sign;
import com.marklynch.objects.WantedPoster;

public class TemplatesReadables {

	public TemplatesReadables() {
		Templates.ROCK_WITH_ETCHING = new Sign();
		Templates.ROCK_WITH_ETCHING.name = "Rock with Etching";
		Templates.ROCK_WITH_ETCHING.imageTexturePath = "rock_with_etching.png";
		Templates.ROCK_WITH_ETCHING.totalHealth = Templates.ROCK_WITH_ETCHING.remainingHealth = 200;
		Templates.ROCK_WITH_ETCHING.widthRatio = 1f;
		Templates.ROCK_WITH_ETCHING.heightRatio = 1f;
		Templates.ROCK_WITH_ETCHING.drawOffsetRatioX = 0f;
		Templates.ROCK_WITH_ETCHING.drawOffsetRatioY = 0f;
		Templates.ROCK_WITH_ETCHING.soundWhenHit = 1f;
		Templates.ROCK_WITH_ETCHING.soundWhenHitting = 1f;
		Templates.ROCK_WITH_ETCHING.soundDampening = 1f;
		Templates.ROCK_WITH_ETCHING.stackable = false;
		Templates.ROCK_WITH_ETCHING.weight = 200f;
		Templates.ROCK_WITH_ETCHING.value = 14;
		Templates.ROCK_WITH_ETCHING.anchorX = 0;
		Templates.ROCK_WITH_ETCHING.anchorY = 0;
		Templates.ROCK_WITH_ETCHING.templateId = GameObject.generateNewTemplateId();

		Templates.SCROLL = new Readable();
		Templates.SCROLL.name = "Scroll";
		Templates.SCROLL.imageTexturePath = "scroll.png";
		Templates.SCROLL.totalHealth = Templates.SCROLL.remainingHealth = 1;
		Templates.SCROLL.widthRatio = 1f;
		Templates.SCROLL.heightRatio = 1f;
		Templates.SCROLL.drawOffsetRatioX = 0f;
		Templates.SCROLL.drawOffsetRatioY = 0f;
		Templates.SCROLL.soundWhenHit = 1f;
		Templates.SCROLL.soundWhenHitting = 1f;
		Templates.SCROLL.soundDampening = 1f;
		Templates.SCROLL.stackable = false;
		Templates.SCROLL.weight = 2f;
		Templates.SCROLL.value = 100;
		Templates.SCROLL.anchorX = 0;
		Templates.SCROLL.anchorY = 0;
		Templates.SCROLL.templateId = GameObject.generateNewTemplateId();

		Templates.SIGN = new Sign();
		Templates.SIGN.name = "Sign";
		Templates.SIGN.imageTexturePath = "SIGN.png";
		Templates.SIGN.totalHealth = Templates.SIGN.remainingHealth = 100;
		Templates.SIGN.widthRatio = 1f;
		Templates.SIGN.heightRatio = 1f;
		Templates.SIGN.drawOffsetRatioX = 0f;
		Templates.SIGN.drawOffsetRatioY = 0f;
		Templates.SIGN.soundWhenHit = 1f;
		Templates.SIGN.soundWhenHitting = 1f;
		Templates.SIGN.soundDampening = 1f;
		Templates.SIGN.stackable = false;
		Templates.SIGN.weight = 76;
		Templates.SIGN.value = 15;
		Templates.SIGN.anchorX = 0;
		Templates.SIGN.anchorY = 0;
		Templates.SIGN.templateId = GameObject.generateNewTemplateId();

		Templates.WANTED_POSTER = new WantedPoster();
		Templates.WANTED_POSTER.name = "Wanted Poster";
		Templates.WANTED_POSTER.imageTexturePath = "wanted_poster.png";
		Templates.WANTED_POSTER.totalHealth = Templates.WANTED_POSTER.remainingHealth = 100;
		Templates.WANTED_POSTER.widthRatio = 1f;
		Templates.WANTED_POSTER.heightRatio = 1f;
		Templates.WANTED_POSTER.drawOffsetRatioX = 0f;
		Templates.WANTED_POSTER.drawOffsetRatioY = 0f;
		Templates.WANTED_POSTER.soundWhenHit = 1f;
		Templates.WANTED_POSTER.soundWhenHitting = 1f;
		Templates.WANTED_POSTER.soundDampening = 1f;
		Templates.WANTED_POSTER.stackable = false;
		Templates.WANTED_POSTER.weight = 77;
		Templates.WANTED_POSTER.value = 18;
		Templates.WANTED_POSTER.anchorX = 0;
		Templates.WANTED_POSTER.anchorY = 0;
		Templates.WANTED_POSTER.templateId = GameObject.generateNewTemplateId();

		Templates.SIGNPOST = new Sign();
		Templates.SIGNPOST.name = "Signpost";
		Templates.SIGNPOST.imageTexturePath = "signpost.png";
		Templates.SIGNPOST.totalHealth = Templates.SIGNPOST.remainingHealth = 100;
		Templates.SIGNPOST.widthRatio = 1f;
		Templates.SIGNPOST.heightRatio = 1.25f;
		Templates.SIGNPOST.drawOffsetRatioX = 0f;
		Templates.SIGNPOST.drawOffsetRatioY = -0.25f;
		Templates.SIGNPOST.soundWhenHit = 1f;
		Templates.SIGNPOST.soundWhenHitting = 1f;
		Templates.SIGNPOST.soundDampening = 1f;
		Templates.SIGNPOST.stackable = false;
		Templates.SIGNPOST.weight = 124f;
		Templates.SIGNPOST.value = 31;
		Templates.SIGNPOST.anchorX = 0;
		Templates.SIGNPOST.anchorY = 0;
		Templates.SIGNPOST.templateId = GameObject.generateNewTemplateId();

		Templates.DOCUMENTS = new Readable();
		Templates.DOCUMENTS.name = "Documents";
		Templates.DOCUMENTS.imageTexturePath = "documents.png";
		Templates.DOCUMENTS.totalHealth = Templates.DOCUMENTS.remainingHealth = 5;
		Templates.DOCUMENTS.widthRatio = 1f;
		Templates.DOCUMENTS.heightRatio = 1f;
		Templates.DOCUMENTS.drawOffsetRatioX = 0f;
		Templates.DOCUMENTS.drawOffsetRatioY = 0f;
		Templates.DOCUMENTS.soundWhenHit = 1f;
		Templates.DOCUMENTS.soundWhenHitting = 1f;
		Templates.DOCUMENTS.soundDampening = 1f;
		Templates.DOCUMENTS.stackable = false;
		Templates.DOCUMENTS.weight = 2f;
		Templates.DOCUMENTS.value = 0;
		Templates.DOCUMENTS.anchorX = 0;
		Templates.DOCUMENTS.anchorY = 0;
		Templates.DOCUMENTS.templateId = GameObject.generateNewTemplateId();
		Templates.DOCUMENTS.slashDamage = 1;

	}

}
