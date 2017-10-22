package com.marklynch.level.constructs.journal;

import java.util.ArrayList;
import java.util.Comparator;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.conversation.ConversationPart;
import com.marklynch.level.quest.Quest;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.ui.Draggable;
import com.marklynch.ui.Scrollable;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.ui.button.Link;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

public class Journal implements Draggable, Scrollable, Comparator<Quest> {

	public static final String active = "Active";
	public static final String resolved = "Resolved";

	public static enum MODE {
		LOG, CONVERSATION
	};

	public MODE mode = MODE.LOG;

	public boolean showing = false;

	public static Quest questToDisplayInJournal = null;
	public static ArrayList<Quest> activeQuests = new ArrayList<Quest>();

	int listX;
	int listY;
	int listBorder;
	int listWidth;

	int contentX;
	int contentY;
	int contentBorder;

	int listItemHeight;

	float activeDrawPosition;
	float resolvedDrawPosition;

	transient static int bottomBorderHeight;

	public static ArrayList<LevelButton> buttons = new ArrayList<LevelButton>();
	public static ArrayList<LevelButton> buttonsToMakeQuestAcive = new ArrayList<LevelButton>();
	public static ArrayList<LevelButton> buttonsToDisplayQuest = new ArrayList<LevelButton>();
	public static ArrayList<LevelButton> buttonsToTrackObjectives = new ArrayList<LevelButton>();

	public ArrayList<Link> logLinks = new ArrayList<Link>();
	public ArrayList<Link> conversationLinks = new ArrayList<Link>();
	public ArrayList<Link> objectiveLinks = new ArrayList<Link>();

	// public static ArrayList<Link> links;
	// Close button
	static LevelButton buttonLog;
	static String log = "LOG";
	static int logLength = Game.font.getWidth(log);
	static LevelButton buttonConversations;
	static String conversations = "CONVERSATIONS";
	static int conversationsLength = Game.font.getWidth(conversations);
	static LevelButton buttonClose;

	public static Texture checkBoxChecked;
	public static Texture checkBoxUnchecked;
	public static Texture exclamationTexture;

