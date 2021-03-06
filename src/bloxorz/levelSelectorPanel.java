package bloxorz;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class levelSelectorPanel extends javax.swing.JPanel {

	String[][] mapArray;

	int playerPosX;
	int playerPosY;

	int ANGLE = 30;
	int SQUARE_DIMENSION = 30;
	Polygon player;
	int xTopLeft, xMiddle, xTopRight, xBottom, yTopLeft, yMiddle, yTopRight, yBottom;

	public levelSelectorPanel() {
		initComponents();
		mapArray = new String[][]{{""}};
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// draws the map
		for (int r = 0; r < mapArray.length; r++) {
			for (int c = 0; c < mapArray[0].length; c++) {
				getColor(r, c, g);
				Polygon p = getPolygon(r, c);
				// draws filled polygon of the right color
				g.fillPolygon(p);

				// adds black border around it
				getBorderColor(r, c, g);
				g.drawPolygon(p);
			}
		}

		// adds x and y labels for debug
//		g.drawString("X", 80, 80);
//		g.drawString("Y", 80, 240);
		// draws the player
		getPlayerPolygon();
		g.setColor(Color.GREEN);
		g.fillPolygon(player);
		// draws black outline around the player
		g.setColor(Color.BLACK);
		g.drawPolygon(player);

		// draws edges of the player
		getInnerLinesOfPlayer(playerPosX, playerPosY, "V");

		g.drawLine(xTopLeft, yTopLeft, xMiddle, yMiddle);
		g.drawLine(xTopRight, yTopRight, xMiddle, yMiddle);
		g.drawLine(xBottom, yBottom, xMiddle, yMiddle);
	}

	private void getColor(int r, int c, Graphics g) {
		switch (mapArray[r][c]) {
			case "H":
				g.setColor(Color.BLACK);
				break;
			case " ":
				g.setColor(this.getBackground());
				break;
			default:
				g.setColor(Color.GRAY);
		}
	}

	private void getBorderColor(int r, int c, Graphics g) {
		switch (mapArray[r][c]) {
			case " ":
				g.setColor(this.getBackground());
				break;
			default:
				g.setColor(Color.BLACK);
				break;
		}
	}

	private Polygon getPolygon(int r, int c) {
		/**
		 * This method returns a Polygon object that contains the required
		 * points based on the row and column within the 2D array.
		 */
		return new Polygon(getXPoints(r, c), getYPoints(r, c), 4);
	}

	private int[] getXPoints(int r, int c) {
		double cosValue = Math.cos(Math.toRadians(ANGLE));
		int xStartPos = 50 + (int) ((r + c) * SQUARE_DIMENSION * cosValue);
		return new int[]{xStartPos, xStartPos + (int) (SQUARE_DIMENSION * cosValue), xStartPos + 2 * (int) (SQUARE_DIMENSION * cosValue), xStartPos + (int) (SQUARE_DIMENSION * cosValue)};
	}

	private int[] getYPoints(int r, int c) {
		int yStartPos = 0; // only here to initialize with a value
		double sinValue = Math.sin(Math.toRadians(ANGLE));
		if (r + c == 0) {
			yStartPos = this.getHeight() / 2 - (int) ((c - r) * SQUARE_DIMENSION * sinValue);
		} else {
			yStartPos = this.getHeight() / 2 - (int) ((c - r) * SQUARE_DIMENSION * sinValue) - (r - c);
		}

		return new int[]{yStartPos, yStartPos + (int) (SQUARE_DIMENSION * sinValue), yStartPos, yStartPos - (int) (SQUARE_DIMENSION * sinValue)};
	}

	private void getPlayerPolygon() {
		int x = playerPosX;
		int y = playerPosY;

		int[] xPoints = getPlayerXPoints(x, y, "V");
		int[] yPoints = getPlayerYPoints(x, y, "V");
		player = new Polygon(xPoints, yPoints, 6);
	}

	private int[] getPlayerXPoints(int x, int y, String direction) {
		double cosValue = Math.cos(Math.toRadians(ANGLE));
		int xStartPos = 50 + (int) ((x + y) * SQUARE_DIMENSION * cosValue);
		switch (direction) {
			case "V":
				return new int[]{xStartPos, xStartPos, xStartPos + (int) (SQUARE_DIMENSION * cosValue), xStartPos + 2 * (int) (SQUARE_DIMENSION * cosValue), xStartPos + 2 * (int) (SQUARE_DIMENSION * cosValue), xStartPos + (int) (SQUARE_DIMENSION * cosValue)};
			case "F":
				return new int[]{xStartPos, xStartPos, xStartPos + (int) (SQUARE_DIMENSION * cosValue), xStartPos + 3 * (int) (SQUARE_DIMENSION * cosValue), xStartPos + 3 * (int) (SQUARE_DIMENSION * cosValue), xStartPos + 2 * (int) (SQUARE_DIMENSION * cosValue)};
			case "B":
				// has to recalculate xStartPos since it usually starts on the bottom left corner
				xStartPos += (int) (SQUARE_DIMENSION * cosValue);
				return new int[]{xStartPos, xStartPos + (int) (SQUARE_DIMENSION * cosValue), xStartPos + (int) (SQUARE_DIMENSION * cosValue), xStartPos - (int) (SQUARE_DIMENSION * cosValue), xStartPos - 2 * (int) (SQUARE_DIMENSION * cosValue), xStartPos - 2 * (int) (SQUARE_DIMENSION * cosValue)};
			case "R":
				return new int[]{xStartPos, xStartPos + (int) (SQUARE_DIMENSION * cosValue), xStartPos + 3 * (int) (SQUARE_DIMENSION * cosValue), xStartPos + 3 * (int) (SQUARE_DIMENSION * cosValue), xStartPos + 2 * (int) (SQUARE_DIMENSION * cosValue), xStartPos, xStartPos - (int) (SQUARE_DIMENSION * cosValue)};
			case "L":
				xStartPos -= (int) (SQUARE_DIMENSION * cosValue);
				return new int[]{xStartPos, xStartPos + (int) (SQUARE_DIMENSION * cosValue), xStartPos + 3 * (int) (SQUARE_DIMENSION * cosValue), xStartPos + 3 * (int) (SQUARE_DIMENSION * cosValue), xStartPos + 2 * (int) (SQUARE_DIMENSION * cosValue), xStartPos, xStartPos - (int) (SQUARE_DIMENSION * cosValue)};
			default:
				return null;
		}
	}

	private int[] getPlayerYPoints(int x, int y, String direction) {
		double sinValue = Math.sin(Math.toRadians(ANGLE));
		int yStartPos;
		if (x + y == 0) {
			yStartPos = this.getHeight() / 2 - (int) ((x - y) * SQUARE_DIMENSION * sinValue);
		} else {
			yStartPos = this.getHeight() / 2 - (int) ((x - y) * SQUARE_DIMENSION * sinValue) - (y - x);
		}

		switch (direction) {
			case "V":
				return new int[]{yStartPos, yStartPos - 2 * SQUARE_DIMENSION, yStartPos - 2 * SQUARE_DIMENSION - (int) (SQUARE_DIMENSION * sinValue), yStartPos - 2 * SQUARE_DIMENSION, yStartPos, yStartPos + (int) (SQUARE_DIMENSION * sinValue)};
			case "F":
				return new int[]{yStartPos, yStartPos - SQUARE_DIMENSION, yStartPos - SQUARE_DIMENSION - (int) (SQUARE_DIMENSION * sinValue), yStartPos - SQUARE_DIMENSION + (int) (SQUARE_DIMENSION * sinValue), yStartPos + (int) (SQUARE_DIMENSION * sinValue), yStartPos + 2 * (int) (SQUARE_DIMENSION * sinValue)};
			case "B":
				// has to recalculate yStartPos since it usually is the bottom left corner 
				yStartPos += (int) (SQUARE_DIMENSION * sinValue);
				return new int[]{yStartPos, yStartPos - (int) (SQUARE_DIMENSION * sinValue), yStartPos - SQUARE_DIMENSION - (int) (SQUARE_DIMENSION * sinValue), yStartPos - SQUARE_DIMENSION - 3 * (int) (SQUARE_DIMENSION * sinValue), yStartPos - SQUARE_DIMENSION - 2 * (int) (SQUARE_DIMENSION * sinValue), yStartPos - 2 * (int) (SQUARE_DIMENSION * sinValue)};
			case "R":
				return new int[]{yStartPos, yStartPos + (int) (SQUARE_DIMENSION * sinValue), yStartPos - (int) (SQUARE_DIMENSION * sinValue), yStartPos - SQUARE_DIMENSION - (int) (SQUARE_DIMENSION * sinValue), yStartPos - SQUARE_DIMENSION - 2 * (int) (SQUARE_DIMENSION * sinValue), yStartPos - SQUARE_DIMENSION};
			case "L":
				yStartPos += (int) (SQUARE_DIMENSION * sinValue);
				return new int[]{yStartPos, yStartPos + (int) (SQUARE_DIMENSION * sinValue), yStartPos - (int) (SQUARE_DIMENSION * sinValue), yStartPos - SQUARE_DIMENSION - (int) (SQUARE_DIMENSION * sinValue), yStartPos - SQUARE_DIMENSION - 2 * (int) (SQUARE_DIMENSION * sinValue), yStartPos - SQUARE_DIMENSION};
			default:
				return null;
		}
	}

	private void getInnerLinesOfPlayer(int x, int y, String direction) {
		// position of the bottom left corner
		double cosValue = Math.cos(Math.toRadians(ANGLE));
		double sinValue = Math.sin(Math.toRadians(ANGLE));
		int xStartPos = 50 + (int) ((x + y) * SQUARE_DIMENSION * cosValue);
		int yStartPos;
		if (x + y == 0) {
			yStartPos = this.getHeight() / 2 - (int) ((x - y) * SQUARE_DIMENSION * sinValue);
		} else {
			yStartPos = this.getHeight() / 2 - (int) ((x - y) * SQUARE_DIMENSION * sinValue) - (y - x);
		}

		switch (direction) {
			case "V":
				xTopLeft = xStartPos;
				yTopLeft = yStartPos - 2 * SQUARE_DIMENSION;

				xMiddle = xStartPos + (int) (SQUARE_DIMENSION * cosValue);
				yMiddle = yStartPos - (int) (SQUARE_DIMENSION * sinValue) - SQUARE_DIMENSION - 3;

				xTopRight = xStartPos + 2 * (int) (SQUARE_DIMENSION * cosValue);
				yTopRight = yStartPos - 2 * SQUARE_DIMENSION;

				xBottom = xStartPos + (int) (SQUARE_DIMENSION * cosValue);
				yBottom = yStartPos + (int) (SQUARE_DIMENSION * sinValue);
				break;
			case "F":
				xTopLeft = xStartPos;
				yTopLeft = yStartPos - SQUARE_DIMENSION;

				xMiddle = xStartPos + 2 * (int) (SQUARE_DIMENSION * cosValue);
				yMiddle = yStartPos - 2;

				xTopRight = xStartPos + 3 * (int) (SQUARE_DIMENSION * cosValue);
				yTopRight = yStartPos - (int) (SQUARE_DIMENSION * sinValue) - 2;

				xBottom = xStartPos + 2 * (int) (SQUARE_DIMENSION * cosValue);
				yBottom = yStartPos + SQUARE_DIMENSION - 3;
				break;
			case "B":
				xStartPos += (int) (SQUARE_DIMENSION * cosValue);
				yStartPos += (int) (SQUARE_DIMENSION * sinValue);

				xTopLeft = xStartPos - 2 * (int) (SQUARE_DIMENSION * cosValue);
				yTopLeft = yStartPos - SQUARE_DIMENSION - 2 * (int) (SQUARE_DIMENSION * sinValue);

				xMiddle = xStartPos;
				yMiddle = yStartPos - SQUARE_DIMENSION;

				xBottom = xStartPos;
				yBottom = yStartPos;

				xTopRight = xStartPos + (int) (SQUARE_DIMENSION * cosValue);
				yTopRight = yStartPos - SQUARE_DIMENSION - (int) (SQUARE_DIMENSION * sinValue);
				break;
			case "R":
				xTopLeft = xStartPos;
				yTopLeft = yStartPos - SQUARE_DIMENSION;

				xMiddle = xStartPos + (int) (SQUARE_DIMENSION * cosValue);
				yMiddle = yStartPos - SQUARE_DIMENSION + (int) (SQUARE_DIMENSION * sinValue);

				xBottom = xStartPos + (int) (SQUARE_DIMENSION * cosValue);
				yBottom = yStartPos + (int) (SQUARE_DIMENSION * sinValue);

				xTopRight = xStartPos + 3 * (int) (SQUARE_DIMENSION * cosValue);
				yTopRight = yStartPos - SQUARE_DIMENSION - (int) (SQUARE_DIMENSION * sinValue);
				break;
			case "L":
				xStartPos -= (int) (SQUARE_DIMENSION * cosValue);
				yStartPos += (int) (SQUARE_DIMENSION * sinValue);

				xTopLeft = xStartPos;
				yTopLeft = yStartPos - SQUARE_DIMENSION;

				xMiddle = xStartPos + (int) (SQUARE_DIMENSION * cosValue);
				yMiddle = yStartPos - SQUARE_DIMENSION + (int) (SQUARE_DIMENSION * sinValue);

				xBottom = xStartPos + (int) (SQUARE_DIMENSION * cosValue);
				yBottom = yStartPos + (int) (SQUARE_DIMENSION * sinValue);

				xTopRight = xStartPos + 3 * (int) (SQUARE_DIMENSION * cosValue);
				yTopRight = yStartPos - SQUARE_DIMENSION - (int) (SQUARE_DIMENSION * sinValue);
				break;
		}
	}

	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

	public void setMapArray(String[][] mapArray) {
		this.mapArray = mapArray;
		setPlayerPoints();
		repaint();
	}

	private void setPlayerPoints() {
		for (int r = 0; r < mapArray.length; r++) {
			for (int c = 0; c < mapArray[0].length; c++) {
				if ("P".equals(mapArray[r][c])) {
					playerPosX = c;
					playerPosY = r;
					return;
				}
			}
		}
	}
}
