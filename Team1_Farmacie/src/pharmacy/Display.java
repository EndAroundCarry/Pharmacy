package pharmacy;

import java.util.List;

public class Display {
	private Configuration configuration;
	private StringBuilder builder = new StringBuilder();
	private List<Drawer> drawers;
	private int cellWidth = 15;

	public Display(Configuration configuration, List<Drawer> drawers) {
		this.configuration = configuration;
		this.drawers = drawers;
	}

	public void createHeader() {
		StringBuilder result = new StringBuilder();
		appendRowDelimitator(result);
		appendFirstCell(result, "");
		for (int i = 1; i <= configuration.getColumn(); i++) {
			appendNormalCell(result, i + "");
		}
		appendRowDelimitator(result);
		this.builder.append(result);
	}

	public void setCellWidth(int cellWidth) {
		this.cellWidth = cellWidth;
	}

	private void appendNormalCell(StringBuilder sb, String content) {
		sb.append(addSpaces(content.length()) + content + "|");
	}

	private void appendFirstCell(StringBuilder sb, String content) {
		sb.append(System.lineSeparator() + "|");
		sb.append(addSpaces(content.length()) + content);
		sb.append("|");
	}

	private String addSpaces(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < cellWidth - length; i++) {
			sb.append(" ");
		}
		return sb.toString();
	}

	public void show() {
		System.out.println(builder.toString());
	}

	public void createRow(int drawersMaxSize, String drawerLetter) {
		StringBuilder result = new StringBuilder();
		if (drawersMaxSize == 0) {
			buildEmptyRow(drawerLetter, result);
		} else {
			buildRow(drawersMaxSize, drawerLetter, result);
		}
		appendRowDelimitator(result);
		this.builder.append(result);
	}

	private void appendRowDelimitator(StringBuilder result) {
		result.append(System.lineSeparator());
		for (int i = 0; i <= (configuration.getColumn() * cellWidth) + cellWidth + configuration.getColumn() + 1; i++) {
			result.append("-");
		}
	}

	private void buildRow(int drawersMaxSize, String drawerLetter, StringBuilder result) {
		for (int rowNumber = 0; rowNumber < drawersMaxSize; rowNumber++) {
			if (rowNumber == 0) {
				appendFirstCell(result, drawerLetter);
			} else {
				appendFirstCell(result, "");
			}
			for (Drawer drawer : drawers) {
				if (drawer.getName().startsWith(drawerLetter)) {
					try {
						Medicine medicine = drawer.getMedicinesInDrawer().get(rowNumber);
						appendNormalCell(result, medicine.getBrand());
					} catch (Exception e) {
						appendNormalCell(result, "");
					}
				}
			}
		}
	}

	private void buildEmptyRow(String drawerLetter, StringBuilder sb) {
		appendFirstCell(sb, drawerLetter);
		for (int i = 0; i < configuration.getColumn(); i++) {
			appendNormalCell(sb, "");
		}
	}

	public int getDrawersMaxSize(String drawerLetter) {
		int maxSize = 0;
		for (Drawer drawer : drawers) {
			if (drawer.getName().startsWith(drawerLetter)) {
				if (drawer.getMedicinesInDrawer().size() > maxSize) {
					maxSize = drawer.getMedicinesInDrawer().size();
				}
			}
		}
		return maxSize;
	}

	public void createRows() {
		for (char letter = 'A'; letter < 'A' + configuration.getLines(); letter++) {
			createRow(getDrawersMaxSize(String.valueOf(letter)), String.valueOf(letter));
		}
	}
}
