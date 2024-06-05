package org.example.gui.mapFrme;

import org.example.gui.MusicBandWindow;
import org.example.recources.MusicBand;
import org.example.system.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import static java.lang.Math.floor;
import static java.lang.Math.min;

public class Canvas extends JPanel {
    private Point viewPosition = new Point(0, 0);
    private double zoomFactor = 1.0;
    private Point mouseCoords = new Point(0, 0);
    private java.util.List<Point> objects = new ArrayList<>();
    private HashMap<Integer, MusicBand> persons = new HashMap<>();
    private LinkedList<MusicBand> new_persons;
    private HashMap<MusicBand, Integer> animationMap = new HashMap<MusicBand, Integer>();
    private int smeshenie_X = 0;
    private int smeshenie_Y = 0;
    private Point lastDragPoint = new Point(0, 0);
    private Timer animationTimer;
    private float alpha = 0f;


    public Canvas() {
        MouseAdapter mouseAdapter = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                lastDragPoint = new Point(mouseCoords.x, mouseCoords.y);
                lastDragPoint = e.getPoint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - lastDragPoint.x;
                int dy = e.getY() - lastDragPoint.y;

                smeshenie_X += dx;
                smeshenie_Y += dy;

                // Обновляем последнюю точку курсора
                lastDragPoint = e.getPoint();

                // Применяем смещение к позиции просмотра канваса
                viewPosition.translate(dx, dy);

                // Вызываем перерисовку канваса
                repaint();
            }


            @Override
            public void mouseMoved(MouseEvent e) {
                mouseCoords = e.getPoint();
                repaint();
            }

        };

        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);

        addMouseWheelListener(e -> {
            double delta = 0.05f * e.getPreciseWheelRotation();
            zoomFactor += -delta;
            zoomFactor = Math.max(0.1, zoomFactor); // limit zoom out
            repaint();
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Keyboard input for panning
        setFocusable(true);
        requestFocusInWindow();

    }


    @Override
    protected synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        ArrayList<MusicBand> musicBandCopy = (ArrayList<MusicBand>) MusicBandWindow.getMusicBandArrayList().clone();
        for (MusicBand musicBand : musicBandCopy) {
            if (!persons.containsKey((int) musicBand.getId())) {

                Float x = musicBand.getCoordinates().getX();
                float y = musicBand.getCoordinates().getY();
                g.setColor(Color.RED);


                g.setColor(new Color((50 + musicBand.getOwner_id() * 100) % 255, (50 + musicBand.getOwner_id() * 100) % 255, musicBand.getOwner_id() * 100 % 255));
                if (Client.getUserId()  == musicBand.getOwner_id()){
                    g.setColor(new Color(49, 70, 142));
                }
                if (animationMap.containsKey(musicBand)) {
                    if (animationMap.get(musicBand) < min((musicBand.getLabel().getSales() + 5) * 100 , 25 * 100)) {
                        animationMap.put(musicBand, animationMap.get(musicBand) + 5);
                    } else persons.put((int) musicBand.getId(), musicBand);
                } else animationMap.put(musicBand, 1);
                if (musicBand.getLabel().getSales() > 20) {
                    g.fillOval((int) (x + smeshenie_X), (int) (y + smeshenie_Y), animationMap.get(musicBand) / 100, animationMap.get(musicBand) / 100);
                } else {
                    g.fillOval((int) (x + smeshenie_X), (int) (y + smeshenie_Y), animationMap.get(musicBand) / 100, animationMap.get(musicBand) / 100);
                }
            } else {
                Float x = musicBand.getCoordinates().getX();
                float y = (float) musicBand.getCoordinates().getY();

                g.setColor(Color.RED);
                g.setColor(new Color((50 + musicBand.getOwner_id() * 100) % 255, (50 + musicBand.getOwner_id() * 100) % 255, musicBand.getOwner_id() * 100 % 255));
                if (Client.getUserId()  == musicBand.getOwner_id()){
                    g.setColor(new Color(49, 70, 142));
                }
                if (musicBand.getCoordinates().getX() > 20) {
                    g.fillOval((int) (x + smeshenie_X), (int) (y + smeshenie_Y), 25, 25);
                } else {
                    g.fillOval((int) (x + smeshenie_X), (int) (y + smeshenie_Y), (int)musicBand.getCoordinates().getX() + 5, (int)musicBand.getCoordinates().getX() + 5);
                }
            }
        }
        g2d.setColor(Color.BLACK);
        g2d.drawString("Coords: (" + (getCordX()) + ", " + (getCordY()) + ")", mouseCoords.x, mouseCoords.y);
        repaint();

    }




    public int getCordX() {
        return (int) ((mouseCoords.x - viewPosition.x) / zoomFactor);
    }

    public int getCordY() {
        return (int) ((mouseCoords.y - viewPosition.y) / zoomFactor);
    }
}



