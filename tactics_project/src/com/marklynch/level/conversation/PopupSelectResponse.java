package com.marklynch.level.conversation;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.popup.Popup;
import com.marklynch.level.popup.PopupButton;
import com.marklynch.ui.button.ClickListener;

public class PopupSelectResponse extends Popup {

	ArrayList<ConversationResponse> conversationResponses;

	public PopupSelectResponse(float width, Level level, ArrayList<ConversationResponse> conversationResponses) {

		super(width, level, Game.level.squares[3][3]);
		this.conversationResponses = conversationResponses;
		updateObjectsButtons();

	}

	public void updateObjectsButtons() {

		buttons.clear();

		for (int i = 0; i < conversationResponses.size(); i++) {
			final int index = i;

			// The line and the highlight are drawn in relation to zoom and
			// position...

			// BUT... I dont want the buttons to zoom :P

			final PopupButton objectButton = new PopupButton(0, 30 + i * 30, 200, 30, null, null,
					"" + conversationResponses.get(i).text, true, true, conversationResponses.get(i).text, this);

			objectButton.clickListener = new ClickListener() {

				@Override
				public void click() {

					// gameObjectSelected(square.inventory.get(index));
					// // editor.popupSelectObject = null; MAYYBE?
					//
					// // editor.clearSelectedObject();
					// // editor.depressButtonsSettingsAndDetailsButtons();
					// // editor.editor.editorState =
					// // EDITOR_STATE.MOVEABLE_OBJECT_SELECTED;
					// // editor.selectedGameObject =
					// // Game.level.inanimateObjects.get(index);
					// // editor.attributesWindow = new AttributesDialog(200,
					// 200,
					// // 200, editor.selectedGameObject, editor);
					// // // getButton(editor.selectedGameObject).down = true;
					// // objectButton.down = true;
				}
			};
			buttons.add(objectButton);

		}

		highlightedButton = buttons.get(highlightedButtonIndex);
		highlightedButton.highlighted = true;

	}
}
