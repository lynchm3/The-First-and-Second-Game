package com.marklynch.ui.button;

import java.util.concurrent.CopyOnWriteArrayList;

import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.Texture;

public abstract class Button {

	public transient Texture enabledTexture;
	public transient Texture disabledTexture;
	public float x, y, width, height;
	public boolean enabled = true;
	public boolean down = false;
	public ClickListener clickListener;
	public ClickListener doubleClickListener;
	protected Object text;
	protected boolean highlighted = false;
	// public Tooltip tooltip;
	public CopyOnWriteArrayList<Tooltip> tooltips = new CopyOnWriteArrayList<Tooltip>();

	public Button(float x, float y, float width, float height, String enabledTexturePath, String disabledTexturePath,
			Object text, String tooltipText) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.enabledTexture = ResourceUtils.getGlobalImage(enabledTexturePath, false);
		this.disabledTexture = ResourceUtils.getGlobalImage(disabledTexturePath, false);
		this.text = text;
		if (tooltipText != null) {
			this.tooltips.add(new Tooltip(false, Tooltip.WHITE, tooltipText));
		}
		// this.tooltipText = tooltipGroup = new TooltipGroup();
		// tooltipGroup.add(new Tooltip(false, Tooltip.WHITE, tooltipText));
		// }

	}

	public void setClickListener(ClickListener clickListener) {
		this.clickListener = clickListener;
	}

	public void setDoubleClickListener(ClickListener doubleClickListener) {
		this.doubleClickListener = doubleClickListener;
	}

	public boolean calculateIfPointInBoundsOfButton(float mouseX, float mouseY) {
		if (mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height) {
			return true;
		}
		return false;
	}

	long lastClickTime = 0;

	public void click() {

		if (!enabled)
			return;

		long thisClickTime = System.currentTimeMillis();
		if (thisClickTime - lastClickTime < 500) {
			doubleClick();
		} else {

			if (clickListener != null) {
				clickListener.click();
			}
		}

		lastClickTime = thisClickTime;
	}

	public void doubleClick() {
		if (doubleClickListener != null && enabled) {
			doubleClickListener.click();
		}
	}

	public abstract void draw();

	public void highlight() {
		highlighted = true;

	}

	public void removeHighlight() {
		highlighted = false;

	}

	// public void drawTooltip() {
	// if (tooltipGroup != null)
	// tooltipGroup.drawStaticUI();
	// }
	//
	public void setTooltipText(Object... tooltipText) {
		if (tooltipText != null) {
			tooltips.clear();
			tooltips.add(new Tooltip(false, Tooltip.WHITE, tooltipText));
		}
	}
	//
	// public void setTooltipText(CopyOnWriteArrayList<Object> tooltipText) {
	// if (tooltipGroup != null)
	// tooltipGroup.setTooltipText(tooltipText);
	// }

}
