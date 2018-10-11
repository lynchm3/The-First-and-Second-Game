package com.marklynch.ui.button;

import java.util.ArrayList;

public class TooltipGroup extends ArrayList<Tooltip> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public float[] toolTipGroupDimensions = new float[2];

	public TooltipGroup() {
	}

	@Override
	public boolean add(Tooltip tooltip) {
		boolean result = super.add(tooltip);
		toolTipGroupDimensions[0] = Math.max(toolTipGroupDimensions[0], tooltip.dimensions[0]);
		toolTipGroupDimensions[1] += tooltip.dimensions[1];
		return result;
	}

	public void drawStaticUI() {
		System.out.println("TooltipGroup.drawStaticUI");
		if (this.size() > 0) {
			get(0).drawStaticUI();
			for (int i = 1; i < this.size(); i++) {
				get(i).drawStaticUI();
			}
		}
	}

	public void setTooltipText(Object[] tooltipText) {
		if (tooltipText != null && this.size() > 0) {
			this.remove(0);
			this.add(new Tooltip(false, tooltipText));
		}

	}

	public void setTooltipText(ArrayList<Object> tooltipText) {
		if (tooltipText != null && this.size() > 0) {
			this.remove(0);
			this.add(new Tooltip(false, tooltipText));
		}

	}

}