	public Journal() {
		resize();

		buttonLog = new LevelButton(contentX, 0, logLength, 30f, "end_turn_button.png", "end_turn_button.png", log,
				true, true, Color.WHITE, Color.BLACK, null);
		buttonLog.setClickListener(new ClickListener() {
			@Override
			public void click() {
				mode = MODE.LOG;
				buttonLog.setTextColor(Color.BLACK);
				buttonLog.buttonColor = Color.WHITE;

				buttonConversations.setTextColor(Color.WHITE);
				buttonConversations.buttonColor = Color.BLACK;
			}
		});
		buttons.add(buttonLog);

		buttonConversations = new LevelButton(contentX + logLength + 16, 0, conversationsLength, 30f,
				"end_turn_button.png", "end_turn_button.png", conversations, true, true, Color.BLACK, Color.WHITE,
				null);
		buttonConversations.setClickListener(new ClickListener() {
			@Override
			public void click() {
				mode = MODE.CONVERSATION;
				buttonConversations.setTextColor(Color.BLACK);
				buttonConversations.buttonColor = Color.WHITE;

				buttonLog.setTextColor(Color.WHITE);
				buttonLog.buttonColor = Color.BLACK;
			}
		});
		buttons.add(buttonConversations);

		buttonClose = new LevelButton(Game.halfWindowWidth - 25f, bottomBorderHeight, 70f, 30f, "end_turn_button.png",
				"end_turn_button.png", "CLOSE [J]", true, false, Color.BLACK, Color.WHITE, null);
		buttonClose.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Game.level.openCloseJournal();
			}
		});
		buttons.add(buttonClose);
	}

	public static void loadStaticImages() {
		checkBoxChecked = ResourceUtils.getGlobalImage("check_box_checked.png");
		checkBoxUnchecked = ResourceUtils.getGlobalImage("check_box_unchecked.png");
		exclamationTexture = ResourceUtils.getGlobalImage("exclamation_mark.png");
	}

	public void resize() {
		listX = 0;
		listY = 0;
		listBorder = 16;
		listWidth = 300;

		contentX = listX + listWidth + listBorder * 2;
		contentY = 30;
		contentBorder = 16;

		listItemHeight = 30;

		bottomBorderHeight = 384;

	}

	public void open() {
		// Sort quests by active, keep selectedQuest at top
		// And also sort by completed...

		Level.quests.sort(this);
		resize();
		showing = true;

		buttons.clear();
		buttonsToMakeQuestAcive.clear();
		buttonsToDisplayQuest.clear();
		buttons.add(buttonLog);
		buttons.add(buttonConversations);
		buttons.add(buttonClose);

		generateLinks();

		int questsDrawnInList = 0;

		boolean activeAdded = false;
		boolean resolvedAdded = false;

		for (final Quest quest : Level.quests) {
			if (quest.started) {

				if (!activeAdded && !quest.resolved) {

					final LevelButton activeButton = new LevelButton(listX + listBorder,
							listY + listBorder + questsDrawnInList * listItemHeight, listWidth - listItemHeight,
							listItemHeight, "end_turn_button.png", "end_turn_button.png", this.active, true, true,
							Color.BLACK, Color.WHITE, "Display quest details");
					questsDrawnInList++;
					activeAdded = true;
					buttons.add(activeButton);
				}

				if (!resolvedAdded && quest.resolved) {

					final LevelButton activeButton = new LevelButton(listX + listBorder,
							listY + listBorder + questsDrawnInList * listItemHeight, listWidth - listItemHeight,
							listItemHeight, "end_turn_button.png", "end_turn_button.png", this.resolved, true, true,
							Color.BLACK, Color.WHITE, "Display quest details");
					questsDrawnInList++;
					resolvedAdded = true;
					buttons.add(activeButton);
				}

				// buttons to make quest the active one
				final LevelButton buttonToMakeQuestActive = new LevelButton(
						listX + listBorder + listWidth - listItemHeight,
						listY + listBorder + questsDrawnInList * listItemHeight, listItemHeight, listItemHeight,
						"end_turn_button.png", "end_turn_button.png", "", true, true, Color.GRAY, Color.WHITE,
						"Track or stop tracking this quest. You can have multipple active quests.");
				buttonToMakeQuestActive.setClickListener(new ClickListener() {

					@Override
					public void click() {
						if (activeQuests.contains(quest)) {
							activeQuests.remove(quest);
							createButtonsToTrackObjectives();
						} else {
							activeQuests.add(quest);
							createButtonsToTrackObjectives();
						}
					}
				});

				buttons.add(buttonToMakeQuestActive);
				buttonsToMakeQuestAcive.add(buttonToMakeQuestActive);

				// Button w/ the name of the quest -

				final LevelButton buttonToShowQuestDetails = new LevelButton(listX + listBorder,
						listY + listBorder + questsDrawnInList * listItemHeight, listWidth - listItemHeight,
						listItemHeight, "end_turn_button.png", "end_turn_button.png", quest.name, true, true,
						Color.BLACK, Color.WHITE, "Display quest details");
				buttonToShowQuestDetails.setClickListener(new ClickListener() {

					@Override
					public void click() {
						for (LevelButton button : buttonsToDisplayQuest) {
							// change colors to off
							button.buttonColor = Color.BLACK;
							button.setTextColor(Color.WHITE);
						}
						buttonToShowQuestDetails.buttonColor = Color.WHITE;
						buttonToShowQuestDetails.setTextColor(Color.BLACK);
						questToDisplayInJournal = quest;
						generateLinks();
						quest.updatedSinceLastViewed = false;

					}
				});

				if (questToDisplayInJournal == quest) {
					buttonToShowQuestDetails.buttonColor = Color.WHITE;
					buttonToShowQuestDetails.setTextColor(Color.BLACK);
					quest.updatedSinceLastViewed = false;
				}

				// if (quest.turnStarted == Game.level.turn) {
				// buttonToShowQuestDetails.click();
				// }

				if (questToDisplayInJournal == null) {
					buttonToShowQuestDetails.click();
				}

				buttons.add(buttonToShowQuestDetails);
				buttonsToDisplayQuest.add(buttonToShowQuestDetails);
				questsDrawnInList++;

			}
		}

	}

	public void generateLinks() {

		if (questToDisplayInJournal == null)
			return;

		logLinks.clear();

		conversationLinks.clear();

		for (JournalLog log : questToDisplayInJournal.logList) {
			logLinks.addAll(log.links);
		}

		for (ConversationPart conversationPart : questToDisplayInJournal.conversationLog) {
			conversationLinks.addAll(conversationPart.links);
		}
	}

	public void close() {
		showing = false;
	}

	public void drawStaticUI() {

		// links.clear();

		// Black cover
		QuadUtils.drawQuad(Color.BLACK, 0, 0, Game.windowWidth, Game.windowHeight);

		// Content
		float height = 0;
		if (questToDisplayInJournal != null) {

			if (mode == MODE.LOG) {
				for (JournalLog journalLog : questToDisplayInJournal.logList) {
					TextUtils.printTextWithImages(contentX + contentBorder, contentY + contentBorder + height,
							Integer.MAX_VALUE, true, journalLog.links,
							new Object[] { journalLog.getTurnString(), journalLog.getArea(), journalLog.getSquare(),
									TextUtils.NewLine.NEW_LINE, journalLog.object });
					height += journalLog.height + 20;
				}
			} else if (mode == MODE.CONVERSATION) {
				for (ConversationPart conversationPart : questToDisplayInJournal.conversationLog) {
					TextUtils.printTextWithImages(contentX + contentBorder, contentY + contentBorder + height,
							Integer.MAX_VALUE, true, conversationPart.linksForJournal,
							new Object[] { conversationPart.getTurnString(), conversationPart.getArea(),
									conversationPart.getSquare(), TextUtils.NewLine.NEW_LINE,
									conversationPart.text[0] });
					height += conversationPart.height + 20;
				}
			}
		}

		if (buttons.size() == 1) {
			TextUtils.printTextWithImages(0, 0, Integer.MAX_VALUE, true, null, new Object[] { "NO QUESTS" });
		} else {
		}

		// Buttons
		for (Button button : buttons) {
			button.draw();
		}

		// check box && exclamation mark
		int questsDrawnInList = 0;
		boolean activeAdded = false;
		boolean resolvedAdded = false;
		for (final Quest quest : Level.quests) {
			if (quest.started) {

				if (!activeAdded && !quest.resolved) {
					questsDrawnInList++;
					activeAdded = true;
				}

				if (!resolvedAdded && quest.resolved) {
					questsDrawnInList++;
					resolvedAdded = true;
				}

				// Check box
				Texture checkBoxTextureToUse = checkBoxUnchecked;
				if (activeQuests.contains(quest)) {
					checkBoxTextureToUse = checkBoxChecked;
				}

				float checkBoxx1 = listX + listBorder + listWidth - listItemHeight;
				float checkBoxY1 = listY + listBorder + questsDrawnInList * listItemHeight;

				TextureUtils.drawTexture(checkBoxTextureToUse, checkBoxx1, checkBoxY1, checkBoxx1 + listItemHeight,
						checkBoxY1 + listItemHeight);

				// Exclamation
				if (quest.updatedSinceLastViewed) {
					float exclamationX1 = listX + listBorder + listWidth - listItemHeight * 2;
					float exclamationY1 = listY + listBorder + questsDrawnInList * listItemHeight;

					TextureUtils.drawTexture(exclamationTexture, exclamationX1, exclamationY1,
							exclamationX1 + listItemHeight, exclamationY1 + listItemHeight);
				}

				questsDrawnInList++;

			}
		}
	}

	public void drawActiveQuestsObjectiveText() {
		int linesPrinted = 0;
		objectiveLinks.clear();
		for (Quest activeQuest : activeQuests) {
			TextUtils.printTextWithImages(Game.windowWidth - Game.font.getWidth(activeQuest.name) - 202,
					20 + 20 * linesPrinted, Integer.MAX_VALUE, false, null,
					new Object[] { new StringWithColor(activeQuest.name, Color.WHITE) });
			linesPrinted++;
			for (Objective objective : activeQuest.currentObjectives) {
				TextUtils.printTextWithImages(Game.windowWidth - Game.font.getWidth(objective.text) - 202,
						20 + 20 * linesPrinted, Integer.MAX_VALUE, false, objective.links, objective);

				objectiveLinks.add(objective.links.get(0));

				QuadUtils.drawQuad(Color.WHITE, Game.windowWidth - 200, 20 + 20 * linesPrinted, Game.windowWidth - 180,
						20 + 20 * linesPrinted + 20);

				// if (objective.gameObject != null) {
				TextureUtils.drawTexture(objective.texture, Game.windowWidth - 200, 20 + 20 * linesPrinted,
						Game.windowWidth - 180, 20 + 20 * linesPrinted + 20);
				// }

				if (objective.showMarker) {
					TextureUtils.drawTexture(checkBoxChecked, Game.windowWidth - 180, 20 + 20 * linesPrinted,
							Game.windowWidth - 160, 20 + 20 * linesPrinted + 20);
				} else {
					TextureUtils.drawTexture(checkBoxUnchecked, Game.windowWidth - 180, 20 + 20 * linesPrinted,
							Game.windowWidth - 160, 20 + 20 * linesPrinted + 20);
				}
				linesPrinted++;
			}
			linesPrinted++;
		}
	}

	public static void drawQuestsMarkersForOnScreenObjectives() {

		for (Quest activeQuest : activeQuests) {
			for (Objective currentObjective : activeQuest.currentObjectives) {

				if (currentObjective.gameObject != null && currentObjective.showMarker) {
					if (currentObjective.gameObject.squareGameObjectIsOn != null) {
						currentObjective.gameObject.squareGameObjectIsOn.drawObjective(currentObjective);
					} else if (currentObjective.gameObject.inventoryThatHoldsThisObject.parent instanceof GameObject
							&& ((GameObject) currentObjective.gameObject.inventoryThatHoldsThisObject.parent).squareGameObjectIsOn != null) {
						((GameObject) currentObjective.gameObject.inventoryThatHoldsThisObject.parent).squareGameObjectIsOn
								.drawObjective(currentObjective);

					}
				} else if (currentObjective.square != null) {
					currentObjective.square.drawObjective(currentObjective);
				}

			}
		}
	}

	public static void drawQuestMarkersForOffScreenObjectives() {
		for (Quest activeQuest : activeQuests) {
			int x1 = (int) Game.halfWindowWidth;
			int y1 = (int) Game.halfWindowHeight;

			int x2 = Integer.MAX_VALUE;
			int y2 = Integer.MAX_VALUE;

			Square targetSquare = null;

			for (Objective currentObjective : activeQuest.currentObjectives) {

				if ((currentObjective.gameObject == null || currentObjective.gameObject.squareGameObjectIsOn == null)
						&& currentObjective.square == null && currentObjective.showMarker == false) {
					return;
				}

				x2 = Integer.MAX_VALUE;
				y2 = Integer.MAX_VALUE;

				if (currentObjective.gameObject != null && currentObjective.gameObject.squareGameObjectIsOn != null) {

					targetSquare = currentObjective.gameObject.squareGameObjectIsOn;

				} else if (currentObjective.square != null) {

					targetSquare = currentObjective.square;

				}

				if (targetSquare.onScreen())
					continue;

				float squareX = (targetSquare.xInGridPixels);
				float squareY = (targetSquare.yInGridPixels);
				x2 = (int) ((Game.windowWidth / 2) + (Game.zoom
						* (squareX - Game.windowWidth / 2 + Game.getDragXWithOffset() + Game.HALF_SQUARE_WIDTH)));
				y2 = (int) ((Game.windowHeight / 2) + (Game.zoom
						* (squareY - Game.windowHeight / 2 + Game.getDragYWithOffset() + Game.HALF_SQUARE_HEIGHT)));

				if (x2 != Integer.MAX_VALUE) {

					String distanceString = Game.level.player.straightLineDistanceTo(targetSquare) + "m";
					float distanceStringWidth = Game.font.getWidth(distanceString);
					// LineUtils.drawLine(Color.WHITE, x1, y1, x2, y2, 5);

					// Get intersection of line and edge of screen

					// Right edge
					int x3 = (int) Game.windowWidth;
					int x4 = (int) Game.windowWidth;
					int y3 = 0;
					int y4 = (int) Game.windowHeight;

					int[] intersect = lineIntersect(x1, y1, x2, y2, x3, y3, x4, y4);

					if (intersect != null) {

						float drawY1 = intersect[1] - 10;
						float drawY2 = intersect[1] + 10;
						if (drawY1 < 0) {
							drawY1 = 0;
							drawY2 = 20;
						} else if (drawY2 > Game.windowHeight) {
							drawY1 = Game.windowHeight - 20;
							drawY2 = Game.windowHeight;
						}

						QuadUtils.drawQuad(Color.WHITE, intersect[0] - 20, drawY1, intersect[0], drawY2);
						TextureUtils.drawTexture(currentObjective.gameObject.imageTexture, intersect[0] - 20, drawY1,
								intersect[0], drawY2);

						TextUtils.printTextWithImages(intersect[0] - 20 - distanceStringWidth - 4, drawY1,
								Integer.MAX_VALUE, false, null, distanceString);
						continue;
					}

					// Left edge
					x3 = 0;
					x4 = 0;
					y3 = 0;
					y4 = (int) Game.windowHeight;

					intersect = lineIntersect(x1, y1, x2, y2, x3, y3, x4, y4);

					if (intersect != null) {

						float drawY1 = intersect[1] - 10;
						float drawY2 = intersect[1] + 10;
						if (drawY1 < 0) {
							drawY1 = 0;
							drawY2 = 20;
						} else if (drawY2 > Game.windowHeight) {
							drawY1 = Game.windowHeight - 20;
							drawY2 = Game.windowHeight;
						}

						QuadUtils.drawQuad(Color.WHITE, intersect[0], drawY1, intersect[0] + 20, drawY2);
						TextureUtils.drawTexture(currentObjective.gameObject.imageTexture, intersect[0], drawY1,
								intersect[0] + 20, drawY2);

						TextUtils.printTextWithImages(intersect[0] + 20 + 4, drawY1, Integer.MAX_VALUE, false, null,
								distanceString);
						continue;
					}

					// Top edge
					x3 = 0;
					x4 = (int) Game.windowWidth;
					y3 = 0;
					y4 = 0;

					intersect = lineIntersect(x1, y1, x2, y2, x3, y3, x4, y4);

					if (intersect != null) {

						float drawX1 = intersect[0] - 10;
						float drawX2 = intersect[0] + 10;
						if (drawX1 < 0) {
							drawX1 = 0;
							drawX2 = 20;
						} else if (drawX2 > Game.windowWidth) {
							drawX1 = Game.windowWidth - 20;
							drawX2 = Game.windowWidth;
						}

						QuadUtils.drawQuad(Color.WHITE, drawX1, intersect[1], drawX2, intersect[1] + 20);
						TextureUtils.drawTexture(currentObjective.gameObject.imageTexture, drawX1, intersect[1], drawX2,
								intersect[1] + 20);
						TextUtils.printTextWithImages(drawX1, intersect[1] + 20 + 4, Integer.MAX_VALUE, false, null,
								distanceString);

						continue;
					}

					// Bottom edge
					x3 = 0;
					x4 = (int) Game.windowWidth;
					y3 = (int) Game.windowHeight;
					y4 = (int) Game.windowHeight;

					intersect = lineIntersect(x1, y1, x2, y2, x3, y3, x4, y4);

					if (intersect != null) {

						float drawX1 = intersect[0] - 10;
						float drawX2 = intersect[0] + 10;
						if (drawX1 < 0) {
							drawX1 = 0;
							drawX2 = 20;
						} else if (drawX2 > Game.windowWidth) {
							drawX1 = Game.windowWidth - 20;
							drawX2 = Game.windowWidth;
						}

						QuadUtils.drawQuad(Color.WHITE, drawX1, intersect[1] - 20, drawX2, intersect[1]);
						TextureUtils.drawTexture(currentObjective.gameObject.imageTexture, drawX1, intersect[1] - 20,
								drawX2, intersect[1]);
						TextUtils.printTextWithImages(drawX1, intersect[1] - 20 - 24, Integer.MAX_VALUE, false, null,
								distanceString);

						continue;
					}

				}

			}
		}
	}

	public static void createButtonsToTrackObjectives() {
		Game.level.buttons.removeAll(buttonsToTrackObjectives);
		buttonsToTrackObjectives.clear();

		int linesPrinted = 0;
		for (Quest activeQuest : activeQuests) {
			linesPrinted++;
			for (final Objective currentObjective : activeQuest.currentObjectives) {
				final LevelButton buttonToTrackObjective = new LevelButton(Game.windowWidth - 180,
						20 + 20 * linesPrinted, 20, 20, "end_turn_button.png", "end_turn_button.png", "", true, true,
						Color.GRAY, Color.WHITE, "Turn on/off map marker for this objective");
				buttonToTrackObjective.setClickListener(new ClickListener() {

					@Override
					public void click() {
						currentObjective.showMarker = !currentObjective.showMarker;
					}
				});

				Game.level.buttons.add(buttonToTrackObjective);
				buttonsToTrackObjectives.add(buttonToTrackObjective);
				linesPrinted++;
			}
			linesPrinted++;
		}
	}

	public static int[] lineIntersect(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
		double denom = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);
		if (denom == 0.0) { // Lines are parallel.
			return null;
		}
		double ua = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / denom;
		double ub = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / denom;
		if (ua >= 0.0f && ua <= 1.0f && ub >= 0.0f && ub <= 1.0f) {

			int[] result = new int[2];
			result[0] = (int) (x1 + ua * (x2 - x1));
			result[1] = (int) (y1 + ua * (y2 - y1));
			return result;
		}

		return null;
	}

	@Override
	public void scroll(float dragX, float dragY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drag(float dragX, float dragY) {
		// TODO Auto-generated method stub

	}

	@Override
	public int compare(Quest quest1, Quest quest2) {

		if (quest1.resolved && !quest2.resolved)
			return +1;

		if (!quest1.resolved && quest2.resolved)
			return -1;

		if (activeQuests.contains(quest1) && !activeQuests.contains(quest2))
			return -1;

		if (!activeQuests.contains(quest1) && activeQuests.contains(quest2))
			return +1;

		return quest2.turnUpdated - quest1.turnUpdated;
	}

}
