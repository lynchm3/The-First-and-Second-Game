package com.marklynch.ui.button;

import java.util.ArrayList;

public class TooltipGroup extends ArrayList<Tooltip> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public float[] toolTipGroupDimensions = new float[2];
	public ArrayList<Tooltip> tooltips;

	public TooltipGroup() {

		tooltips = new ArrayList<Tooltip>();
	}

	@Override
	public boolean add(Tooltip tooltip) {
		boolean result = super.add(tooltip);
		toolTipGroupDimensions[0] = Math.max(toolTipGroupDimensions[0], tooltip.dimensions[0]);
		toolTipGroupDimensions[1] += tooltip.dimensions[1];
		return result;
	}

	public void drawStaticUI() {
		if (tooltips != null && tooltips.size() > 0) {
			tooltips.get(0).drawStaticUI();
			for (int i = 1; i < tooltips.size(); i++) {
				tooltips.get(i).drawStaticUI();
			}
		}

	}

	public void setTooltipText(Object[] tooltipText) {

		if (tooltipText != null && tooltips.size() > 0) {
			tooltips.remove(0);
			tooltips.add(new Tooltip(false, tooltipText));
		}

	}

	public void setTooltipText(ArrayList<Object> tooltipText) {

		if (tooltipText != null && tooltips.size() > 0) {
			tooltips.remove(0);
			tooltips.add(new Tooltip(false, tooltipText));
		}

	}

}